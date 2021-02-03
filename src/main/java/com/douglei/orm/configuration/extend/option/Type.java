package com.douglei.orm.configuration.extend.option;

import com.douglei.orm.metadata.type.MetadataType;
import com.douglei.orm.metadata.type.MetadataTypeContainer;
import com.douglei.tools.StringUtil;
import com.douglei.tools.reflect.ClassUtil;

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
			MetadataTypeContainer.register((MetadataType)ClassUtil.newInstance(value));
		}
	};
	
	/**
	 * 获取扩展项类型
	 * @param type
	 * @return
	 */
	static Type get(String type) {
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
