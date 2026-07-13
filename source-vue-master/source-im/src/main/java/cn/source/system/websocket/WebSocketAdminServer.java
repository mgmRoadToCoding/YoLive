package cn.source.system.websocket;

import cn.source.framework.web.service.TokenService;
import cn.source.system.domain.ImChatSession;
import cn.source.system.domain.ImChatMessage;
import cn.source.system.mapper.ImChatSessionMapper;
import cn.source.system.mapper.ImChatMessageMapper;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * 管理端 WebSocket 服务：用于接收后台连接并推送更新通知
 */
@Component
@ServerEndpoint("/websocket/admin")
public class WebSocketAdminServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketAdminServer.class);

    /**
     * 允许的最大在线数（与用户端保持一致）
     */
    public static int socketMaxOnlineCount = 1000;

    private static final Semaphore socketSemaphore = new Semaphore(socketMaxOnlineCount);

    /**
     * 注入 TokenService 用于校验连接
     */
    private static TokenService tokenService;
    /**
     * 注入会话与消息Mapper
     */
    private static ImChatSessionMapper imChatSessionMapper;
    private static ImChatMessageMapper imChatMessageMapper;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        WebSocketAdminServer.tokenService = tokenService;
    }

    @Autowired
    public void setImChatSessionMapper(ImChatSessionMapper imChatSessionMapper) {
        WebSocketAdminServer.imChatSessionMapper = imChatSessionMapper;
    }

    @Autowired
    public void setImChatMessageMapper(ImChatMessageMapper imChatMessageMapper) {
        WebSocketAdminServer.imChatMessageMapper = imChatMessageMapper;
    }


    @OnOpen
    public void onOpen(Session session) throws Exception {
        boolean flag = SemaphoreUtils.tryAcquire(socketSemaphore);
        if (!flag) {
            LOGGER.error("当前管理端在线人数超过限制数- {}", socketMaxOnlineCount);
            session.close();
            return;
        }

        // 连接前进行token校验（从query参数获取）
        String rawToken = null;
        try {
            Map<String, List<String>> params = session.getRequestParameterMap();
            List<String> tokens = params == null ? null : params.get("token");
            if (tokens != null && !tokens.isEmpty()) {
                rawToken = tokens.get(0);
            }
        } catch (Exception e) {
            LOGGER.warn("[管理端WS] 读取连接参数失败", e);
        }

        if (rawToken == null || tokenService == null || !tokenService.isTokenValid(rawToken)) {
            LOGGER.warn("[管理端WS] token无效或过期，拒绝连接: sessionId={}", session.getId());
            try { session.close(); } catch (Exception ignore) {}
            SemaphoreUtils.release(socketSemaphore);
            return;
        }

        WebSocketAdminUsers.put(session.getId(), session);
        LOGGER.info("[管理端WS] 建立连接 - {}", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.info("[管理端WS] 关闭连接 - {}", session.getId());
        WebSocketAdminUsers.remove(session.getId());
        SemaphoreUtils.release(socketSemaphore);
    }

    @OnError
    public void onError(Session session, Throwable exception) throws Exception {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (Exception ignore) {}

        LOGGER.info("[管理端WS] 连接异常 - {}", session == null ? "unknown" : session.getId());
        LOGGER.info("[管理端WS] 异常信息 - {}", exception);
        if (session != null) {
            WebSocketAdminUsers.remove(session.getId());
        }
        SemaphoreUtils.release(socketSemaphore);
    }

    /**
     * 管理端目前不接收业务消息，保留接口做心跳/调试
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info("[管理端WS] 收到消息：sessionId={}, message={} ", session.getId(), message);
        // 期望管理端发送JSON：{"sessionId": 数字ID, "content": "文本"}
        Long sessionPk = null;
        String contentText = message;
        try {
            if (message != null && message.trim().startsWith("{")) {
                JSONObject json = JSON.parseObject(message);
                String sid = json.getString("sessionId");
                if (sid != null) {
                    try {
                        sessionPk = Long.parseLong(sid);
                    } catch (NumberFormatException ignore) {}
                }
                String c = json.getString("content");
                if (c != null) {
                    contentText = c;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("[管理端WS] 解析消息失败，将按纯文本处理", e);
        }

        if (sessionPk == null) {
            LOGGER.warn("[管理端WS] 未提供有效的 sessionId，无法转发");
            return;
        }

        ImChatSession chatSession = null;
        try {
            chatSession = imChatSessionMapper == null ? null : imChatSessionMapper.selectImChatSessionById(sessionPk);
        } catch (Exception e) {
            LOGGER.warn("[管理端WS] 查询会话失败 id={}", sessionPk, e);
        }
        if (chatSession == null) {
            LOGGER.warn("[管理端WS] 会话不存在 id={}", sessionPk);
            return;
        }

        Long targetUserId = chatSession.getUserId();
        int successCount = 0;
        try {
            // 查找该用户的所有在线WebSocket连接并转发，仅统计发送成功的连接
            for (Session userSession : WebSocketServer.getSessionsByUserId(targetUserId)) {
                if (userSession != null && userSession.isOpen()) {
                    try {
                        userSession.getBasicRemote().sendText(contentText);
                        successCount++;
                    } catch (Exception sendEx) {
                        LOGGER.warn("[管理端WS] 向用户连接发送失败 userId={}, sessionId={}", targetUserId, userSession.getId(), sendEx);
                    }
                }
            }
            LOGGER.info("[管理端WS] 已尝试转发到用户{}，成功连接数={}", targetUserId, successCount);
        } catch (Exception e) {
            LOGGER.warn("[管理端WS] 转发消息到用户失败 userId={}", targetUserId, e);
        }

        // 仅在至少一个在线连接成功发送后，标记会话为已处理（handled=1）
        if (successCount > 0) {
            try {
                if (imChatSessionMapper != null) {
                    imChatSessionMapper.updateHandledById(sessionPk, 1);
                    LOGGER.info("[管理端WS] 已更新会话处理状态为1 id={}", sessionPk);
                }
            } catch (Exception e) {
                LOGGER.warn("[管理端WS] 更新会话处理状态失败 id={}", sessionPk, e);
            }

            // 成功转发后，持久化管理端回复消息
            try {
                if (imChatMessageMapper != null) {
                    ImChatMessage msg = new ImChatMessage();
                    msg.setSessionId(String.valueOf(sessionPk));
                    msg.setUserId(chatSession.getUserId());
                    msg.setRole("admin");
                    msg.setContent(contentText);
                    imChatMessageMapper.insertImChatMessage(msg);
                    LOGGER.info("[管理端WS] 已持久化管理端回复消息 sessionId={}", sessionPk);
                }
            } catch (Exception e) {
                LOGGER.warn("[管理端WS] 持久化管理端回复消息失败 id={}", sessionPk, e);
            }
        } else {
            LOGGER.info("[管理端WS] 未找到在线接收者，handled 不更新，sessionId={}", sessionPk);
        }
    }
}
