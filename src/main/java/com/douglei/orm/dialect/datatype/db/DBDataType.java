package com.douglei.orm.dialect.datatype.db;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.DataTypeClassification;
import com.douglei.orm.dialect.datatype.DataTypeException;
import com.douglei.orm.mapping.validator.ValidateFailResult;

/**
 * 数据库的数据类型
 * @author DougLei
 */
public abstract class DBDataType extends DataType implements Serializable{
	private static final int NO_LIMIT = -1; // 对长度(或精度)没有限制
	
	protected final String name;// 类型的名称
	protected final int sqlType;// @see java.sql.Types
	
	private int maxLength;// 类型支持的最大长度
	private int maxPrecision;// 类型支持的最大精度
	
	protected DBDataType(String name, int sqlType) {
		this(name, sqlType, NO_LIMIT, NO_LIMIT);
	}
	protected DBDataType(String name, int sqlType, int maxLength) {
		this(name, sqlType, maxLength, NO_LIMIT);
	}
	protected DBDataType(String name, int sqlType, int maxLength, int maxPrecision) {
		this.name = name;
		this.sqlType = sqlType;
		this.maxLength = maxLength;
		this.maxPrecision = maxPrecision;
	}
	
	@Override
	public final DataTypeClassification getClassification() {
		return DataTypeClassification.DB;
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
	 * 修正输入的长度值
	 * @param inputLength
	 * @return
	 */
	public final int correctInputLength(int inputLength) {
		if(this.maxLength == NO_LIMIT) 
			return NO_LIMIT;
		if(inputLength < 1 || inputLength > this.maxLength) 
			return this.maxLength;
		return inputLength;
	}
	
	/**
	 * 修正输入的精度值
	 * @param inputLength
	 * @param inputPrecision
	 * @return
	 */
	public final int correctInputPrecision(int inputLength, int inputPrecision) {
		if(this.maxPrecision == NO_LIMIT) 
			return NO_LIMIT;
		if(inputPrecision < 0 || inputPrecision > this.maxPrecision) 
			inputPrecision = this.maxPrecision;
		if(inputPrecision > inputLength) // 精度不能大于长度
			inputPrecision = inputLength;
		return inputPrecision;
	}
	
	/**
	 * 是否是字符类型
	 * @return
	 */
	public boolean isCharacterType() {
		return false;
	}
	
	/**
	 * 获取数据库数据类型对应的sql语句
	 * @param length
	 * @param precision
	 * @return
	 */
	public String getSqlStatement(int length, int precision) {
		return name;
	}
	
	/**
	 * 支持处理的Class数组
	 * @return 
	 */
	public Class<?>[] supportClasses() {
		return null;
	}
	
	/**
	 * 给 {@link PreparedStatement} 设置对应的参数值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		throw new DataTypeException("["+getClass().getName()+"] 类型不支持执行 setValue(PreparedStatement, int, Object)方法; 传入的value为 [" + value + "]");
	}
	
	/**
	 * 从 {@link ResultSet} 中获取对应的列值
	 * @param columnIndex
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException{
		throw new DataTypeException("["+getClass().getName()+"] 类型不支持执行 getValue(int, ResultSet)方法");
	}
	
	/**
	 * 从 {@link CallableStatement} 中获取输出参数的值
	 * @param parameterIndex
	 * @param callableStatement
	 * @return
	 * @throws SQLException
	 */
	public Object getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException{
		throw new DataTypeException("["+getClass().getName()+"] 类型不支持执行 getValue(int, CallableStatement)方法");
	}
	
	/**
	 * 验证指定属性名的属性值
	 * @param name
	 * @param value
	 * @param length
	 * @param precision
	 * @return 
	 */
	public ValidateFailResult validate(String name, Object value, int length, int precision){
		throw new DataTypeException("["+getClass().getName()+"] 类型不支持执行 validate(String, Object, int, int)方法");
	}
	
	@Override
	public final String toString() {
		return name;
	}
}
