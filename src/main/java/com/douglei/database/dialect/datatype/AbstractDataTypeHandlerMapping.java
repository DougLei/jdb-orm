package com.douglei.database.dialect.datatype;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.ormtype.OrmDataTypeHandler;
import com.douglei.database.dialect.datatype.ormtype.OrmDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDatatTypeHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDataTypeHandlerMapping{
	private final OrmDataTypeHandlerMapping ormDataTypeHandlerMapping = new OrmDataTypeHandlerMapping();
	private final ResultSetColumnDataTypeHandlerMapping resultsetColumnDataTypeHandlerMapping = new ResultSetColumnDataTypeHandlerMapping();
	private final ClassDataTypeHandlerMapping classDataTypeHandlerMapping = new ClassDataTypeHandlerMapping();
	
	public AbstractDataTypeHandlerMapping() {
		initialRegisterOrmDataTypeHandlers();
		initialRegisterResultsetColumnDataTypeHandlers();
	}
	
	protected abstract void initialRegisterOrmDataTypeHandlers();
	protected void registerOrmDataTypeHandler(OrmDataTypeHandler ormDataTypeHandler) {
		ormDataTypeHandlerMapping.register(ormDataTypeHandler);
	}
	protected abstract void initialRegisterResultsetColumnDataTypeHandlers();
	protected void registerResultsetColumnDataTypeHandler(ResultSetColumnDatatTypeHandler resultSetColumnDatatTypeHandler) {
		resultsetColumnDataTypeHandlerMapping.register(resultSetColumnDatatTypeHandler);
	}
	
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		return ormDataTypeHandlerMapping.getDataTypeHandlerByClassType(code);
	}
	
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		return classDataTypeHandlerMapping.getDataTypeHandlerByClassType(value);
	}
	
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType) {
		return resultsetColumnDataTypeHandlerMapping.getDataTypeHandlerByDatabaseColumnType(columnType);
	}
}
