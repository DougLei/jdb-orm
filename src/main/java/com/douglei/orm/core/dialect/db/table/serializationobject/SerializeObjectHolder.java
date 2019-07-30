package com.douglei.orm.core.dialect.db.table.serializationobject;

import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 序列化对象持有者
 * @author DougLei
 */
public class SerializeObjectHolder {
	private TableMetadata table;// 本次操作的table
	private TableMetadata oldTable;// 上一次操作的table, 该对象通过反序列化获得, 所以可能为空
	
	public SerializeObjectHolder(TableMetadata table, TableMetadata oldTable) {
		this.table = table;
		this.oldTable = oldTable;
	}
	
	public TableMetadata getTable() {
		return table;
	}
	public TableMetadata getOldTable() {
		return oldTable;
	}
	public SerializeOPType getSerializeOPType() {
		if(table == null) {
			return SerializeOPType.DROP;
		}else {
			if(oldTable == null) {
				return SerializeOPType.CREATE;
			}else {
				return SerializeOPType.UPDATE;
			}
		}
	}
}
