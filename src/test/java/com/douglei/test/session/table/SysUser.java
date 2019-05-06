package com.douglei.test.session.table;

import java.util.ArrayList;
import java.util.List;

public class SysUser {
	private String id;
	private String name;
	private int age;
	private String sex;
	
	public SysUser() {
	}
	public SysUser(String id, String name, int age, String sex) {
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
	private static List<SysUser> list = new ArrayList<SysUser>();
	static {
		list.add(new SysUser("1", "石磊", 28, "男"));
		list.add(new SysUser("2", "DougLei", 27, "女"));
	}
	
	public static List<SysUser> getList(){
		return list;
	}
	
	@Override
	public String toString() {
		return "SysUser [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
}
