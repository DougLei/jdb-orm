package com.douglei.database.dialect.db.table;

import java.util.Collection;
import java.util.List;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.CreateMode;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.metadata.table.column.extend.Constraint;
import com.douglei.database.metadata.table.column.extend.Index;

/**
 * 
 * @author DougLei
 */
public class TableCreator {
	private CreateMode createMode;
	
	private String tableName;
	private Collection<ColumnMetadata> columns;
	private List<Constraint> constraints;
	private List<Index> indexes;
	
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
	public List<Constraint> getConstraints() {
		return constraints;
	}
	public List<Index> getIndexes() {
		return indexes;
	}
}
