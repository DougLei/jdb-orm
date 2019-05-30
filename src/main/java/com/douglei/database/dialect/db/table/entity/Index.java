package com.douglei.database.dialect.db.table.entity;

/**
 * 
 * @author DougLei
 */
public class Index extends DBObject{
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
}
