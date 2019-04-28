package com.douglei.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.statement.StatementHandler;
import com.douglei.utils.reflect.IntrospectorUtil;

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
		
		Map<String, Object> propertyMap = IntrospectorUtil.getProperyValues(object, tableMetadata.getColumnMetadataCodes());
		
		List<Object> parameters = new ArrayList<Object>();
		StringBuffer insertSql = new StringBuffer("insert into ");
		insertSql.append(tableMetadata.getName()).append("(");
		StringBuffer valuesSql = new StringBuffer("values(");
		
		
		Object value = null;
		Set<String> codesets = propertyMap.keySet();
		for (String cs : codesets) {
			value = propertyMap.get(cs);
			if(value != null) {
				insertSql.append(tableMetadata.getColumnMetadata(cs).getName()).append(",");
				valuesSql.append("?,");
				parameters.add(value);
			}
		}
		insertSql.setLength(insertSql.length()-1);
		insertSql.append(") ");
		valuesSql.setLength(valuesSql.length()-1);
		valuesSql.append(") ");
		
		
		StatementHandler statementHandler = connection.createStatementHandler(insertSql.append(valuesSql).toString(), parameters);
		statementHandler.executeUpdate(parameters);
	}
	
	@Override
	public void save(EntityMap entity) {
		String code = entity.getName();
		logger.debug("对{} 实例 {} 进行save操作", entity.getClass(), code);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			logger.debug("不存在code为[{}]的映射", code);
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		
		TableMetadata tableMetadata = (TableMetadata) mapping.getMetadata();
		
		Map<String, Object> propertyMap = entity.filterColumnMetadataPropertyMap(tableMetadata);
		
		List<Object> parameters = new ArrayList<Object>();
		StringBuffer insertSql = new StringBuffer("insert into ");
		insertSql.append(tableMetadata.getName()).append("(");
		StringBuffer valuesSql = new StringBuffer("values(");
		
		Object value = null;
		Set<String> codesets = propertyMap.keySet();
		for (String cs : codesets) {
			value = propertyMap.get(cs);
			if(value != null) {
				insertSql.append(tableMetadata.getColumnMetadata(cs).getName()).append(",");
				valuesSql.append("?,");
				parameters.add(value);
			}
		}
		insertSql.setLength(insertSql.length()-1);
		insertSql.append(") ");
		valuesSql.setLength(valuesSql.length()-1);
		valuesSql.append(") ");
		
		
		StatementHandler statementHandler = connection.createStatementHandler(insertSql.append(valuesSql).toString(), parameters);
		statementHandler.executeUpdate(parameters);
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
