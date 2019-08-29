package com.douglei.orm.sessionfactory.data.validator;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.data.validator.sql.SqlValidator;
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
	 * 针对表对象的验证, 传入和表映射的类实例
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
		return doValidate(code, null, object);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param name 针对sql映射, content的name值
	 * @param object
	 * @return
	 */
	public ValidationResult doValidate(String code, String name, Object object) {
		Mapping mapping = mappingWrapper.getMapping(code);
		switch(mapping.getMappingType()) {
			case TABLE:// 验证表数据
				return new PersistentObjectValidator((TableMetadata) mapping.getMetadata()).doValidate(object);
			case SQL:// 验证sql数据
				return new SqlValidator((SqlMetadata)mapping.getMetadata(), name).doValidate(object);
		}
		return null;
	}
	
	
	/**
	 * 针对表对象的验证, 传入和表映射的类实例集合
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> doValidate(List<? extends Object> objects) {
		return doValidate(objects.get(0).getClass().getName(), objects);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> doValidate(String code, List<? extends Object> objects) {
		return doValidate(code, null, objects);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param name 针对sql映射, content的name值
	 * @param objects
	 * @return 
	 */
	public List<ValidationResult> doValidate(String code, String name, List<? extends Object> objects) {
		Mapping mapping = mappingWrapper.getMapping(code);
		List<ValidationResult> validationResults = null;
		
		byte index = 0;
		ValidationResult vr = null;
		switch(mapping.getMappingType()) {
			case TABLE:// 验证表数据
				PersistentObjectValidator persistentObjectValidator = new PersistentObjectValidator((TableMetadata) mapping.getMetadata());
				for (Object object : objects) {
					vr = persistentObjectValidator.doValidate(object);
					if(vr != null) {
						vr.setIndex(index);
						if(validationResults == null) {
							validationResults = new ArrayList<ValidationResult>(objects.size());
						}
						validationResults.add(vr);
					}
					index++;
				}
				break;
			case SQL:// 验证sql数据
				SqlValidator sqlValidator = new SqlValidator((SqlMetadata) mapping.getMetadata(), name);
				for (Object object : objects) {
					vr = sqlValidator.doValidate(object);
					if(vr != null) {
						vr.setIndex(index);
						if(validationResults == null) {
							validationResults = new ArrayList<ValidationResult>(objects.size());
						}
						validationResults.add(vr);
					}
					index++;
				}
				break;
		}
		return validationResults;
	}
}
