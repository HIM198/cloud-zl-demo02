package com.zl.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 
 * @author GQ (guoquan913@qq.com) 
 * @ClassName: UUIDUtil 
 * @Version 版本 V1.0
 * @ModifiedBy 修改人 
 * @Copyright GQ
 * @date 2015年4月13日 下午10:01:23 
 * @description
 */
public class UUIDUtil {
	
	public static String getUUID(){    
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	public static String getUUIDDHG(){    
		String uuid = UUID.randomUUID().toString().trim();
		return uuid;
	}

	/**
	 * 自定义规则生成32位编码
	 * 
	 * @return string
	 */
	public static String getUUIDByRules(String rules) {
		int rpoint = 0;
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int length = 32;
		for (int i = 0; i < length; i++) {
			if (rules != null) {
				rpoint = rules.length();
				int randNum = rand.nextInt(rpoint);
				generateRandStr.append(rules.substring(randNum, randNum + 1));
			}
		}
		return generateRandStr + "";
	}
}
