package com.youjun.api.components;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket的具体实现类
 */
@Slf4j
@Component
@ServerEndpoint(value = "/webSocket/{id}")
public class WebSocketServer {
    /**
     * 客户端ID
     */
    private Long userId = 0L;
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 记录当前在线连接数(为保证线程安全，须对使用此变量的方法加lock或synchronized)
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 用来存储当前在线的客户端(此map线程安全)
     */
    private static ConcurrentHashMap<Long, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功后调用
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") Long id) throws IOException {
        this.session = session;
        // 接收到发送消息的客户端ID
        this.userId = id;
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
            //加入set中
        } else {
            webSocketMap.put(userId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        //可以群发消息
        //消息可以保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //传送给对应toUserId用户的websocket
                sendToAll(String.format(userId.toString(), message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时回调
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 推送信息给指定ID客户端，如客户端不在线，则返回不在线信息给自己
     *
     * @param message      客户端发来的消息
     * @param sendClientId 客户端ID
     */
    public void sendToUser(String message, Long sendClientId) {
        try {
            if (webSocketMap.get(sendClientId) != null) {
                webSocketMap.get(sendClientId).sendMessage(message);
            } else {
                log.error("客户端{}不存在", sendClientId);
            }
        } catch (Exception e) {
            log.error("推送消息到指定客户端出错", e);
        }
    }

    /**
     * 推送发送信息给所有人
     *
     * @param message 要推送的消息
     */
    public void sendToAll(String message) {
        try {
            for (Long key : webSocketMap.keySet()) {
                webSocketMap.get(key).sendMessage(message);
            }
        } catch (Exception e) {
            log.error("推送消息到所有客户端出错", e);
        }
    }

    /**
     * 推送消息
     *
     * @param message 要推送的消息
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static Integer getOnlineCount() {
        return onlineCount.get();
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.incrementAndGet();
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.decrementAndGet();
    }

}