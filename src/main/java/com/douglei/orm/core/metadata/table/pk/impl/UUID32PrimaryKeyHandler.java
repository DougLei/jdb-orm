package com.douglei.orm.core.metadata.table.pk.impl;

import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
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
	public void setValue2EntityMap(String code, ColumnMetadata column, Map<String, Object> entityMap) {
		entityMap.put(code, IdentityUtil.get32UUID());
	}
}
