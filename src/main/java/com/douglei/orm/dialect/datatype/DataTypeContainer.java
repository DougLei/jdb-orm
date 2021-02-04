package com.douglei.orm.dialect.datatype;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.db.impl.Null;

/**
 * 类型容器
 * @author DougLei
 */
public class DataTypeContainer {
	private final Map<String, DataType> container = new HashMap<String, DataType>();
	private final Map<Class<?>, DBDataType> extendContainer4ClassType = new HashMap<Class<?>, DBDataType>();
	private final Map<Integer, DBDataType> extendContainer4ColumnType = new HashMap<Integer, DBDataType>();
	
	/**
	 * 注册类型
	 * @param dataType
	 */
	public DataType register(DataType dataType) {
		String name = dataType.getName();
		if(container.containsKey(name))
			throw new DataTypeException("已经存在名为 ["+name+"] 的数据类型");
		container.put(name, dataType);
		
		if(dataType.getClassification() == Classification.DB) 
			register2ExtendContainer((DBDataType)dataType);
		return dataType;
	}
	// 注册到扩展容器中
	private void register2ExtendContainer(DBDataType dbDataType) {
		if(dbDataType.supportClasses() != null) {
			for(Class<?> clazz : dbDataType.supportClasses()) 
				extendContainer4ClassType.put(clazz, dbDataType);
		}
		extendContainer4ColumnType.put(dbDataType.getSqlType(), dbDataType);
	}
	
	/**
	 * 获取指定类型名称的数据类型
	 * @param name 
	 */
	public DataType get(String name) {
		DataType dataType = container.get(name);
		if(dataType == null) {
			try {
				dataType = register((DataType) Class.forName(name).newInstance());
			} catch (Exception e) {
				throw new DataTypeException("在获取名为["+name+"]的数据类型时出现异常, 请检查类型名称, 或自定义数据类型实现类的路径是否正确", e);
			}
		}
		return dataType;
	}
	
	// ----------------------------------------------------------------------
	/**
	 * 根据传入参数的Class, 获取对应的DBDataType
	 * @param value
	 */
	public DBDataType getDBDataTypeByObject(Object value) {
		if(value == null) 
			return Null.getSingleton();
		
		DBDataType dbDataType = extendContainer4ClassType.get(value.getClass());
		if(dbDataType == null)
			throw new DataTypeException("框架目前不支持处理Class=["+value.getClass()+"]的数据类型");
		return dbDataType;
	}
	
	/**
	 * 获取指定columnType的DBDataType
	 * @param columnType 
	 */
	public DBDataType getDBDataTypeByColumnType(int columnType) {
		DBDataType dbDataType = extendContainer4ColumnType.get(columnType);
		if(dbDataType == null)
			throw new DataTypeException("框架目前不支持处理columnType=["+columnType+"]的数据类型");
		return dbDataType;
	}
}
