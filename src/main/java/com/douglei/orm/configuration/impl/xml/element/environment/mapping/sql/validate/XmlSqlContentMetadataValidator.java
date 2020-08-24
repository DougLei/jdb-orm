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
import com.douglei.orm.core.metadata.MetadataValidator;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.ContentType;
import com.douglei.orm.core.metadata.sql.IncrementIdValueConfig;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * <sql-content>
 * @author DougLei
 */
public class XmlSqlContentMetadataValidator implements MetadataValidator<Node, SqlContentMetadata> {
	private static String nodeName = "<sql-content>";
	
	@Override
	public SqlContentMetadata doValidate(Node contentNode) throws MetadataValidateException {
		NamedNodeMap attributeMap = contentNode.getAttributes();
		String contentName = getName(attributeMap.getNamedItem("name"));
		ContentType contentType = getContentType(attributeMap);
		if(contentType == null && MappingXmlConfigContext.existsSqlContent(contentName)) {// 如果是sql-content, 先去容器中查找是否存在, 如果存在则直接返回, 否则再向下解析
			return MappingXmlConfigContext.getSqlContent(contentName);
		}
		
		NodeList children = contentNode.getChildNodes();
		int length = doValidateContent(children);
		
		DialectType[] dialectTypes = getDialectTypes(attributeMap.getNamedItem("dialect"));
		IncrementIdValueConfig incrementIdValueConfig = getIncrementIdValueConfig(contentType, attributeMap);
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(contentName, dialectTypes, incrementIdValueConfig);
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
	protected ContentType getContentType(NamedNodeMap attributeMap) {
		return null;
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
	
	// 获取配置的dialect
	private DialectType[] getDialectTypes(Node dialect) {
		String dialectValue = null; 
		if(dialect == null || StringUtil.isEmpty(dialectValue = dialect.getNodeValue())) {
			return new DialectType[] { EnvironmentContext.getDialect().getType() };
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
						throw new MetadataValidateException("在oracle数据库中, 执行insert类型的sql, 并期待返回自增主键值时, 必须配置oracleSequenceName");
					config.setOracleSequenceName(oracleSequenceName);
				}
				return config;
			}
		}
		return null;
	}
}
