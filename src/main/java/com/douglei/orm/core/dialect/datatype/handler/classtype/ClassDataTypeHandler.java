package com.douglei.orm.core.dialect.datatype.handler.classtype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeFeatures;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 用于处理映射文件中dataType属性
 * @author DougLei
 */
public abstract class ClassDataTypeHandler implements DataTypeHandler, DBDataTypeFeatures{
	private static final long serialVersionUID = -5360227504550088048L;

	@Override
	public String getCode() {
		return getClass().getName();
	}
	
	/**
	 * 支持处理的Class类型
	 * @return
	 */
	public Class<?>[] supportClasses(){
		return null;
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return null;// 默认验证通过
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " code=["+getCode()+"], supportClasses=["+Arrays.toString(supportClasses())+"]";
	}
	
	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.CLASS;
	}

	@Deprecated
	@Override
	public Object getValue(short columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}

	public boolean unEquals(ClassDataTypeHandler otherHandler) {
		return !getCode().equals(otherHandler.getCode());
	}
}
