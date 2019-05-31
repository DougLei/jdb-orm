package com.douglei.context;

import java.util.List;

import com.douglei.core.dialect.db.table.op.create.TableCreator;
import com.douglei.core.dialect.db.table.op.drop.TableDrop;
import com.douglei.core.metadata.sql.SqlContentType;

/**
 * 运行时映射配置
 * @author DougLei
 */
class RunMappingConfiguration {
	List<TableCreator> tableCreators;// 记录create table对象集合
	List<TableDrop> tableDrops;// 记录drop table对象集合
	
	SqlContentType sqlContentType;// 记录每个sql content的type
}
