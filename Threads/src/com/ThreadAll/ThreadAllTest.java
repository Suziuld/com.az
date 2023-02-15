package com.ThreadAll;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author:fs
 * @Date:2023/2/151:40
 * 线程的创建方式
 */
public class ThreadAllTest {
    public static void main(String[] args) {
        //启动线程1
        new Thread1().start();
        //启动线程2
        new Thread(new  Thread2()).start();
        //启动线程3
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Thread3());
        new Thread(futureTask).start();
        try {
            Integer integer = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        new Thread(new FutureTask<Integer>(new Thread3())).start();
    }
}
//1,继承Thread类
class Thread1 extends Thread{
    @Override
    public void run() {
        System.out.println("继承了Thread线程类-------");
    }
}
//2，实现Runnable接口
class Thread2 implements Runnable{

    @Override
    public void run() {
        System.out.println("实现了Runnable线程接口------");
    }
}
//3,实现Callabale接口
class Thread3 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("实现了Callabale线程接口");
        return 5;
    }
}

