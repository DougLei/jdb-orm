package com.douglei.orm.session;

import java.util.List;

public class Classes {
	private String id;
	private String pid;
	private String name;
	private List<Classes> subClasses;											
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Classes> getSubClasses() {
		return subClasses;
	}
	public void setSubClasses(List<Classes> subClasses) {
		this.subClasses = subClasses;
	}
}
