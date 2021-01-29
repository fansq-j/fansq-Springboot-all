package com.github.fansq.rabbitmq.config;

import com.github.fansq.rabbitmq.contract.RabbitContract;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation
 */
@Configuration
public class RabbitConfig {


    @Autowired
    private RabbitFailureHandling rabbitFailureHandling;

    /**
     * 配置
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(rabbitFailureHandling);
        rabbitTemplate.setReturnCallback(rabbitFailureHandling);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * mq 配置
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置并发
        factory.setConcurrentConsumers(50);
        //最大并发
        factory.setMaxConcurrentConsumers(50);
//        //消息接收——手动确认
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        //设置超时
//        factory.setReceiveTimeout(2000L);
//        //设置重试间隔
//        factory.setFailedDeclarationRetryInterval(3000L);
        //监听自定义格式转换
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(RabbitContract.TEST_QUEUE_NAME);              // 监听的队列
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);     // 根据情况确认消息
//        container.setMessageListener((MessageListener) (message) -> {
//            System.out.println("====接收到消息=====");
//            System.out.println(new String(message.getBody()));
//            //抛出NullPointerException异常则重新入队列
//            //throw new NullPointerException("消息消费失败");
//            //当抛出的异常是AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false
//            //throw new AmqpRejectAndDontRequeueException("消息消费失败");
//            //当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
//            //throw new ImmediateAcknowledgeAmqpException("消息消费失败");
//        });
//        return container;
//    }


    /**
     * 如果没有配置死信队列 想法送到别的队列，可以设置这个
     * 可以配置MessageRecoverer对异常消息进行处理，
     * 此处理会在listener.retry次数尝试完并还是抛出异常的情况下才会调用，默认有两个实现
     * 1.RepublishMessageRecoverer：将消息重新发送到指定队列，需手动配置
     * 2.RejectAndDontRequeueRecoverer：如果不手动配置MessageRecoverer，会默认使用这个，实现仅仅是将异常打印抛出
     */
//    @Bean
//    public MessageRecoverer messageRecoverer(){
//        return new RepublishMessageRecoverer(rabbitTemplate,"error-exchange","error-routing-key");
//    }
}