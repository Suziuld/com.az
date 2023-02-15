package com.lamda;

/**
 * @Author:fs
 * @Date:2023/2/150:18
 *
 * 带参数的Lamda表达式
 */
public class LamdaTest2 {


    public static void main(String[] args) {
        Ilove love = null;
/*        //1,带参数类型的lamda表达式
        Ilove love = (int a) ->{
                System.out.println("i love me1----->"+a);
            };
        love.love(5);
        //2,不带参数类型的lamda表达式
        love = (a)->{
            System.out.println("i love me1----->"+a);
        };
       love.love(77); */

        //3,简化三
        love = a -> {
            System.out.println("i love me1----->"+a);
        };
//        love.love(55);

        //4,在3的基础上继续简化
        love = a -> System.out.println("i love me1----->"+a);
        love.love(5);
        };
    //总结：lamda表达式只能有一行代码的时候才可以简化为一行，如果有多行就用代码块包裹
    }
interface Ilove{
    void love(int a);
}

