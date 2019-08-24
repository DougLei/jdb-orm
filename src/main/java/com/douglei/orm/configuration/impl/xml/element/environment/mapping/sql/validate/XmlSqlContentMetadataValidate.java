package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.MetadataValidate;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlContentType;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * <sql-content>
 * @author DougLei
 */
public class XmlSqlContentMetadataValidate implements MetadataValidate<Node, SqlContentMetadata> {
	private static final String nodeName = "<sql-content>";
	
	@Override
	public SqlContentMetadata doValidate(Node contentNode) throws MetadataValidateException {
		NamedNodeMap attributeMap = contentNode.getAttributes();
		String contentName = getName(attributeMap.getNamedItem("name"));
		if(getContentType(attributeMap) == SqlContentType._SQL_CONTENT_ && MappingXmlConfigContext.existsSqlContent(contentName)) {// 如果是sql-content, 先去容器中查找是否存在, 如果存在则直接返回, 否则再向下解析
			return MappingXmlConfigContext.getSqlContentByName(contentName);
		}
		
		NodeList children = contentNode.getChildNodes();
		int length = doValidateContent(children);
		
		DialectType[] dialectTypes = getDialectTypes(attributeMap.getNamedItem("dialect"));
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(contentName, dialectTypes);
		SqlNode sqlNode = null;
		for(int i=0;i<length;i++) {
			sqlNode = SqlNodeHandlerMapping.doHandler(children.item(i));
			if(sqlNode != null) {
				sqlContentMetadata.addRootSqlNode(sqlNode);
			}
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
		throw new MetadataValidateException(getNodeName() + "元素的name属性值不能为空");
	}
	
	/**
	 * 获取当前sql content的类型
	 * @param attributeMap
	 */
	protected SqlContentType getContentType(NamedNodeMap attributeMap) {
		MappingXmlConfigContext.setSqlContentType(SqlContentType._SQL_CONTENT_);
		return SqlContentType._SQL_CONTENT_;
	}
	
	private int doValidateContent(NodeList children) {
		int childrenLength = 0;
		if(children == null || (childrenLength = children.getLength()) == 0) {
			throw new MetadataValidateException(getNodeName() + "元素中不存在任何sql语句");
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
	
	private DialectType[] getDialectTypes(Node dialect) {
		String dialectValue = null; 
		if(dialect == null || StringUtil.isEmpty(dialectValue = dialect.getNodeValue())) {
			return new DialectType[] { EnvironmentContext.getEnvironmentProperty().getDialect().getType() };
		}else {
			String[] dialectValueArray = dialectValue.split(",");
			DialectType dt = null;
			List<DialectType> dts = null;
			
			for(String _dialect: dialectValueArray) {
				dt = DialectType.toValue(_dialect.toUpperCase());
				if(dt == null) {
					throw new MetadataValidateException(getNodeName() + "元素中的dialect属性值错误:["+dialect+"], 目前支持的值包括: " + Arrays.toString(DialectType.values()));
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
