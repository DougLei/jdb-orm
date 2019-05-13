package com.douglei.database.dialect.datatype;

import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.ormtype.OrmDataTypeHandler;
import com.douglei.database.dialect.datatype.ormtype.OrmDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDataTypeHandlerMapping{
	private final ClassDataTypeHandlerMapping classDataTypeHandlerMapping = new ClassDataTypeHandlerMapping();
	private final OrmDataTypeHandlerMapping ormDataTypeHandlerMapping = new OrmDataTypeHandlerMapping();
	private final ResultSetColumnDataTypeHandlerMapping resultsetColumnDataTypeHandlerMapping = new ResultSetColumnDataTypeHandlerMapping();
	
	public AbstractDataTypeHandlerMapping() {
		initialRegisterClassDataTypeHandlers();
		initialRegisterOrmDataTypeHandlers();
		initialRegisterResultsetColumnDataTypeHandlers();
	}
	
	protected abstract void initialRegisterClassDataTypeHandlers();
	protected void registerClassDataTypeHandler(ClassDataTypeHandler classDataHandler) {
		classDataTypeHandlerMapping.initialRegister(classDataHandler);
	}
	protected abstract void initialRegisterOrmDataTypeHandlers();
	protected void registerOrmDataTypeHandler(OrmDataTypeHandler ormDataTypeHandler) {
		ormDataTypeHandlerMapping.initialRegister(ormDataTypeHandler);
	}
	protected abstract void initialRegisterResultsetColumnDataTypeHandlers();
	protected void registerResultsetColumnDataTypeHandler(ResultSetColumnDataTypeHandler resultSetColumnDatatTypeHandler) {
		resultsetColumnDataTypeHandlerMapping.initialRegister(resultSetColumnDatatTypeHandler);
	}
	
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(value);
	}
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		return ormDataTypeHandlerMapping.getDataTypeHandlerByClassType(code);
	}
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType) {
		return resultsetColumnDataTypeHandlerMapping.getDataTypeHandlerByDatabaseColumnType(columnType);
	}
	
	/**
	 * 注册扩展的DataTypeHandler
	 * @param extDataTypeHandler
	 */
	public void registerExtDataTypeHandler(ExtDataTypeHandler extDataTypeHandler) {
		switch(extDataTypeHandler.getType()) {
			case CLASS:
				classDataTypeHandlerMapping.initialRegister(extDataTypeHandler.getClassDataTypeHandler());
				return;
			case ORM:
				ormDataTypeHandlerMapping.initialRegister(extDataTypeHandler.getOrmsDataTypeHandler());
				return;
			case RESULTSET_COLUMN:
				resultsetColumnDataTypeHandlerMapping.initialRegister(extDataTypeHandler.getResultsetColumnDataTypeHandler());
				return;
		}
	}
}
