package com.douglei.orm.mapping.handler.entity;

/**
 * mapping的操作
 * @author DougLei
 */
public enum MappingOP {
	
	/**
	 * 添加或覆盖
	 */
	ADD_OR_COVER(3),
	
	/**
	 * 删除
	 */
	DELETE(2),
	
	/**
	 * 只删除数据库对象, 和映射容器没有关系, 目前是针对存储过程和视图
	 */
	DELETE_DATABASE_OBJECT_ONLY(1);
	
	private int priority; // 执行的优先级, 优先级越低越优先, 在操作多个映射时, 会按照优先级的顺序操作
	private MappingOP(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}
}
