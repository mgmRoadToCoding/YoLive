package cn.source.system.websocket;

import cn.source.common.core.redis.RedisCache;
import cn.source.common.core.domain.model.LoginUser;
import cn.source.framework.web.service.TokenService;
import cn.source.system.domain.ImChatMessage;
import cn.source.system.domain.ImChatSession;
import cn.source.system.mapper.ImChatMessageMapper;
import cn.source.system.mapper.ImChatSessionMapper;
import cn.source.common.utils.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
/**
 * websocket 消息处理dExecutorService;

 *
 * @author ruoyi
 */
@Component
@ServerEndpoint("/websocket/message")
public class WebSocketServer {
    /**
     * WebSocketServer 日志控制器
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 注入消息redis
     */
    private static RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 注入AI回复服务
     */
    private static AiReplyService aiReplyService;

    @Autowired
    public void setAiReplyService(AiReplyService aiReplyService) {
        this.aiReplyService = aiReplyService;
    }

    /**
     * 注入TokenService
     */
    private static TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        WebSocketServer.tokenService = tokenService;
    }

    /**
     * 注入聊天消息Mapper
     */
    private static ImChatMessageMapper imChatMessageMapper;

    @Autowired
    public void setImChatMessageMapper(ImChatMessageMapper imChatMessageMapper) {
        WebSocketServer.imChatMessageMapper = imChatMessageMapper;
    }

    /**
     * 注入会话Mapper
     */
    private static ImChatSessionMapper imChatSessionMapper;

    @Autowired
    public void setImChatSessionMapper(ImChatSessionMapper imChatSessionMapper) {
        WebSocketServer.imChatSessionMapper = imChatSessionMapper;
    }

    /**
     * 默认最多允许同时在线人数1000
     */
    public static int socketMaxOnlineCount = 1000;

    private static Semaphore socketSemaphore = new Semaphore(socketMaxOnlineCount);

    /**
     * 异步回复执行器
     */
    private static final ScheduledExecutorService replyExecutor = Executors.newScheduledThreadPool(2);

    /**
     * 会话绑定的登录用户信息
     */
    private static final Map<String, LoginUser> SESSION_USERS = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) throws Exception {
        boolean semaphoreFlag = false;
        // 尝试获取信号量
        semaphoreFlag = SemaphoreUtils.tryAcquire(socketSemaphore);
        if (!semaphoreFlag) {
            // 未获取到信号量
            LOGGER.error("\n 当前在线人数超过限制数- {}", socketMaxOnlineCount);
            WebSocketUsers.sendMessageToUserByText(session, "当前在线人数超过限制数：" + socketMaxOnlineCount);
            session.close();
        } else {
            // 连接前进行token校验（从query参数获取）
            String rawToken = null;
            try {
                Map<String, List<String>> params = session.getRequestParameterMap();
                List<String> tokens = params == null ? null : params.get("token");
                if (tokens != null && !tokens.isEmpty()) {
                    rawToken = tokens.get(0);
                }
            } catch (Exception e) {
                LOGGER.warn("[AI客服WS] 读取连接参数失败", e);
            }

            if (rawToken == null || !tokenService.isTokenValid(rawToken)) {
                LOGGER.warn("[AI客服WS] token无效或过期，拒绝连接: sessionId={}", session.getId());
                WebSocketUsers.sendMessageToUserByText(session, "连接被拒绝：token无效或已过期");
                session.close();
                SemaphoreUtils.release(socketSemaphore);
                return;
            }

            LoginUser loginUser = tokenService.getLoginUser(rawToken);
            if (loginUser != null) {
                SESSION_USERS.put(session.getId(), loginUser);
            }
            // 添加用户
            WebSocketUsers.put(session.getId(), session);
            LOGGER.info("\n 建立连接 - {}", session);
            LOGGER.info("\n 当前人数 - {}", WebSocketUsers.getUsers().size());
        }
    }

    /**
     * 根据用户ID获取其所有活跃的 WebSocket 会话。
     * 用于管理端将消息转发给对应在线用户。
     */
    public static List<Session> getSessionsByUserId(Long userId) {
        List<Session> sessions = new ArrayList<>();
        if (userId == null) {
            return sessions;
        }
        try {
            for (Map.Entry<String, LoginUser> entry : SESSION_USERS.entrySet()) {
                LoginUser lu = entry.getValue();
                if (lu != null && Objects.equals(lu.getUserId(), userId)) {
                    Session s = WebSocketUsers.get(entry.getKey());
                    if (s != null) {
                        sessions.add(s);
                    }
                }
            }
        } catch (Exception ignore) {}
        return sessions;
    }

    /**
     * 连接关闭时处理
     */
    @OnClose
    public void onClose(Session session) {
        LOGGER.info("\n 关闭连接 - {}", session);
        // 移除用户
        WebSocketUsers.remove(session.getId());
        SESSION_USERS.remove(session.getId());
        // 获取到信号量则需释放
        SemaphoreUtils.release(socketSemaphore);
    }

    /**
     * 抛出异常时处理
     */
    @OnError
    public void onError(Session session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            // 关闭连接
            session.close();
        }
        String sessionId = session.getId();
        LOGGER.info("\n 连接异常 - {}", sessionId);
        LOGGER.info("\n 异常信息 - {}", exception);
        // 移出用户
        WebSocketUsers.remove(sessionId);
        SESSION_USERS.remove(sessionId);
        // 获取到信号量则需释放
        SemaphoreUtils.release(socketSemaphore);
    }

    /**
     * 服务器接收到客户端消息时调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // 打印收到的消息
        try {
            LOGGER.info("[AI客服WS] 收到消息：sessionId={}, message={}", session.getId(), message);
        } catch (Exception e) {
            LOGGER.warn("[AI客服WS] 记录消息失败", e);
        }

        // 解析客户端消息中的会话ID与文本内容（JSON优先，纯文本兼容）
        String chatSessionId = null;
        String contentText = message;
        try {
            if (message != null && message.trim().startsWith("{")) {
                JSONObject json = JSON.parseObject(message);
                chatSessionId = json.getString("sessionId");
                String c = json.getString("content");
                if (c != null) {
                    contentText = c;
                }
            }
        } catch (Exception ignore) {
        }
        if (chatSessionId == null) {
            // 兼容旧逻辑：未传会话ID时使用 WebSocket 连接ID
            chatSessionId = session.getId();
        }

        // 根据 sessionId 从数据库 im_chat_session 获取会话类型（0=ai，1=human）
        boolean isAi = true; // 当无法从DB获取时，保持AI默认行为
        Long sessionPk = null;
        try {
            try {
                sessionPk = Long.parseLong(chatSessionId);
            } catch (NumberFormatException nfe) {
                sessionPk = null; // 非数值型ID，保留默认 isAi=true
            }
            if (sessionPk != null && imChatSessionMapper != null) {
                ImChatSession chatSession = imChatSessionMapper.selectImChatSessionById(sessionPk);
                if (chatSession != null && chatSession.getType() != null) {
                    Integer typeVal = chatSession.getType();
                    // 0=ai, 1=human(人工)
                    isAi = (typeVal == 0);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("[AI客服WS] 查询会话类型失败，按AI默认处理: sessionId={}", chatSessionId, e);
        }

        // 可选：仍保留ticket缓存逻辑（>80长度）
        if (contentText != null && contentText.length() > 80) {
            String ticketCacheKey = DateUtils.getDate() + ":" + contentText;
            redisCache.setCacheObject(ticketCacheKey, session.getId(), 30, TimeUnit.MINUTES);
        }

        // 持久化用户消息
        try {
            LoginUser loginUser = SESSION_USERS.get(session.getId());
            ImChatMessage msg = new ImChatMessage();
            msg.setSessionId(chatSessionId);
            msg.setUserId(loginUser == null ? null : loginUser.getUserId());
            msg.setRole("user");
            msg.setContent(contentText);
            imChatMessageMapper.insertImChatMessage(msg);
        } catch (Exception e) {
            LOGGER.warn("[AI客服WS] 保存用户消息失败", e);
        }

        // 如果为人工客服，标记会话为未处理，并转发给管理端（若管理端未连接则忽略），然后跳过AI回复
        if (!isAi) {
            try {
                if (sessionPk != null) {
                    // 0 = 未处理
                    imChatSessionMapper.updateHandledById(sessionPk, 0);
                }
            } catch (Exception e) {
                LOGGER.warn("[AI客服WS] 更新人工会话处理状态失败 sessionId={}", chatSessionId, e);
            }

            try {
                if (WebSocketAdminUsers.getUsers() != null && !WebSocketAdminUsers.getUsers().isEmpty()) {
                    JSONObject notify = new JSONObject();
                    notify.put("event", "human_message");
                    notify.put("sessionId", chatSessionId);
                    notify.put("content", contentText);
                    WebSocketAdminUsers.sendMessageToAdminsByText(notify.toJSONString());
                    LOGGER.info("[AI客服WS] 已转发人工消息到管理端：sessionId={}", chatSessionId);
                } else {
                    LOGGER.info("[AI客服WS] 管理端未连接，人工消息不转发：sessionId={}", chatSessionId);
                }
            } catch (Exception e) {
                LOGGER.warn("[AI客服WS] 转发人工消息到管理端失败 sessionId={}", chatSessionId, e);
            }

            return;
        }

        String reply;
        try {
            reply = aiReplyService.generateReply(chatSessionId, contentText);
        } catch (Exception e) {
            LOGGER.warn("[AI客服WS] 生成AI回复异常，降级固定回复", e);
            reply = "这是ai客服的回复";
        }
        // 持久化AI/客服回复
        try {
            LoginUser loginUser = SESSION_USERS.get(session.getId());
            ImChatMessage msg = new ImChatMessage();
            msg.setSessionId(chatSessionId);
            msg.setUserId(loginUser == null ? null : loginUser.getUserId());
            msg.setRole("agent");
            msg.setContent(reply);
            imChatMessageMapper.insertImChatMessage(msg);
        } catch (Exception e) {
            LOGGER.warn("[AI客服WS] 保存客服回复失败", e);
        }
        WebSocketUsers.sendMessageToUserByText(session, reply == null ? "aaa" : reply);
    }
}
