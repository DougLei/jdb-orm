package com.douglei.orm.mapping.impl.table.metadata;

import com.douglei.orm.mapping.metadata.AbstractDatabaseObjectMetadata;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class AbstractMetadata4Table extends AbstractDatabaseObjectMetadata{
	private static final long serialVersionUID = 1195889286012689051L;

	protected AbstractMetadata4Table(String name, String oldName) {
		super(name);
		
		super.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) 
			super.oldName = super.name;
		else 
			super.oldName = oldName.toUpperCase();
	}
}
