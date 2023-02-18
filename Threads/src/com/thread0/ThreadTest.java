package com.thread0;

/**
 * @Author:fs
 * @Date:2023/2/1418:38
 */
//创健线程方式1 继承Thread类
public class ThreadTest extends Thread{

    //Run方法线程体
    @Override
    public void run() {
        for (int i = 0; i <300 ; i++) {
            System.out.println("Run方法线程体+++++++++ " + i);
        }
    }
    //main主方法体
    public static void main(String[] args) {

        ThreadTest ts = new ThreadTest();
        //开启线程方法，当线程调用start方法时，线程和main主方法体一起运行！
        ts.start();

        for (int i = 0; i < 300; i++) {
            System.out.println("main方法主方法体 " + i);
        }


    }
}
