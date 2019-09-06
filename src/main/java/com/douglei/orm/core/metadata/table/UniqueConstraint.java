package com.douglei.orm.core.metadata.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author DougLei
 */
@SuppressWarnings("unchecked")
public class UniqueConstraint implements Serializable{
	private static final long serialVersionUID = 1466827271949722121L;
	private byte size;
	private Object code;// 唯一约束的列code, 可能为String(multiColumn=false), 也可能为List<String>(multiColumn=true)
	
	public UniqueConstraint(Constraint constraint) {
		Collection<ColumnMetadata> columns = constraint.getColumns();
		size = (byte) columns.size();
		if(size == 1) {
			code = columns.iterator().next().getCode();
		}else {
			List<String> codes = new ArrayList<String>(columns.size());
			columns.forEach(column -> codes.add(column.getCode()));
			code = codes;
		}
	}

	public byte size() {
		return size;
	}
	public boolean isMultiColumns() {
		return size > 1;
	}
	public String getCode() {
		return code.toString();
	}
	public List<String> getCodes(){
		return (List<String>) code;
	}
	public String getAllCode() {
		if(isMultiColumns()) {
			StringBuilder codeString = new StringBuilder(size*16);
			getCodes().forEach(code -> codeString.append(",").append(code));
			return codeString.substring(1);
		}
		return code.toString();
	}
}
