package com.youjun.api.components;

import com.rabbitmq.client.Channel;
import com.youjun.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * bct mq 交互数据接收
 */
@Slf4j
@Component
public class BctDataReceiver {
    private static Logger log = LoggerFactory.getLogger(BctDataReceiver.class);

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "SWAP_REGULATION_QUEUE", durable = "true"),
                    exchange = @Exchange(value = "SWAP_REGULATION"),
                    key = "swap.regulation")
    })
    //方式一
    public void handle(@Payload byte[] body, Message message, Channel channel) throws IOException {
        try {
            log.info("bct MQ 接收消息");
            String json = JsonUtils.mapper.readValue(body, String.class);
            log.info("json:{}", json);
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                //log.error("消息已重复处理失败,睡眠60s再次返回队列...");
                //TimeUnit.SECONDS.sleep(60);
                //log.info("睡眠结束");
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
            log.info("已确认接收");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("消息失败即将再次返回队列处理...");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
    //方式二 body 等价于 message.getBody()
    /*public void handle(Message message, Channel channel) throws IOException {
        try {
            log.info("bct MQ 接收消息");
            String json = JsonUtils.mapper.readValue(message.getBody(), String.class);
            log.info("json:{}", json);
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                //log.error("消息已重复处理失败,睡眠60s再次返回队列...");
                //TimeUnit.SECONDS.sleep(60);
                //log.info("睡眠结束");
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
            log.info("已确认接收");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("消息失败即将再次返回队列处理...");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }*/

}
