package com.douglei.orm.mapping.validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.OrmException;
import com.douglei.orm.mapping.validator.impl.DataTypeValidatorParser;
import com.douglei.orm.mapping.validator.impl.NotNullValidatorParser;

/**
 * 
 * @author DougLei
 */
public class ValidatorUtil {
	private static final Map<String, ValidatorParser> VALIDATOR_PARSER_MAP = new HashMap<String, ValidatorParser>();
	static {
		NotNullValidatorParser notNull = new NotNullValidatorParser();
		VALIDATOR_PARSER_MAP.put(notNull.getType(), notNull);
		
		DataTypeValidatorParser dataType = new DataTypeValidatorParser();
		VALIDATOR_PARSER_MAP.put(dataType.getType(), dataType);
	}

	/**
	 * 获取指定类型的Validator解析器
	 * @param type
	 * @return
	 */
	public static ValidatorParser get(String type) {
		ValidatorParser validator = VALIDATOR_PARSER_MAP.get(type);
		if(validator == null) {
			try {
				validator = (ValidatorParser)Class.forName(type).newInstance();
				VALIDATOR_PARSER_MAP.put(validator.getType(), validator);
			} catch (Exception e) {
				throw new OrmException("获取"+type+"类型的Validator解析器时出现异常, 请检查类型名称, 或自定义的Validator解析器实现类的路径是否正确", e);
			} 
		}
		return validator;
	}
	
	/**
	 * 根据优先级, 将验证执行器进行从小到大排序
	 * @param list
	 */
	public static void sort(List<Validator> list) {
		if(list.size() > 1)
			Collections.sort(list, SORT_COMPARATOR);
	}
	// ValidatorMetadata的排序比较器
	private static final Comparator<Validator> SORT_COMPARATOR = new Comparator<Validator>() {
		@Override
		public int compare(Validator o1, Validator o2) {
			if(o1.getPriority() < o2.getPriority())
				return -1;
			if(o1.getPriority() > o2.getPriority())
				return 1;
			return 0;
		}
	};
}
