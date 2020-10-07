package com.douglei.orm.dialect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.dialect.impl.mysql.MySqlDialectType;
import com.douglei.orm.dialect.impl.oracle.OracleDialectType;
import com.douglei.orm.dialect.impl.sqlserver.SqlServerDialectType;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class DialectContainer {
	private static final Map<DialectType, Class<? extends Dialect>> typeContainer = new HashMap<DialectType, Class<? extends Dialect>>(4);
	private static final List<Dialect> instanceContainer = new ArrayList<Dialect>(4);
	
	static {
		register(new MySqlDialectType());
		register(new OracleDialectType());
		register(new SqlServerDialectType());
	}
	
	/**
	 * 注册方言
	 * @param type
	 */
	public static void register(DialectType type) {
		typeContainer.keySet().forEach(dt -> {
			if(dt.getId() == type.getId()) 
				throw new RepeatedDialectException("已经存在id为 ["+type.getId()+"] 的方言 ======> " + dt);
			if(dt.getName().equals(type.getName()) && Arrays.equals(dt.supportDatabaseMajorVersions(), type.supportDatabaseMajorVersions()))
				throw new RepeatedDialectException("已经存在name为 ["+type.getName()+"], 且supportDatabaseMajorVersions为 "+Arrays.toString(type.supportDatabaseMajorVersions())+" 的方言 ======> " + dt);
		});
		typeContainer.put(type, type.targetClass());
	}
	
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
		
		for(Entry<DialectType, Class<? extends Dialect>> entry : typeContainer.entrySet()) {
			if(entry.getKey().support(key)) {
				Dialect dialect = (Dialect) ConstructorUtil.newInstance(entry.getValue());
				dialect.setType(entry.getKey());
				instanceContainer.add(dialect);
				return dialect;
			}
		}
		
		throw new NullPointerException("框架目前不支持"+key+"的数据库");
	}
}
