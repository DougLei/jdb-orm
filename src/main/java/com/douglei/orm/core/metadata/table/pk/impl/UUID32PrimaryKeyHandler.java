package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;
import java.util.Set;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.tools.utils.IdentityUtil;

/**
 * 
 * @author DougLei
 */
public class UUID32PrimaryKeyHandler extends PrimaryKeyHandler{

	@Override
	public boolean supportProcessMultiPKColumns() {
		return true;
	}

	@Override
	public void setValue2EntityMap(Set<String> primaryKeyColumnCodes, TableMetadata table, Map<String, Object> entityMap, PrimaryKeySequence primaryKeySequence) {
		primaryKeyColumnCodes.forEach(code -> entityMap.put(code, IdentityUtil.get32UUID()));
	}
	
	@Override
	public String getName() {
		return "uuid32";
	}
}
