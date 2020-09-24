package com.douglei.orm.configuration.impl.element.environment.mapping;

import org.dom4j.io.SAXReader;

/**
 * 
 * @author DougLei
 */
class MappingResolver {
	private SAXReader tableMappingResolver; // 表映射处理器, 表资源就只需要阅读器即可
	private MappingResolver4Sql sqlMappingResolver; // sql映射处理器
	
	/**
	 * @see MappingResolverContext#getTableMappingReader()
	 * @return
	 */
	public SAXReader getTableMappingResolver() {
		if(tableMappingResolver == null)
			tableMappingResolver = new SAXReader();
		return tableMappingResolver;
	}
	
	/**
	 * 获取sql映射处理器
	 * @return
	 */
	public MappingResolver4Sql getSqlMappingResolver() {
		if(sqlMappingResolver == null)
			sqlMappingResolver = new MappingResolver4Sql();
		return sqlMappingResolver;
	}
}
