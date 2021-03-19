package com.douglei.orm.mapping.impl.sql.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.impl.sql.SqlMapping;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.parser.content.ContentMetadataParser;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidatorUtil;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMappingParser extends MappingParser {
	protected static ContentMetadataParser contentMetadataParser = new ContentMetadataParser();
	
	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		Element rootElement = MappingParseToolContext.getMappingParseTool().getDocumentBuilder().parse(input).getDocumentElement();
		
		// 解析SqlMetadata
		NodeList sqlNodeList = rootElement.getElementsByTagName(MappingTypeNameConstants.SQL);
		if(sqlNodeList == null || sqlNodeList.getLength() == 0) 
			throw new MetadataParseException("必须配置<sql>");
		Node sqlNode = sqlNodeList.item(0);
		SqlMetadata sqlMetadata = parseSqlMetadata(sqlNode);
		
		// 记录配置的验证器Map集合
		addValidators(sqlMetadata, sqlNode);
		
		// 记录配置的sql-content Map集合
		boolean existsSqlContentMap = MappingParseToolContext.getMappingParseTool().setSqlContentMap(sqlNode);
		
		// 添加content
		addContents(sqlMetadata, sqlNode);
		
		// 添加sql-content
		if(existsSqlContentMap) {
			MappingParseToolContext.getMappingParseTool().getSqlContentMap().values().forEach(obj -> {
				if(obj instanceof SqlContentMetadata)
					sqlMetadata.addSqlContent((SqlContentMetadata)obj);
			});
		}
		
		return buildMappingSubjectByDocumentBuilder(entity.isEnableProperty(), new SqlMapping(sqlMetadata), rootElement);
	}
	
	// 解析SqlMetadata
	private SqlMetadata parseSqlMetadata(Node sqlNode) throws MetadataParseException {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		
		// 解析namespace
		String namespace = getAttributeValue(attributeMap.getNamedItem("namespace"));
		if(namespace == null)
			throw new MetadataParseException("<sql>的namespace属性值不能为空");
		
		// 解析oldNamespace
		String oldNamespace = getAttributeValue(attributeMap.getNamedItem("oldNamespace"));
		return new SqlMetadata(namespace, oldNamespace);
	}
	
	/**
	 * 获取属性值
	 * @param node 
	 * @return
	 */
	protected String getAttributeValue(Node attributeNode) {
		if(attributeNode != null) {
			String value = attributeNode.getNodeValue();
			if(StringUtil.unEmpty(value)) 
				return value;
		}
		return null;
	}
	
	/**
	 * 添加验证器Map集合
	 * @param sqlMetadata
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void addValidators(SqlMetadata sqlMetadata, Node sqlNode) throws XPathExpressionException{
		NodeList list = MappingParseToolContext.getMappingParseTool().getValidatorNodeList(sqlNode);
		if(list.getLength() == 0)
			return;
		
		List<Validator> validators = null;
		for(int i=0;i<list.getLength();i++) {
			NamedNodeMap attributeMap = list.item(i).getAttributes();
			if(attributeMap.getLength() < 2) 
				continue;
			
			for(int j=0;j<attributeMap.getLength();j++) {
				Node attribute = attributeMap.item(j);
				if("name".equals(attribute.getNodeName()))
					continue;
				
				Validator validator = ValidatorUtil.get(attribute.getNodeName()).parse(attribute.getNodeValue());
				if(validator == null)
					continue;
				
				if(validators == null)
					validators = new ArrayList<Validator>(attributeMap.getLength()-1);
				validators.add(validator);
			}
			
			if(validators == null)
				continue;
			
			ValidatorUtil.sort(validators);
			sqlMetadata.putValidators(attributeMap.getNamedItem("name").getNodeValue(), validators);
			validators = null;
		}
	}
	
	/**
	 * 添加content
	 * @param sqlMetadata
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void addContents(SqlMetadata sqlMetadata, Node sqlNode) throws XPathExpressionException {
		NodeList contentNodeList = MappingParseToolContext.getMappingParseTool().getContentNodeList(sqlNode);
		if(contentNodeList.getLength() == 0) 
			throw new MetadataParseException("<sql>中至少要配置一个<content>");
		
		List<ContentMetadata> contents = new ArrayList<ContentMetadata>(contentNodeList.getLength());
		for (int i=0;i<contentNodeList.getLength();i++) {
			ContentMetadata content = contentMetadataParser.parse(contentNodeList.item(i));
			if(contents.contains(content))
				throw new MetadataParseException("<sql>中配置了重复name["+content.getName()+"]的<content>");
			contents.add(content);
		} 
		sqlMetadata.setContents(contents);
	}
}