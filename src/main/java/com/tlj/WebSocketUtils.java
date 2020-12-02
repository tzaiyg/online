package com.tlj;


import javax.websocket.Session;
import javax.websocket.RemoteEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WebSocketUtils {
    /**
     * 模拟存储websocket session
     */
    public static final Map<String, Session> LIVING_SESSION_CACHE = new ConcurrentHashMap();

    public static void sendMessageAll(String message){
        LIVING_SESSION_CACHE.forEach((sessionId,session) -> sendMessage(session,message));
    }

    /**
     * 发送给指定用户
     * @param session
     * @param message
     */
    public static void sendMessage(Session session, String message){
        if(session == null){
            return;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if(basic == null){
            return;
        }
        try {
            basic.sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}