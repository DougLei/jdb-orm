package com.douglei.orm.sessionfactory.validator.table.handler;

import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.Operation;

/**
 * 
 * @author DougLei
 */
public interface ValidateHandler {
	
	/**
	 * 验证数据
	 * @param objectMap
	 * @param tableMetadata
	 * @return
	 */
	ValidateFailResult validate(Map<String, Object> objectMap, TableMetadata tableMetadata);
	
	/**
	 * 
	 * @return
	 */
	Operation getOperation();
}
