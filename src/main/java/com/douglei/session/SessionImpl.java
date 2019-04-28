package com.douglei.session;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.ConnectionWrapper;

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
		String code = object.getClass().getName();
		logger.debug("对实体对象{} 进行save操作", code);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			logger.debug("不存在code为[{}]的映射", code);
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		
		TableMetadata tableMetadata = (TableMetadata) mapping.getMetadata();
		List<String> columnNames = tableMetadata.getColumnNames();
		
		
		
	}
	
	@Override
	public void save(EntityMap entity) {
		String code = entity.getName();
		logger.debug("对实体对象{} 进行save操作", code);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			logger.debug("不存在code为[{}]的映射", code);
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		
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
