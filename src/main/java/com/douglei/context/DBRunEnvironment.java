package com.douglei.context;

import com.douglei.core.dialect.Dialect;
import com.douglei.core.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
class DBRunEnvironment {
	Dialect dialect;// 方言
	CreateMode tableCreateMode;// 全局的表创建模式
}
