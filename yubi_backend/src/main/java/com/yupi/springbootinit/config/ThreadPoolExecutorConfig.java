package com.yupi.springbootinit.config;

// 1. 导入正确的TimeUnit（核心修复点）
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 * 修复点：删除错误的TimeUnit导入，添加正确的java.util.concurrent.TimeUnit
 */
@Configuration
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 自定义线程工厂，给线程命名便于排查问题
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 1; // 线程编号初始值

            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("自定义线程-" + count++); // 简化自增写法
                return thread;
            }
        };

        // 初始化线程池，参数类型完全匹配
        return new ThreadPoolExecutor(
                2, // 核心线程数：线程池常驻的最小线程数
                4, // 最大线程数：线程池允许创建的最大线程数
                100, // 非核心线程空闲超时时间：超过该时间未使用则销毁
                TimeUnit.SECONDS, // 此时的TimeUnit是正确的JDK并发包类型
                new ArrayBlockingQueue<>(4), // 任务队列：容量为4的有界阻塞队列
                threadFactory // 自定义线程工厂
        );
    }
}