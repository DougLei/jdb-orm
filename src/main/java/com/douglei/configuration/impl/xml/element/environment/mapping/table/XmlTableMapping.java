package com.douglei.configuration.impl.xml.element.environment.mapping.table;

import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.table.TableMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.table.validate.XmlColumnMetadataValidate;
import com.douglei.configuration.impl.xml.element.environment.mapping.table.validate.XmlTableMetadataValidate;
import com.douglei.configuration.impl.xml.util.ElementUtil;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.utils.StringUtil;

/**
 * table 映射
 * @author DougLei
 */
public class XmlTableMapping extends XmlMapping implements TableMapping{
	private static final Logger logger = LoggerFactory.getLogger(XmlTableMapping.class);
	
	private TableMetadata tableMetadata;
	
	private MetadataValidate tableMetadataValidate = new XmlTableMetadataValidate();
	private MetadataValidate columnMetadataValidate = new XmlColumnMetadataValidate();
	
	public XmlTableMapping(String configFileName, Element rootElement) {
		super(configFileName);
		logger.debug("开始解析table类型的映射文件: {}", configFileName);
		
		try {
			Element tableElement = ElementUtil.getNecessaryAndSingleElement("<table>", rootElement.elements("table"));
			tableMetadata = (TableMetadata) tableMetadataValidate.doValidate(tableElement);
			addColumnMetadata(ElementUtil.getNecessaryAndSingleElement(" <columns>", tableElement.elements("columns")));
			setPrimaryKeyColumn(ElementUtil.getNecessaryAndSingleElement(" <primary-key>", tableElement.elements("primary-key")));
		} catch (Exception e) {
			throw new MetadataValidateException("在文件"+configFileName+"中, "+ e.getMessage());
		}
		
		logger.debug("结束解析table类型的映射文件: {}", configFileName);
	}

	/**
	 * 添加列元数据
	 * @param columnsElement
	 * @throws MetadataValidateException 
	 */
	private void addColumnMetadata(Element columnsElement) throws MetadataValidateException {
		List<?> elems = columnsElement.elements("column");
		if(elems == null || elems.size() == 0) {
			throw new NullPointerException("<columns>元素下至少配置一个<column>元素");
		}
		
		boolean tableMetadataClassNameNotNull = tableMetadata.classNameNotNull();
		ColumnMetadata columnMetadata = null;
		for (Object object : elems) {
			columnMetadata = (ColumnMetadata)columnMetadataValidate.doValidate(object);
			if(tableMetadataClassNameNotNull && columnMetadata.propertyNameIsNull()) {
				throw new NullPointerException("<table>元素配置了className属性值, "+((Element)object).asXML()+"元素中必须配置propertyName属性值");
			}
			columnMetadata.fixPropertyNameValue(tableMetadata.classNameNotNull());
			tableMetadata.addColumnMetadata(columnMetadata);
		}
	}
	
	/**
	 * 设置主键列元数据
	 * @param primaryKeyElement
	 */
	private void setPrimaryKeyColumn(Element primaryKeyElement) {
		List<?> elems = primaryKeyElement.elements("column-name");
		if(elems == null || elems.size() == 0) {
			throw new NullPointerException("<primary-key>元素下至少配置一个<column-name>元素，标明主键列");
		}
		
		String primaryKeyColumnName = null;
		ColumnMetadata columnMetadata = null;
		for (Object object : elems) {
			primaryKeyColumnName = ((Element)object).attributeValue("value");
			if(StringUtil.isEmpty(primaryKeyColumnName)) {
				throw new NullPointerException("<primary-key>元素下配置的<column-name>元素中, value属性值不能为空");
			}
			columnMetadata = tableMetadata.getColumnMetadata(primaryKeyColumnName);
			if(columnMetadata == null) {
				throw new NullPointerException("<primary-key>元素下配置的<column-name value=\""+primaryKeyColumnName+"\">, 不存名为["+primaryKeyColumnName+"]的列信息");
			}
			tableMetadata.addPrimaryKeyColumnMetadata(columnMetadata);
		}
	}
	
	@Override
	public MappingType getMappingType() {
		return MappingType.TABLE;
	}

	public String getCode() {
		return tableMetadata.getCode();
	}

	@Override
	public Metadata getMetadata() {
		return tableMetadata;
	}
}
