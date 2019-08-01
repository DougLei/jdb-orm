package com.douglei.orm.core.dialect.impl.oracle.db.object.pk.sequence;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * oracle主键序列
 * @author DougLei
 */
public class OraclePrimaryKeySequence extends PrimaryKeySequence{
	private static final long serialVersionUID = -7741465855053611421L;

	public OraclePrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		super(name, createSql, dropSql, tableName, primaryKeyColumn);
	}

	@Override
	protected String processCreateSql(String createSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return StringUtil.isEmpty(createSql)?"create sequence " + getName():createSql;
	}

	@Override
	protected String processDropSql(String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return StringUtil.isEmpty(dropSql)?"drop sequence " + getName():dropSql;
	}

	@Override
	public String getNextvalSql() {
		return getName() + ".nextval";
	}
	
	@Override
	public String getCurrvalSql() {
		return getName() + ".currval";
	}
}
