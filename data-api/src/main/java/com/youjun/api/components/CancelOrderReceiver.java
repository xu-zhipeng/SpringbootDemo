package com.youjun.api.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的消费者
 * Created by macro on 2018/9/14.
 */
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {
    private static Logger log =LoggerFactory.getLogger(CancelOrderReceiver.class);
    /*@Autowired
    private OmsPortalOrderService portalOrderService;*/
    @RabbitHandler
    public void handle(Long orderId){
//        portalOrderService.cancelOrder(orderId);
        log.info("process orderId:{}",orderId);
    }
}
