package com.douglei.core.dialect.db.table.entity;

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
		// TODO 索引还没有实现
		
	}
	
	@Override
	protected String getObjectName() {
		return "索引";
	}
	public IndexType getIndexType() {
		return indexType;
	}
}
