package com.douglei.orm.configuration.impl.xml.element.environment.mapping.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.table.ConstraintConfigurationException;
import com.douglei.orm.configuration.environment.mapping.table.IndexConfigurationException;
import com.douglei.orm.configuration.environment.mapping.table.PrimaryKeyHandlerConfigurationException;
import com.douglei.orm.configuration.environment.mapping.table.RepeatedPrimaryKeyException;
import com.douglei.orm.configuration.environment.mapping.table.TableMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate.XmlColumnMetadataValidate;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate.XmlTableMetadataValidate;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.context.ImportDataContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataValidate;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;
import com.douglei.orm.core.metadata.table.ConstraintType;
import com.douglei.orm.core.metadata.table.Index;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandlerContext;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.pk.impl.IncrementPrimaryKeyHandler;
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
			setPrimaryKeyHandler(tableElement.element("primaryKeyHandler"));
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getColumnElements(Element tableElement) throws DocumentException {
		List columnElements = null;
		
		// 解析<columns>
		Element columnsElement = Dom4jElementUtil.validateElementExists("columns", tableElement);
		List<?> localColumnElements = columnsElement.elements("column");
		
		// 解析<import-columns>
		Element importColumnsElement = tableElement.element("import-columns");
		if(importColumnsElement != null) {
			List<?> importColumnElements = getImportColumnElements(((Element)importColumnsElement));
			if(importColumnElements != null) {
				columnElements = new ArrayList((localColumnElements==null?0:localColumnElements.size()) + importColumnElements.size());
				addToColumnElements(localColumnElements, columnElements);
				addToColumnElements(importColumnElements, columnElements);
			}
		}
		
		if(columnElements == null) {
			columnElements = localColumnElements;
		}
		if(columnElements == null || columnElements.size() == 0) {
			throw new NullPointerException("<columns>元素下至少配置一个<column>元素, 或通过<import-columns path=\"\">导入列");
		}
		return columnElements;
	}

	/**
	 * 获取导入的column元素集合
	 * @param importColumnElement
	 * @return
	 * @throws DocumentException 
	 */
	private List<?> getImportColumnElements(Element importColumnElement) throws DocumentException {
		String importColumnFilePath = importColumnElement.attributeValue("path");
		if(StringUtil.notEmpty(importColumnFilePath)) {
			return ImportDataContext.getImportColumnElements(importColumnFilePath);
		}
		return null;
	}
	
	/**
	 * 将sourceColumnElements集合中的数据, 添加到columnElements
	 * @param sourceColumnElements
	 * @param columnElements
	 */
	private void addToColumnElements(List<?> sourceColumnElements, List<Object> columnElements) {
		if(sourceColumnElements != null && sourceColumnElements.size() > 0) {
			for (Object sce : sourceColumnElements) {
				columnElements.add(sce);
			}
		}
	}

	/**
	 * 添加列元数据
	 * @param columnsElement
	 * @throws MetadataValidateException 
	 */
	private void addColumnMetadata(List<?> columnElements) throws MetadataValidateException {
		boolean classNameEmpty = tableMetadata.classNameEmpty();
		ColumnMetadata columnMetadata = null;
		for (Object object : columnElements) {
			columnMetadata = (ColumnMetadata)columnMetadataValidate.doValidate(object);
			if(columnMetadata.isPrimaryKey() && tableMetadata.existsPrimaryKey()) {
				throw new RepeatedPrimaryKeyException("主键配置重复, 通过<column>只能将单个列配置为主键, 如果需要配置联合主键, 请通过<constraint type='primary_key'>元素配置");
			}
			columnMetadata.correctProperty(classNameEmpty);
			tableMetadata.addColumn(columnMetadata);
		}
		tableMetadata.sync();
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
					if(columnNames.size() > 1 && constraintType.supportColumnCount() == 1) {
						throw new ConstraintConfigurationException("不支持给多个列添加联合["+constraintType.name()+"]约束");
					}
					
					constraint = new Constraint(constraintType, tableMetadata.getName());
					switch(constraintType) {
						case PRIMARY_KEY:
							tableMetadata.validatePrimaryKeyColumnExists();
							for(Object columnName: columnNames) {
								columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnName).getValue().toUpperCase(), true);
								columnMetadata.set2PrimaryKeyConstraint(true);
								constraint.addColumn(columnMetadata);
							}
						case UNIQUE:
							for(Object columnName: columnNames) {
								columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnName).getValue().toUpperCase(), true);
								isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
								columnMetadata.set2UniqueConstraint();
								constraint.addColumn(columnMetadata);
							}
							break;
						case DEFAULT_VALUE:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnNames.get(0)).getValue().toUpperCase(), true);
							isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
							columnMetadata.set2DefaultValue(constraintElement.attributeValue("value"));
							if(columnMetadata.getDefaultValue() == null) {
								throw new NullPointerException("配置默认值约束, 默认值不能为空");
							}
							constraint.addColumn(columnMetadata);
							break;
						case CHECK:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnNames.get(0)).getValue().toUpperCase(), true);
							isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
							columnMetadata.set2CheckConstraint(constraintElement.attributeValue("expression"));
							if(columnMetadata.getCheck() == null) {
								throw new NullPointerException("配置检查约束, 检查约束的表达式不能为空");
							}
							constraint.addColumn(columnMetadata);
							break;
						case FOREIGN_KEY:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(((Attribute)columnNames.get(0)).getValue().toUpperCase(), true);
							isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
							columnMetadata.set2ForeginKeyConstraint(constraintElement.attributeValue("fkTableName"), constraintElement.attributeValue("fkColumnName"));
							if(columnMetadata.getFkTableName() == null) {
								throw new NullPointerException("配置外键约束, 关联的表名和列名均不能为空");
							}
							constraint.addColumn(columnMetadata);
							break;
					}
					tableMetadata.addConstraint(constraint);
				}
			}
		}
	}
	
	// 验证指定列是否已经存在主键约束
	private void isAlreadyExistsPrimaryKeyConstraint(ColumnMetadata column, ConstraintType ct) {
		if(column.isPrimaryKey()) {
			throw new ConstraintConfigurationException("列["+column.getName()+"]为主键列, 禁止配置["+ct.name()+"]约束");
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
				String indexName = null;
				Map<DialectType, String> createSqlStatements = null;
				Map<DialectType, String> dropSqlStatements = null;
				DialectType currentDialectType = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getType();
				
				for (Object object : elements) {
					indexElement = ((Element)object);
					if(StringUtil.isEmpty(indexName = indexElement.attributeValue("name"))) {
						throw new NullPointerException("索引名不能为空");
					}
					
					createSqlStatements = getIndexSqlStatementMap("create", indexName, currentDialectType, indexElement.elements("createSql"));
					dropSqlStatements = getIndexSqlStatementMap("drop", indexName, currentDialectType, indexElement.elements("dropSql"));
					if(!createSqlStatements.keySet().equals(dropSqlStatements.keySet())) {
						throw new IndexConfigurationException("索引[" + indexName + "]的create sql语句["+createSqlStatements.size()+"个]["+createSqlStatements.keySet()+"]和drop sql语句["+dropSqlStatements.size()+"个]["+dropSqlStatements.keySet()+"]不匹配");
					}
					
					tableMetadata.addIndex(new Index(tableMetadata.getName(), indexName, createSqlStatements, dropSqlStatements));
				}
			}
		}
	}
	
	// 获取索引sql语句map
	private Map<DialectType, String> getIndexSqlStatementMap(String description, String indexName, DialectType currentDialectType, List<?> sqlElements) {
		if(sqlElements == null || sqlElements.size() == 0) {
			throw new NullPointerException(description + "索引[" + indexName + "]的sql语句不能为空");
		}
		Map<DialectType, String> sqlStatements = new HashMap<DialectType, String>(sqlElements.size());
		for (Object object : sqlElements) {
			putIndexSqlStatement(description, indexName, ((Element)object).attributeValue("dialect"), ((Element)object).getTextTrim(), currentDialectType, sqlStatements);
		}
		return sqlStatements;
	}
	
	// 将对应的索引sql语句put到map集合中
	private void putIndexSqlStatement(String description, String indexName, String dialect, String sqlStatement, DialectType currentDialectType, Map<DialectType, String> sqlStatements) {
		if(StringUtil.isEmpty(sqlStatement)) {
			throw new NullPointerException(description + "索引[" + indexName + "]的sql语句不能为空");
		}
		if(StringUtil.isEmpty(dialect)) {
			sqlStatements.put(currentDialectType, sqlStatement);
		} else {
			DialectType dt = null;
			for(String _dialect : dialect.split(",")) {
				dt = DialectType.toValue(_dialect.toUpperCase());
				if(dt == null) {
					throw new NullPointerException("<indexes> -> <index> -> <"+description+"Sql>元素中的dialect属性值错误:["+_dialect+"], 目前支持的值包括: " + Arrays.toString(DialectType.values()));
				}
				if(dt == DialectType.ALL) {
					for(DialectType _dt: DialectType.values_()) {
						sqlStatements.put(_dt, sqlStatement);
					}
					break;
				}else {
					sqlStatements.put(dt, sqlStatement);
				}
			}
		}
	}
	
	/**
	 * 设置主键处理器
	 * @param primaryKeyHandlerElement
	 */
	private void setPrimaryKeyHandler(Element primaryKeyHandlerElement) {
		if(tableMetadata.existsPrimaryKey() && primaryKeyHandlerElement != null) {
			// 获取主键处理器
			PrimaryKeyHandler primaryKeyHandler = PrimaryKeyHandlerContext.getHandler(primaryKeyHandlerElement.attributeValue("type"));
			if(primaryKeyHandler != null) {
				if(!primaryKeyHandler.supportProcessMultiPKColumns() && tableMetadata.getPrimaryKeyCount() > 1) {
					throw new PrimaryKeyHandlerConfigurationException("["+primaryKeyHandler.getName() +"]主键处理器不支持处理多个主键, 表=["+tableMetadata.getName()+"], 主键=["+tableMetadata.getPrimaryKeyColumnCodes()+"]");
				}
				tableMetadata.setPrimaryKeyHandler(primaryKeyHandler);
				
				// 如果是自增类型, 且需要创建主键序列, 则去解析<sequence>元素
				if(primaryKeyHandler instanceof IncrementPrimaryKeyHandler && DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDBFeatures().needCreatePrimaryKeySequence()) {
					((IncrementPrimaryKeyHandler)primaryKeyHandler).setPrimaryKeySequence(getPrimaryKeySequence(primaryKeyHandlerElement.element("sequence")));
				}
			}
		}
	}
	
	/**
	 * 获取主键序列配置对象
	 * @param sequenceElement
	 * @return
	 */
	private PrimaryKeySequence getPrimaryKeySequence(Element sequenceElement) {
		if(sequenceElement != null) {
			// TODO 
		}
		return null;
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
