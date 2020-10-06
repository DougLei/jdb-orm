package com.douglei.orm.dialect.datatype;

import java.util.HashMap;
import java.util.Map;

import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 类型容器
 * @author DougLei
 */
public class DataTypeContainer {
	private final Map<String, DataType> container = new HashMap<String, DataType>(32);
	
	/**
	 * 添加类型
	 * @param dataType
	 */
	public DataType put(DataType dataType) {
		if(container.containsKey(dataType.getName()))
			throw new RepeatedDataTypeException("已经存在名为"+dataType.getName()+"的数据类型");
		container.put(dataType.getName(), dataType);
		return dataType;
	}
	
	/**
	 * 获取指定类型名称的数据类型
	 * @param name 可以传入类型名称, 或继承了 {@link DataType} 抽象类的实现类的全路径
	 */
	public DataType get(String name) {
		DataType dataType = container.get(name);
		if(dataType == null) 
			dataType = put((DataType) ConstructorUtil.newInstance(name));
		return dataType;
	}
}
