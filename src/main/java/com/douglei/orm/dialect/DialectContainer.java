package com.douglei.orm.dialect;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.environment.datasource.DatabaseMetadataEntity;

/**
 * 
 * @author DougLei
 */
public class DialectContainer {
	private static final List<Dialect> instanceContainer = new ArrayList<Dialect>(DialectType.values().length);
	
	/**
	 * 获取方言实例
	 * @param entity
	 * @return
	 */
	public static Dialect get(DatabaseMetadataEntity entity) {
		if(instanceContainer.size() > 0) {
			for(Dialect dialect : instanceContainer) {
				if(dialect.getType().support(entity))
					return dialect;
			}
		}
		
		for(DialectType dialectType : DialectType.values()) {
			if(dialectType.support(entity)) {
				Dialect dialect = dialectType.newInstance();
				instanceContainer.add(dialect);
				return dialect;
			}
		}
		throw new NullPointerException("框架目前不支持"+entity+"的数据库");
	}
}
