package com.douglei.database.dialect.datatype.classtype.impl;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.wrapper.Blob;

/**
 * 
 * @author DougLei
 */
public class BlobDataTypeHandler extends ClassDataTypeHandler{

	@Override
	public String getCode() {
		return "blob";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Blob.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			setNullBlob(preparedStatement, parameterIndex);
		}else {
			byte[] blobContent = null;
			if(value instanceof byte[]) {
				blobContent = (byte[]) value;
			}else if(value instanceof Blob){
				blobContent = ((Blob)value).getValue();
				if(blobContent == null || blobContent.length == 0) {
					setNullBlob(preparedStatement, parameterIndex);
				}
			}else {
				blobContent = value.toString().getBytes();
			}
			preparedStatement.setBinaryStream(parameterIndex, new ByteArrayInputStream(blobContent), blobContent.length);
		}
	}
	
	private void setNullBlob(PreparedStatement preparedStatement, int parameterIndex) throws SQLException {
		preparedStatement.setNull(parameterIndex, 2004);
	}
}
