package com.dead;

/**
 * @Author:fs
 * @Date:2023/2/150:54
 * 线程死锁：多个线程互相抱着对方需要的资源，形成僵持
 * 使用线程的synchronized()方法给方法上锁
 * 1，	互斥条件：一个资源每次只能被一个进程使用
 * 2，	请求与保持条件：一个进程请求资源而阻塞时，对已获得的资源保持不放。
 * 3，	不剥夺条件：进程已获取的资源，在未使用完之前，不能强行剥夺
 * 4，	循环等待条件：若干进程之间形成一种头尾相接的循环等到资源关系
 * 死锁解决方发：先获取一个锁然后再释放，然后再获取再释放，通俗就是我必须先释放然后才能获取下一个锁
 */
public class DeadThreadTest {

    public static void main(String[] args) {
        MakeUp makeUp1 = new MakeUp(0, "灰姑娘");
        MakeUp makeUp2 = new MakeUp(1, "白雪公主");

        makeUp1.start();
        makeUp2.start();
    }
}
//口红
class Lipstick{

}
//镜子
class Mirror{

}
class MakeUp extends Thread{
    //需要的资源只有一份，用static来保证使用的资源只有一份
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();
    int choice;
    String girName;

    MakeUp(int choice,String girName){
        this.choice = choice;
        this.girName = girName;
    }
    @Override
    public void run() {

        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //化妆，互相持有对方的锁，就是要拿到对象的资源
    private void makeup() throws InterruptedException {
        //获取口红的锁
        if (choice ==0){
            synchronized (lipstick){
                System.out.println(this.girName+"获取口红的锁");
                Thread.sleep(1000);
            }
            //1秒后获取镜子的锁
            synchronized (mirror){
                System.out.println(this.girName+"获取镜子的锁");
            }
        }else {
            //获取镜子的锁
            synchronized (mirror){

                System.out.println(this.girName+"获取镜子的锁");
                Thread.sleep(2000);
            }
            //1秒后获取口红的锁
            synchronized (lipstick){
                System.out.println(this.girName+"获取口红的锁");
            }
        }
    }
}
