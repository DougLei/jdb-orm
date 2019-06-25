package com.douglei.orm.core.dialect.db.table.handler.serializationobject;

import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 序列化对象持有者
 * @author DougLei
 */
public class SerializationObjectHolder {
	private TableMetadata table;// 本次操作的table
	private TableMetadata oldTable;// 上一次操作的table, 该对象通过反序列化获得, 所以可能为空
	private SerializeOPType serializeOPType;
	
	public SerializationObjectHolder(TableMetadata table, TableMetadata oldTable) {
		this.table = table;
		this.oldTable = oldTable;
		setSerializeOPType();
	}
	
	private void setSerializeOPType() {
		if(table == null) {
			serializeOPType = SerializeOPType.DROP;
		}else {
			if(oldTable == null) {
				serializeOPType = SerializeOPType.CREATE;
			}else {
				serializeOPType = SerializeOPType.UPDATE;
			}
		}
	}

	public TableMetadata getTable() {
		return table;
	}
	public TableMetadata getOldTable() {
		return oldTable;
	}
	public SerializeOPType getSerializeOPType() {
		return serializeOPType;
	}
}
