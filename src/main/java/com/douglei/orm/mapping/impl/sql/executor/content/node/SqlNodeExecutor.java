package com.douglei.orm.mapping.impl.sql.executor.content.node;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public interface SqlNodeExecutor<T extends SqlNode> {
	
	/**
	 * 获取sql节点类型
	 * @return
	 */
	SqlNodeType getType();
	
	/**
	 * 该node是否满足匹配
	 * @param currentSqlNode
	 * @param sqlParameter
	 * @return
	 */
	default boolean matching(T currentSqlNode, Object sqlParameter) {
		return matching(currentSqlNode, sqlParameter, null);
	}
	
	/**
	 * 该node是否满足匹配
	 * @param currentSqlNode
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	default boolean matching(T currentSqlNode, Object sqlParameter, String previousAlias) {
		return true;
	}
	
	/**
	 * 获取可执行的sql node
	 * @param purposeEntity
	 * @param currentSqlNode
	 * @param sqlContentMetadataMap
	 * @param sqlParameter
	 * @return
	 */
	default ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, T currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter) {
		return getExecutableSqlNode(purposeEntity, currentSqlNode, sqlContentMetadataMap, sqlParameter, null);
	}
	
	/**
	 * 获取可执行的sql node
	 * @param purposeEntity
	 * @param currentSqlNode
	 * @param sqlContentMetadataMap
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, T currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias);
	
	/**
	 * 验证参数
	 * @param currentSqlNode
	 * @param sqlContentMetadataMap
	 * @param validatorsMap
	 * @param sqlParameter
	 * @return
	 */
	default ValidateFailResult validate(T currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter) {
		return validate(currentSqlNode, sqlContentMetadataMap, validatorsMap, sqlParameter, null);
	}
	
	/**
	 * 验证参数
	 * @param currentSqlNode
	 * @param sqlContentMetadataMap
	 * @param validatorsMap
	 * @param sqlParameter
	 * @param previousAlias 上一层的sql参数名前缀, 即别名alias
	 * @return
	 */
	default ValidateFailResult validate(T currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter, String previousAlias) {
		return null;
	}
}
