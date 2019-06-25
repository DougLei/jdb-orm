package com.douglei.orm.core.dialect.db.table.handler.dbobject;


/**
 * 数据库对象持有者
 * @author DougLei
 */
public class DBObjectHolder {
	private Object dbObject;
	private DBObjectType dbObjectType;
	private DBObjectOPType dbObjectOPType;
	
	public DBObjectHolder(Object dbObject, DBObjectType dbObjectType, DBObjectOPType dbObjectOPType) {
		this.dbObject = dbObject;
		this.dbObjectType = dbObjectType;
		this.dbObjectOPType = dbObjectOPType;
	}
	
	public Object getDbObject() {
		return dbObject;
	}
	public DBObjectType getDbObjectType() {
		return dbObjectType;
	}
	public DBObjectOPType getDbObjectOPType() {
		return dbObjectOPType;
	}
}
