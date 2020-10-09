package com.douglei.orm.dialect.impl.oracle;

import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Blob;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Char;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Clob;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Cursor;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Date;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NChar;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NVarchar2;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Varchar2;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.BlobDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.CharDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.ClobDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.DatetimeDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.NCharDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.NStringDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.NumberDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.SNumberDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.mapping.StringDataType;
import com.douglei.orm.dialect.impl.oracle.object.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.oracle.sqlhandler.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.oracle.sqlhandler.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public class OracleDialect extends AbstractDialect{

	public OracleDialect() {
		initDataTypeContainer();
		super.objectHandler = new DBObjectHandlerImpl();
		super.sqlStatementHandler = new SqlStatementHandlerImpl();
		super.sqlQueryHandler = new SqlQueryHandlerImpl(sqlStatementHandler);
	}

	/**
	 * 初始化数据类型容器
	 */
	private void initDataTypeContainer() {
		dataTypeContainer.register(Blob.getSingleton());
		dataTypeContainer.register(Char.getSingleton());
		dataTypeContainer.register(Clob.getSingleton());
		dataTypeContainer.register(Cursor.getSingleton());
		dataTypeContainer.register(Date.getSingleton());
		dataTypeContainer.register(NChar.getSingleton());
		dataTypeContainer.register(com.douglei.orm.dialect.impl.oracle.datatype.db.Number.getSingleton());
		dataTypeContainer.register(NVarchar2.getSingleton());
		dataTypeContainer.register(Varchar2.getSingleton());
		
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
		return DialectType.ORACLE;
	}
}
