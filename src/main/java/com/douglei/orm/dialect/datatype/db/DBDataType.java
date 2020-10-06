package com.douglei.orm.dialect.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.Classification;
import com.douglei.orm.dialect.datatype.DataType;

/**
 * 数据库的数据类型
 * @author DougLei
 */
public abstract class DBDataType extends DataType {
	private static final int NO_LIMIT = -1;
	
	protected String name;// 类型的名称, 大写
	private int sqlType;// @see java.sql.Types
	
	private int length;// 长度
	private int precision;// 精度
	
	protected DBDataType(int sqlType) {
		this(sqlType, NO_LIMIT, NO_LIMIT);
	}
	protected DBDataType(int sqlType, int length) {
		this(sqlType, length, NO_LIMIT);
	}
	protected DBDataType(int sqlType, int length, int precision) {
		this.name = getClass().getSimpleName().toUpperCase();
		this.sqlType = sqlType;
		this.length = length;
		this.precision = precision;
	}
	
	@Override
	public final Classification getClassification() {
		return Classification.DB;
	}
	
	@Override
	public final String getName() {
		return name;
	}
	
	/**
	 * 获取sql类型的值
	 * @return
	 */
	public final int getSqlType() {
		return sqlType;
	}
	
	/**
	 * 是否是字符类型
	 * @return
	 */
	public boolean isCharacterType() {
		return false;
	}
	
	/**
	 * 修正输入的长度值
	 * @param inputLength
	 * @return
	 */
	public int correctInputLength(int inputLength) {
		if(this.length == NO_LIMIT) 
			return NO_LIMIT;
		if(inputLength < 1 || inputLength > this.length) 
			return this.length;
		return inputLength;
	}
	
	/**
	 * 修正输入的精度值
	 * @param inputLength
	 * @param inputPrecision
	 * @return
	 */
	public int correctInputPrecision(int inputLength, int inputPrecision) {
		if(this.precision == NO_LIMIT) 
			return NO_LIMIT;
		if(inputPrecision < 0 || inputPrecision > this.precision) 
			inputPrecision = this.precision;
		if(inputPrecision > inputLength) 
			inputPrecision = inputLength;
		return inputPrecision;
	}
	
	/**
	 * 获取数据库数据类型对应的sql语句
	 * @param length
	 * @param precision
	 * @return
	 */
	public String getSqlStatement(int length, int precision) {
		if(length == NO_LIMIT) 
			return name;
		if(precision == NO_LIMIT) 
			return name + "("+length+")";
		return name + "("+length+", "+precision+")";
	}
	
	// --------------------------------------------------------------------------------------
	/**
	 * 给 {@link PreparedStatement} 设置对应的参数值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	public abstract void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException;
	
	/**
	 * 从 {@link ResultSet} 中获取对应的列值
	 * @param columnIndex
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public abstract Object getValue(short columnIndex, ResultSet rs) throws SQLException;
	
	/**
	 * 从 {@link CallableStatement} 中获取输出参数的值
	 * @param parameterIndex
	 * @param callableStatement
	 * @return
	 * @throws SQLException
	 */
	public abstract Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException;
}
