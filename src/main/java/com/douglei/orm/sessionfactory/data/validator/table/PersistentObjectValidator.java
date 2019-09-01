package com.douglei.orm.sessionfactory.data.validator.table;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AbstractPersistentObject;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.UniqueValue;
import com.douglei.tools.utils.Collections;

/**
 * 持久化对象验证器
 * @author DougLei
 */
public class PersistentObjectValidator extends AbstractPersistentObject {
	
	private short validateDataCount;// 要验证的数据数量, 可以判断出是否是批量验证, 批量验证的时候, 需要验证唯一约束
	private List<Object> uniqueValues;// 如果是批量验证, 且有唯一约束, 则记录每个对象中相应的唯一列的值
	
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
				if((result = column.getValidatorHandler().doValidate(value)) != null) {
					return result;
				}
			}
			
			// 如果还存在唯一约束, 则要对集合中的数据也进行验证
			if(validateDataCount > 1 && existsUniqueConstraint()) {
				if(uniqueValues == null) {
					uniqueValues = new ArrayList<Object>(validateDataCount);
					uniqueValues.add(getPersistentObjectUniqueValue());
				}else {
					if((result = validateUniqueValue()) != null) {
						return result;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 验证唯一值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ValidationResult validateUniqueValue() {
		Object currentPersistentObjectUniqueValue = getPersistentObjectUniqueValue();
		if(uniqueConstraints.size() == 1) {
			for (Object beforePersistentObjectUniqueValue : uniqueValues) {
				if(currentPersistentObjectUniqueValue.equals(beforePersistentObjectUniqueValue)) {
					return new UniqueValidationResult(uniqueConstraints.get(0).getAllCode(), ((UniqueValue)currentPersistentObjectUniqueValue).getValue());
				}
			}
		}else {
			List<UniqueValue> currentPersistentObjectUniqueValues = (List<UniqueValue>) currentPersistentObjectUniqueValue;
			List<UniqueValue> beforePersistentObjectUniqueValues = null;
			short index;
			for (Object beforePersistentObjectUniqueValue : uniqueValues) {
				beforePersistentObjectUniqueValues = (List<UniqueValue>) beforePersistentObjectUniqueValue;
				for(index=0; index < uniqueConstraints.size(); index++) {
					if(currentPersistentObjectUniqueValues.get(index).equals(beforePersistentObjectUniqueValues.get(index))) {
						return new UniqueValidationResult(uniqueConstraints.get(index).getAllCode(), currentPersistentObjectUniqueValues.get(index).getValue());
					}
				}
			}
		}
		uniqueValues.add(currentPersistentObjectUniqueValue);
		return null;
	}
	
	/**
	 * 销毁
	 */
	public void destroy() {
		Collections.clear(uniqueValues);
	}
}
