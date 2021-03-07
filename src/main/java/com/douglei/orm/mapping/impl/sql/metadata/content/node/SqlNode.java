package com.douglei.orm.mapping.impl.sql.metadata.content.node;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public interface SqlNode extends Metadata {
	
	/**
	 * 获取sql节点类型
	 * @return
	 */
	SqlNodeType getType();
}
