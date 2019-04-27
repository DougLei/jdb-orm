package com.douglei.session.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.session.AbstractSession;
import com.douglei.session.Session;

/**
 * 
 * @author DougLei
 */
public class SessionImpl extends AbstractSession implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}

	@Override
	public void save(Object object) {
		if(object == null) {
			logger.debug("要进行save(Object object), 传入的object=null");
			throw new NullPointerException("要保存的对象不能为空");
		}
		
		String code = object.getClass().getName();
		logger.debug("对实体对象{} 进行save操作", code);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			logger.debug("不存在code为[{}]映射", code);
			throw new NullPointerException("不存在code为["+code+"]映射");
		}
		
		
		
	}
	
	@Override
	public void save(EntityMap entity) {
		
	}

	@Override
	protected void flush() {
	}

	@Override
	public void close() {
		flush();
		super.close();
	}
}
