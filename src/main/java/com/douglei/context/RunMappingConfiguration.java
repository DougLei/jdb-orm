package com.douglei.context;

import java.util.List;

import com.douglei.database.dialect.table.TableCreator;
import com.douglei.database.metadata.sql.SqlContentType;

/**
 * 运行时映射配置
 * @author DougLei
 */
class RunMappingConfiguration {
	List<TableCreator> tableCreators;// 记录create table对象集合
	SqlContentType sqlContentType;// 记录每个sql content的type
}
