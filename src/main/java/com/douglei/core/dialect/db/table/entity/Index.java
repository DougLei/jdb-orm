package com.douglei.core.dialect.db.table.entity;

/**
 * 
 * @author DougLei
 */
public class Index extends DB_CI_Object{
	private IndexType indexType;

	public Index(IndexType indexType, String tableName) {
		super(tableName);
		this.indexType = indexType;
	}
	
	@Override
	protected void processDBObject() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getObjectName() {
		return "索引";
	}
	public IndexType getIndexType() {
		return indexType;
	}
	@Override
	public DBObjectType getDBObjectType() {
		return DBObjectType.INDEX;
	}
}
