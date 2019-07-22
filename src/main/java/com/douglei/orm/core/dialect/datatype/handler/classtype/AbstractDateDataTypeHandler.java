package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;
import com.douglei.tools.utils.datatype.date.DateTypeUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDateDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 2173313902868843968L;

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
		if(VerifyTypeMatchUtil.isDate(value)) {
			preparedStatement.setTimestamp(parameterIndex, DateTypeUtil.parseSqlTimestamp(value));
		} else {
			preparedStatement.setNull(parameterIndex, getSqlType());
		}
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		if(value instanceof Date || VerifyTypeMatchUtil.isDate(value.toString())) {
			return null;
		}
		return "数据值类型错误, 应为日期类型";
	}
}