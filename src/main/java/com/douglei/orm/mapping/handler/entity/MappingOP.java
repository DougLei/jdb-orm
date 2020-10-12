package com.douglei.orm.mapping.handler.entity;

/**
 * mapping的操作
 * @author DougLei
 */
public enum MappingOP {
	
	/**
	 * 添加或覆盖
	 */
	ADD_OR_COVER,
	
	/**
	 * 删除
	 */
	DELETE,
	
	/**
	 * 只删除数据库结构, 和映射容器没有关系, 目前是针对存储过程和视图
	 */
	DELETE_DATABASE_STRUCT_ONLY;
}
