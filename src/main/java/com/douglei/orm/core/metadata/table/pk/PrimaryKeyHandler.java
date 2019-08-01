package com.douglei.orm.core.metadata.table.pk;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.impl.SequencePrimaryKeyHandler;

/**
 * 
 * @author DougLei
 */
public abstract class PrimaryKeyHandler {
	
	/**
	 * 获取处理器名称
	 * @return
	 */
	public String getName() {
		return getClass().getName();
	}
	
	/**
	 * 主键处理器是否支持处理多个主键列
	 * 默认为false
	 * @return
	 */
	public boolean supportProcessMultiPKColumns() {
		return true;
	}
	
	/**
	 * 给实体map设置主键值
	 * @param primaryKeyColumnCodes 当前操作的所有主键列code集合
	 * @param table 当前操作的主键列所属的表
	 * @param entityMap 当前操作的实体map
	 * @param primaryKeySequence 主键序列对象, 针对 {@link SequencePrimaryKeyHandler}
	 */
	public abstract void setValue2EntityMap(Set<String> primaryKeyColumnCodes, TableMetadata table, Map<String, Object> entityMap, PrimaryKeySequence primaryKeySequence);
}
