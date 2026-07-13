package cn.source.system.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理端 WebSocket 连接管理器
 * 仅保存连接到 /websocket/admin 的会话，并提供群发能力。
 */
public class WebSocketAdminUsers {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketAdminUsers.class);

    /**
     * 管理端连接集
     */
    private static final Map<String, Session> ADMIN_USERS = new ConcurrentHashMap<>();

    /**
     * 存储管理端用户
     *
     * @param key 唯一键
     * @param session 会话
     */
    public static void put(String key, Session session) {
        ADMIN_USERS.put(key, session);
    }

    /**
     * 获取管理端用户
     */
    public static Session get(String key) {
        return ADMIN_USERS.get(key);
    }

    /**
     * 移除管理端用户
     */
    public static void remove(String key) {
        ADMIN_USERS.remove(key);
    }

    /**
     * 获取所有管理端连接
     */
    public static Map<String, Session> getUsers() {
        return ADMIN_USERS;
    }

    /**
     * 群发文本消息到管理端
     */
    public static void sendMessageToAdminsByText(String message) {
        Collection<Session> values = ADMIN_USERS.values();
        for (Session session : values) {
            sendMessageToAdminByText(session, message);
        }
    }

    /**
     * 发送文本消息到指定管理端连接
     */
    public static void sendMessageToAdminByText(Session session, String message) {
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                LOGGER.error("[管理端发送消息异常]", e);
            }
        } else {
            LOGGER.info("[管理端连接不存在或已离线]");
        }
    }
}