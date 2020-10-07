package com.douglei.orm.mapping.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.procedure.ProcedureMappingType;
import com.douglei.orm.mapping.impl.sql.SqlMappingType;
import com.douglei.orm.mapping.impl.table.TableMappingType;
import com.douglei.orm.mapping.impl.view.ViewMappingType;

/**
 * 
 * @author DougLei
 */
public class MappingTypeContainer {
	private static final Map<String, MappingType> container = new HashMap<String, MappingType>(8);
	private static final List<String> fileSuffixes = new ArrayList<String>(6);
	
	static {
		register(new TableMappingType());
		register(new SqlMappingType());
		register(new ProcedureMappingType());
		register(new ViewMappingType());
	}
	
	/**
	 * 注册MappingType
	 * @param mappingType
	 */
	public static void register(MappingType mappingType) {
		if(container.containsKey(mappingType.getName()))
			throw new RepeatedMappingTypeException("已存在名为 ["+mappingType.getName()+"] 的映射类型");
		if(fileSuffixes.contains(mappingType.getFileSuffix()))
			throw new RepeatedMappingTypeException("已存在文件后缀为 ["+mappingType.getFileSuffix()+"] 的映射类型");
		
		container.put(mappingType.getName(), mappingType);
		fileSuffixes.add(mappingType.getFileSuffix());
	}
	
	/**
	 * 根据映射类型的名称, 获取映射类型实例
	 * @param name  通过 {@link MappingTypeNameConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名 
	 * @return
	 */
	public static MappingType getMappingTypeByName(String name) {
		MappingType mappingType = container.get(name);
		if(mappingType == null) {
			List<String> names = new ArrayList<String>(fileSuffixes.size());
			for(MappingType mt : container.values()) 
				names.add(mt.getName());
			throw new NullPointerException("不存在名为 ["+name+"] 的映射类型, 目前包含的映射类型名有: " + names);
		}
		return mappingType;
	}
	
	/**
	 * 根据文件获取映射类型
	 * @param file
	 * @return
	 */
	public static MappingType getMappingTypeByFile(String file) {
		for(MappingType mappingType : container.values()) {
			if(file.endsWith(mappingType.getFileSuffix()))
				return mappingType;
		}
		throw new NullPointerException("传入的" + file + ", 没有匹配到对应的MappingType");
	}
	
	/**
	 * 获取映射文件的后缀
	 * @return
	 */
	public static List<String> getFileSuffixes() {
		return fileSuffixes;
	}
}
