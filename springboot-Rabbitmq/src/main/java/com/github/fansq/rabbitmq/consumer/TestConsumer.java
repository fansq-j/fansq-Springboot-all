package com.github.fansq.rabbitmq.consumer;

import com.github.fansq.rabbitmq.contract.RabbitContract;
import com.github.fansq.rabbitmq.entity.TestMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation 消费者
 * simpleRabbitListenerContainerFactory  这个要和config中配置的一样
 * 否则无法消费消息
 * Jackson2JsonMessageConverter 这个要对应json类型的数据  所以是message
 */
@Component
@RabbitListener(queues = RabbitContract.TEST_QUEUE_NAME,containerFactory="simpleRabbitListenerContainerFactory")
@Slf4j
public class TestConsumer {

    private int retryCount = 0;
    @RabbitHandler
    public void receiveMessage(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        long deliveryTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            log.info("我是消费者");
            log.info(new String(message.getBody()));
            //deliveryTag:该消息的index
            //multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
            int tep = 10/0;
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
                e.printStackTrace();
                //deliveryTag:该消息的index
                //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                //requeue：被拒绝的是否重新入队列
                retryCount++;
                System.out.println("第"+retryCount);
                throw  e;
        }finally {
            //重试次数达到限制
            if (retryCount == 3) {
                System.out.println("处理订单消息异常，nack消息到死信队列");
                //不重新入队，发送到死信队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        }


    }
}
