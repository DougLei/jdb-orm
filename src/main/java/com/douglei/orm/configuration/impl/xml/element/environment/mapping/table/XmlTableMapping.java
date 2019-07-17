package com.douglei.orm.configuration.impl.xml.element.environment.mapping.table;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.table.RepeatedPrimaryKeyException;
import com.douglei.orm.configuration.environment.mapping.table.TableMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate.XmlColumnMetadataValidate;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate.XmlTableMetadataValidate;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.orm.context.ImportDataContext;
import com.douglei.orm.core.dialect.db.table.entity.Constraint;
import com.douglei.orm.core.dialect.db.table.entity.ConstraintType;
import com.douglei.orm.core.dialect.db.table.entity.Index;
import com.douglei.orm.core.dialect.db.table.entity.IndexType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataValidate;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * table 映射
 * @author DougLei
 */
public class XmlTableMapping extends XmlMapping implements TableMapping{
	private static final Logger logger = LoggerFactory.getLogger(XmlTableMapping.class);
	
	private static final MetadataValidate tableMetadataValidate = new XmlTableMetadataValidate();
	private static final MetadataValidate columnMetadataValidate = new XmlColumnMetadataValidate();
	
	private TableMetadata tableMetadata;
	
	public XmlTableMapping(String configFileName, Element rootElement) {
		super(configFileName);
		logger.debug("开始解析table类型的映射文件: {}", configFileName);
		
		try {
			Element tableElement = Dom4jElementUtil.validateElementExists("table", rootElement);
			tableMetadata = (TableMetadata) tableMetadataValidate.doValidate(tableElement);
			addColumnMetadata(getColumnElements(tableElement));
			addConstraint(tableElement.element("constraints"));
			addIndex(tableElement.element("indexes"));
			tableMetadata.columnDataMigration();
		} catch (Exception e) {
			throw new MetadataValidateException("在文件"+configFileName+"中, "+ e.getMessage());
		}
		logger.debug("结束解析table类型的映射文件");
	}

	/**
	 * 获取column元素集合
	 * @param tableElement
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	private List<?> getColumnElements(Element tableElement) throws DocumentException {
		// 解析<columns>
		Element columnsElement = Dom4jElementUtil.validateElementExists("columns", tableElement);
		List<Object> columnElements = columnsElement.elements("column");
		if(columnElements == null || columnElements.size() == 0) {
			throw new NullPointerException("<columns>元素下至少配置一个<column>元素");
		}
		
		// 解析<import-columns>
		Element importColumnsElement = tableElement.element("import-columns");
		if(importColumnsElement != null) {
			List<Object> importColumnElements = getImportColumnElements(((Element)importColumnsElement).attributeValue("path"));
			if(importColumnElements != null) {
				columnElements.addAll(importColumnElements);
			}
		}
		return columnElements;
	}

	/**
	 * 获取导入的column元素集合
	 * @param importColumnFilePath
	 * @return
	 * @throws DocumentException 
	 */
	private List<Object> getImportColumnElements(String importColumnFilePath) throws DocumentException {
		if(StringUtil.notEmpty(importColumnFilePath)) {
			return ImportDataContext.getImportColumnElements(importColumnFilePath);
		}
		return null;
	}

	/**
	 * 添加列元数据
	 * @param columnsElement
	 * @throws MetadataValidateException 
	 */
	private void addColumnMetadata(List<?> columnElements) throws MetadataValidateException {
		boolean classNameIsNull = tableMetadata.classNameIsNull();
		ColumnMetadata columnMetadata = null;
		for (Object object : columnElements) {
			columnMetadata = (ColumnMetadata)columnMetadataValidate.doValidate(object);
			if(columnMetadata.isPrimaryKey() && tableMetadata.existsPrimaryKey()) {
				throw new RepeatedPrimaryKeyException("主键配置重复, 通过<column>只能将单个列配置为主键, 如果需要配置联合主键, 请通过<constraint type='primary_key'>元素配置");
			}
			
			columnMetadata.fixPropertyNameValue(classNameIsNull);
			tableMetadata.addColumn(columnMetadata);
		}
	}
	
	/**
	 * 添加约束
	 * @param constraintsElement
	 */
	private void addConstraint(Element constraintsElement) {
		if(constraintsElement != null) {
			List<?> elements = constraintsElement.elements("constraint");
			if(elements != null && elements.size() > 0) {
				Element constraintElement = null;
				ConstraintType constraintType = null;
				List<?> columnNames = null;
				Constraint constraint = null;
				ColumnMetadata columnMetadata = null;
				
				for (Object object : elements) {
					constraintElement = ((Element)object);
					columnNames = constraintElement.selectNodes("column-name/@value");
					if(columnNames == null || columnNames.size() == 0) {
						throw new NullPointerException(constraintElement.asXML() + " 中没有配置任何<column-name value=\"xxx\">元素");
					}
					
					constraintType = ConstraintType.toValue(((Element)object).attributeValue("type"));
					if(constraintType == null) {
						throw new NullPointerException("<constraint>元素中的type属性值错误:["+((Element) object).attributeValue("type")+"], 目前支持的值包括: " + Arrays.toString(ConstraintType.values()));
					}
					
					constraint = new Constraint(constraintType, tableMetadata.getName());
					switch(constraintType) {
						case PRIMARY_KEY:
						case UNIQUE:
							for(Object columnName: columnNames) {
								columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnName).getValue().toUpperCase(), true);
								constraint.addColumn(columnMetadata);
							}
							break;
						case DEFAULT_VALUE:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnNames.get(0)).getValue().toUpperCase(), true);
							constraint.addColumn(columnMetadata).setDefaultValue(constraintElement.attributeValue("value"));
							break;
						case CHECK:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnNames.get(0)).getValue().toUpperCase(), true);
							constraint.addColumn(columnMetadata).setCheck(constraintElement.attributeValue("expression"));
							break;
						case FOREIGN_KEY:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnNames.get(0)).getValue().toUpperCase(), true);
							constraint.addColumn(columnMetadata).setForeignKey(constraintElement.attributeValue("fkTableName"), constraintElement.attributeValue("fkColumnName"));
							break;
					}
					tableMetadata.addConstraint(constraint);
				}
			}
		}
	}
	
	/**
	 * 添加索引
	 * @param indexesElement
	 */
	private void addIndex(Element indexesElement) {
		if(indexesElement != null) {
			List<?> elements = indexesElement.elements("index");
			if(elements != null && elements.size() > 0) {
				Element indexElement = null;
				IndexType indexType = null;
				for (Object object : elements) {
					indexElement = ((Element)object);
					indexType = IndexType.toValue(indexElement.attributeValue("type"));
//					if(indexType == null) { // TODO 目前索引这个类型为null, 因为还未实现索引的配置功能
//						throw new NullPointerException("<index>元素中的type属性值错误:["+indexElement.attributeValue("type")+"], 目前支持的值包括: " + Arrays.toString(IndexType.values()));
//					}
					tableMetadata.addIndex(new Index(indexType, tableMetadata.getName(), 
							indexElement.attributeValue("name"), 
							Dom4jElementUtil.validateElementExists("createSql", indexElement).getTextTrim(), 
							Dom4jElementUtil.validateElementExists("dropSql", indexElement).getTextTrim()));
				}
			}
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
