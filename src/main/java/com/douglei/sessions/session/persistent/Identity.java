package com.douglei.sessions.session.persistent;

import java.util.Map;

/**
 * 持久化对象
 * @author DougLei
 */
public class Identity {
	private Object id;

	public Identity(Object id) {
		this.id = id;
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
	public boolean isNull() {
		if(id == null) {
			return true;
		}
		if(id instanceof Map<?, ?>) {
			if(((Map<?, ?>)id).size() == 0) {
				return true;
			}
		}
		return false;
	}
}
