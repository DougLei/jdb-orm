package com.douglei.database.dialect.datatype.resultset.columntype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerCodeException;

/**
 * 
 * @author DougLei
 */
public class ResultSetColumnDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(ResultSetColumnDataTypeHandlerMapping.class);
	private final Map<Integer, ResultSetColumnDataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<Integer, ResultSetColumnDataTypeHandler>();
	
	public void register(ResultSetColumnDataTypeHandler resultSetColumnDatatTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("register {}", resultSetColumnDatatTypeHandler.toString());
		}
		int[] types = resultSetColumnDatatTypeHandler.supportColumnTypes();
		if(types == null) {
			logger.debug("supportColumnTypes is null");
		}else {
			for (int type : types) {
				DATATYPE_HANDLER_MAP.put(type, resultSetColumnDatatTypeHandler);
			}
		}
	}
	
	/**
	 * 根据java.sql.ResultSet columnType类型, 获取对应的DataTypeHandler
	 * @param columnType
	 * @param columnTypeName
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType, String columnTypeName) {
		ResultSetColumnDataTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(columnType);
		if(dataTypeHandler == null) {
			throw new UnsupportDataTypeHandlerCodeException("目前无法处理columnType=["+columnType+"], columnTypeName=["+columnTypeName+"]的数据类型");
		}
		return dataTypeHandler;
	}
}
