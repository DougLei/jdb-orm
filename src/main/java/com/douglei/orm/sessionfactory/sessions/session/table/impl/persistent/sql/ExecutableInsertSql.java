package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.DatabaseNameConstants;
import com.douglei.orm.mapping.impl.table.metadata.AutoincrementPrimaryKey;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class ExecutableInsertSql extends AbstractExecutableTableSql{
	
	public ExecutableInsertSql(TableMetadata tableMetadata, Map<String, Object> objectMap) {
		super(tableMetadata, objectMap);
		super.parameters = new ArrayList<Object>(objectMap.size());
		installSQL();
	}
	
	/**
	 * 组装InsertSQL
	 */
	private void installSQL() {
		StringBuilder insertSQL = new StringBuilder(300);
		insertSQL.append("insert into ").append(tableMetadata.getName()).append("(");
		
		StringBuilder valuesSQL = new StringBuilder(100);
		valuesSQL.append(" values(");
		
		// Oracle数据库下, 自增序列拼接相关的SQL
		AutoincrementPrimaryKey apk = tableMetadata.getAutoincrementPrimaryKey();
		if(apk != null && EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) {
			insertSQL.append(apk.getColumn()).append(',');
			valuesSQL.append(apk.getSequence()).append(".nextval,");
		}
		
		// 只保存不为空, 且非自增主键的值
		for (Entry<String, Object> entry : objectMap.entrySet()) {
			if(entry.getValue() == null || (apk != null && entry.getKey().equals(apk.getCode())))
				continue; 
			
			ColumnMetadata column = tableMetadata.getColumnMap4Code().get(entry.getKey());
			insertSQL.append(column.getName()).append(',');
			valuesSQL.append("?,");
			parameters.add(new InputSqlParameter(entry.getValue(), column.getDBDataType()));
		}
		
		// 去掉最后一个,(逗号)  并将insertSQL和valuesSQL拼接起来
		insertSQL.setLength(insertSQL.length()-1); 
		valuesSQL.setLength(valuesSQL.length()-1);
		super.sql = insertSQL.append(")").append(valuesSQL).append(")").toString();
	}
}
