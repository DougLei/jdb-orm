package com.douglei.orm.dialect.impl;

import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.DataTypeContainer;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.tools.file.scanner.impl.ClassScanner;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	private DataTypeContainer dataTypeContainer = new DataTypeContainer();
	protected SqlStatementHandler sqlStatementHandler;
	
	protected AbstractDialect() {
		// 初始化数据类型容器
		String basePackage = getClass().getPackage().getName() + ".datatype.";
		ClassScanner scanner = new ClassScanner();
		scanner.scan(basePackage + "db").forEach(clazz -> dataTypeContainer.register((DataType)ClassUtil.newInstance(clazz)));
		scanner.scan(basePackage + "mapping").forEach(clazz -> dataTypeContainer.register((DataType)ClassUtil.newInstance(clazz)));
	}
	
	@Override
	public final DataTypeContainer getDataTypeContainer() {
		return dataTypeContainer;
	}
	
	@Override
	public final SqlStatementHandler getSqlStatementHandler() {
		return sqlStatementHandler;
	}
}
