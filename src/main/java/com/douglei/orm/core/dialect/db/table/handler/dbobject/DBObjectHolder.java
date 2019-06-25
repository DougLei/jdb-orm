package com.douglei.orm.core.dialect.db.table.handler.dbobject;

import com.douglei.orm.core.dialect.db.table.handler.OPType;

/**
 * 数据库对象持有者
 * @author DougLei
 */
public class DBObjectHolder {
	private Object dbObject;
	private DBObjectType dbObjectType;
	private OPType dbObjectOPType;
	
	public DBObjectHolder(Object dbObject, DBObjectType dbObjectType, OPType dbObjectOPType) {
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
	public OPType getDbObjectOPType() {
		return dbObjectOPType;
	}
}
