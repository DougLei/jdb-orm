package com.douglei.core.dialect.db.table.op.create;

import java.util.Collection;

import com.douglei.core.dialect.db.table.entity.Column;
import com.douglei.core.dialect.db.table.entity.Constraint;
import com.douglei.core.metadata.table.CreateMode;
import com.douglei.core.metadata.table.TableMetadata;

/**
 * 
 * @author DougLei
 */
public class TableCreator {

	private CreateMode createMode;
	
	private String tableName;
	private Collection<Column> columns;
	private Collection<Constraint> constraints;
	
	public TableCreator(TableMetadata tableMetadata) {
		this.createMode = tableMetadata.getCreateMode();
		
		this.tableName = tableMetadata.getName();
		this.columns = tableMetadata.getColumns();
		this.constraints = tableMetadata.getConstraints();
	}

	public CreateMode getCreateMode() {
		return createMode;
	}
	public String getTableName() {
		return tableName;
	}
	public Collection<Column> getColumns() {
		return columns;
	}
	public Collection<Constraint> getConstraints() {
		return constraints;
	}
}
