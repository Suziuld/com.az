package cn.hp.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 随机数的工具类
 */
public class RandomNumber {


	private static Integer timeInt= 60*60;


	/**
	 * 生成随机 小时秒数
	 * @param max 最大值（没有的时候按10来算）
	 * @return
	 */
	public static long randomNum(Integer max) {
		if(max==null){
			max=24;
		}
		Random random= new Random(); // 定义随机类
		int  result=random.nextInt( max); // 返回[0,10)集合中的整数，注意不包括10
		return (result+1)*timeInt;
	}

}

