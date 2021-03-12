package com.douglei.orm.mapping.impl.sqlquery;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.impl.sqlquery.metadata.DataType;
import com.douglei.orm.mapping.impl.sqlquery.metadata.ParameterMetadata;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.sql.query.QuerySqlStatement;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
class SqlQueryMappingParser extends MappingParser{
	
	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		Element rootElement = MappingParseToolContext.getMappingParseTool().getSAXReader().read(input).getRootElement();
		Element querySqlElement = Dom4jUtil.getElement(MappingTypeNameConstants.SQL_QUERY, rootElement);
		
		// 创建QuerySqlMetadata实例
		String sql = Dom4jUtil.getElement("content", querySqlElement).getTextTrim();
		if(StringUtil.isEmpty(sql))
			throw new MetadataParseException("<content>中的sql内容不能为空");
		SqlQueryMetadata querySqlMetadata = new SqlQueryMetadata(getName(querySqlElement), getSql(Dom4jUtil.getElement("content", querySqlElement).getTextTrim()));
		
		// 设置参数
		Map<String, ParameterMetadata> parameterMap= parseParameterMap(querySqlElement.element("parameters"));
		querySqlMetadata.setParameterMap(parameterMap);
		
		return buildMappingSubjectByDom4j(entity.isEnableProperty(), new SqlQueryMapping(querySqlMetadata), rootElement);
	}

	// 获取指定Element的name属性值
	private String getName(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name))
			throw new MetadataParseException("<"+element.getName()+">中的name属性值不能为空");
		return name;
	}
	
	// 获取sql
	private SqlMetadata getSql(String sql) {
		if(StringUtil.isEmpty(sql))
			throw new MetadataParseException("<content>中的sql内容不能为空");
		
		QuerySqlStatement statement = new QuerySqlStatement(sql, false);
		return new SqlMetadata(statement.getSql(), statement.getWithClause());
	}

	// 解析参数
	@SuppressWarnings("unchecked")
	private Map<String, ParameterMetadata> parseParameterMap(Element element) {
		if(element == null)
			throw new MetadataParseException("<query-sql>下必须配置<parameters>");
			
		List<Element> elements = element.elements("parameter");
		if(elements.isEmpty())
			throw new MetadataParseException("<parameters>下至少配置一个<parameter>");
		
		// 解析出参数Map集合
		Map<String, ParameterMetadata> parameterMap= new HashMap<String, ParameterMetadata>();
		ParameterMetadata parameter = null;
		for (Element elem: elements) {
			parameter = new ParameterMetadata(getName(elem).toUpperCase(), DataType.valueOf(elem.attributeValue("dataType").toUpperCase()), "true".equalsIgnoreCase(elem.attributeValue("required")));
			if(parameterMap.containsKey(parameter.getName()))
				throw new MetadataParseException("重复配置了name为["+parameter.getName()+"]的<parameter>");
			parameterMap.put(parameter.getName(), parameter);
		}
		return parameterMap;
	}
}