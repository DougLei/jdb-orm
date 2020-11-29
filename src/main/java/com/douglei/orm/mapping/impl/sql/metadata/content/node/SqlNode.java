package com.douglei.orm.mapping.impl.sql.metadata.content.node;

import java.io.Serializable;

import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public interface SqlNode extends Serializable{
	
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
	 * @param alias
	 * @return
	 */
	default boolean matching(Object sqlParameter, String alias) {
		return true;
	}
	
	/**
	 * 获取可执行的sql node
	 * @param purposeEntity
	 * @param sqlParameter
	 * @return
	 */
	default ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter) {
		return getExecuteSqlNode(purposeEntity, sqlParameter, null);
	}
	
	/**
	 * 获取可执行的sql node
	 * @param purposeEntity
	 * @param sqlParameter
	 * @param alias sql参数名前缀, 即别名alias
	 * @return
	 */
	ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String alias);
	
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
	 * @param alias
	 * @return
	 */
	ValidationResult validateParameter(Object sqlParameter, String alias);
}
