package com.douglei.orm.mapping.validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public class ValidatorUtil {
	
	// Validator的排序比较器
	private static final Comparator<Validator> VALIDATOR_SORT_COMPARATOR = new Comparator<Validator>() {
		@Override
		public int compare(Validator o1, Validator o2) {
			if(o1.getPriority() < o2.getPriority())
				return -1;
			if(o1.getPriority() > o2.getPriority())
				return 1;
			return 0;
		}
	};
	
	/**
	 * 根据优先级, 将验证器进行从小到大排序
	 * @param list
	 */
	public static void sortByPriority(List<Validator> list) {
		if(list.size() > 1)
			Collections.sort(list, VALIDATOR_SORT_COMPARATOR);
	}
}
