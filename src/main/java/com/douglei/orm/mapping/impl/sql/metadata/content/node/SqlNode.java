package com.douglei.orm.mapping.impl.sql.metadata.content.node;

import java.io.Serializable;

import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public interface SqlNode extends Metadata{
	
	/**
	 * 获取sql节点类型
	 * @return
	 */
	SqlNodeType getType();
	
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
	default ValidationResult validateParameter(Object sqlParameter) {
		return validateParameter(sqlParameter, null);
	}
	
	/**
	 * 验证参数
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	ValidationResult validateParameter(Object sqlParameter, String previousAlias);
}
