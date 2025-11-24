package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class AiManagerTest {

    @Resource
    private AiManager aiManager;

    @Test
    void sendMsgToXingHuo() {
        String answer = aiManager.sendMsgToXingHuo(true,"邓紫棋");
        System.out.println(answer);

    }
}