package com.douglei.orm.mapping.type;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;

/**
 * 映射类型
 * @author DougLei
 */
public abstract class MappingType {
	private String name;
	private String fileSuffix;
	private int priority;
	private boolean supportOpDatabaseObject;
	
	/**
	 * 
	 * @param name 映射类型的名称, 唯一的
	 * @param fileSuffix 映射类型对应文件的后缀, 唯一的
	 * @param priority 优先级, 优先级越低越优先, 在操作多个映射时, 会按照优先级的顺序操作,  <b>注意, 如果是第三方用来扩映射类型, 该值要求不能小于等于60</b>
	 * @param supportOpDatabaseObject 当前类型是否支持操作数据库对象, 例如操作表, 存储过程, 视图等
	 */
	public MappingType(String name, String fileSuffix, int priority, boolean supportOpDatabaseObject) {
		this.name = name;
		this.fileSuffix = fileSuffix;
		this.priority = priority;
		this.supportOpDatabaseObject = supportOpDatabaseObject;
	}
	
	/**
	 * 解析
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract Mapping parse(InputStream input) throws Exception;
	
	public final String getName() {
		return name;
	}
	public final String getFileSuffix() {
		return fileSuffix;
	}
	public final int getPriority() {
		return priority;
	}
	public final boolean supportOpDatabaseObject() {
		return supportOpDatabaseObject;
	}

	@Override
	public String toString() {
		return "MappingType [name=" + name + ", fileSuffix=" + fileSuffix + ", priority=" + priority + ", supportOpDatabaseObject=" + supportOpDatabaseObject + "]";
	}
}
