package com.douglei.orm.mapping.impl.sql.metadata.parser.content;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserContainer;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.tools.StringUtil;

/**
 * <sql-content>
 * @author DougLei
 */
public class SqlContentMetadataParser implements MetadataParser<Node, SqlContentMetadata> {
	
	@Override
	public SqlContentMetadata parse(Node contentNode) throws MetadataParseException {
		NamedNodeMap attributeMap = contentNode.getAttributes();
		String contentName = getName(attributeMap.getNamedItem("name"));
		if(isSqlContentNode() && MappingParserContext.existsSqlContent(contentName)) {// 如果是sql-content, 先去容器中查找是否存在, 如果存在则直接返回, 否则就向下进行解析
			return MappingParserContext.getSqlContent(contentName);
		}
		
		ContentType contentType = getContentType(attributeMap);
		IncrementIdValueConfig incrementIdValueConfig = getIncrementIdValueConfig(contentType, attributeMap);
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(contentName, incrementIdValueConfig);
		
		NodeList children = contentNode.getChildNodes();
		int length = validateContent(children);
		SqlNode sqlNode = null;
		for(int i=0;i<length;i++) {
			sqlNode = SqlNodeParserContainer.parse(children.item(i));
			if(sqlNode != null) 
				sqlContentMetadata.addSqlNode(sqlNode);
		}
		return sqlContentMetadata;
	}
	
	/**
	 * 获取节点名称
	 * @return
	 */
	protected String getNodeName() {
		return "<sql-content>";
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
		throw new MetadataParseException(getNodeName() + "元素的name属性值不能为空");
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
	
	/**
	 * 验证sql内容中有效的元素数量, 防止里面都是注释, 没有实际的sql语句
	 * @param children
	 * @return
	 */
	private int validateContent(NodeList children) {
		if(children == null) 
			throw new MetadataParseException(getNodeName() + "元素中不存在任何sql语句");
		
		int childrenLength = children.getLength();
		if(childrenLength == 0)
			throw new MetadataParseException(getNodeName() + "元素中不存在任何sql语句");
		
		short nodeType, nodeCount = 0;
		for(int i=0;i<childrenLength;i++) {
			nodeType = children.item(i).getNodeType();
			if(nodeType != Node.COMMENT_NODE) {
				nodeCount = 1;
				break;
			}
		}
		if(nodeCount == 0)
			throw new MetadataParseException(getNodeName() + "元素中不存在任何sql语句");
		
		validateProcedureContent(childrenLength, children);
		return childrenLength;
	}
	
	/**
	 * 验证存储过程的内容, 只能存在call语句和注释
	 * @param childrenLength
	 * @param children
	 */
	protected void validateProcedureContent(int childrenLength, NodeList children) {
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
						throw new MetadataParseException("在oracle数据库中, 执行insert类型的sql, 并期待返回自增主键值时, 必须配置oracleSequenceName");
					config.setOracleSequenceName(oracleSequenceName);
				}
				return config;
			}
		}
		return null;
	}
}
