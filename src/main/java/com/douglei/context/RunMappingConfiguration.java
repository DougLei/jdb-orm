package com.douglei.context;

import java.util.List;

import com.douglei.database.dialect.db.table.TableCreator;
import com.douglei.database.metadata.sql.SqlContentType;

/**
 * 运行时映射配置
 * @author DougLei
 */
class RunMappingConfiguration {
	boolean existsPrimaryKey;// 记录当前表是否已经配置主键
	List<TableCreator> tableCreators;// 记录create table对象集合
	
	SqlContentType sqlContentType;// 记录每个sql content的type
}
