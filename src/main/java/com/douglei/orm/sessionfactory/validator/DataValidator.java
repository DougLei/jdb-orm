package com.douglei.orm.sessionfactory.validator;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.handler.MappingHandler;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.validator.sql.SqlValidator;
import com.douglei.orm.sessionfactory.validator.table.PersistentObjectValidator;

/**
 * 
 * @author DougLei
 */
public class DataValidator {
	private MappingHandler mappingHandler;
	
	public DataValidator(MappingHandler mappingHandler) {
		this.mappingHandler = mappingHandler;
	}

	// ------------------------------------------------------------------------------------------------------
	/**
	 * 验证表映射数据
	 * @param object
	 * @return
	 */
	public ValidationResult validate4TableMapping(Object object) {
		return validate4TableMapping(object.getClass().getName(), object);
	}
	
	/**
	 * 验证表映射数据
	 * @param code 表映射的tableName
	 * @param object
	 * @return
	 */
	public ValidationResult validate4TableMapping(String code, Object object) {
		TableMetadata tableMetadata = mappingHandler.getTableMetadata(code);
		return new PersistentObjectValidator(tableMetadata).doValidate(object);
	}
	
	/**
	 * 验证表映射数据
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> validate4TableMapping(List<? extends Object> objects){
		return validate4TableMapping(objects.get(0).getClass().getName(), objects);
	}
	
	/**
	 * 验证表映射数据
	 * @param code 表映射的tableName
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> validate4TableMapping(String code, List<? extends Object> objects){
		TableMetadata tableMetadata = mappingHandler.getTableMetadata(code);
		PersistentObjectValidator persistentObjectValidator = new PersistentObjectValidator(tableMetadata, objects.size());
		
		List<ValidationResult> validationResults = null;
		ValidationResult validationResult;
		short index = 0;
		for (Object object : objects) {
			validationResult = persistentObjectValidator.doValidate(object);
			if(validationResult != null) {
				validationResult.setIndex(index);
				if(validationResults == null) 
					validationResults = new ArrayList<ValidationResult>(objects.size());
				validationResults.add(validationResult);
			}
			index++;
		}
		return validationResults;
	}
	
	// ------------------------------------------------------------------------------------------------------
	/**
	 * 验证sql映射数据
	 * <pre>
	 * 若参数name为null, 则根据参数purpose的值, 决定要验证的content: 
	 * 	1. purpose为UPDATE/UNKNOW时, 验证所有sql content.
	 * 	2. purpose为QUERY/PROCEDURE时, 验证一个sql content; 
	 * </pre>
	 * @param purpose 用途
	 * @param namespace
	 * @param name 
	 * @param object
	 * @return
	 */
	public ValidationResult validate4SqlMapping(Purpose purpose, String namespace, String name, Object object) {
		SqlMetadata sqlMetadata = mappingHandler.getSqlMetadata(namespace);
		return new SqlValidator(purpose, sqlMetadata, name).validate(object);
	}
	
	/**
	 * 验证sql映射数据
	 * <pre>
	 * 若参数name为null, 则根据参数purpose的值, 决定要验证的content: 
	 * 	1. purpose为UPDATE/UNKNOW时, 验证所有sql content.
	 * 	2. purpose为QUERY/PROCEDURE时, 验证一个sql content; 
	 * </pre>
	 * @param purpose 用途
	 * @param namespace
	 * @param name 
	 * @param objects
	 * @return
	 */
	public List<ValidationResult> validate4SqlMapping(Purpose purpose, String namespace, String name, List<? extends Object> objects){
		SqlMetadata sqlMetadata = mappingHandler.getSqlMetadata(namespace);
		SqlValidator sqlValidator = new SqlValidator(purpose, sqlMetadata, name);
		
		List<ValidationResult> validationResults = null;
		ValidationResult validationResult;
		short index = 0;
		for (Object object : objects) {
			validationResult = sqlValidator.validate(object);
			if(validationResult != null) {
				validationResult.setIndex(index);
				if(validationResults == null) 
					validationResults = new ArrayList<ValidationResult>(objects.size());
				validationResults.add(validationResult);
			}
			index++;
		}
		return validationResults;
	}
}
