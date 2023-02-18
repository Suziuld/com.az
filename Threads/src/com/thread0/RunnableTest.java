package com.thread0;

/**
 * @Author:fs
 * @Date:2023/2/1419:26
 *
 * 线程实现方法2：实现Runnable类，重写run，执行线程需要放入Runnable接口实现类，然后调用start方法
 */
public class RunnableTest implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 2000; i++) {
            System.out.println("Runnable线程执行了+++++"+i);
        }
    }

    public static void main(String[] args) {

/*        //创健Runnable的实现类
        RunnableTest runnableTest = new RunnableTest();
        //创健线程对象，开启线程
        Thread thread = new Thread(runnableTest);
        thread.start();*/

        //简写
        new Thread(new RunnableTest()).start();
        for (int i = 0; i < 2000; i++) {
            System.out.println("main主题方法执行了+++++"+i);
        }
    }
}
