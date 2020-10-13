package com.douglei.orm.mapping;

import com.douglei.orm.mapping.handler.entity.impl.AddOrCoverMappingEntity;

/**
 * 映射特性设置器, 在系统启动的过程中, 当系统加载配置的映射资源文件时, 其用来设置每个映射的特性
 * @author DougLei
 */
public class MappingFeatureSetter {
	
	/**
	 * 设置特性
	 * @param mappingResourceFile 基于classpath的映射资源文件路径
	 * @param entity 每个映射实体, 其默认特性为: supportCover=false, supportDelete=false
	 * @return 给entity设置完特性后, 将其返回
	 */
	public AddOrCoverMappingEntity setFeature(String mappingResourceFile, AddOrCoverMappingEntity entity) {
		return entity;
	}
}
