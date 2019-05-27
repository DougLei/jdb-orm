package com.douglei.configuration.impl.xml.element.environment.mapping.table;

/**
 * 当前表是否create
 * @author DougLei
 */
public class CurrentTableIsCreate {
	private static final ThreadLocal<Boolean> IS_CREATE = new ThreadLocal<Boolean>();
	public static void setIsCreate(boolean isCreate) {
		IS_CREATE.set(isCreate);
	}
	public static boolean isCreate() {
		return IS_CREATE.get();
	}
}
