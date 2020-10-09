package com.douglei.orm.dialect.impl.mysql;

import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Bigint;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Char;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Datetime;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Int;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Mediumblob;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Mediumtext;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Smallint;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Varchar;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.BlobDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.CharDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.ClobDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.DatetimeDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.NCharDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.NStringDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.NumberDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.SNumberDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.mapping.StringDataType;
import com.douglei.orm.dialect.impl.mysql.object.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.mysql.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class MySqlDialect extends AbstractDialect{
	
	public MySqlDialect() {
		initDataTypeContainer();
		super.objectHandler = new DBObjectHandlerImpl();
		super.sqlStatementHandler = new SqlStatementHandlerImpl();
		super.sqlQueryHandler = new SqlQueryHandlerImpl(sqlStatementHandler);
	}
	
	/**
	 * 初始化数据类型容器
	 */
	private void initDataTypeContainer() {
		dataTypeContainer.register(Bigint.getSingleton());
		dataTypeContainer.register(Char.getSingleton());
		dataTypeContainer.register(Datetime.getSingleton());
		dataTypeContainer.register(Decimal.getSingleton());
		dataTypeContainer.register(Int.getSingleton());
		dataTypeContainer.register(Mediumblob.getSingleton());
		dataTypeContainer.register(Mediumtext.getSingleton());
		dataTypeContainer.register(Smallint.getSingleton());
		dataTypeContainer.register(Varchar.getSingleton());
		
		dataTypeContainer.register(new BlobDataType());
		dataTypeContainer.register(new CharDataType());
		dataTypeContainer.register(new ClobDataType());
		dataTypeContainer.register(new DatetimeDataType());
		dataTypeContainer.register(new NCharDataType());
		dataTypeContainer.register(new NStringDataType());
		dataTypeContainer.register(new NumberDataType());
		dataTypeContainer.register(new SNumberDataType());
		dataTypeContainer.register(new StringDataType());
	}
	
	@Override
	public DialectType getType() {
		return DialectType.MYSQL;
	}
}
