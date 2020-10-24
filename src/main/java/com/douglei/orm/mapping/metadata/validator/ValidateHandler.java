package com.douglei.orm.mapping.metadata.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public class ValidateHandler implements Serializable{
	private static final long serialVersionUID = -8047443259945028038L;
	
	private String name;
	private List<Validator> validators = new ArrayList<Validator>(5);
	
	public ValidateHandler(String name) {
		this.name = name;
	}

	/**
	 * 添加验证器
	 * @param validator
	 */
	public void addValidator(Validator validator) {
		validators.add(validator);
	}
	
	/**
	 * 进行验证, 如果验证通过, 则返回null, 否则返回验证失败的message
	 * @param value
	 * @return
	 */
	public ValidationResult validate(Object value) {
		ValidationResult result = null;
		for (Validator validator : validators) {
			result = validator.validate(name, value);
			if(result != null || !validator.toNext(value))
				break;
		}
		return result;
	}

	/**
	 * 对验证器集合进行排序
	 */
	public void sort() {
		if(validators.size() > 2)
			Collections.sort(validators, ValidatorComparator.instance);
	}
	
	public String getName() {
		return name;
	}
}
