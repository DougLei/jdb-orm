package com.douglei.sessions.session.persistent;

/**
 * 持久化对象
 * @author DougLei
 */
public class PersistentObjectIdentity {
	private Object id;

	public PersistentObjectIdentity(Object id) {
		if(id == null) {
			throw new NullPointerException("id不能为空");
		}
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
		PersistentObjectIdentity other = (PersistentObjectIdentity) obj;
		if (id == null) {
			if (other.id == null) {
				return true;
			}
			return false;
		}
		return id.equals(other.id);
	}
}
