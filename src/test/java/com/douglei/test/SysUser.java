package com.douglei.test;

import java.util.ArrayList;
import java.util.List;

public class SysUser {
	private String id;
	private String name;
	private int age;
	private char sex;
	
	public SysUser() {
	}
	public SysUser(String id, String name, int age, char sex) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	
	
	private static List<SysUser> list = new ArrayList<SysUser>();
	static {
		list.add(new SysUser("1", "石磊", 28, '男'));
		list.add(new SysUser("2", "DougLei", 27, '女'));
	}
	
	public static List<SysUser> getList(){
		return list;
	}
}
