package com.douglei.orm.singleton;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 6111882281007893271L;
	private static final User singleton = new User("Douglei", 30);
	public static User getSingleton() {
		return singleton;
	}
	
	private String name;
	private int age;

	private User(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	
	// 在反序列化时, 防止单例模式被破坏
	public Object readResolve() {
		return singleton;
	}
}
