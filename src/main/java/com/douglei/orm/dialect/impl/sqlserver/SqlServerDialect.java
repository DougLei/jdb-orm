package com.douglei.orm.dialect.impl.sqlserver;

import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Bigint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Char;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Datetime;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Int;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NChar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NVarchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Numeric;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Smallint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Text;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varbinarymax;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varcharmax;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.BlobDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.CharDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.ClobDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.DatetimeDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.NCharDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.NStringDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.NumberDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.SNumberDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.mapping.StringDataType;
import com.douglei.orm.dialect.impl.sqlserver.object.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class SqlServerDialect extends AbstractDialect{
	
	public SqlServerDialect() {
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
		dataTypeContainer.register(NChar.getSingleton());
		dataTypeContainer.register(Numeric.getSingleton());
		dataTypeContainer.register(NVarchar.getSingleton());
		dataTypeContainer.register(Smallint.getSingleton());
		dataTypeContainer.register(Text.getSingleton());
		dataTypeContainer.register(Varbinarymax.getSingleton());
		dataTypeContainer.register(Varchar.getSingleton());
		dataTypeContainer.register(Varcharmax.getSingleton());
		
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
		return DialectType.SQLSERVER;
	}
}
