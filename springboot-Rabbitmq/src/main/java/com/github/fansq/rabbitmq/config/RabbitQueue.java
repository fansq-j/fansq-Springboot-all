package com.github.fansq.rabbitmq.config;

import com.github.fansq.rabbitmq.contract.RabbitContract;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation 队列
 */
@Configuration
public class RabbitQueue {
    @Bean
    @Order(value = 4)
    public Queue noticeDeadQueue() {
        return new Queue(RabbitContract.TEST_DEAD_QUEUE_NAME, true);
    }

    @Bean
    @Order(value = 6)
    public Queue noticeQueue() {

        // 消息发布队列绑定死信（备胎交换机和备胎队列）
        Map<String, Object> args = new HashMap<>(2);
        // 绑定死交换机
        args.put(RabbitContract.DEAD_LETTER_EXCHANGE_KEY, RabbitContract.TEST_DEAD_EXCHANGE_NAME);
        args.put(RabbitContract.DEAD_LETTER_ROUTING_KEY, RabbitContract.TEST_DEAD_ROUTING_KEY);
        /**
         durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false
         */
        return new Queue(RabbitContract.TEST_QUEUE_NAME,true,false,false,args);
    }
}
