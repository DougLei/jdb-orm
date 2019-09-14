package com.douglei.orm.sessionfactory.dynamic.mapping;

/**
 * 
 * @author DougLei
 */
public class DynamicMappingOnceMaxCountOverflowException extends DynamicMappingException {
	private static final long serialVersionUID = 9010145567466258173L;

	public DynamicMappingOnceMaxCountOverflowException(short configCount, short actualCount) {
		super("本次操作动态映射的数量溢出 (配置最多一次操作"+configCount+"个, 本次操作的数量为"+actualCount+"个)");
	}
}
