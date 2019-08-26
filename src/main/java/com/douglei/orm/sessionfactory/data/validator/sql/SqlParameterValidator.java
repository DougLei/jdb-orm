package com.douglei.orm.sessionfactory.data.validator.sql;

import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * sql参数验证器
 * @author DougLei
 */
public class SqlParameterValidator {
	
	private SqlMetadata sqlMetadata;
	private String name;
	
	public SqlParameterValidator(SqlMetadata sqlMetadata, String name) {
		this.sqlMetadata = sqlMetadata;
		this.name = name;
	}
	
	public ValidationResult setOriginObjectAndDoValidate(Object originObject) {
		// TODO 这里获取sql参数集合, 对数据进行验证
		
		
		return null;
	}
}
