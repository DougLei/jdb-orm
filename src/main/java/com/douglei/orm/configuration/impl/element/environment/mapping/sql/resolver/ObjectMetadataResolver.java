package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolverMapping;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.MetadataResolver;
import com.douglei.orm.core.metadata.MetadataResolvingException;
import com.douglei.orm.core.metadata.sql.ContentType;
import com.douglei.orm.core.metadata.sql.IncrementIdValueConfig;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * <sql-content>
 * @author DougLei
 */
public class ObjectMetadataResolver implements MetadataResolver<Node, SqlContentMetadata> {
	private String nodeName = "<sql-content>";
	
	@Override
	public SqlContentMetadata resolving(Node contentNode) throws MetadataResolvingException {
		NamedNodeMap attributeMap = contentNode.getAttributes();
		String contentName = getName(attributeMap.getNamedItem("name"));
		if(isSqlContentNode() && MappingResolverContext.existsSqlContent(contentName)) // 如果是sql-content, 先去容器中查找是否存在, 如果存在则直接返回, 否则就向下进行解析
			return MappingResolverContext.getSqlContent(contentName);
		
		ContentType contentType = getContentType(attributeMap);
		IncrementIdValueConfig incrementIdValueConfig = getIncrementIdValueConfig(contentType, attributeMap);
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(contentName, incrementIdValueConfig);
		
		NodeList children = contentNode.getChildNodes();
		int length = doValidateContent(children);
		SqlNode sqlNode = null;
		for(int i=0;i<length;i++) {
			sqlNode = SqlNodeResolverMapping.resolving(children.item(i));
			if(sqlNode != null) 
				sqlContentMetadata.addRootSqlNode(sqlNode);
		}
		return sqlContentMetadata;
	}
	
	/**
	 * 获取节点名称
	 * @return
	 */
	protected String getNodeName() {
		return nodeName;
	}
	
	/**
	 * 获取元素中name属性的值
	 * @param nameAttribute
	 * @return
	 */
	public String getName(Node nameAttribute) {
		if(nameAttribute != null) {
			String name = nameAttribute.getNodeValue();
			if(StringUtil.notEmpty(name)) {
				return name;
			}
		}
		throw new MetadataResolvingException(getNodeName() + "元素的name属性值不能为空");
	}
	
	/**
	 * 是否是<sql-content>节点
	 * @return
	 */
	protected boolean isSqlContentNode() {
		return true;
	}
	
	/**
	 * 获取当前sql content的类型
	 * @param attributeMap
	 */
	protected ContentType getContentType(NamedNodeMap attributeMap) {
		return null;
	}
	
	private int doValidateContent(NodeList children) {
		int childrenLength = 0;
		if(children == null || (childrenLength = children.getLength()) == 0) {
			throw new MetadataResolvingException(getNodeName() + "元素中不存在任何sql语句");
		}
		doValidateProcedureContent(childrenLength, children);
		return childrenLength;
	}
	
	/**
	 * 验证存储过程的内容
	 * @param childrenLength
	 * @param children
	 */
	protected void doValidateProcedureContent(int childrenLength, NodeList children) {
	}
	
	/**
	 * 获取自增主键值的配置
	 * @param contentType
	 * @param attributeMap
	 * @return
	 */
	private IncrementIdValueConfig getIncrementIdValueConfig(ContentType contentType, NamedNodeMap attributeMap) {
		if (contentType == ContentType.INSERT) {
			Node keyAttr = attributeMap.getNamedItem("key");
			String key;
			if(keyAttr != null && StringUtil.notEmpty(key = keyAttr.getNodeValue())) {
				IncrementIdValueConfig config = new IncrementIdValueConfig(key);
				
				if(EnvironmentContext.getDialect().getType() == DialectType.ORACLE) {
					Node oracleSequenceNameAttr = attributeMap.getNamedItem("oracleSequenceName");
					String oracleSequenceName = null;
					if(oracleSequenceNameAttr == null || StringUtil.isEmpty(oracleSequenceName = oracleSequenceNameAttr.getNodeValue()))
						throw new MetadataResolvingException("在oracle数据库中, 执行insert类型的sql, 并期待返回自增主键值时, 必须配置oracleSequenceName");
					config.setOracleSequenceName(oracleSequenceName);
				}
				return config;
			}
		}
		return null;
	}
}
