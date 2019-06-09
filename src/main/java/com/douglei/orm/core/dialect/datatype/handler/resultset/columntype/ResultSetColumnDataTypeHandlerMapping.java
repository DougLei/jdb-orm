package com.douglei.orm.core.dialect.datatype.handler.resultset.columntype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.datatype.handler.UnsupportDataTypeHandlerException;

/**
 * 
 * @author DougLei
 */
public class ResultSetColumnDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(ResultSetColumnDataTypeHandlerMapping.class);
	private final Map<Integer, ResultSetColumnDataTypeHandler> DATATYPE_HANDLER_MAP = new HashMap<Integer, ResultSetColumnDataTypeHandler>(16);
	
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
	 * @param columnType 主要是通过该参数值获取对应的ResultSetColumnDataTypeHandler实例, 另外两个参数, 是为了在获取失败时, 进行提示信息
	 * @param columnName
	 * @param columnTypeName
	 * @return
	 */
	public ResultSetColumnDataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType, String columnName, String columnTypeName) {
		ResultSetColumnDataTypeHandler dataTypeHandler = DATATYPE_HANDLER_MAP.get(columnType);
		if(dataTypeHandler == null) {
			throw new UnsupportDataTypeHandlerException("目前无法处理columnName=["+columnName+"], columnType=["+columnType+"], columnTypeName=["+columnTypeName+"]的数据类型");
		}
		return dataTypeHandler;
	}
}
