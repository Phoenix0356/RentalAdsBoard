package com.example.RentalAdsBoard.websocket;

import com.example.RentalAdsBoard.controller.exception.ChatException;
import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.vo.ChatVo;
import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Every message receiving or sending should be a json String containing an entity of chatVo
 *
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class ServerSocket implements ApplicationContextAware {
    private static CopyOnWriteArraySet<ServerSocket> serverSockets =new CopyOnWriteArraySet<>();

    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();
    private static ApplicationContext applicationContext;
    private static final Logger logger = LoggerFactory.getLogger("SocketLogger");
    private Session session;
    private String username;
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

            ObjectMapper mapper = new ObjectMapper();
            ChatVo chatVo = mapper.readValue(message, ChatVo.class);
            sendOneMessage(chatVo);

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
            logger.error(String.valueOf(e));
        }


    }

    @OnError
    public void onError(Throwable throwable) throws IOException, java.io.IOException {
        logger.error(String.valueOf(throwable));
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    public void sendOneMessage(ChatVo chatVo) throws Exception {
        Session sessionTo = sessionPool.get(chatVo.getUserTo());
        if (sessionTo!=null&&sessionTo.isOpen()){
            try {
                chatVo.setRead(true);
                ObjectMapper mapper = new ObjectMapper();
                String jsonStr = mapper.writeValueAsString(chatVo);
                sessionTo.getAsyncRemote().sendText(jsonStr);

            }catch (Exception e){
                throw new ChatException("send message failed");
            }
        }else {
            chatVo.setRead(false);
        }
        chatService.saveMessage(chatVo);

    }


}
