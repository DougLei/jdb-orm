package com.douglei.orm.dialect.impl.oracle.object.pk.sequence;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.tools.StringUtil;

/**
 * oracle主键序列
 * @author DougLei
 */
public class OraclePrimaryKeySequence extends PrimaryKeySequence{
	private static final long serialVersionUID = 6391062981855439045L;

	public OraclePrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		if(StringUtil.isEmpty(name)) 
			name = "PKSEQ_" + tableName;
		EnvironmentContext.getDialect().getObjectHandler().validateObjectName(name);

		super.name = name;
		super.createSql = StringUtil.isEmpty(createSql)?"create sequence " + getName() +" nocache":createSql;
		super.dropSql = StringUtil.isEmpty(dropSql)?"drop sequence " + getName():dropSql;
	}

	/**
	 * 获取下一个序列值的sql语句
	 * @return
	 */
	public String getNextvalSql() {
		return getName() + ".nextval";
	}
}
