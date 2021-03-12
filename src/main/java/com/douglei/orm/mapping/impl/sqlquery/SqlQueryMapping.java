package com.douglei.orm.mapping.impl.sqlquery;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;

/**
 * 
 * @author DougLei
 */
public class SqlQueryMapping extends Mapping {

	public SqlQueryMapping(SqlQueryMetadata metadata) {
		super(MappingTypeNameConstants.SQL_QUERY, metadata);
	}
}
