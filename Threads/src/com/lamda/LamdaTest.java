package com.lamda;

/**
 * @Author:fs
 * @Date:2023/2/1423:52
 *
 * 推导Lamda表达式
 */

public class LamdaTest {
    //3,实现静态内部类
    static class Like2 implements Ilike{
        @Override
        public void lamda() {
            System.out.println("i like lamda2");
        }
    }

    public static void main(String[] args) {
        Ilike ilike = new Like();
        ilike.lamda();

        ilike = new Like2();
        ilike.lamda();



        //4,局部内部类
         class Like3 implements Ilike{
            @Override
            public void lamda() {
                System.out.println("i like lamda3");
            }
        }

         ilike = new Like3();
         ilike.lamda();



         //5，匿名内部类,没有类的名称，必须借助接口或者父类
        ilike = new Ilike() {
            @Override
            public void lamda() {
                System.out.println("i like lamda4");
            }
        };
        ilike.lamda();


        //6,用lamda简化
        ilike = ()->{
            System.out.println("i like lamda5");
        };ilike.lamda();
    }
}
//1，定义一个函数式接口
interface Ilike{
    void lamda();
}

//2,实现函数式接口
class Like implements Ilike{

    @Override
    public void lamda() {
        System.out.println("i like lamda");
    }
}
