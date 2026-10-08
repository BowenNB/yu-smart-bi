package com.yupi.springbootinit.bizmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// 使用@Component注解标记该类为一个组件，让Spring框架能够扫描并将其纳入管理
@Component
public class BiMessageProducer {
	// 使用@Resource注解对rabbitTemplate进行依赖注入
    @Resource
    private RabbitTemplate rabbitTemplate;
	/**
     * 发送消息的方法
     *
     * @param exchange 交换机名称，指定消息要发送到哪个交换机
     *
     */
    public void sendMessage(String exchange) {
        // 使用rabbitTemplate的convertAndSend方法将消息发送到指定的交换机和路由键
        rabbitTemplate.convertAndSend(BiMqConstant.BI_EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY, exchange);
    }

}
