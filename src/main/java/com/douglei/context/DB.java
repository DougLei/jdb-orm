package com.douglei.context;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
class DB {
	Dialect dialect;// 方言
	CreateMode tableCreateMode;// 全局的表创建模式
}
