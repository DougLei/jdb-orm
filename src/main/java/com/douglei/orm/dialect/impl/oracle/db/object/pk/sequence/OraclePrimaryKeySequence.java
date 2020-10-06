package com.douglei.orm.dialect.impl.oracle.db.object.pk.sequence;

import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * oracle主键序列
 * @author DougLei
 */
public class OraclePrimaryKeySequence extends PrimaryKeySequence{
	private static final long serialVersionUID = 6945818563370013043L;

	public OraclePrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		super(true, name, createSql, dropSql, tableName, primaryKeyColumn);
	}

	@Override
	protected String processCreateSql(String createSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return StringUtil.isEmpty(createSql)?"create sequence " + getName() +" nocache":createSql;
	}

	@Override
	protected String processDropSql(String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return StringUtil.isEmpty(dropSql)?"drop sequence " + getName():dropSql;
	}

	/**
	 * 获取下一个序列值的sql语句
	 * @return
	 */
	public String getNextvalSql() {
		return getName() + ".nextval";
	}
}
