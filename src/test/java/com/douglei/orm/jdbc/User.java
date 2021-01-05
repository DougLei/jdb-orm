package com.douglei.orm.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String threadName;
	private int id;
	private String name;
	public User(String threadName, ResultSet resultset) {
		this.threadName = threadName;
		try {
			resultset.next();
			this.id = resultset.getInt(1);
			this.name = resultset.getString(2);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "User [threadName=" + threadName + ", id=" + id + ", name=" + name + "]";
	}
}
