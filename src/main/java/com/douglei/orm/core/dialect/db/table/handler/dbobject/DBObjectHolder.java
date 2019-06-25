package com.douglei.orm.core.dialect.db.table.handler.dbobject;


/**
 * 数据库对象持有者
 * @author DougLei
 */
public class DBObjectHolder {
	/*
	 * originObject用来记录对表、约束、索引直接操作时的对象
	 * targetObject和originObject搭配使用, 当修改表名、列名、列数据类型等时, 用来记录目标数据对象和原数据对象
	 */
	
	private Object originObject;// 原数据对象
	private Object targetObject;// 目标数据对象
	private DBObjectType dbObjectType;
	private DBObjectOPType dbObjectOPType;
	
	public DBObjectHolder(Object originObject, DBObjectType dbObjectType, DBObjectOPType dbObjectOPType) {
		this(originObject, null, dbObjectType, dbObjectOPType);
	}
	public DBObjectHolder(Object originObject, Object targetObject, DBObjectType dbObjectType, DBObjectOPType dbObjectOPType) {
		this.originObject = originObject;
		this.targetObject = targetObject;
		this.dbObjectType = dbObjectType;
		this.dbObjectOPType = dbObjectOPType;
	}
	
	public Object getOriginObject() {
		return originObject;
	}
	public Object getTargetObject() {
		return targetObject;
	}
	public DBObjectType getDbObjectType() {
		return dbObjectType;
	}
	public DBObjectOPType getDbObjectOPType() {
		return dbObjectOPType;
	}
}
