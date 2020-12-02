package com.tlj;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static com.tlj.WebSocketUtils.LIVING_SESSION_CACHE;
import static com.tlj.WebSocketUtils.sendMessage;
import static com.tlj.WebSocketUtils.sendMessageAll;

    @RestController
    @ServerEndpoint("/chat-room/{username}")
    public class SocketController {
        private static final Logger log = LoggerFactory.getLogger(SocketController.class);

        @OnOpen
        public void openSession(@PathParam("username") String name, Session session){
            LIVING_SESSION_CACHE.put(name,session);
            String message = "欢迎用户["+ name + "]来到朦胧的夜聊天室";
            log.info(message);
            sendMessageAll(message);
        }

        @OnMessage
        public void onMessage(@PathParam("username") String username, String message){
            log.info(message);
            sendMessageAll("用户["+username+"]:" + message);
        }

        @OnClose
        public void onClose(@PathParam("username") String username, Session session){
            //移除session
            LIVING_SESSION_CACHE.remove(username);
            //通知他人
            sendMessageAll("用户["+username+"]已经离开朦胧的夜聊天室");
            try {
                session.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @OnError
        public void onError(Session session,Throwable throwable){
            try {
                session.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            throwable.printStackTrace();
        }

        @GetMapping("/chat-room/{sender}/to/{receive}")
        public void onMessage(@PathVariable("sender") String sender,@PathVariable("receive") String receive, String message){
            sendMessage(LIVING_SESSION_CACHE.get(receive),"["+sender+"]:"+message);
        }
    }

