package com.douglei.orm.sessionfactory.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.handler.MappingHandler;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.Purpose;
import com.douglei.orm.sessionfactory.validator.sql.SqlValidator;
import com.douglei.orm.sessionfactory.validator.table.PersistentObjectValidator;
import com.douglei.orm.sessionfactory.validator.table.handler.ValidateHandler;

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
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param object
	 * @param validateHandler
	 * @return
	 */
	public ValidateFailResult validate4TableMapping(Object object, ValidateHandler validateHandler) {
		return validate4TableMapping_(object.getClass().getName(), object, validateHandler);
	}
	/**
	 * 验证表映射数据
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param object
	 * @param validateHandler
	 * @return
	 */
	public ValidateFailResult validate4TableMapping(String tableName, Map<String, Object> objectMap, ValidateHandler validateHandler) {
		return validate4TableMapping_(tableName.toUpperCase(), objectMap, validateHandler);
	}
	// 验证表映射数据
	private ValidateFailResult validate4TableMapping_(String code, Object object, ValidateHandler validateHandler) {
		TableMetadata tableMetadata = mappingHandler.getTableMetadata(code);
		return new PersistentObjectValidator(tableMetadata, validateHandler).validate(object);
	}
	
	/**
	 * 验证表映射数据
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param objects
	 * @param validateHandler
	 * @return
	 */
	public List<ValidateFailResult> validate4TableMapping(List<? extends Object> objects, ValidateHandler validateHandler){
		return validate4TableMapping_(objects.get(0).getClass().getName(), objects, validateHandler);
	}
	
	/**
	 * 验证表映射数据
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objects
	 * @param validateHandler
	 * @return
	 */
	public List<ValidateFailResult> validate4TableMapping(String tableName, List<Map<String, Object>> objectMaps, ValidateHandler validateHandler){
		return validate4TableMapping_(tableName.toUpperCase(), objectMaps, validateHandler);
	}
	// 验证表映射数据
	public List<ValidateFailResult> validate4TableMapping_(String code, List<? extends Object> objects, ValidateHandler validateHandler){
		TableMetadata tableMetadata = mappingHandler.getTableMetadata(code);
		PersistentObjectValidator validator = new PersistentObjectValidator(tableMetadata, validateHandler);
		
		List<ValidateFailResult> failResults = null;
		for(int i=0;i<objects.size();i++) {
			ValidateFailResult failResult = validator.validate(objects.get(i));
			if(failResult == null)
				continue;
			
			failResult.setIndex(i);
			if(failResults == null) 
				failResults = new ArrayList<ValidateFailResult>(objects.size());
			failResults.add(failResult);
		}
		return failResults;
	}
	
	// ------------------------------------------------------------------------------------------------------
	/**
	 * 验证sql映射数据
	 * <pre>
	 * 若参数name为null, 则根据参数purpose的值, 决定要验证的content: 
	 * 	1. purpose为UPDATE时, 验证所有sql content.
	 * 	2. purpose为QUERY/PROCEDURE时, 验证一个sql content; 
	 * </pre>
	 * @param purpose 用途
	 * @param namespace
	 * @param name 
	 * @param object
	 * @return
	 */
	public ValidateFailResult validate4SqlMapping(Purpose purpose, String namespace, String name, Object object) {
		SqlMetadata sqlMetadata = mappingHandler.getSqlMetadata(namespace);
		return new SqlValidator(purpose, sqlMetadata, name).validate(object);
	}
	
	/**
	 * 批量验证sql映射数据
	 * <pre>
	 * 若参数name为null, 则根据参数purpose的值, 决定要验证的content: 
	 * 	1. purpose为UPDATE时, 验证所有sql content.
	 * 	2. purpose为QUERY/PROCEDURE时, 验证一个sql content; 
	 * </pre>
	 * @param purpose 用途
	 * @param namespace
	 * @param name 
	 * @param objects
	 * @return
	 */
	public List<ValidateFailResult> validates4SqlMapping(Purpose purpose, String namespace, String name, List<? extends Object> objects){
		SqlMetadata sqlMetadata = mappingHandler.getSqlMetadata(namespace);
		SqlValidator sqlValidator = new SqlValidator(purpose, sqlMetadata, name);
		
		List<ValidateFailResult> validationResults = null;
		ValidateFailResult validationResult;
		short index = 0;
		for (Object object : objects) {
			validationResult = sqlValidator.validate(object);
			if(validationResult != null) {
				validationResult.setIndex(index);
				if(validationResults == null) 
					validationResults = new ArrayList<ValidateFailResult>(objects.size());
				validationResults.add(validationResult);
			}
			index++;
		}
		return validationResults;
	}
}
