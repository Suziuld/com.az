package com.thread0;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author:fs
 * @Date:2023/2/1419:00
 */

//实现多线程下载同步
public class ThreadJPGTest extends Thread{
    private String url; //需要下载的目标的地址
    private String name; //下载后的文件名


    //构造器
    public  ThreadJPGTest(String url,String name){
        this.url = url; //当前的url
        this.name = name; //当前的文件名
    }

    //下载的执行体
    @Override
    public void run() {
        WebDownload webDownload = new WebDownload();
        webDownload.getJPG(url, name);
        System.out.println("下载的文件名："+name);
    }

    public static void main(String[] args) {
        ThreadJPGTest t1 = new ThreadJPGTest("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸1.jpg");
        ThreadJPGTest t2 = new ThreadJPGTest("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸2.jpg");
        ThreadJPGTest t3 = new ThreadJPGTest("https://ts1.cn.mm.bing.net/th/id/R-C.c94a0861fdc90cbac8e741d79fde61b1?rik=wVE9eIQbt98vbQ&riu=http%3a%2f%2fpic.bizhi360.com%2fbpic%2f42%2f3942.jpg&ehk=n6AHFfBNktKWKky2%2fNqf066DK0UMetqG%2f7k9iLrI1Ak%3d&risl=&pid=ImgRaw&r=0","壁纸3.jpg");

        //线程启动，执行顺序，t1，t2，t3
        t1.start();
        t2.start();
        t3.start();
    }
}
//下载器
class WebDownload{
    public void getJPG(String url,String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Io下载异常！！！！请检查getJPG方法");
        }
    }
}
