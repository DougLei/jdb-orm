package com.douglei.orm.context;

import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
class DBRunEnvironment {
	String configurationId;// 配置id
	String serializationFileRootPath;// 序列化文件根路径
	Dialect dialect;// 方言
	CreateMode tableCreateMode;// 全局的表创建模式
}
