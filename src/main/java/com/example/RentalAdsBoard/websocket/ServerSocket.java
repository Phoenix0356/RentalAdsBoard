package com.example.RentalAdsBoard.websocket;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class ServerSocket {
    private static final Logger logger = LoggerFactory.getLogger("SocketLogger");
    private Session session;
    private String username;

    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
    // 所以可以用一个静态set保存起来。
    private static CopyOnWriteArraySet<ServerSocket> serverSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username")String username, EndpointConfig endpointConfig){
        try {
            this.session = session;
            this.username = username;
            serverSockets.add(this);
            sessionPool.put(username,session);
            logger.info("[websocket] 新的连接：id={}", this.session.getId());
        }catch (Exception e){
           return;
        }



    }
    @OnMessage
    public void onMessage(String message) throws IOException, java.io.IOException {
        logger.info("[serverSocket] 收到消息：sessionId={}，message={}", this.session.getId(), message);

        if (message.equalsIgnoreCase("bye")) {
            // 由服务器主动关闭连接。状态码为 NORMAL_CLOSURE（正常关闭）。
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye"));;
            return;
        } else if (message.equalsIgnoreCase("hello")) {
            this.session.getAsyncRemote().sendText("Hello");
            return;
        }


        this.session.getAsyncRemote().sendText("["+ Instant.now().toEpochMilli() +"] Hello " + message);
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
        logger.info("[websocket] 连接异常：id={}，throwable={}", this.session.getId(), throwable.getMessage());
        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    public void sendOneMessage(String message,String username){
        Session session=sessionPool.get(username);
        if (session!=null&&session.isOpen()){
            try {
                session.getAsyncRemote().sendText(message);
                logger.info("send message to " + username);
            }catch (Exception e){
                return;
            }
        }

    }
}
