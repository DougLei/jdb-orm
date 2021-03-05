package com.douglei.orm.dialect.datatype.db.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.validator.ValidateFailResult;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractCharacter extends DBDataType {

	protected AbstractCharacter(String name, int sqlType) {
		super(name, sqlType);
	}
	protected AbstractCharacter(String name, int sqlType, int maxLength) {
		super(name, sqlType, maxLength);
	}
	
	@Override
	public final boolean isCharacterType() {
		return true;
	}
	
	@Override
	public String getSqlStatement(int length, int precision) {
		return name + "("+length+")";
	}
	
	@Override
	public final void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setString(parameterIndex, value.toString());
	}
	
	@Override
	public final String getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getString(columnIndex);
	}
	
	@Override
	public final String getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}
	
	@Override
	public ValidateFailResult validate(String name, Object value, int length, int precision) {
		if(value instanceof String || value.getClass() == char.class || value instanceof Character) {
			int characterLength = calcLength(value.toString());
			if(characterLength > length) 
				return new ValidateFailResult(name, "数据值长度超长, 设置长度为%d, 实际长度为%d", "jdb.data.validator.value.overlength", length, characterLength);
			return null;
		}
		return new ValidateFailResult(name, "数据值类型错误, 应为字符类型", "jdb.data.validator.value.datatype.error.string");
	}
	
	/**
	 * 计算出字符串的长度
	 * @param string
	 * @return
	 */
	protected int calcLength(String string) {
		return string.length();
	}
}
