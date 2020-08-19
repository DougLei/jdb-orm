package com.douglei.orm.session;

import java.io.Serializable;

public class SysUser implements Serializable{
	private static final long serialVersionUID = 8240817972018054005L;
	private String id;
	private String name;
	private int age;
	private String sex;
	
	public SysUser() {}
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
	
//	@Override
//	public String toString() {
//		return "SysUser [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + "]";
//	}
}
