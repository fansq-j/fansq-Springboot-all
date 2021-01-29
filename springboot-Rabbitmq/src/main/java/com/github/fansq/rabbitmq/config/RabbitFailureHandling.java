package com.github.fansq.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @author fansq
 * @date 20-11-3
 * @deprecation  文件消息发送失败处理
 */
@Component
@Slf4j
public class RabbitFailureHandling implements
        RabbitTemplate.ConfirmCallback,
        RabbitTemplate.ReturnCallback{

    /**
     * 当exchange 和 routingKey相绑定时，
     * 消息通过exchange 和 routingKey进入相对应的队列中则只会触发ConfirmCallback 不会触发ReturnCallback
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            log.info("消息发送成功到exchange");
        }else{
            log.info("消息发送失败：id "+ correlationData +"消息发送失败的原因"+ cause);
        }
    }

    /**
     * 交换机路由队列失败
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("returnedMessage [消息从交换机到队列失败]  message："+message+
                "replyCode"+replyCode+
                "replyText"+replyText+
                "exchange"+exchange+
                "routingKey"+routingKey);
    }
}
