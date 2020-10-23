package com.douglei.orm.configuration.extend.option;

import com.douglei.orm.mapping.type.MappingType;
import com.douglei.orm.mapping.type.MappingTypeContainer;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 扩展项类型
 * @author DougLei
 */
enum Type {
	
	/**
	 * 映射类型
	 */
	MAPPING_TYPE {
		@Override
		void handle(String value) {
			MappingTypeContainer.register((MappingType)ConstructorUtil.newInstance(value));
		}
	};
	
	/**
	 * 获取扩展项类型
	 * @param type
	 * @return
	 */
	static Type toValue(String type) {
		if(StringUtil.isEmpty(type))
			throw new NullPointerException("扩展项的类型不能为空");
		if("mappingType".equals(type))
			return MAPPING_TYPE;
		throw new NullPointerException("不存在名为["+type+"]的扩展项类型");
	}

	/**
	 * 处理扩展项的值
	 * @param value
	 */
	abstract void handle(String value);
}
