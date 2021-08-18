package com.youjun.api.components;

import com.youjun.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * bct mq 交互数据接收
 */
@Slf4j
@Component
@RabbitListener(queues = "SWAP_REGULATION_QUEUE")
public class BctDataReceiver {
    private static Logger log = LoggerFactory.getLogger(BctDataReceiver.class);

    @RabbitHandler
    public void handle(@Payload byte[] body) {
        try {
            log.info("bct MQ 接收消息");
            String json = JsonUtils.mapper.readValue(body, String.class);
            log.info("json:{}", json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
