package com.youjun.api.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * bct mq 交互数据接收
 */
@Slf4j
@Component
@RabbitListener(queues = "SWAP_REGULATION_QUEUE")
public class BctDataSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitHandler
    public void sendMessage(String json) {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        //方式一：
        Message message = MessageBuilder.withBody(json.getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                //给消息设置延迟毫秒值
                .setExpiration(String.valueOf(1000))
                .build();
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", message);
        //方式二：
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(1000));
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        });
        //方式二：lambda
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", json, lamMessage -> {
            //给消息设置延迟毫秒值
            lamMessage.getMessageProperties().setExpiration(String.valueOf(1000));
            lamMessage.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return lamMessage;
        });
        System.out.println("消息发送完毕！");
    }

}
