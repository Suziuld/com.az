package com.az;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author:Sz
 * @Date:2023/2/1816:39
 * 生产者：发消息
 */
public class Producer {
    //创建队列
    public static final String QIEIE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //创建rbmittMq工厂
        ConnectionFactory connrb = new ConnectionFactory();
        //创建工厂IP链接rabbitmq
        connrb.setHost("127.0.0.1");
        //设置用户名
        connrb.setUsername("guest");
        //设置密码
        connrb.setPassword("guest");

        //创建连接
        Connection conn = connrb.newConnection();
        //获取通信通道
        Channel channel1 = conn.createChannel();
        /*创建队列
        1,参数一 队列名称
        * 2,队列里的参数是否要持久化，默认情况放在内存中
        3,该队列是否进行消息的共享，是否只供一个消费者消费,false表示不共享
        4，最后一个消费者开启连接以后，该队列是否删除，ture表示删除
        5,其他参数，延迟消息~、、、
        * */
        channel1.queueDeclare(QIEIE_NAME,false,false, false, null);
        //准备发消息
        String massig="hello word";//初次使用
        //发送一个消费者
        /*1,第一个参数表示发送到哪个交换机 null表示不发送
            2，表示路由的Key值是什么
            3，表示其他参数信息
            4，表示发送的消息
        * */
        channel1.basicPublish("",QIEIE_NAME,null,massig.getBytes());
        System.out.println("消息发送完毕");

    }
}
