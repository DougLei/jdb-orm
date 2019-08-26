package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.id;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 持久化对象
 * @author DougLei
 */
public class Identity {
	private static final Logger logger = LoggerFactory.getLogger(Identity.class);
	
	private Object id;
	private TableMetadata tableMetadata;

	public Identity(Object id) {
		this.id = id;
		if(isNull()) {
			throw new NullPointerException("id不能为空");
		}
		if(!isSupportType()) {
			throw new UnsupportedIdentityDataTypeException("目前id只支持[int][java.lang.Integer类型][java.lang.String类型]或[java.util.Map<String, Object>类型]");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取持久化对象id为: {} -- {}", id.getClass().getName(), id.toString());
		}
	}
	
	public void setTableMetadata(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
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
		// 当主键一致时， 如果该表没有配置主键, 且没有使用到主键序列, 则返回true, 表现为两个对象主键一致, 会发生冲突
		if(id.equals(other.id) && tableMetadata != null && tableMetadata.getPrimaryKeySequence() == null) {
			return true;
		}
		return false;
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
		}else if(id instanceof Map<?, ?>) {
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
	 * 是否是支持的类型
	 * @return
	 */
	public boolean isSupportType() {
		if(id.getClass() == int.class || id instanceof Integer || id instanceof String || id instanceof Map) {
			return true;
		}
		return false;
	}
}