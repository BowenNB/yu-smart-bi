package com.yupi.springbootinit.bizmq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

//标记该类为一个Spring Boot测试类，Spring Boot会在测试运行时加载应用程序上下文
@SpringBootTest
class MyMessageProducerTest {

    //注入一个消息生产者的实例
    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    void sendMessage() {
        myMessageProducer.sendMessage("code_exchange","my_routingKey","你好呀");
    }
}