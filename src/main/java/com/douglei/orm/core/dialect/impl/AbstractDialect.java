package com.douglei.orm.core.dialect.impl;

import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.db.features.DBFeatures;
import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.dialect.db.table.handler.TableSqlStatementHandler;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	
	protected AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	protected SqlHandler sqlHandler;
	protected DBObjectHandler dbObjectHandler;
	protected DBFeatures dbFeatures;
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
		dbObjectHandler = (DBObjectHandler) ConstructorUtil.newInstance(basePackageName + ".db.object.DBObjectHandlerImpl");
		dbFeatures = (DBFeatures) ConstructorUtil.newInstance(basePackageName + ".db.features.DBFeaturesImpl");
		tableSqlStatementHandler = (TableSqlStatementHandler) ConstructorUtil.newInstance(basePackageName + ".db.table.TableSqlStatementHandlerImpl");
	}

	@Override
	public AbstractDataTypeHandlerMapping getDataTypeHandlerMapping() {
		return dataTypeHandlerMapping;
	}
	
	@Override
	public SqlHandler getSqlHandler() {
		return sqlHandler;
	}
	
	@Override
	public DBObjectHandler getDBObjectHandler() {
		return dbObjectHandler;
	}

	@Override
	public DBFeatures getDBFeatures() {
		return dbFeatures;
	}

	@Override
	public TableSqlStatementHandler getTableSqlStatementHandler() {
		return tableSqlStatementHandler;
	}
}
