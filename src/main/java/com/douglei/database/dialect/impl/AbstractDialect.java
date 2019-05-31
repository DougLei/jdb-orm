package com.douglei.database.dialect.impl;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.db.database.DatabaseSqlStatementHandler;
import com.douglei.database.dialect.db.features.FeaturesHolder;
import com.douglei.database.dialect.db.objectname.DBObjectNameHandler;
import com.douglei.database.dialect.db.sql.SqlHandler;
import com.douglei.database.dialect.db.table.TableHandler;
import com.douglei.database.dialect.db.table.TableSqlStatementHandler;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	protected AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	
	protected static final TableHandler tableHandler = new TableHandler();
	protected SqlHandler sqlHandler;
	protected DBObjectNameHandler dbObjectNameHandler;
	protected FeaturesHolder featuresHolder;
	protected DatabaseSqlStatementHandler databaseSqlStatementHandler;
	protected TableSqlStatementHandler tableSqlStatementHandler;
	
	private boolean isInitialized;
	public AbstractDialect() {
		if(!isInitialized) {
			initialPropertyInstance(getClass().getPackage().getName());
			isInitialized = true;
		}
	}
	protected void initialPropertyInstance(String basePackageName) {
		dataTypeHandlerMapping = (AbstractDataTypeHandlerMapping) ConstructorUtil.newInstance(basePackageName + ".datatype.handler.DataTypeHandlerMapping");
		
		sqlHandler = (SqlHandler) ConstructorUtil.newInstance(basePackageName + ".db.sql.SqlHandlerImpl");
		dbObjectNameHandler = (DBObjectNameHandler) ConstructorUtil.newInstance(basePackageName + ".db.objectname.DBObjectNameHandlerImpl");
		featuresHolder = (FeaturesHolder) ConstructorUtil.newInstance(basePackageName + ".db.features.FeaturesHolderImpl");
		tableSqlStatementHandler = (TableSqlStatementHandler) ConstructorUtil.newInstance(basePackageName + ".db.table.TableSqlStatementHandlerImpl");
		databaseSqlStatementHandler = (DatabaseSqlStatementHandler) ConstructorUtil.newInstance(basePackageName + ".db.database.DatabaseSqlStatementHandlerImpl");
	}

	@Override
	public AbstractDataTypeHandlerMapping getDataTypeHandlerMapping() {
		return dataTypeHandlerMapping;
	}
	
	@Override
	public TableHandler getTableHandler() {
		return tableHandler;
	}
	
	@Override
	public SqlHandler getSqlHandler() {
		return sqlHandler;
	}
	
	@Override
	public DBObjectNameHandler getDBObjectNameHandler() {
		return dbObjectNameHandler;
	}

	@Override
	public FeaturesHolder getFeaturesHolder() {
		return featuresHolder;
	}

	@Override
	public DatabaseSqlStatementHandler getDatabaseSqlStatementHandler() {
		return databaseSqlStatementHandler;
	}

	@Override
	public TableSqlStatementHandler getTableSqlStatementHandler() {
		return tableSqlStatementHandler;
	}
}
