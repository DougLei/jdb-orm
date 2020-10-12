package com.douglei.orm.sessionfactory.validator;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.sessionfactory.validator.sql.SqlValidator;
import com.douglei.orm.sessionfactory.validator.table.PersistentObjectValidator;

/**
 * 
 * @author DougLei
 */
public class DataValidator {
	private MappingContainer mappingContainer;
	
	public DataValidator(MappingContainer mappingContainer) {
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
		
		switch(mapping.getType()) {
			case MappingTypeConstants.TABLE:// 验证表数据
				return new PersistentObjectValidator((TableMetadata) mapping.getMetadata()).doValidate(object);
			case MappingTypeConstants.SQL:// 验证sql数据
				return new SqlValidator((SqlMetadata)mapping.getMetadata(), name).validate(object);
			default:
				throw new UnsupportedOperationException("不支持"+mapping.getType()+"类型的验证");
		}
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
		switch(mapping.getType()) {
			case MappingTypeConstants.TABLE:// 验证表数据
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
			case MappingTypeConstants.SQL:// 验证sql数据
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
			default:
				throw new UnsupportedOperationException("不支持"+mapping.getType()+"类型的验证");
		}
		return validationResults;
	}
}
