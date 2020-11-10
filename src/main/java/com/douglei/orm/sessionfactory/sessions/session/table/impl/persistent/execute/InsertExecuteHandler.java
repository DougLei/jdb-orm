package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.dialect.impl.oracle.object.pk.sequence.OraclePrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 
 * @author DougLei
 */
public class InsertExecuteHandler extends TableExecuteHandler{
	private Object originObject;
	
	public InsertExecuteHandler(TableMetadata tableMetadata, Map<String, Object> objectMap, Object originObject) {
		setBaseInfo(tableMetadata, objectMap);
		this.originObject = originObject;
		initial();
	}
	
	@Override
	protected void initial() {
		StringBuilder insertSql = new StringBuilder(300);
		insertSql.append("insert into ").append(tableMetadata.getName()).append("(");
		
		StringBuilder valuesSql = new StringBuilder();
		valuesSql.append(" values(");
		
		tableMetadata.setPrimaryKeyValue2ObjectMap(objectMap, originObject);
		parameters = new ArrayList<Object>(objectMap.size());
		
		ColumnMetadata column = null;
		for (Entry<String, Object> entry : objectMap.entrySet()) {
			if(entry.getValue() != null) {// 只保存不为空的值, 空值不处理
				column = tableMetadata.getColumns_().get(entry.getKey());
				insertSql.append(column.getName()).append(',');
				if(entry.getValue() instanceof OraclePrimaryKeySequence) {
					valuesSql.append(((OraclePrimaryKeySequence)entry.getValue()).getNextvalSql()).append(',');
				}else {
					valuesSql.append("?,");
					parameters.add(new InputSqlParameter(entry.getValue(), column.getDBDataType()));
				}
			}
		}
		
		// 去掉最后一个,(逗号)  并将insertSql和valuesSql拼接起来
		insertSql.setLength(insertSql.length()-1); 
		valuesSql.setLength(valuesSql.length()-1);
		this.sql = insertSql.append(")").append(valuesSql).append(")").toString();
	}
}
