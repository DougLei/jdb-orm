package com.douglei.orm.context;

import java.util.List;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.core.metadata.sql.SqlContentType;

/**
 * 运行时映射配置
 * @author DougLei
 */
class RunMappingConfiguration {
	List<Mapping> createTableMappings;// 记录create table mapping对象集合
	List<Mapping> dropTableMappings;// 记录drop table mapping对象集合
	
	SqlContentType sqlContentType;// 记录每个sql content的type
	
	String executeMappingDescription;// 记录执行的每个映射描述
}
