package com.douglei.database.dialect.db.table;

import java.util.Collection;
import java.util.List;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.CreateMode;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.database.metadata.table.column.extend.ColumnIndex;

/**
 * 
 * @author DougLei
 */
public class TableCreator {
	private CreateMode createMode;
	
	private String tableName;
	private Collection<ColumnMetadata> columns;
	private List<ColumnConstraint> constraints;
	private List<ColumnIndex> indexes;
	
	public TableCreator(TableMetadata tableMetadata) {
		this.createMode = tableMetadata.getCreateMode();
		
		this.tableName = tableMetadata.getName();
		this.columns = tableMetadata.getColumnMetadatas();
		this.constraints = tableMetadata.getConstraints();
		this.indexes = tableMetadata.getIndexes();
	}

	public CreateMode getCreateMode() {
		return createMode;
	}
	public String getTableName() {
		return tableName;
	}
	public Collection<ColumnMetadata> getColumns() {
		return columns;
	}
	public List<ColumnConstraint> getConstraints() {
		return constraints;
	}
	public List<ColumnIndex> getIndexes() {
		return indexes;
	}
}
