package com.douglei.orm.core.dialect.db.table.handler.dbobject;


/**
 * 数据库对象持有者
 * @author DougLei
 */
public class DBObjectHolder {
	private Object dbObject;
	private Object dbObject2;
	private DBObjectType dbObjectType;
	private DBObjectOPType dbObjectOPType;
	
	public DBObjectHolder(Object dbObject, DBObjectType dbObjectType, DBObjectOPType dbObjectOPType) {
		this(dbObjectOPType, null, dbObjectType, dbObjectOPType);
	}
	public DBObjectHolder(Object dbObject, Object dbObject2, DBObjectType dbObjectType, DBObjectOPType dbObjectOPType) {
		this.dbObject = dbObject;
		this.dbObject2 = dbObject2;
		this.dbObjectType = dbObjectType;
		this.dbObjectOPType = dbObjectOPType;
	}
	
	public Object getDbObject() {
		return dbObject;
	}
	public Object getDbObject2() {
		return dbObject2;
	}
	public DBObjectType getDbObjectType() {
		return dbObjectType;
	}
	public DBObjectOPType getDbObjectOPType() {
		return dbObjectOPType;
	}
}
