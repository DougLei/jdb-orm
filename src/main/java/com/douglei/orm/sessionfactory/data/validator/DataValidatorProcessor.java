package com.douglei.orm.sessionfactory.data.validator;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
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
	private MappingContainer mappingContainer;
	
	public DataValidatorProcessor(MappingContainer mappingContainer) {
		this.mappingContainer = mappingContainer;
	}

	// ------------------------------------------------------------------------------------------------------
	/**
	 * 针对表对象的验证, 传入和表映射的类实例
	 * @param object
	 * @return
	 */
	public ValidationResult validate(Object object) {
		return validate(object.getClass().getName(), null, object);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param object
	 * @return
	 */
	public ValidationResult validate(String code, Object object) {
		return validate(code, null, object);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param name 针对sql映射, content的name值, 如果验证表则该参数传入null即可
	 * @param object
	 * @return
	 */
	public ValidationResult validate(String code, String name, Object object) {
		Mapping mapping = mappingContainer.getMapping(code);
		if(mapping == null)
			throw new NullPointerException("不存在code为"+code+"的映射信息");
		
		switch(mapping.getMappingType()) {
			case TABLE:// 验证表数据
				return new PersistentObjectValidator((TableMetadata) mapping.getMetadata()).doValidate(object);
			case SQL:// 验证sql数据
				return new SqlValidator((SqlMetadata)mapping.getMetadata(), name).validate(object);
		}
		return null;
	}
	
	// ------------------------------------------------------------------------------------------------------
	/**
	 * 针对表对象的验证, 传入和表映射的类实例集合
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> validate(List<? extends Object> objects) {
		return validate(objects.get(0).getClass().getName(), null, objects);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> validate(String code, List<? extends Object> objects) {
		return validate(code, null, objects);
	}
	
	/**
	 * 
	 * @param code 表映射的tableName/className, sql映射的namespace
	 * @param name 针对sql映射, content的name值
	 * @param objects
	 * @return 
	 */
	public List<ValidationResult> validate(String code, String name, List<? extends Object> objects) {
		Mapping mapping = mappingContainer.getMapping(code);
		if(mapping == null)
			throw new NullPointerException("不存在code为"+code+"的映射信息");
		List<ValidationResult> validationResults = null;
		
		short index = 0;
		ValidationResult vr = null;
		switch(mapping.getMappingType()) {
			case TABLE:// 验证表数据
				PersistentObjectValidator persistentObjectValidator = new PersistentObjectValidator((TableMetadata) mapping.getMetadata(), objects.size());
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
				persistentObjectValidator.destroy();
				break;
			case SQL:// 验证sql数据
				SqlValidator sqlValidator = new SqlValidator((SqlMetadata) mapping.getMetadata(), name);
				for (Object object : objects) {
					vr = sqlValidator.validate(object);
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
