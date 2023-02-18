package com.az;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author:Sz
 * @Date:2023/2/1817:343
 * 消费者代码：接收消息
 */
public class Consumer {
    //队列名称跟生产者对应
    public static final String QUEUE_NAME = "hello";
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory connMq = new ConnectionFactory();
        //创建IP连接地址
        connMq.setHost("127.0.0.1");
        //设置用户名
        connMq.setUsername("guest");
        //设置密码
        connMq.setPassword("guest");
        //创建连接
        Connection conn = connMq.newConnection();
        //创建信道，进行消费者消费
        Channel channel2 = conn.createChannel();

        //声明 使用lanmda表达式 接收消息
        DeliverCallback deliverCallback = (var1,var2) ->{
            System.out.println(new String(var2.getBody()));
        };
        //取消消息回调 lanmbda
        CancelCallback cancelCallback = (var1) -> {
            System.out.println("消费消息被中断="+var1);
        };

        //消息接收
        /*
        * 1,第一个参数：消费哪个队列
        * 2,第二个参数：消费成功后是否要自动答(true)或者手动答(flase)
        * 3,第三个参数：消费者未成功消费回调
        * 4,消费者取消消费的回调
        * */
        channel2.basicConsume(QUEUE_NAME, true,deliverCallback,cancelCallback);
    }

}
