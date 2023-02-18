package com.thread0;

/**
 * @Author:fs
 * @Date:2023/2/1422:27
 * 龟兔赛跑
 */
public class Race implements Runnable{

    //胜利者 静态意思是必须保证只有一个winer
    private static String winer;
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            //模拟兔子睡觉 i%10 == 0 是指兔子每跑10步休息1ms
            if (Thread.currentThread().getName().equals("兔子") && i%10 == 0){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //判断比赛是否结束 这里的i为步数 判断100是否跑完
            boolean falg = gameOver(i);
            //判断falg是否为100，如果是就跳出程序，程序结束
            if (falg){
                break;
            }
            System.out.println(Thread.currentThread().getName()+"---->跑了"+i+"步");
        }

    }
    //判断比赛是否结束  steps(步数)
    public boolean gameOver(int steps){
    //判断是否有胜利者
        if(winer != null){ //已经存在胜利者
            return true;
        }{
            if (steps >= 100){//表示已经跑完100
                winer = Thread.currentThread().getName();
                System.out.println("winer 胜利者是"+winer);
                return true;
            }
        }
        //
        return false;
    }

    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race,"兔子").start();
        new Thread(race,"乌龟").start();
    }
}
