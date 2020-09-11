package com.douglei.orm.sessionfactory.mapping;

import java.util.Arrays;
import java.util.List;

import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.core.mapping.MappingExecuteException;
import com.douglei.orm.core.mapping.MappingHandler;

/**
 * 映射处理器
 * @author DougLei
 */
public class MappingProcessor {
	private MappingHandler mappingHandler;
	
	public MappingProcessor(MappingHandler mappingHandler) {
		this.mappingHandler = mappingHandler;
	}

	/**
	 * 操作映射
	 * @param entities
	 * @throws MappingExecuteException 
	 */
	public synchronized void execute(MappingEntity... entities) throws MappingExecuteException {
		mappingHandler.execute(Arrays.asList(entities));
	}
	
	/**
	 * 操作映射
	 * @param entities
	 * @throws MappingExecuteException 
	 */
	public synchronized void execute(List<MappingEntity> entities) throws MappingExecuteException {
		mappingHandler.execute(entities);
	}
	
	/**
	 * 判断是否存在指定code的映射
	 * @param code
	 * @return
	 */
	public boolean exists(String code) {
		return mappingHandler.exists(code);
	}
}
