package com.thread0;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author:fs
 * @Date:2023/2/1419:38
 */
public class RunnableDown implements Runnable{

    private String url;
    private String name;
    //构造器
    public RunnableDown(String url,String name){
        this.url = url;
        this.name = name;
    }
    //线程下载器
    @Override
    public void run() {
        //new 下载器然后传入参数，开启线程进行下载
        WebDownLoad1 webDownLoad1 = new WebDownLoad1();
        webDownLoad1.getJpg1(url, name);
        System.out.println("下载文件的名字:"+name);
    }

    //下载器
    class WebDownLoad1{
        //下载方法
        public void getJpg1(String url,String name){
            try {
                FileUtils.copyURLToFile(new URL(url), new File(name));
            } catch (IOException e) {
                System.out.println("Runnable线程下的WebDownLoad载出现错误！！！！！！");
                e.printStackTrace();
            }
        }
    }
    //main主方法
    public static void main(String[] args) {
        RunnableDown runnableDown4 = new RunnableDown("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸4.jpg");
        RunnableDown runnableDown5 = new RunnableDown("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸5.jpg");
        RunnableDown runnableDown6 = new RunnableDown("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸6.jpg");

        //线程启动
        new Thread(runnableDown4).start();
        new Thread(runnableDown5).start();
        new Thread(runnableDown6).start();
    }
}
