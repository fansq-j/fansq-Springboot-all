package com.github.fansq.rabbitmq.controller;

import com.github.fansq.rabbitmq.producer.TestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fansq
 * @date 2021-1-28
 * @deprecation controller
 */
@RestController
public class TestController {

    @Autowired
    private TestProducer testProducer;

    @RequestMapping("testSendMessage")
    public String testSendMessage(){
        testProducer.sendMessage();
        return "测试简单写了";
    }
}
