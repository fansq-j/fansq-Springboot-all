package com.github.fansq.rabbitmq.config;

import com.github.fansq.rabbitmq.contract.RabbitContract;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation 绑定
 */
@Configuration
public class RabbitBinding {

    @Autowired
    private RabbitQueue queueConfig;
    @Autowired
    private RabbitExchange exchangeConfig;

    /**
     将消息队列和交换机进行绑定
     */
    @Bean
    @Order(value = 9)
    public Binding bindingPortal() {
        return BindingBuilder.bind(queueConfig.noticeQueue()).to(exchangeConfig.directExchange()).with(RabbitContract.TEST_ROUTING_KEY);
    }

    /**
     * 死信队列与死信交换机进行绑定
     * @return
     */
    @Bean
    @Order(value = 10)
    public Binding bindingDeadExchange() {
        return BindingBuilder.bind(queueConfig.noticeDeadQueue()).to(exchangeConfig.noticeDeadExchange()).with(RabbitContract.TEST_DEAD_ROUTING_KEY);
    }
}
