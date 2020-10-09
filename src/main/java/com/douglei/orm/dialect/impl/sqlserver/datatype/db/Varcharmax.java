package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class Varcharmax extends AbstractCharacter{
	private static final Varcharmax singleton = new Varcharmax();
	public static Varcharmax getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varcharmax() {
		super(12);
		super.name = "VARCHAR";
	}
	
	@Override
	public String getSqlStatement(int length, int precision) {
		return name + "(MAX)";
	}
	
	@Override
	public ValidationResult validate(String name, Object value, int length, int precision) {
		if(value instanceof String || value.getClass() == char.class || value instanceof Character) 
			return null;
		return new ValidationResult(name, "数据值类型错误, 应为字符类型", "jdb.data.validator.value.datatype.error.string");
	}
}
