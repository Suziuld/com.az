package com.thread0;

/**
 * @Author:fs
 * @Date:2023/2/1422:14
 * 多线程调用一个对象
 * 问题：当多个线程去拿同一个资源，线程变得不安全，导致数据紊乱(并发问题)
 */
//火车票例子
public class Thread4 implements Runnable{
    //票数
    private int ticketNums = 60 ;

    //Thread.currentThread().getName()表示拿到当前线程的名字
    @Override
    public void run() {
        while (true){
            if (ticketNums <=0){
                break;
            }
            try {
                //Thread.sleep指模拟延迟
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"----->拿到了第"+ticketNums--+"票");
        }
    }

    public static void main(String[] args) {
        Thread4 t = new Thread4();
        new Thread(t,"A").start();
        new Thread(t,"B").start();
        new Thread(t,"C").start();
    }
}
