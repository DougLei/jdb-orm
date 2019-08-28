package com.douglei.orm.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.douglei.tools.utils.naming.converter.ConverterUtil;
import com.douglei.tools.utils.naming.converter.impl.ColumnName2PropertyNameConverter;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * ResultSet Map转换工具类
 * @author DougLei
 */
public class ResultSetMapConvertUtil {
	
	/**
	 * 将resultset map的key, 由列名转换成属性名
	 * @param originResultsetMap
	 * @return
	 */
	private static Map<String, Object> resultsetMapKey2PropertyName_(Map<String, Object> originResultsetMap) {
		Map<String, Object> targetMap = new HashMap<String, Object>(originResultsetMap.size());
		Set<String> keys = originResultsetMap.keySet();
		for (String key : keys) {
			targetMap.put(ConverterUtil.convert(key, ColumnName2PropertyNameConverter.class), originResultsetMap.get(key));
		}
		return targetMap;
	}
	
	/**
	 * 将resultset map的key, 由列名转换成属性名
	 * @param originResultsetMap
	 * @return
	 */
	public static Map<String, Object> resultsetMapKey2PropertyName(Map<String, Object> resultsetMap) {
		if(resultsetMap != null && resultsetMap.size() > 0) {
			return resultsetMapKey2PropertyName_(resultsetMap);
		}
		return null;
	}
	
	/**
	 * 将resultset list<map>中map的key, 由列名转换成属性名
	 * @param originListMap
	 * @return
	 */
	public static List<Map<String, Object>> resultsetListMapKey2PropertyName(List<Map<String, Object>> resultsetListMap){
		if(resultsetListMap!= null && resultsetListMap.size()>0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(resultsetListMap.size());
			for (Map<String, Object> resultsetMap : resultsetListMap) {
				list.add(resultsetMapKey2PropertyName(resultsetMap));
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 转换为class对象
	 * @param resultsetMap
	 * @param targetClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toClass(Map<String, Object> resultsetMap, Class<T> targetClass) {
		if(resultsetMap != null && resultsetMap.size() > 0) {
			resultsetMap = resultsetMapKey2PropertyName_(resultsetMap);
			return (T) IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), resultsetMap);
		}
		return null;
	}
	
	/**
	 * 转换为list class对象集合
	 * @param resultsetListMap
	 * @param targetClass
	 * @return
	 */
	public static <T> List<T> toListClass(List<Map<String, Object>> resultsetListMap, Class<T> targetClass){
		if(resultsetListMap!= null && resultsetListMap.size()>0) {
			List<T> listT = new ArrayList<T>(resultsetListMap.size());
			for (Map<String, Object> resultsetMap : resultsetListMap) {
				listT.add(toClass(resultsetMap, targetClass));
			}
			return listT;
		}
		return null;
	}
}
