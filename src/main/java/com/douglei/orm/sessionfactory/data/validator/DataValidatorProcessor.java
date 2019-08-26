package com.douglei.orm.sessionfactory.data.validator;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.data.validator.table.PersistentObjectValidator;

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
		Mapping mapping = mappingWrapper.getMapping(code);
		switch(mapping.getMappingType()) {
			case TABLE:// 验证表数据
				return new PersistentObjectValidator((TableMetadata) mapping.getMetadata(), object).doValidate();
			case SQL:// TODO 怎么验证sql数据
				break;
			}
		return null;
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
		switch(mapping.getMappingType()) {
			case TABLE:// 验证表数据
				PersistentObjectValidator persistentObjectValidator = new PersistentObjectValidator((TableMetadata) mapping.getMetadata());
				for (Object object : objects) {
					persistentObjectValidator.setOriginObject(object);
					bvr = BatchValidationResult.newInstance(index++, persistentObjectValidator.doValidate());
					if(bvr != null) {
						if(batchValidationResults == null) {
							batchValidationResults = new ArrayList<BatchValidationResult>(objects.size());
						}
						batchValidationResults.add(bvr);
					}
				}
				break;
			case SQL:// TODO 怎么验证sql数据
				for (Object object : objects) {
					
				}
				break;
		}
		return batchValidationResults;
	}
}
