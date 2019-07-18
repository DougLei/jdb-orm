package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataValidate;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlContentType;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlContentMetadataValidate implements MetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Node)obj);
	}

	private SqlContentMetadata doValidate(Node contentNode) {
		NamedNodeMap attributeMap = contentNode.getAttributes();
		SqlContentType type = getSqlContentType(attributeMap.getNamedItem("type"));
		RunMappingConfigurationContext.setCurrentSqlContentType(type);
		
		NodeList children = contentNode.getChildNodes();
		int length = doValidateContent(children);
		
		DialectType[] dialectTypes = getDialectTypes(attributeMap.getNamedItem("dialect"));
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(dialectTypes, type);
		SqlNode sqlNode = null;
		for(int i=0;i<length;i++) {
			sqlNode = SqlNodeHandlerMapping.doHandler(children.item(i));
			if(sqlNode != null) {
				sqlContentMetadata.addRootSqlNode(sqlNode);
			}
		}
		return sqlContentMetadata;
	}

	private SqlContentType getSqlContentType(Node type) {
		if(type == null) {
			throw new MetadataValidateException("<content>元素的type属性值不能为空");
		}else {
			SqlContentType sqlContentType = SqlContentType.toValue(type.getNodeValue());
			if(sqlContentType == null) {
				throw new NullPointerException("<content>元素中的type属性值错误:["+type+"], 目前支持的值包括: " + Arrays.toString(SqlContentType.values()));
			}
			return sqlContentType;
		}
	}
	
	private int doValidateContent(NodeList children) {
		int childrenLength = 0;
		if(children == null || (childrenLength = children.getLength()) == 0) {
			throw new NullPointerException("<content>元素中不存在任何sql语句");
		}
		if(RunMappingConfigurationContext.getCurrentSqlContentType() == SqlContentType.PROCEDURE) {
			short nodeType, textNodeCount = 0, otherNodeCount = 0;
			for(int i=0;i<childrenLength;i++) {
				nodeType = children.item(i).getNodeType();
				if(nodeType != Node.COMMENT_NODE) {
					if(nodeType == Node.TEXT_NODE) {
						textNodeCount++;
					}else {
						otherNodeCount++;
					}
				}
			}
			if(textNodeCount == 0 || otherNodeCount > 0) {
				throw new IllegalArgumentException("<content type='procedure'>时, 其中必须配置, 且只能配置sql文本内容 {call procedure_name([parameter...])}, 不能配置其他元素内容");
			}
		}
		return childrenLength;
	}
	
	private DialectType[] getDialectTypes(Node dialect) {
		String dialectValue = null; 
		if(dialect == null || StringUtil.isEmpty(dialectValue = dialect.getNodeValue())) {
			return new DialectType[] { DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getType() };
		}else {
			List<DialectType> dts = null;
			DialectType dt = null;
			String[] dialectValueArray = dialectValue.split(",");
			
			for(String _dialect: dialectValueArray) {
				dt = DialectType.toValue(_dialect.toUpperCase());
				if(dt == null) {
					throw new NullPointerException("<content>元素中的dialect属性值错误:["+dialect+"], 目前支持的值包括: " + Arrays.toString(DialectType.values()));
				}
				if(dt == DialectType.ALL) {
					return DialectType.values_();
				}else {
					if(dts == null) {
						dts = new ArrayList<DialectType>(dialectValueArray.length);
					}
					dts.add(dt);
				}
			}
			return dts.toArray(new DialectType[dts.size()]);
		}
	}
}
