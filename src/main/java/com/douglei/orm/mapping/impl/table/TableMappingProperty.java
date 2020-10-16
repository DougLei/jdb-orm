package com.douglei.orm.mapping.impl.table;

import java.util.List;

import com.douglei.orm.mapping.MappingProperty;

/**
 * 
 * @author DougLei
 */
public class TableMappingProperty extends MappingProperty {
	private List<Column> columns;
	
	public TableMappingProperty(String code, String type) {
		super(code, type);
	}
	
	void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<Column> getColumns() {
		return columns;
	}
}
