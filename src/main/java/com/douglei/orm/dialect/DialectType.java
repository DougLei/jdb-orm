package com.douglei.orm.dialect;

import java.util.Arrays;

/**
 * 
 * @author DougLei
 */
public abstract class DialectType {
	private Class<? extends Dialect> targetClass;
	private int id;
	private String name;
	private int[] supportDatabaseMajorVersions;
	
	public DialectType(Class<? extends Dialect> targetClass, int id, String name, int... supportDatabaseMajorVersions) {
		this.targetClass = targetClass;
		this.id = id;
		this.name = name;
		this.supportDatabaseMajorVersions = supportDatabaseMajorVersions;
	}

	/**
	 * 获取唯一值
	 * @return
	 */
	public final int getId() {
		return id;
	}
	
	/**
	 * 获取对应方言的实现类
	 * @return
	 */
	public final Class<? extends Dialect> targetClass(){
		return targetClass;
	}
	
	/**
	 * 获取方言名
	 * @return
	 */
	public final String getName(){
		return name;
	}
	
	/**
	 * 支持的数据库主版本
	 * @return
	 */
	public int[] supportDatabaseMajorVersions() {
		return supportDatabaseMajorVersions;
	}
	
	/**
	 * 当前方言是否支持参数中的数据库
	 * @param key
	 * @return
	 */
	public final boolean support(DialectKey key) {
		if(!name.equals(key.getName())) 
			return false;
		
		for(int version : supportDatabaseMajorVersions) {
			if(version == key.getDatabaseMajorVersion())
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return " [class="+getClass().getName()+", id="+id+", name=" + name + ", supportDatabaseMajorVersions=" + Arrays.toString(supportDatabaseMajorVersions) + "] ";
	}
}
