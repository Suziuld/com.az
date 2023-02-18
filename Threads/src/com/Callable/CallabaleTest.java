package com.Callable;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.concurrent.*;

/**
 * @Author:fs
 * @Date:2023/2/1422:54
 * 线程创建方式3：实现Callabale接口
 * Callabale的好处：
 * 1，可以定义返回值
 * 2，可以抛出异常，但是需要用到其他类
 *  //1,创建执行服务：
 *         ExecutorService ser = Executors.newFixedThreadPool(3);
 *         //2,提交执行：
 *         Future<Boolean> result7 = ser.submit(callabaleTest7);
 *         Future<Boolean> result8 = ser.submit(callabaleTest8);
 *         Future<Boolean> result9 = ser.submit(callabaleTest9);
 *         //3,获取结果,此处的异常可以跑出也可以try/cath
 *         boolean r7 = result7.get();
 *         boolean r8 = result8.get();
 *         boolean r9 = result9.get();
 *         //打印结果
 *         System.out.println(r7);
 *         System.out.println(r8);
 *         System.out.println(r9);
 *         //关闭服务
 *         ser.shutdown();
 */
public class CallabaleTest implements Callable<Boolean> {


    private String url;
    private String name;
    //构造器
    public CallabaleTest(String url,String name){
        this.url = url;
        this.name = name;
    }

    //Callabale实现的方法
    @Override
    public Boolean call() throws Exception {
        //new 下载器然后传入参数，开启线程进行下载
        WebDownLoad2 webDownLoad2 = new WebDownLoad2();
        webDownLoad2.getJpg2(url, name);
        System.out.println("下载文件的名字:"+name);
        return true;
    }

    //下载器
    class WebDownLoad2{
        //下载方法
        public void getJpg2(String url,String name){
            try {
                FileUtils.copyURLToFile(new URL(url), new File(name));
            } catch (IOException e) {
                System.out.println("Runnable线程下的WebDownLoad载出现错误！！！！！！");
                e.printStackTrace();
            }
        }
    }

    //main主方法
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallabaleTest callabaleTest7 = new CallabaleTest("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸7.jpg");
        CallabaleTest callabaleTest8 = new CallabaleTest("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸8.jpg");
        CallabaleTest callabaleTest9 = new CallabaleTest("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸9.jpg");

        /*//线程启动
        new Thread(callabaleTest7).start();
        new Thread(callabaleTest8).start();
        new Thread(callabaleTest9).start();*/


        //Callabale线程启动ExecutorService
        //1,创建执行服务：
        ExecutorService ser = Executors.newFixedThreadPool(3);
        //2,提交执行：
        Future<Boolean> result7 = ser.submit(callabaleTest7);
        Future<Boolean> result8 = ser.submit(callabaleTest8);
        Future<Boolean> result9 = ser.submit(callabaleTest9);
        //3,获取结果,此处的异常可以跑出也可以try/cath
        boolean r7 = result7.get();
        boolean r8 = result8.get();
        boolean r9 = result9.get();
        //打印结果
        System.out.println(r7);
        System.out.println(r8);
        System.out.println(r9);
        //关闭服务
        ser.shutdown();


    }
}
