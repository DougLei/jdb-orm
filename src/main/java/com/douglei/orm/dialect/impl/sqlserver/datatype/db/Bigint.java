package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class Bigint extends DBDataType{
	
	public Bigint() {
		super(-5);
	}
}
