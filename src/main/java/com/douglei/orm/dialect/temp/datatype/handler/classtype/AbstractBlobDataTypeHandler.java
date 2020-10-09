package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DataType2;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.Blob;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractBlobDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 2863641750405602113L;

	@Override
	public String getCode() {
		return DataType2.BLOB.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {BlobDataType.class};
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(value == null) {
			setNullBlob(preparedStatement, parameterIndex);
		}else {
			byte[] blobContent = null;
			if(value instanceof byte[]) {
				blobContent = (byte[]) value;
			}else if(value instanceof BlobDataType){
				blobContent = ((BlobDataType)value).getValue();
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
