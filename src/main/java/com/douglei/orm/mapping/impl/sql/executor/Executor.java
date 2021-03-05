package com.douglei.orm.mapping.impl.sql.executor;

import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public interface Executor {
	
	/**
	 * 该node是否满足匹配
	 * @param sqlParameter
	 * @return
	 */
	default boolean matching(Object sqlParameter) {
		return matching(sqlParameter, null);
	}
	
	/**
	 * 该node是否满足匹配
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	default boolean matching(Object sqlParameter, String previousAlias) {
		return true;
	}
	
	/**
	 * 获取可执行的sql node
	 * @param purposeEntity
	 * @param sqlParameter
	 * @return
	 */
	default ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter) {
		return getExecutableSqlNode(purposeEntity, sqlParameter, null);
	}
	
	/**
	 * 获取可执行的sql node
	 * @param purposeEntity
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias);
	
	/**
	 * 验证参数
	 * @param sqlParameter
	 * @return
	 */
	default ValidateFailResult validateParameter(Object sqlParameter) {
		return validateParameter(sqlParameter, null);
	}
	
	/**
	 * 验证参数
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	ValidateFailResult validateParameter(Object sqlParameter, String previousAlias);
}
