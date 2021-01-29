package com.github.fansq.rabbitmq.producer;

import com.alibaba.fastjson.JSONObject;
import com.github.fansq.rabbitmq.contract.RabbitContract;
import com.github.fansq.rabbitmq.entity.TestMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation
 */
@Component
public class TestProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(){
      //  for(int i = 0;i<5;i++){
            TestMessage testMessage = new TestMessage();
            String str = "我是测试的消息";
            testMessage.setMessage(str);
            Message message = MessageBuilder.withBody(JSONObject.toJSONString(testMessage).getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                    .build();
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(
                    RabbitContract.TEST_EXCHANGE_NAME,
                    RabbitContract.TEST_ROUTING_KEY,
                    message,
                    correlationData);
      //  }
    }
}
