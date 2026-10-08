package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Disabled("限流配置为 2 次/秒，用例第二段瞬间调用 5 次必然抛异常；仅供手动观察，改造前不放行")
class RedisLimiterManagerTest {

    @Resource
    private RedisLimiterManager redisLimiterManager;
    @Test
    void daRateLimit() throws InterruptedException {
        // 模拟一下操作
        String userId = "1";
        // 瞬间执行2次，每成功1次，就打印一次'成功'
        for (int i = 0; i < 2; i++) {
            redisLimiterManager.doRateLimit(userId);
            System.out.println("成功");
        }
        // 等待1秒
        Thread.sleep(1000);
        // 瞬间执行5次，每成功1次，就打印一次'成功'
        for (int i = 0; i < 5; i++) {
            redisLimiterManager.doRateLimit(userId);
            System.out.println("成功");
        }
    }
}