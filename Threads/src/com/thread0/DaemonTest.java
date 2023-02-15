package com.thread0;

/**
 * @Author:fs
 * @Date:2023/2/150:44
 *
 * 守护线程：使用Thread中的thread.setDaemon方法进行线程守护
 * 例子:菩萨守护人
 */
public class DaemonTest {
    public static void main(String[] args) {
        God god = new God();
        You you = new You();

        Thread thread = new Thread(god);
        thread.setDaemon(true); //默认为false表示用户线程，正常的线程都是用户线程
        thread.start();//开启线程
        new Thread(you).start();
    }
}
//菩萨
class God implements Runnable{

    @Override
    public void run() {
        while (true){
            System.out.println("--------菩萨一直守护着你-------");
        }
    }
}

//人
class You implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 36500; i++) {
            System.out.println("------每一天都开心！！-----");
        }
        System.out.println("-----Bye word----");
    }
}
