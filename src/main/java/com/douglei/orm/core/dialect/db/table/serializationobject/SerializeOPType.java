package com.douglei.orm.core.dialect.db.table.serializationobject;

/**
 * 序列化操作类型
 * @author DougLei
 */
public enum SerializeOPType {
	
	/**
	 * 在create/drop_create表时, 将最新的TableMetadata对象序列化到磁盘中, 如果之前存在, 则覆盖
	 * 如果出现异常回滚, 则将序列化文件删除即可, 但不会恢复之前被覆盖的序列化文件
	 */
	CREATE,
	
	/**
	 * 在dynamic_update表时, 将最新的TableMetadata对象序列化到磁盘中, 如果之前存在, 则覆盖
	 * 如果出现异常回滚, 则将序列化文件删除, 再恢复之前被覆盖的序列化文件
	 */
	UPDATE,
	
	/**
	 * 在drop表时, 将序列化文件删除
	 * 如果出现异常回滚, 则恢复之前被删除的序列化文件
	 */
	DROP;
}
