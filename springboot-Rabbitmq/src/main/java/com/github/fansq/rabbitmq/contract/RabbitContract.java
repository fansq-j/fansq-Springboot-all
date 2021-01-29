package com.github.fansq.rabbitmq.contract;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation 常量
 */
public class RabbitContract {
    /**
     * 交换机名称
     */
    public static final String TEST_EXCHANGE_NAME = "TEST_EXCHANGE_NAME";
    /**
     * 队列名称
     */
    public static final String TEST_QUEUE_NAME = "TEST_QUEUE_NAME";
    /**
     * 路由键
     */
    public static final String TEST_ROUTING_KEY = "TEST_ROUTING_KEY";
    /**
     * 死信交换机名称
     */
    public static final String TEST_DEAD_EXCHANGE_NAME = "TEST_DEAD_EXCHANGE_NAME";
    /**
     * 死信队列名称
     */
    public static final String TEST_DEAD_QUEUE_NAME = "TEST_DEAD_QUEUE_NAME";
    /**
     * 死信路由键
     */
    public static final String TEST_DEAD_ROUTING_KEY = "TEST_DEAD_ROUTING_KEY";
    /**
     * 死信队列 交换机标识符
     */
    public static final String DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
}
