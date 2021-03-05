package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class ExecutableUpdateSql extends ExecutableTableSql{
	private boolean updateNullValue;
	
	public ExecutableUpdateSql(TableMetadata tableMetadata, Map<String, Object> objectMap, boolean updateNullValue) {
		super(tableMetadata, objectMap);
		this.updateNullValue = updateNullValue;
		installSQL();
	}

	/**
	 * 组装UpdateSQL
	 */
	protected void installSQL() {
		StringBuilder updateSql = new StringBuilder(300);
		updateSql.append("update ").append(tableMetadata.getName()).append(" set ");
		
		super.parameters = new ArrayList<Object>(objectMap.size());
		
		// 获取主键列名集合
		List<String> primaryKeyColumnNames = tableMetadata.getPrimaryKeyConstraint().getColumnNameList();
		
		// 拼装update set sql语句
		ColumnMetadata column = null;
		for (Entry<String, Object> entry : objectMap.entrySet()) {
			if(updateNullValue || entry.getValue() != null) {
				column = tableMetadata.getColumnMap4Code().get(entry.getKey());
				if(primaryKeyColumnNames.contains(column.getName())) // 主键数据不在这里处理
					continue;
				
				updateSql.append(column.getName()).append("=?,");
				parameters.add(new InputSqlParameter(entry.getValue(), column.getDBDataType()));
			}
		}
		updateSql.setLength(updateSql.length()-1);
		
		// 拼装where sql语句
		updateSql.append(" where ");
		for (String columnName : primaryKeyColumnNames) {
			updateSql.append(columnName).append("=?");
			
			column = tableMetadata.getColumnMap4Name().get(columnName);
			parameters.add(new InputSqlParameter(objectMap.get(column.getCode()), column.getDBDataType()));
			
			updateSql.append(" and ");
		}
		updateSql.setLength(updateSql.length()-5);
		
		super.sql = updateSql.toString();
	}
}
