package com.douglei.orm.sessionfactory.data.validator;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class DataValidatorProcessor {
	
	private MappingWrapper mappingWrapper;
	
	public DataValidatorProcessor(MappingWrapper mappingWrapper) {
		this.mappingWrapper = mappingWrapper;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public ValidationResult doValidate(Object object) {
		return doValidate(object.getClass().getName(), object);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param object
	 * @return
	 */
	public ValidationResult doValidate(String code, Object object) {
		return doValidate(mappingWrapper.getMapping(code), object);
	}
	
	/**
	 * 
	 * @param objects
	 * @return
	 */
	public List<BatchValidationResult> doValidate(List<Object> objects) {
		return doValidate(objects.get(0).getClass().getName(), objects);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param objects
	 * @return
	 */
	public List<BatchValidationResult> doValidate(String code, List<Object> objects) {
		Mapping mapping = mappingWrapper.getMapping(code);
		List<BatchValidationResult> batchValidationResults = null;
		
		byte index = 0;
		BatchValidationResult bvr = null;
		for (Object object : objects) {
			bvr = BatchValidationResult.newInstance(index, doValidate(mapping, object));
			if(bvr != null) {
				if(batchValidationResults == null) {
					batchValidationResults = new ArrayList<BatchValidationResult>(objects.size());
				}
				batchValidationResults.add(bvr);
			}
		}
		return batchValidationResults;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param object
	 * @return
	 */
	private ValidationResult doValidate(Mapping mapping, Object object) {
		switch(mapping.getMappingType()) {
			case TABLE:
				return validateTableMappingData((TableMetadata)mapping.getMetadata(), object);
			case SQL:
				return validateSqlMappingData((SqlMetadata)mapping.getMetadata(), object);
		}
		return null;
	}

	/**
	 * 验证表映射数据
	 * @param table
	 * @param object
	 * @return
	 */
	private ValidationResult validateTableMappingData(TableMetadata table, Object object) {
		// TODO 验证表映射数据
		return null;
	}

	/**
	 * 验证sql映射数据
	 * @param sql
	 * @param object
	 * @return
	 */
	private ValidationResult validateSqlMappingData(SqlMetadata sql, Object object) {
		// TODO 验证sql映射数据
		return null;
	}
}
