package com.douglei.core.dialect.datatype.handler.classtype;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.core.dialect.datatype.DataType;
import com.douglei.core.dialect.datatype.handler.wrapper.Blob;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractBlobDataTypeHandler extends ClassDataTypeHandler{

	@Override
	public String getCode() {
		return DataType.BLOB.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Blob.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
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
		preparedStatement.setNull(parameterIndex, getSqlType());
	}
}
