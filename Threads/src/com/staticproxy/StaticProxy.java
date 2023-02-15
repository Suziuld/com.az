package com.staticproxy;

/**
 * @Author:fs
 * @Date:2023/2/1423:19
 * 静态代理模式总结：
 * 真实对象和代理对象都要实现同一个接口
 * 代理对象要代理真实角色
 * 如果不使用代理对象就可以直接调用自己 You you = new You();
 * 代理对象的好处：代理对象可以做好多真实对象做不了的事情
 * 真实对象：真实对象只需要专注做好自己的事情
 * 例如多线程：new Therd( ()->System.out.println("要结婚了！");).start();
 * 同时可以写为:new WeddingCompany(new You()).HappyMarry();
 *
 * 真实对象：真实对象只需要专注做好自己的事情
 */
public class StaticProxy {
    //代理方法启动
    public static void main(String[] args) {
/*        WeddingCompany weddingCompany = new WeddingCompany(new You());
        weddingCompany.HappyMarry();*/
        new WeddingCompany(new You()).HappyMarry();
    }

    //创健静态代理接口
    interface Marry{
        //静态代理方法
        void HappyMarry();
    }
    //创建一个类实现静态代理接口方法
    //真实角色类
    static class You implements Marry{
        @Override
        public void HappyMarry() {
            System.out.println("要结婚了！");
        }
    }

    //创健第二个类实现静态代理接口
    //代理角色实现类
    static class WeddingCompany implements Marry{
        //代理的目标 Marry
        private Marry marry;

        public WeddingCompany(Marry marry) {
            this.marry = marry; //真实对象
        }

        @Override
        public void HappyMarry() {
            after();
            this.marry.HappyMarry();
            befor();
        }

        private void after() {
            System.out.println("结婚前的布置现场");
        }

        private void befor() {
            System.out.println("结婚之后，收尾款");
        }
    }
}
