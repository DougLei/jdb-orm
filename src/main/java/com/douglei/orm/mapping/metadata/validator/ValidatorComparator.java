package com.douglei.orm.mapping.metadata.validator;

import java.util.Comparator;

/**
 * 
 * @author DougLei
 */
class ValidatorComparator implements Comparator<Validator> {
	static final ValidatorComparator instance = new ValidatorComparator();
	
	@Override
	public int compare(Validator o1, Validator o2) {
		if(o1.getOrder() < o2.getOrder())
			return -1;
		if(o1.getOrder() > o2.getOrder())
			return 1;
		return 0;
	}
}
