package com.github.fansq.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation 启动类
 * 重试三次加入死信队列
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootRabbitmq {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootRabbitmq.class,args);
    }
}
