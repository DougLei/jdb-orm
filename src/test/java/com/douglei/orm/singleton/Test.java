package com.douglei.orm.singleton;

import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

public class Test {
	public static void main(String[] args) {
//		JdkSerializeProcessor.serialize2File(User.getSingleton(), "C:\\Users\\Administrator.USER-20200530ZD\\Desktop\\user");
		
		User u = JdkSerializeProcessor.deserializeFromFile(User.class, "C:\\Users\\Administrator.USER-20200530ZD\\Desktop\\user");
		System.out.println(u);
		System.out.println(User.getSingleton());
		
	}
}
