package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Map;
import java.util.Scanner;

public class MultiProducer {
    // 定义队列名称
    private static final String TASK_QUEUE_NAME = "multi_queue_2";

    public static void main(String[] argv) throws Exception {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RebbitMQ服务的主机名
        factory.setHost("localhost");
        // 创建一个新连接
        try (Connection connection = factory.newConnection();
             // 创建一个新的频道
             Channel channel = connection.createChannel()) {
            //Map<String, Object> args = Map.of("x-queue-type", "quorum");
            /*
            主要作用是：
            1.
            提高可靠性：消息和队列元数据会在多个节点复制（基于 Raft），单节点故障时更稳。
            2.
            更适合生产环境高可用场景。
            3.
            行为会和 classic 不同：资源开销通常更高，部分特性限制也不同（例如优先级等能力支持差异）。
            简单说：这行就是告诉 RabbitMQ「这个队列要按 quorum 类型创建」
             */
            // 声明队列参数，包括队列名称、是否持久化
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            // 创建一个输入扫描器，用于读取控制器输入
            Scanner scanner = new Scanner(System.in);
            // 使用循环，每当用户在控制台输入一行文本，就将其作为消息发送
            while(scanner.hasNext()) {
                String message = scanner.nextLine();
                // 发布消息到队列，设置消息持久化
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));

                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }

}
