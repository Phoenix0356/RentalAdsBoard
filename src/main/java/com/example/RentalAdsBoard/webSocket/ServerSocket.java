package com.example.RentalAdsBoard.webSocket;

import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.vo.ChatVo;
import io.jsonwebtoken.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/websocket/{username}/{targetUsername}")
public class ServerSocket implements ApplicationContextAware {
    private static CopyOnWriteArraySet<ServerSocket> serverSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();
    private static ApplicationContext applicationContext;
    private Session session;
    private String username;
    private String targetUsername;

    private ChatService chatService;
    // get beans from Spring
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServerSocket.applicationContext=applicationContext;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username")String username,
                       @PathParam(value = "targetUsername")String targetUsername,
                       EndpointConfig endpointConfig){
        try {
            this.session=session;
            this.username = username;
            this.targetUsername = targetUsername;

            this.chatService=applicationContext.getBean(ChatService.class);

            serverSockets.add(this);
            sessionPool.put(username,session);

        }catch (Exception e){
           e.printStackTrace();

        }



    }
    @OnMessage
    public void onMessage(String message) throws IOException {
        try {

            sendOneMessage(message, targetUsername);
            chatService.saveMessage(new ChatVo(username,targetUsername,message));
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @OnClose
    public void onClose(CloseReason closeReason){
        try{
            serverSockets.remove(this);
            sessionPool.remove(username);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    @OnError
    public void onError(Throwable throwable) throws IOException, java.io.IOException {}

    public void sendOneMessage(String message,String targetUsername) throws Exception {
        Session session=sessionPool.get(targetUsername);
        if (session!=null&&session.isOpen()){
            try {
                session.getAsyncRemote().sendText(message);

            }catch (Exception e){
                throw new Exception();
            }
        }

    }
}
