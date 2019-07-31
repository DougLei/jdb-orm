package com.douglei.orm.core.metadata.table.pk;

import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
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
	 * @param code 当前操作的主键列的code
	 * @param column 当前操作的主键列
	 * @param primaryKeyColumns 当前操作的所有主键列集合
	 * @param table 当前操作的主键列所属的表
	 * @param entityMap 当前操作的实体map
	 * @param primaryKeySequence 主键序列对象, 针对 {@link SequencePrimaryKeyHandler}
	 * @return 是否继续, 返回false则停止设置主键值的操作
	 */
	public abstract boolean setValue2EntityMap(String code, ColumnMetadata column, Map<String, ColumnMetadata> primaryKeyColumns, TableMetadata table, Map<String, Object> entityMap, PrimaryKeySequence primaryKeySequence);
}
