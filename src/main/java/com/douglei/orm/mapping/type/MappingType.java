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
	private boolean opDatabaseStruct;
	private boolean opMappingContainer;
	
	/**
	 * 
	 * @param name 映射类型的名称, 唯一的
	 * @param fileSuffix 映射类型对应文件的后缀, 唯一的
	 * @param priority 优先级, 优先级越低越优先, 在操作多个映射时, 会按照优先级的顺序操作, 注意, 如果是第三方用来扩映射类型, 该值不能小于等于60
	 * @param opDatabaseStruct 当前类型能否操作数据库结构, 例如操作表结构, 操作存储过程结构等
	 * @param opMappingContainer 当前类型能否操作映射容器, 即通过映射容器, 对映射进行增删改查
	 */
	public MappingType(String name, String fileSuffix, int priority, boolean opDatabaseStruct, boolean opMappingContainer) {
		this.name = name;
		this.fileSuffix = fileSuffix;
		this.priority = priority;
		this.opDatabaseStruct = opDatabaseStruct;
		this.opMappingContainer = opMappingContainer;
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
	public final boolean opDatabaseStruct() {
		return opDatabaseStruct;
	}
	public final boolean opMappingContainer() {
		return opMappingContainer;
	}

	@Override
	public String toString() {
		return "MappingType [name=" + name + ", fileSuffix=" + fileSuffix + ", priority=" + priority + ", opDatabaseStruct=" + opDatabaseStruct + ", opMappingContainer=" + opMappingContainer + "]";
	}
}
