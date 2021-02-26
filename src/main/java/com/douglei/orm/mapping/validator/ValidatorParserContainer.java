package com.douglei.orm.mapping.validator;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.OrmException;
import com.douglei.orm.mapping.validator.datatype.DataTypeValidatorParser;
import com.douglei.orm.mapping.validator.notblank.NotBlankValidatorParser;
import com.douglei.orm.mapping.validator.notnull.NotNullValidatorParser;

/**
 * 
 * @author DougLei
 */
public class ValidatorParserContainer {
	private static final Map<String, ValidatorParser> VALIDATOR_PARSER_MAP = new HashMap<String, ValidatorParser>();
	static {
		NotNullValidatorParser notNullValidatorParser = new NotNullValidatorParser();
		VALIDATOR_PARSER_MAP.put(notNullValidatorParser.getType(), notNullValidatorParser);
		
		NotBlankValidatorParser notBlankValidatorParser = new NotBlankValidatorParser();
		VALIDATOR_PARSER_MAP.put(notBlankValidatorParser.getType(), notBlankValidatorParser);
		
		DataTypeValidatorParser dataTypeValidatorParser = new DataTypeValidatorParser();
		VALIDATOR_PARSER_MAP.put(dataTypeValidatorParser.getType(), dataTypeValidatorParser);
	}
	
	/**
	 * 获取指定类型的Validator解析器
	 * @param type
	 * @return
	 */
	public static ValidatorParser get(String type) {
		ValidatorParser parser = VALIDATOR_PARSER_MAP.get(type);
		if(parser == null) {
			try {
				parser = (ValidatorParser)Class.forName(type).newInstance();
				VALIDATOR_PARSER_MAP.put(parser.getType(), parser);
			} catch (Exception e) {
				throw new OrmException("获取"+type+"类型的Validator解析器时出现异常, 请检查类型名称, 或自定义的Validator解析器实现类的路径是否正确", e);
			} 
		}
		return parser;
	}
}
