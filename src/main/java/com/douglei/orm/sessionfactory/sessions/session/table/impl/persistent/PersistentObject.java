package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableDeleteSql;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableInsertSql;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableTableSql;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableUpdateSql;

/**
 * 
 * @author DougLei
 */
public class PersistentObject extends AbstractPersistentObject{
	private Operation operation;
	private boolean updateNullValue; // 修改时使用
	
	/**
	 * 
	 * @param tableMetadata 
	 * @param originObject 要操作的源数据实例
	 * @param operation  
	 */
	public PersistentObject(TableMetadata tableMetadata, Object originObject, Operation operation) {
		super(tableMetadata);
		super.setOriginObject(originObject);
		this.operation = operation;
	}
	
	/**
	 * 设置是否更新null值; operation == Operation.UPDATE时生效
	 * @param updateNullValue
	 */
	public void setUpdateNullValue(boolean updateNullValue) {
		if(operation == Operation.UPDATE)
			this.updateNullValue = updateNullValue;
	}
	
	/**
	 * 获取ExecutableTableSql实例
	 * @return
	 */
	public ExecutableTableSql getExecutableTableSql() {
		switch(operation) {
			case INSERT:
				return new ExecutableInsertSql(tableMetadata, objectMap);
			case DELETE:
				return new ExecutableDeleteSql(tableMetadata, objectMap);
			case UPDATE:
				return new ExecutableUpdateSql(tableMetadata, objectMap, updateNullValue);
		}
		return null;
	}
	
	/**
	 * 获取具体的操作
	 * @return
	 */
	public Operation getOperation() {
		return operation;
	}
	
	@Override
	public String toString() {
		return "PersistentObject [operation=" + operation + ", originObject=" + originObject + "]";
	}
}
