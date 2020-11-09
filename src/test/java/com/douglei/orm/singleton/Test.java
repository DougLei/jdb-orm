package com.douglei.orm.singleton;

public class Test {
	public static void main(String[] args) {
//		JdkSerializeProcessor.serialize2File(User.getSingleton(), "C:\\Users\\Administrator.USER-20200530ZD\\Desktop\\user");
		
//		User u = JdkSerializeProcessor.deserializeFromFile(User.class, "C:\\Users\\Administrator.USER-20200530ZD\\Desktop\\user");
//		System.out.println(u);
//		System.out.println(User.getSingleton());
		
		
		StringBuilder deleteSql = new StringBuilder("delete User where id=1 and name=2 and ");
		System.out.println(deleteSql);
		deleteSql.setLength(deleteSql.length()-5);
		System.out.println(deleteSql);
	}
}
