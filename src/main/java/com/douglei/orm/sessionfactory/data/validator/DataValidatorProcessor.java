package com.douglei.orm.sessionfactory.data.validator;

import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public class DataValidatorProcessor {
	
	private MappingWrapper mappingWrapper;
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public ValidatorResult doValidate(Object obj) {
		return doValidate(obj.getClass().getName(), obj);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param obj
	 * @return
	 */
	public ValidatorResult doValidate(String code, Object obj) {
		// TODO 
		Mapping mapping = mappingWrapper.getMapping(code);
		switch(mapping.getMappingType()) {
			case TABLE:
				return validateTableMappingData((TableMetadata)mapping.getMetadata(), obj);
			case SQL:
				return validateSqlMappingData((SqlMetadata)mapping.getMetadata(), obj);
		}
		return null;
	}
	
	/**
	 * 
	 * @param objs
	 * @return
	 */
	public ValidatorResult doValidate(List<Object> objs) {
		return doValidate(objs.get(0).getClass().getName(), objs);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param objs
	 * @return
	 */
	public ValidatorResult doValidate(String code, List<Object> objs) {
		
	}

	/**
	 * 验证表映射数据
	 * @param table
	 * @param obj
	 * @return
	 */
	private ValidatorResult validateTableMappingData(TableMetadata table, Object obj) {
		// TODO 验证表映射数据
		return null;
	}

	/**
	 * 验证sql映射数据
	 * @param sql
	 * @param obj
	 * @return
	 */
	private ValidatorResult validateSqlMappingData(SqlMetadata sql, Object obj) {
		// TODO 验证sql映射数据
		return null;
	}
}
