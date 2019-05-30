package com.douglei.configuration.impl.xml.element.environment.mapping.table;

import java.util.Arrays;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.table.NotExistsPrimaryKeyException;
import com.douglei.configuration.environment.mapping.table.RepeatPrimaryKeyException;
import com.douglei.configuration.environment.mapping.table.TableMapping;
import com.douglei.configuration.environment.mapping.table.UnsupportConstraintConfigurationException;
import com.douglei.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.table.validate.XmlColumnMetadataValidate;
import com.douglei.configuration.impl.xml.element.environment.mapping.table.validate.XmlTableMetadataValidate;
import com.douglei.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.context.RunMappingConfigurationContext;
import com.douglei.database.dialect.db.table.TableCreator;
import com.douglei.database.dialect.db.table.entity.Constraint;
import com.douglei.database.dialect.db.table.entity.ConstraintType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.CreateMode;
import com.douglei.database.metadata.table.TableMetadata;

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
			addColumnMetadata(Dom4jElementUtil.validateElementExists("columns", tableElement));
			addConstraint(tableElement.element("constraints"));
			if(!tableMetadata.existsPrimaryKey()) {
				throw new NotExistsPrimaryKeyException("必须配置主键");
			}
			tableMetadata.columnDataMigration();
			if(tableMetadata.getCreateMode() == CreateMode.AUTO_CREATE) {
				RunMappingConfigurationContext.addTableCreator(new TableCreator(tableMetadata));
			}
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
		
		boolean classNameIsNull = tableMetadata.classNameIsNull();
		ColumnMetadata columnMetadata = null;
		for (Object object : elems) {
			columnMetadata = (ColumnMetadata)columnMetadataValidate.doValidate(object);
			if(columnMetadata.isPrimaryKey() && tableMetadata.existsPrimaryKey()) {
				throw new RepeatPrimaryKeyException("主键配置重复, 通过<column>只能将单个列配置为主键, 如果需要配置联合主键, 请通过<constraint type='primary_key'>元素配置");
			}
			
			columnMetadata.fixPropertyNameValue(classNameIsNull);
			tableMetadata.addColumn(columnMetadata);
		}
	}
	
	/**
	 * 添加约束
	 * @param elements
	 */
	private void addConstraint(Element constraintsElement) {
		if(constraintsElement != null) {
			List<?> elements = constraintsElement.elements("constraint");
			if(elements != null && elements.size() > 0) {
				ConstraintType constraintType = null;
				List<?> values = null;
				Constraint constraint = null;
				ColumnMetadata columnMetadata = null;
				for (Object object : elements) {
					values = ((Element)object).selectNodes("column-name/@value");
					if(values == null || values.size() == 0) {
						continue;
					}
					
					constraintType = ConstraintType.toValue(((Element)object).attributeValue("type"));
					if(constraintType == null) {
						throw new NullPointerException("<constraint>元素中的type属性值错误:["+((Element) object).attributeValue("type")+"], 目前支持的值包括: " + Arrays.toString(ConstraintType.values()));
					}
					if(constraintType == ConstraintType.DEFAULT_VALUE) {
						throw new UnsupportConstraintConfigurationException("不支持通过<constraint>元素给列配置默认值约束, 如需配置, 请在对应的<column>元素中配置defaultValue属性值");
					}
					if(constraintType == ConstraintType.PRIMARY_KEY && tableMetadata.existsPrimaryKey()) {
						throw new RepeatPrimaryKeyException("主键配置重复, 只能使用<constraint>元素配置(一次)主键, 或通过<column>元素配置单个列为主键");
					}
					
					constraint = new Constraint(constraintType, tableMetadata.getName());
					for(Object value: values) {
						columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)value).getValue().toUpperCase());
						if(constraintType == ConstraintType.PRIMARY_KEY) {
							columnMetadata.processPrimaryKeyAndNullabled(true, false);// 如果是主键约束, 则该列必须不能为空
						}
						constraint.addColumn(columnMetadata);
					}
					tableMetadata.addConstraint(constraint);
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
