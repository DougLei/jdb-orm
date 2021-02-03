package com.douglei.orm.metadata.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.procedure.ProcedureMappingType;
import com.douglei.orm.mapping.impl.sql.SqlMappingType;
import com.douglei.orm.mapping.impl.table.TableMappingType;
import com.douglei.orm.mapping.impl.view.ViewMappingType;
import com.douglei.orm.metadata.MetadataException;

/**
 * 元数据类型容器
 * @author DougLei
 */
public class MetadataTypeContainer {
	private final Map<String, MetadataType> container = new HashMap<String, MetadataType>();
	
	public MetadataTypeContainer() {
		register(new TableMappingType());
		register(new SqlMappingType());
		register(new ProcedureMappingType());
		register(new ViewMappingType());
	}
	
	/**
	 * 注册新的类型
	 * @param type
	 */
	public void register(MetadataType type) {
		if(container.containsKey(type.getName()))
			throw new MetadataException("已存在名为["+type.getName()+"]的元数据类型");
		
		for(MetadataType mt : container.values()) {
			if(mt.getFileSuffix().equals(type.getFileSuffix()))
				throw new MetadataException("已存在文件后缀为["+type.getFileSuffix()+"]的元数据类型");
		}
		container.put(type.getName(), type);
	}
	
	/**
	 * 
	 * @param name 元数据类型名称, 可参考{@link MetadataTypeNameConstants}
	 * @return
	 */
	public MetadataType getMetadataTypeByName(String name) {
		MetadataType type = container.get(name);
		if(type == null) 
			throw new MetadataException("不存在名为["+name+"]的元数据类型");
		return type;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public MetadataType getMetadataTypeByFile(String file) {
		for(MetadataType type : container.values()) {
			if(file.endsWith(type.getFileSuffix()))
				return type;
		}
		throw new MetadataException("不存在文件后缀为["+file+"]的元数据类型");
	}
	
	/**
	 * 获取目前容器中所有元数据类型支持的文件后缀集合
	 * @return
	 */
	public List<String> getFileSuffixes() {
		List<String> fileSuffixes = new ArrayList<String>(container.size());
		container.values().forEach(type -> fileSuffixes.add(type.getFileSuffix()));
		return fileSuffixes;
	}
}
