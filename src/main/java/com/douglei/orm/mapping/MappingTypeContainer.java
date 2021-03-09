package com.douglei.orm.mapping;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.impl.procedure.ProcedureMappingType;
import com.douglei.orm.mapping.impl.sql.SqlMappingType;
import com.douglei.orm.mapping.impl.table.TableMappingType;
import com.douglei.orm.mapping.impl.view.ViewMappingType;

/**
 * 
 * @author DougLei
 */
public class MappingTypeContainer {
	private static final Logger logger = LoggerFactory.getLogger(MappingTypeContainer.class);
	private static final Map<String, MappingType> CONTAINER = new HashMap<String, MappingType>();
	static {
		// 注册内置的映射类型
		register(new TableMappingType());
		register(new SqlMappingType());
		register(new ViewMappingType());
		register(new ProcedureMappingType());
	}
	
	/**
	 * 注册MappingType
	 * @param type
	 */
	public static void register(MappingType type) {
		if(CONTAINER.containsKey(type.getName()))
			throw new MappingTypeException("注册MappingType失败: 已存在名为["+type.getName()+"]的映射类型");
		
		for(MappingType mt : CONTAINER.values()) {
			if(mt.getFileSuffix().equals(type.getFileSuffix()))
				throw new MappingTypeException("注册MappingType失败: 已存在文件后缀为["+type.getFileSuffix()+"]的映射类型");
		}
		
		logger.info("MappingType注册成功: name为[{}], fileSuffix为[{}]", type.getName(), type.getFileSuffix());
		CONTAINER.put(type.getName(), type);
	}
	
	/**
	 * 
	 * @param name 映射类型名称, 可参考{@link MappingTypeNameConstants}
	 * @return
	 */
	public static MappingType getMappingTypeByName(String name) {
		MappingType type = CONTAINER.get(name);
		if(type == null)
			throw new MappingTypeException("不支持名为["+name+"]的映射类型");
		return type;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static MappingType getMappingTypeByFile(String file) {
		for(MappingType mappingType : CONTAINER.values()) {
			if(file.endsWith(mappingType.getFileSuffix()))
				return mappingType;
		}
		throw new MappingTypeException("不支持文件后缀为["+file+"]的映射类型");
	}
	
	/**
	 * 获取目前容器中所有映射类型支持的文件后缀数组
	 * @return
	 */
	public static String[] getFileSuffixes() {
		String[] fileSuffixes = new String[CONTAINER.size()];
		
		int index = 0;
		for(MappingType mt : CONTAINER.values()) 
			fileSuffixes[index++] = mt.getFileSuffix();
			
		return fileSuffixes;
	}
	
	/**
	 * 
	 * @param name
	 * @param fileSuffix
	 * @return
	 */
	public static boolean exists(String name, String fileSuffix) {
		if(CONTAINER.containsKey(name))
			return true;
		
		for(MappingType mt : CONTAINER.values()) {
			if(mt.getFileSuffix().equals(fileSuffix))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param name
	 */
	public static void remove(String name) {
		if(CONTAINER.containsKey(name))
			CONTAINER.remove(name);
	}
}
