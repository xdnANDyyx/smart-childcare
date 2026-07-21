package com.zhyyt.nursery.utils;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static cn.dev33.satoken.SaManager.log;

/**
 * WebSocket 服务端 (用于实时消息推送)
 */
@Slf4j
@Component
@ServerEndpoint("/ws/{userId}")
public class WebSocketServer {

    private static final ConcurrentHashMap<Long, Session> SESSION_MAP = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        SESSION_MAP.put(userId, session);
        log.info("WebSocket连接建立: userId={}, 当前在线人数={}", userId, SESSION_MAP.size());
    }

    @OnClose
    public void onClose(@PathParam("userId") Long userId) {
        SESSION_MAP.remove(userId);
        log.info("WebSocket连接关闭: userId={}, 当前在线人数={}", userId, SESSION_MAP.size());
    }

    @OnError
    public void onError(@PathParam("userId") Long userId, Throwable error) {
        log.error("WebSocket错误: userId={}", userId, error);
        SESSION_MAP.remove(userId);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") Long userId) {
        log.info("收到消息: userId={}, message={}", userId, message);
    }

    /**
     * 发送消息给指定用户
     */
    public static void sendMessage(Long userId, String message) {
        Session session = SESSION_MAP.get(userId);
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 广播消息
     */
    public static void broadcast(String message) {
        SESSION_MAP.forEach((userId, session) -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        });
    }
}
