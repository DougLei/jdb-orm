package com.douglei.database.dialect.datatype.resultset.columntype;

import java.util.HashMap;
import java.util.Map;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerCodeException;

/**
 * 
 * @author DougLei
 */
public class ResultSetColumnDataTypeHandlerMapping {
	private final Map<Integer, ResultSetColumnDatatTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<Integer, ResultSetColumnDatatTypeHandler>();
	
	/**
	 * 根据java.sql.ResultSet columnType类型, 获取对应的DataTypeHandler
	 * @param columnType
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType) {
		ResultSetColumnDatatTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(columnType);
		if(dataTypeHandler == null) {
			throw new UnsupportDataTypeHandlerCodeException("目前无法处理columnType=["+columnType+"]的数据类型");
		}
		return dataTypeHandler;
	}

	public void register(ResultSetColumnDatatTypeHandler resultSetColumnDatatTypeHandler) {
		DATATYPE_HANDLER_MAP.put(resultSetColumnDatatTypeHandler.supportColumnType(), resultSetColumnDatatTypeHandler);
	}
}
