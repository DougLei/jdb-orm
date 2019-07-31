package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeySequence;
import com.douglei.tools.utils.IdentityUtil;

/**
 * 
 * @author DougLei
 */
public class UUID36PrimaryKeyHandler extends PrimaryKeyHandler{
	
	@Override
	public boolean setValue2EntityMap(String code, ColumnMetadata column, Map<String, ColumnMetadata> primaryKeyColumns, TableMetadata table, Map<String, Object> entityMap, PrimaryKeySequence primaryKeySequence) {
		entityMap.put(code, IdentityUtil.getUUID());
		return true;
	}
	
	@Override
	public String getName() {
		return "uuid36";
	}
}
