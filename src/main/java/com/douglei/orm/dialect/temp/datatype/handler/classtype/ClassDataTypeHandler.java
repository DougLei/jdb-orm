package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.douglei.orm.dialect.temp.datatype.handler.DataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeFeatures;

/**
 * 用于处理映射文件中dataType属性
 * @author DougLei
 */
public abstract class ClassDataTypeHandler implements DataTypeHandler, DBDataTypeFeatures{
	private static final long serialVersionUID = 8265757807858620596L;

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
