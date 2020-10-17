package com.douglei.orm.sessionfactory.validator.table;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.impl.table.metadata.UniqueConstraint;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AbstractPersistentObject;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.UniqueValue;

/**
 * 持久化对象验证器
 * @author DougLei
 */
public class PersistentObjectValidator extends AbstractPersistentObject {
	private int validateDataCount;// 要验证的数据数量, 可以判断出是否是批量验证, 批量验证的时候, 需要验证唯一约束
	
	private List<UniqueConstraint> uniqueConstraints;// 唯一约束集合
	private List<Object> uniqueValues;// 如果是批量验证, 且有唯一约束, 则记录每个对象中相应的唯一列的值
	
	public PersistentObjectValidator(TableMetadata tableMetadata) {
		this(tableMetadata, 1);
	}
	public PersistentObjectValidator(TableMetadata tableMetadata, int validateDataCount) {
		super(tableMetadata);
		if((this.validateDataCount = validateDataCount) > 1)
			this.uniqueConstraints = tableMetadata.getUniqueConstraints();
	}

	// 进行验证
	public ValidationResult doValidate(Object originObject) {
		if(tableMetadata.getValidateColumns() != null) {
			setOriginObject(originObject);
			
			Object value = null;
			ValidationResult result = null;
			for(ColumnMetadata column : tableMetadata.getValidateColumns()) {
				value = objectMap.get(column.getCode());
				if((result = column.getValidateHandler().validate(value)) != null) {
					return result;
				}
			}
			
			// 如果还存在唯一约束, 则要对集合中的数据也进行验证
			if(uniqueConstraints != null) {
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
	 * 获取持久化对象的唯一值实例
	 * 调用该方法前, 必须要先使用 {@link AbstractPersistentObject#existsUniqueColumn()} 方法判断一下, 如果返回true才能调用该方法
	 * @return {@link UniqueValue} / {@link List<UniqueValue>}
	 */
	private Object getPersistentObjectUniqueValue(){
		if(uniqueConstraints.size() == 1) {
			return new UniqueValue(objectMap, uniqueConstraints.get(0));
		}else {
			List<UniqueValue> currentPersistentObjectUniqueValues = new ArrayList<UniqueValue>(uniqueConstraints.size());
			uniqueConstraints.forEach(uc -> currentPersistentObjectUniqueValues.add(new UniqueValue(objectMap, uc)));
			return currentPersistentObjectUniqueValues;
		}
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
					return new UniqueValidationResult(uniqueConstraints.get(0).getAllCode());
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
						return new UniqueValidationResult(uniqueConstraints.get(index).getAllCode());
					}
				}
			}
		}
		uniqueValues.add(currentPersistentObjectUniqueValue);
		return null;
	}
}
