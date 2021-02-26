package com.douglei.orm.mapping.impl.sql;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.parser.SqlMetadataParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.ContentMetadataParser;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidatorParserContainer;
import com.douglei.orm.mapping.validator.ValidatorUtil;

/**
 * 
 * @author DougLei
 */
class SqlMappingParser extends MappingParser {
	private static SqlMetadataParser sqlMetadataParser = new SqlMetadataParser();
	private static ContentMetadataParser contentMetadataParser = new ContentMetadataParser();
	
	private SqlMetadata sqlMetadata;
	
	@Override
	public MappingSubject parse(InputStream input) throws Exception {
		Element rootElement = MappingParserContext.getDocumentBuilder().parse(input).getDocumentElement();
		
		// 解析SqlMetadata
		NodeList sqlNodeList = rootElement.getElementsByTagName(MappingTypeNameConstants.SQL);
		if(sqlNodeList == null || sqlNodeList.getLength() == 0) 
			throw new MetadataParseException("必须配置<sql>");
		Node sqlNode = sqlNodeList.item(0);
		sqlMetadata = sqlMetadataParser.parse(sqlNode);
		
		// 记录配置的验证器Map集合
		setValidators(sqlNode);
		// 记录配置的验证器Map集合
		MappingParserContext.setSqlContents(sqlNode);
		
		// 添加content
		addContents(sqlNode);
		
		return buildMappingSubjectByDocumentBuilder(new SqlMapping(sqlMetadata), rootElement);
	}
	
	/**
	 * 记录配置的验证器Map集合
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void setValidators(Node sqlNode) throws XPathExpressionException{
		NodeList list = MappingParserContext.getValidatorNodeList(sqlNode);
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
				
				Validator validator = ValidatorParserContainer.get(attribute.getNodeName()).parse(attribute.getNodeValue());
				if(validator == null)
					continue;
				
				if(validators == null)
					validators = new ArrayList<Validator>(attributeMap.getLength()-1);
				validators.add(validator);
			}
			
			if(validators == null)
				continue;
			
			ValidatorUtil.sortByPriority(validators);
			sqlMetadata.addValidators(attributeMap.getNamedItem("name").getNodeValue(), validators);
			validators = null;
		}
	}
	
	/**
	 * 添加content
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void addContents(Node sqlNode) throws XPathExpressionException {
		NodeList contentNodeList = MappingParserContext.getContentNodeList(sqlNode);
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

	
//	public static void main(String[] args) throws Exception {
//		Element rootElement = MappingParserContext.getDocumentBuilder().parse(new FileInputStream(new File("D:\\workspace4\\jdb-orm\\doc\\sql映射配置结构设计.smp.xml"))).getDocumentElement();
//		NodeList sqlNodeList = rootElement.getElementsByTagName(MappingTypeNameConstants.SQL);
//		if(sqlNodeList == null || sqlNodeList.getLength() == 0) 
//			throw new MetadataParseException("必须配置<sql>");
//		Node sqlNode = sqlNodeList.item(0);
//		NodeList contentNodeList = MappingParserContext.getContentNodeList(sqlNode);
//		List<ContentMetadata> contents = new ArrayList<ContentMetadata>(contentNodeList.getLength());
//		for (int i=0;i<contentNodeList.getLength();i++) {
//			ContentMetadata content = contentMetadataParser.parse(contentNodeList.item(i));
//		}
//		XPathExpression validatorNodeExpression = XPathFactory.newInstance().newXPath().compile("validators/validator[@name!='']");
//		NodeList list = (NodeList) validatorNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
//		NamedNodeMap attributes = list.item(0).getAttributes();
//		for(int j=0;j<attributes.getLength();j++) {
//			Node attribute = attributes.item(j);
//			System.out.println(attribute.getNodeName() + "\t\t" + attribute.getNodeValue());
//		}
//	}
}