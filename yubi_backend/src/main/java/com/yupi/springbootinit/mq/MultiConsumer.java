package com.yupi.springbootinit.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class MultiConsumer {

  // 定义要使用队列的名称，multi_queue
  private static final String TASK_QUEUE_NAME = "multi_queue_2";

  public static void main(String[] argv) throws Exception {
    // 创建一个连接工厂
    ConnectionFactory factory = new ConnectionFactory();
    // 设置RabbitMQ服务的主机名
    factory.setHost("localhost");
    // 创建一个新的连接
    final Connection connection = factory.newConnection();

      for (int i = 0; i < 2; i++) {
          // 从连接获取一个新的通道
          final Channel channel = connection.createChannel();
          // 声明一个队列，并设置属性：队列名称，持久化，非排他，非自动删除，其他删除；如果队列不存在，则创建它
          channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
          // 控制台答打印等待消息的信息
          System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
          // 设置预取计数为1，这样RabbitMQ将不会同时分发多于1条消息给工作者。换句话说，在一个工作者处理并确认当前消息之前，RabbitMQ不会将新的消息分发给它。这有助于实现公平分发，即使某些工作者处理消息的速度较慢，也不会被过多的消息淹没。
          channel.basicQos(1);

          int finalI = i;
          // 创建消息接收回调函数，当消息到达时被调用
          DeliverCallback deliverCallback = (consumerTag, delivery) -> {
              // 将接收到的消息转为字符串
              String message = new String(delivery.getBody(), "UTF-8");

              try {
                  System.out.println(" [x] Received '" + "编号:" + finalI + ":"+ message + "'");
                  // 处理工作，模拟处理消息所花费的时间，机器处理能力有限（接收一条消息，20秒后再接收下一条消息）
                  Thread.sleep(20000);
                  // (不用dowork）模拟
                  // doWork(message);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } finally {
                  System.out.println(" [x] Done");
                  // 手动发送应答，告诉RabbitMQ消息已被处理
                  channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
              }
          };
          // 开始消费信息，传入队列名称，是否自动确认（手动确认消息处理完成），投递回调和消费者取消回调
          channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
          });
      }
  }

//  private static void doWork(String task) {
//    for (char ch : task.toCharArray()) {
//        if (ch == '.') {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException _ignored) {
//                Thread.currentThread().interrupt();
//            }
//        }
}