package com.douglei.orm.core.dialect.db.table.handler.tablemapping;

import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 
 * @author DougLei
 */
public class TableMappingHolder {
	private List<Mapping> tableMappings;
	private List<String> tableMappingCodes;
	
	/**
	 * 	这个标识的是实际的操作
	 * 	如果是create, 那就是将映射加到系统中
	 * 	如果是drop, 那就是将映射从系统中移除
	 * 	因为是rollback时才用到该对象, 所以和其他holder的OPType操作相反
	 * 	因为在registerMapping时就已经进行了实际的添加或移除操作
	 */
	private TableMappingOPType tableMappingOPType;
	
	public TableMappingHolder(List<Mapping> tableMappings, List<String> tableMappingCodes) {
		if(tableMappings != null) {
			this.tableMappings = tableMappings;
			this.tableMappingOPType = TableMappingOPType.CREATE;
		}else if(tableMappingCodes != null) {
			this.tableMappingCodes = tableMappingCodes;
			this.tableMappingOPType = TableMappingOPType.DROP;
		}
	}
	
	public List<Mapping> getTableMappings() {
		return tableMappings;
	}
	public List<String> getTableMappingCodes() {
		return tableMappingCodes;
	}
	public TableMappingOPType getTableMappingOPType() {
		return tableMappingOPType;
	}
}
