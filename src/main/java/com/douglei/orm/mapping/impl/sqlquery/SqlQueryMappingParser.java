package com.douglei.orm.mapping.impl.sqlquery;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.parser.SqlMappingParser;
import com.douglei.orm.mapping.impl.sqlquery.metadata.DataType;
import com.douglei.orm.mapping.impl.sqlquery.metadata.ParameterMetadata;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.mapping.metadata.MetadataParseException;

/**
 * 
 * @author DougLei
 */
class SqlQueryMappingParser extends SqlMappingParser{
	
	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		Element rootElement = MappingParseToolContext.getMappingParseTool().getDocumentBuilder().parse(input).getDocumentElement();
		
		// 解析SqlQueryMetadata
		NodeList sqlNodeList = rootElement.getElementsByTagName(MappingTypeNameConstants.SQL_QUERY);
		if(sqlNodeList == null || sqlNodeList.getLength() == 0) 
			throw new MetadataParseException("必须配置<sql-query>");
		
		Node sqlNode = sqlNodeList.item(0);
		SqlQueryMetadata querySqlMetadata = parseSqlQueryMetadata(sqlNode);
		
		// 设置content
		querySqlMetadata.setContent(parseContentMetadata(sqlNode));
		
		// 设置parameters
		querySqlMetadata.setParameterMap(parseParameterMap(sqlNode));
		
		return buildMappingSubjectByDocumentBuilder(entity.isEnableProperty(), new SqlQueryMapping(querySqlMetadata), rootElement);
	}
	
	// 解析SqlQueryMetadata
	private SqlQueryMetadata parseSqlQueryMetadata(Node sqlNode) throws MetadataParseException {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		
		// 解析name
		String name = getAttributeValue(attributeMap.getNamedItem("name"));
		if(name == null)
			throw new MetadataParseException("<sql-query>的name属性值不能为空");
		
		// 解析oldName
		String oldName = getAttributeValue(attributeMap.getNamedItem("oldName"));
		return new SqlQueryMetadata(name, oldName);
	}
	
	// 解析content
	private ContentMetadata parseContentMetadata(Node sqlNode) throws XPathExpressionException {
		NodeList contentNodeList = MappingParseToolContext.getMappingParseTool().getContentNodeList(sqlNode);
		if(contentNodeList.getLength() != 1) 
			throw new MetadataParseException("<sql-query>中必须且只能配置一个<content>");
		return contentMetadataParser.parse(contentNodeList.item(0));
	}
	
	// 解析parameters
	private Map<String, ParameterMetadata> parseParameterMap(Node sqlNode) throws XPathExpressionException {
		NodeList list = MappingParseToolContext.getMappingParseTool().getParameterNodeList(sqlNode);
		if(list.getLength() == 0)
			throw new MetadataParseException("<sql-query>下必须配置有效的<parameters>");
		
		Map<String, ParameterMetadata> parameterMap = new HashMap<String, ParameterMetadata>();
		ParameterMetadata parameter = null;
		for(int i=0;i<list.getLength();i++) {
			NamedNodeMap attributeMap = list.item(i).getAttributes();
			
			String name = getAttributeValue(attributeMap.getNamedItem("name"));
			if(name == null)
				throw new MetadataParseException("<parameter>的name属性值不能为空");
			
			String dataType = getAttributeValue(attributeMap.getNamedItem("dataType"));
			if(dataType == null)
				throw new MetadataParseException("<parameter>的dataType属性值不能为空");
			
			parameter = new ParameterMetadata(name.toUpperCase(), DataType.valueOf(dataType.toUpperCase()), "true".equalsIgnoreCase(getAttributeValue(attributeMap.getNamedItem("required"))));
			if(parameterMap.containsKey(parameter.getName()))
				throw new MetadataParseException("重复配置了name为["+parameter.getName()+"]的<parameter>");
			parameterMap.put(parameter.getName(), parameter);
		}
		return parameterMap;
	}
}