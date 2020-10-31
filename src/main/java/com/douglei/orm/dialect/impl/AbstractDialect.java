package com.douglei.orm.dialect.impl;

import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.DataTypeContainer;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.sqlhandler.SqlQueryHandler;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.tools.instances.resource.scanner.impl.ClassScanner;
import com.douglei.tools.utils.reflect.ClassLoadUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	private DataTypeContainer dataTypeContainer = new DataTypeContainer();
	private DBObjectHandler objectHandler;
	private SqlStatementHandler sqlStatementHandler;
	private SqlQueryHandler sqlQueryHandler;
	
	protected AbstractDialect() {
		initDataTypeContainer();
		this.objectHandler = createObjectHandler();
		this.sqlStatementHandler = createSqlStatementHandler();
		this.sqlQueryHandler = createSqlQueryHandler(this.sqlStatementHandler);
	}
	
	// 初始化数据类型容器
	private void initDataTypeContainer() {
		String basePackage = getClass().getPackage().getName() + ".datatype.";
		ClassScanner scanner = new ClassScanner();
		scanner.scan(basePackage + "db").forEach(clazz ->{
			try {
				dataTypeContainer.register((DataType)ClassLoadUtil.loadClass(clazz).getDeclaredMethod("getSingleton").invoke(null));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});
		scanner.scan(basePackage + "mapping").forEach(clazz ->{
			dataTypeContainer.register((DataType)ConstructorUtil.newInstance(clazz));
		});
	}

	protected abstract DBObjectHandler createObjectHandler();
	protected abstract SqlStatementHandler createSqlStatementHandler();
	protected abstract SqlQueryHandler createSqlQueryHandler(SqlStatementHandler sqlStatementHandler);

	@Override
	public final DataTypeContainer getDataTypeContainer() {
		return dataTypeContainer;
	}
	
	@Override
	public final DBObjectHandler getObjectHandler() {
		return objectHandler;
	}

	@Override
	public final SqlStatementHandler getSqlStatementHandler() {
		return sqlStatementHandler;
	}

	@Override
	public final SqlQueryHandler getSqlQueryHandler() {
		return sqlQueryHandler;
	}
}
