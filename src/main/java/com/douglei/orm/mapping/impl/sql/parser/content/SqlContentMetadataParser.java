package com.douglei.orm.mapping.impl.sql.parser.content;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.DatabaseNameConstants;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.metadata.content.AutoIncrementIDMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlContentMetadataParser {
	
	/**
	 * 解析SqlContentMetadata
	 * @param node
	 * @return
	 * @throws MetadataParseException
	 */
	public SqlContentMetadata parse(Node node) throws MetadataParseException {
		NamedNodeMap attributeMap = node.getAttributes();
		String name = getName(node, attributeMap.getNamedItem("name"));
		
		// 如果是sql-content, 先去容器中查找是否存在, 如果存在则直接返回, 否则就向下进行解析
		if("sql-content".equals(node.getNodeName()) && MappingParseToolContext.getMappingParseTool().getSqlContentMap().get(name) instanceof SqlContentMetadata) 
			return MappingParseToolContext.getMappingParseTool().getSqlContent(name);
		
		ContentType type = parseContentType(attributeMap);
		AutoIncrementIDMetadata autoIncrementID = getAutoIncrementID(type, attributeMap);
		SqlContentMetadata metadata = new SqlContentMetadata(name, type, autoIncrementID);
		
		NodeList children = node.getChildNodes();
		for(int i=0;i<children.getLength();i++) {
			SqlNode sqlNode = SqlNodeContainer.parse(children.item(i));
			if(sqlNode != null) 
				metadata.addSqlNode(sqlNode);
		}
		return metadata;
	}
	
	/**
	 * 获取元素中name属性的值
	 * @param contentNode
	 * @param attribute
	 * @return
	 */
	public String getName(Node contentNode, Node attribute) {
		if(attribute != null) {
			String name = attribute.getNodeValue();
			if(StringUtil.unEmpty(name)) 
				return name;
		}
		throw new MetadataParseException("<"+contentNode.getNodeName() + ">中的name属性值不能为空");
	}
	
	/**
	 * 解析当前sql content的类型
	 * @param attributeMap
	 */
	protected ContentType parseContentType(NamedNodeMap attributeMap) {
		return null;
	}
	
	/**
	 * 获取自增主键值的配置
	 * @param type
	 * @param attributeMap
	 * @return
	 */
	private AutoIncrementIDMetadata getAutoIncrementID(ContentType type, NamedNodeMap attributeMap) {
		if(type != ContentType.INSERT)
			return null;
		
		Node key = attributeMap.getNamedItem("key");
		if(key == null || StringUtil.isEmpty(key.getNodeValue()))
			return null;
			
		AutoIncrementIDMetadata metadata = new AutoIncrementIDMetadata(key.getNodeValue());
		
		if(EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) {
			Node sequence = attributeMap.getNamedItem("sequence");
			if(sequence == null || StringUtil.isEmpty(sequence.getNodeValue()))
				throw new MetadataParseException("在oracle数据库中执行insert类型的sql, 并期待返回自增主键值时, 必须配置sequence");
			metadata.setSequence(sequence.getNodeValue());
		}
		return metadata;
	}
}
