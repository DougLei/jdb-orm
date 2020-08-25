package com.douglei.orm.session;

public class UserP {
	private SysUser user;
	public UserP(SysUser user) {
		super();
		this.user = user;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "UserP [user=" + user + "]";
	}
}
