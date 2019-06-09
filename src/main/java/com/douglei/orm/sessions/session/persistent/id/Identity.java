package com.douglei.orm.sessions.session.persistent.id;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.utils.StringUtil;

/**
 * 持久化对象
 * @author DougLei
 */
public class Identity {
	private static final Logger logger = LoggerFactory.getLogger(Identity.class);
	
	private Object id;

	public Identity(Object id) {
		this.id = id;
		if(isNull()) {
			throw new NullPointerException("id不能为空");
		}
		if(!(id.getClass() == int.class || id instanceof Integer || id instanceof String || id instanceof Map)) {
			throw new UnsupportedIdentityDataTypeException("目前id只支持[java.lang.Integer类型]、[java.lang.String类型]或[java.util.Map<String, Object>类型]");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取持久化对象id为: {} -- {}", id.getClass(), id.toString());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Identity other = (Identity) obj;
		if (id == null) {
			if (other.id == null) {
				return true;
			}
			return false;
		}
		return id.equals(other.id);
	}
	
	@Override
	public String toString() {
		if(id == null) {
			return null;
		}
		if(id instanceof Map) {
			if(((Map<?, ?>)id).size() == 0) {
				return null;
			}
		}
		return id.toString();
	}

	/**
	 * id值是否为空
	 * @return
	 */
	private boolean isNull() {
		if(id == null) {
			logger.debug("id is null");
			return true;
		}
		if(id instanceof Map<?, ?>) {
			logger.debug("id is map");
			Map<?, ?> tmap = (Map<?, ?>) id;
 			if(tmap.size() == 0) {
 				logger.debug("id map's size is 0");
				return true;
			}
 			Collection<?> values = tmap.values();
 			for (Object value : values) {
				if(StringUtil.isEmpty(value)) {
					logger.debug("id map, one of values is null");
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取主键值
	 * @param pkCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPKValue(String pkCode) {
		if(id instanceof Map) {
			return ((Map<String, Object>)id).get(pkCode);
		}else {
			return id;
		}
	}
}
