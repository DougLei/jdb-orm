package com.douglei.orm.sessionfactory.data.validator.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AbstractPersistentObject;

/**
 * 持久化对象验证器
 * @author DougLei
 */
public class PersistentObjectValidator extends AbstractPersistentObject {
	
	private short validateDataCount=1;// 要验证的数据数量, 可以判断出是否是批量验证, 批量验证的时候, 需要验证唯一约束
	private List<Map<String, Object>> uniqueValuesMap;// 如果是批量验证, 且有唯一约束, 则记录每个对象中相应的唯一列的值
	
	public PersistentObjectValidator(TableMetadata tableMetadata) {
		super(tableMetadata);
	}
	public PersistentObjectValidator(TableMetadata tableMetadata, int validateDataCount) {
		super(tableMetadata);
		this.validateDataCount = (short) validateDataCount;
	}

	// 进行验证
	public ValidationResult doValidate(Object originObject) {
		if(tableMetadata.existsValidateColumns()) {
			setOriginObject(originObject);
			Object value = null;
			ValidationResult result = null;
			for(ColumnMetadata column : tableMetadata.getValidateColumns()) {
				value = propertyMap.get(column.getCode());
				result = column.getValidatorHandler().doValidate(value);
				if(result != null) {
					return result;
				}
			}
			
			// 如果还存在唯一约束的列, 则先将这些有唯一约束的列数据记录下来
			if(validateDataCount>1 && tableMetadata.existsValidateUniqueColumns()) {
				if(uniqueValuesMap == null) {
					uniqueValuesMap = new ArrayList<Map<String,Object>>(validateDataCount);
				}
				uniqueValuesMap
			}
		}
		return null;
	}
	
}
