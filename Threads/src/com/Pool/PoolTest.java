package com.Pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:fs
 * @Date:2023/2/151:28
 * 线程池的创建需要 ExecutroService 和 Excutros
 * 线程池：1，创建线程池
 */
public class PoolTest {
    public static void main(String[] args) {
        //创建线程池，参数为线程池的大小
        ExecutorService service = Executors.newFixedThreadPool(10);
        //执行 service.execute
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());
        //关闭服务
        service.shutdown();
    }
}
class MyThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+i+"线程的名字-----");
        }
    }
}
