package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.metadata.validator.ValidatorResult;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDateDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = -6225768595647748110L;

	@Override
	public String getCode() {
		return DataType.DATE.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Date.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(DateFormatUtil.verifyIsDate(value)) {
			preparedStatement.setTimestamp(parameterIndex, DateFormatUtil.parseSqlTimestamp(value));
		} else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public ValidatorResult doValidate(String validateFieldName, Object value, short length, short precision) {
		if(value instanceof Date || DateFormatUtil.verifyIsDate(value)) {
			return null;
		}
		return new ValidatorResult(validateFieldName) {
			
			@Override
			public String getMessage() {
				return "数据值类型错误, 应为日期类型";
			}
			
			@Override
			protected String getI18nCode() {
				return i18nCodePrefix + "value.datatype.error.date";
			}
		};
	}
}