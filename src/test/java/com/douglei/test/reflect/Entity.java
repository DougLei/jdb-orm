package com.douglei.test.reflect;

public class Entity {
	private Entity() {}
	private static final Entity instance = new Entity();
	public static final Entity singleInstance() {
		System.out.println("进入了singleInstance");
		return instance;
	}
}
