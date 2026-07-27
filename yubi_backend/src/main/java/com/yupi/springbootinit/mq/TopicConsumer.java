package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TopicConsumer {

  private static final String EXCHANGE_NAME = "topic_exchanges";

  public static void main(String[] argv) throws Exception {
      // 创建连接工厂对象
    ConnectionFactory factory = new ConnectionFactory();
    // 设置连接工厂的主机地址为本地主机
    factory.setHost("localhost");
    // 建立连接
    Connection connection = factory.newConnection();
    // 创建通道
    Channel channel = connection.createChannel();
    // 声明使用主题交换机，并指 定交换机名称和类型
    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

    // 创建前端队列
    String queueName = "frontend_queue";
    // 声明队列，设置队列为持久化的，非独占的，非自动删除的
    channel.queueDeclare(queueName, true, false, false, null);
    // 将其绑定到主题交换机上，使用路由键模式"#.前端.#"
    channel.queueBind(queueName, EXCHANGE_NAME, "#.前端.#");

    // 创建后端队列
    String queueName2 = "backend_queue";
    // 声明队列，设置队列为持久化的，非独占的，非自动删除的
    channel.queueDeclare(queueName2, true, false, false, null);
    // 将其绑定到主题交换机上，使用路由键模式"#.后端.#"
    channel.queueBind(queueName2, EXCHANGE_NAME, "#.后端.#");

    // 创建后端队列
    String queueName3 = "product_queue";
    // 声明队列，设置队列为持久化的，非独占的，非自动删除的
    channel.queueDeclare(queueName3, true, false, false, null);
    // 将其绑定到主题交换机上，使用路由键模式"#.产品.#"
    channel.queueBind(queueName3, EXCHANGE_NAME, "#.产品.#");

    // 打印等待队列的提示信息
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    // 创建消息处理的回调函数（消费者：员工小a接收）
    DeliverCallback xiaoadeliverCallback = (consumerTag, delivery) -> {
      // 获取消息内容和路由键
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [xiaoa] Recieved '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };

    // 创建消息处理的回调函数（消费者：员工小b接收）
    DeliverCallback xiaobdeliverCallback = (consumerTag, delivery) -> {
      // 获取消息内容和路由键
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [xiaob] Recieved '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };

    // 创建消息处理的回调函数（消费者：员工小c接收）
    DeliverCallback xiaocdeliverCallback = (consumerTag, delivery) -> {
      // 获取消息内容和路由键
      String message = new String(delivery.getBody(), "UTF-8");
      System.out.println(" [xiaoc] Recieved '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };

    // 启动消费者并绑定消息处理的回调函数到各个队列上
    // 员工小a处理前端队列接收到的信息
    channel.basicConsume(queueName, true, xiaoadeliverCallback, consumerTag -> { });
    // 员工小b处理后端队列接收到的信息
    channel.basicConsume(queueName2, true, xiaobdeliverCallback, consumerTag -> { });
    // 员工小c处理产品队列接收到的信息
    channel.basicConsume(queueName3, true, xiaocdeliverCallback, consumerTag -> { });
  }
}
