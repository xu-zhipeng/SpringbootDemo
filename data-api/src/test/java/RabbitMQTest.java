import com.fasterxml.jackson.core.JsonProcessingException;
import com.youjun.api.DataApiApplication;
import com.youjun.api.modules.office.controller.dto.BctDataDTO;
import com.youjun.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/8/7
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DataApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RabbitMQTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void sendQueue() {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("test",11);
//        map.put("name","job");
//        JsonUtils.toJson(map)
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", "test");
        System.out.println("消息发送完毕！");
    }

    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void sendQueueMap() {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", 11);
        map.put("name", "job");
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", map);
        System.out.println("消息发送完毕！");
    }

    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void sendQueueJson() {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", 11);
        map.put("name", "job");
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", JsonUtils.toJson(map));
        System.out.println("消息发送完毕！");
    }

    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void sendQueueBctDataDTO() {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        BctDataDTO bctDataDTO = new BctDataDTO();
        bctDataDTO.setLcmId("111");
        bctDataDTO.setContractCode("2222");
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", bctDataDTO);
        System.out.println("消息发送完毕！");
    }

    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void sendQueueMessageDTO() throws JsonProcessingException {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        BctDataDTO bctDataDTO = new BctDataDTO();
        bctDataDTO.setLcmId("111");
        bctDataDTO.setContractCode("2222");
        byte[] bytes = JsonUtils.mapper.writeValueAsBytes(bctDataDTO);
        Message message = MessageBuilder.withBody(bytes)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", message);
        System.out.println("消息发送完毕！");
    }


    // 发送一条点对点（Direct）的消息，又称为直连
    @Test
    public void sendQueueMessageJSONString() throws JsonProcessingException {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        BctDataDTO bctDataDTO = new BctDataDTO();
        bctDataDTO.setLcmId("111");
        bctDataDTO.setContractCode("2222");
        String json = JsonUtils.toJson(bctDataDTO);
        Message message = MessageBuilder.withBody(json.getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", message);
        System.out.println("消息发送完毕！");
    }

    @Test
    public void sendQueueMessageJSONJSON() throws JsonProcessingException {
        System.out.println("开始向队列中发送一条消息！");
        // 参数1：管理中的队列名  参数2：发送的消息
        BctDataDTO bctDataDTO = new BctDataDTO();
        bctDataDTO.setLcmId("111");
        bctDataDTO.setContractCode("2222");
        String json = JsonUtils.toJson(bctDataDTO);
        json = JsonUtils.toJson(json);
        Message message = MessageBuilder.withBody(json.getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        amqpTemplate.convertAndSend("SWAP_REGULATION_QUEUE", message);
        System.out.println("消息发送完毕！");
    }
}
