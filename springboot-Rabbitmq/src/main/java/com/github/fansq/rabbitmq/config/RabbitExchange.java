package com.github.fansq.rabbitmq.config;

import com.github.fansq.rabbitmq.contract.RabbitContract;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation 交换机
 */
@Configuration
public class RabbitExchange {

    /**
     *   定义fanout exchange
     *   durable="true" rabbitmq重启的时候不需要创建新的交换机
     *   direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     *   fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     *   topic 交换机中的路由键为模糊匹配 符号“#”匹配一个或多个词，符号“”匹配不多不少一个词
     *   hearders 交换机会根据消息内容的hearders属性进行匹配
     *   消息将会转发给queue参数指定的消息队列
     *
     *   Order为初始化顺序
     */
    @Bean
    @Order(value = 2)
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitContract.TEST_EXCHANGE_NAME, true, false);
    }

    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    @Order(value = 1)
    public DirectExchange noticeDeadExchange() {
        return new DirectExchange(RabbitContract.TEST_DEAD_EXCHANGE_NAME);
    }
}
