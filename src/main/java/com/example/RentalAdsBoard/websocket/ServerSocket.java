package com.example.RentalAdsBoard.websocket;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.ChatVo;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/websocket/{username}/{targetUsername}")
public class ServerSocket implements ApplicationContextAware {
    private static CopyOnWriteArraySet<ServerSocket> serverSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();
    private static ApplicationContext applicationContext;
    private static final Logger logger = LoggerFactory.getLogger("SocketLogger");
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
            this.session = session;
            this.username = username;
            this.targetUsername = targetUsername;

            this.chatService=applicationContext.getBean(ChatService.class);

            serverSockets.add(this);
            sessionPool.put(username,session);
            logger.info("[websocket] 新的连接：id={}", this.session.getId());
        }catch (IOException ioException){
            logger.error(String.valueOf(ioException));

        }



    }
    @OnMessage
    public void onMessage(String message) throws IOException {
        try {
            logger.info("[serverSocket] 收到消息：sessionId={}，message={}", this.session.getId(), message);
            sendOneMessage(message, targetUsername);
            chatService.saveMessage(new ChatVo(username,targetUsername,message));
        }catch (Exception e){
            logger.error(String.valueOf(e));
        }
    }



    @OnClose
    public void onClose(CloseReason closeReason){
        try{
            serverSockets.remove(this);
            sessionPool.remove(username);
            logger.info("[websocket] 连接断开：id={}，reason={}", this.session.getId(),closeReason);
        }catch (Exception e) {
            return;
        }


    }

    @OnError
    public void onError(Throwable throwable) throws IOException, java.io.IOException {
        logger.error(String.valueOf(throwable));
//        logger.info("[websocket] 连接异常：id={}，throwable={}", this.session.getId(), throwable.getMessage());
//        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
//        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    public void sendOneMessage(String message,String targetUsername) throws Exception {
        Session session=sessionPool.get(targetUsername);
        if (session!=null&&session.isOpen()){
            try {
                session.getAsyncRemote().sendText(message);
                logger.info("send message: "+message+" to " + targetUsername);
            }catch (Exception e){
                throw new Exception();
            }
        }

    }


}
