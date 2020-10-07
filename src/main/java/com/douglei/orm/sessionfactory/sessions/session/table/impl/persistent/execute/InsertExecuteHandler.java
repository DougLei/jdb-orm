package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.dialect.impl.oracle.db.object.pk.sequence.OraclePrimaryKeySequence;
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
		
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		
		tableMetadata.setPrimaryKeyValue2ObjectMap(objectMap, originObject);
		parameters = new ArrayList<Object>(objectMap.size());
		
		boolean isFirst = true;
		Object value = null;
		ColumnMetadata column = null;
		Set<String> codes = objectMap.keySet();
		for (String code : codes) {
			value = objectMap.get(code);
			if(value != null) {// 只保存不为空的值, 空值不需要处理
				if(isFirst) {
					isFirst = false;
				}else {
					insertSql.append(",");
					values.append(",");
				}
				column = tableMetadata.getColumnByCode(code);

				insertSql.append(column.getName());
				if(value instanceof OraclePrimaryKeySequence) {
					values.append(((OraclePrimaryKeySequence)value).getNextvalSql());
				}else {
					values.append("?");
					parameters.add(new InputSqlParameter(value, column.getDBDataType()));
				}
			}
		}
		insertSql.append(")").append(values).append(")");
		this.sql = insertSql.toString();
	}
}
