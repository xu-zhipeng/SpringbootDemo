package com.youjun.api.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * <p>
 *  socket 封装类
 * </p>
 *
 * @author kirk
 * @since 2021/6/11
 */
@ServerEndpoint(value = "/webSocket/{userId}")
@Component
public class CustomWebSocket {
    static Logger log = LoggerFactory.getLogger(CustomWebSocket.class);
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger();
    // concurrent包的线程安全，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, CustomWebSocket> webSocketMap = new ConcurrentHashMap<>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /** 接收userId */
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);// 加入set中
            addOnlineCount();// 在线数加1
        }
        log.info(userId + "加入");
        log.info("当前在线人数为：" + getOnlineCount());
        /*try {
            sendMessage("欢迎" + userId + "加入");
        } catch (IOException e) {
            System.out.println("IO异常");
        }*/
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
        }
        subOnlineCount(); // 在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        message = "来自"+userId+"的消息:" + message;
        log.info("onMessage:"+message);
        // 群发消息
        /*for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }*/
    }

    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (String key : webSocketMap.keySet()) {
            try {
                webSocketMap.get(key).sendMessage(message);
            } catch (IOException e) {
                log.error("群发,{{}}信息发送失败",message);
                continue;
            }
        }
    }

    /**
     * 发送自定义消息
     * 按userid发送消息
     */
    public static void sendInfo(String message, String userId) throws IOException {
        log.info("发送消息到：" + userId + "，报文：" + message);
        try {
            if (webSocketMap.containsKey(userId)) {
                webSocketMap.get(userId).sendMessage(message);
            } else {
                log.error("用户" + userId + ",不在线！");
            }
        }catch (Exception e){
            log.error("用户：{{}},{{}}信息发送失败",userId,message);
        }
    }

    /**
     * 获取在线客户端数量
     *
     * @return
     */
    public static AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    /**
     * 添加线上链接的客户端数量
     */
    public static void addOnlineCount() {
        CustomWebSocket.onlineCount.incrementAndGet();
    }

    /**
     * 客户端下载是调用将在线数量减1
     */
    public static void subOnlineCount() {
        CustomWebSocket.onlineCount.decrementAndGet();
    }
}

