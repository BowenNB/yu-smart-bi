package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiManagerTest {

    private static final Logger log = LoggerFactory.getLogger(AiManagerTest.class);
    @Resource
    private AiManager aiManager;

    @Test
    void sendMsgToXingHuo() {
        String answer = aiManager.sendMsgToXingHuo(true,"分析需求: \n" +
                "分析网站用户的增长情况\n" +
                "原始数据：\n" +
                "日期，用户数\n" +
                "1号，10\n" +
                "2号，20\n" +
                "3号, 30");
        System.out.println("answer = " +
                answer);

    }
}