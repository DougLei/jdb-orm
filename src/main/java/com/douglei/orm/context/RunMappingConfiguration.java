package com.douglei.orm.context;

import java.util.List;

import com.douglei.orm.core.metadata.sql.SqlContentType;
import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 运行时映射配置
 * @author DougLei
 */
class RunMappingConfiguration {
	List<TableMetadata> createTables;// 记录create table对象集合
	List<TableMetadata> dropTables;// 记录drop table对象集合
	
	SqlContentType sqlContentType;// 记录每个sql content的type
}
