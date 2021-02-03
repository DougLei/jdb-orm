package com.douglei.orm.metadata.type;

import java.io.InputStream;

import com.douglei.orm.metadata.MetadataEntity;

/**
 * 元数据类型
 * @author DougLei
 */
public abstract class MetadataType {
	private String name;
	private String fileSuffix;
	private int priority;
	private boolean supportOpDatabaseObject;
	
	/**
	 * 
	 * @param name 类型名称, 必须唯一
	 * @param fileSuffix 类型支持的文件后缀, 必须唯一
	 * @param priority 优先级, 优先级越低越优先, 在同时操作多种类型的元数据时, 框架会按照优先级的顺序操作,  <b>注意, 如果是第三方扩展的类型, 该值要求不能小于70</b>
	 * @param supportOpDatabaseObject 当前类型是否支持操作数据库对象, 例如操作表, 存储过程, 视图等
	 */
	public MetadataType(String name, String fileSuffix, int priority, boolean supportOpDatabaseObject) {
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
	public abstract MetadataEntity parse(InputStream input) throws Exception;
	
	/**
	 * 获取类型名称
	 * @return
	 */
	public final String getName() {
		return name;
	}
	/**
	 * 获取类型支持的文件后缀
	 * @return
	 */
	public final String getFileSuffix() {
		return fileSuffix;
	}
	/**
	 * 获取类型的优先级
	 * @return
	 */
	public final int getPriority() {
		return priority;
	}
	/**
	 * 获取类型是否支持操作数据库对象
	 * @return
	 */
	public final boolean supportOpDatabaseObject() {
		return supportOpDatabaseObject;
	}

	@Override
	public String toString() {
		return "MetadataType [name=" + name + ", fileSuffix=" + fileSuffix + ", priority=" + priority + ", supportOpDatabaseObject=" + supportOpDatabaseObject + "]";
	}
}
