package com.douglei.orm.dialect;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public class DialectContainer {
	private static final List<Dialect> instanceContainer = new ArrayList<Dialect>(DialectType.values().length);
	
	/**
	 * 获取方言实例
	 * @param key
	 * @return
	 */
	public static Dialect get(DialectKey key) {
		if(!instanceContainer.isEmpty()) {
			for(Dialect dialect : instanceContainer) {
				if(dialect.getType().support(key))
					return dialect;
			}
		}
		
		for(DialectType dialectType : DialectType.values()) {
			if(dialectType.support(key)) {
				Dialect dialect = dialectType.newDialectInstance();
				instanceContainer.add(dialect);
				return dialect;
			}
		}
		throw new NullPointerException("框架目前不支持"+key+"的数据库");
	}
}
