package com.douglei.orm.configuration.impl.xml.element.environment.mapping.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.ImportDataContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;
import com.douglei.orm.core.metadata.table.ConstraintType;
import com.douglei.orm.core.metadata.table.Index;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandler;
import com.douglei.orm.core.metadata.table.pk.PrimaryKeyHandlerContext;
import com.douglei.orm.core.metadata.table.pk.impl.SequencePrimaryKeyHandler;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * table 映射
 * @author DougLei
 */
public class XmlTableMapping extends XmlMapping implements TableMapping{
	private static final Logger logger = LoggerFactory.getLogger(XmlTableMapping.class);
	private static final XmlTableMetadataValidate tableMetadataValidate = new XmlTableMetadataValidate();
	private static final XmlColumnMetadataValidate columnMetadataValidate = new XmlColumnMetadataValidate();
	
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
			setColumnValidator(tableElement.element("validators"));
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
	private List<Element> getColumnElements(Element tableElement) throws DocumentException {
		List<Element> columnElements = null;
		
		// 解析<columns>
		List<Element> localColumnElements = Dom4jElementUtil.elements("column", tableElement.element("columns"));
		
		// 解析<import-columns>
		Element importColumnsElement = tableElement.element("import-columns");
		if(importColumnsElement != null) {
			List<Element> importColumnElements = getImportColumnElements(importColumnsElement);
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
	private List<Element> getImportColumnElements(Element importColumnElement) throws DocumentException {
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
	private void addToColumnElements(List<Element> sourceColumnElements, List<Element> columnElements) {
		if(sourceColumnElements != null && sourceColumnElements.size() > 0) {
			for (Element sce : sourceColumnElements) {
				columnElements.add(sce);
			}
		}
	}

	/**
	 * 添加列元数据
	 * @param columnsElement
	 * @throws MetadataValidateException 
	 */
	private void addColumnMetadata(List<Element> columnElements) throws MetadataValidateException {
		boolean classNameEmpty = tableMetadata.classNameEmpty();
		ColumnMetadata columnMetadata = null;
		for (Element element : columnElements) {
			columnMetadata = columnMetadataValidate.doValidate(element);
			if(columnMetadata.isPrimaryKey() && tableMetadata.existsPrimaryKey()) {
				throw new RepeatedPrimaryKeyException("主键配置重复, 通过<column>只能将单个列配置为主键, 如果需要配置联合主键, 请通过<constraint type='primary_key'>元素配置");
			}
			columnMetadata.correctPropertyValue(classNameEmpty);
			tableMetadata.addColumn(columnMetadata);
		}
		tableMetadata.sync();
	}
	
	/**
	 * 添加约束
	 * @param constraintsElement
	 */
	@SuppressWarnings("unchecked")
	private void addConstraint(Element constraintsElement) {
		if(constraintsElement != null) {
			List<Element> elements = Dom4jElementUtil.elements("constraint", constraintsElement);
			if(elements != null) {
				ConstraintType constraintType = null;
				List<Attribute> columnNames = null;
				Constraint constraint = null;
				ColumnMetadata columnMetadata = null;
				
				for (Element constraintElement : elements) {
					columnNames = constraintElement.selectNodes("column/@name");
					if(columnNames == null || columnNames.size() == 0) {
						throw new NullPointerException(constraintElement.asXML() + " 中没有配置任何<column name=\"xxx\">元素");
					}
					
					constraintType = ConstraintType.toValue(constraintElement.attributeValue("type"));
					if(constraintType == null) {
						throw new NullPointerException("<constraint>元素中的type属性值错误:["+constraintElement.attributeValue("type")+"], 目前支持的值包括: " + Arrays.toString(ConstraintType.values()));
					}
					if(columnNames.size() > 1 && constraintType.supportColumnCount() == 1) {
						throw new ConstraintConfigurationException("不支持给多个列添加联合["+constraintType.name()+"]约束");
					}
					
					constraint = new Constraint(constraintType, tableMetadata.getName());
					switch(constraintType) {
						case PRIMARY_KEY:
							tableMetadata.validatePrimaryKeyColumnExists();
							for(Attribute columnName: columnNames) {
								columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(columnName.getValue().toUpperCase(), true);
								columnMetadata.set2PrimaryKeyConstraint(true);
								constraint.addColumn(columnMetadata);
							}
						case UNIQUE:
							for(Attribute columnName: columnNames) {
								columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(columnName.getValue().toUpperCase(), true);
								isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
								columnMetadata.set2UniqueConstraint();
								constraint.addColumn(columnMetadata);
							}
							break;
						case DEFAULT_VALUE:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(columnNames.get(0).getValue().toUpperCase(), true);
							isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
							columnMetadata.set2DefaultValue(constraintElement.attributeValue("value"));
							if(columnMetadata.getDefaultValue() == null) {
								throw new NullPointerException("配置默认值约束, 默认值不能为空");
							}
							constraint.addColumn(columnMetadata);
							break;
						case CHECK:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(columnNames.get(0).getValue().toUpperCase(), true);
							isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
							columnMetadata.set2CheckConstraint(constraintElement.attributeValue("expression"));
							if(columnMetadata.getCheck() == null) {
								throw new NullPointerException("配置检查约束, 检查约束的表达式不能为空");
							}
							constraint.addColumn(columnMetadata);
							break;
						case FOREIGN_KEY:
							columnMetadata = (ColumnMetadata) tableMetadata.getColumnByName(columnNames.get(0).getValue().toUpperCase(), true);
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
			List<Element> elements = Dom4jElementUtil.elements("index", indexesElement);
			if(elements != null) {
				String indexName = null;
				Map<DialectType, String> createSqlStatements = null;
				Map<DialectType, String> dropSqlStatements = null;
				DialectType currentDialectType = EnvironmentContext.getEnvironmentProperty().getDialect().getType();
				
				for (Element indexElement : elements) {
					if(StringUtil.isEmpty(indexName = indexElement.attributeValue("name"))) {
						throw new NullPointerException("索引名不能为空");
					}
					
					createSqlStatements = getIndexSqlStatementMap("create", indexName, currentDialectType, Dom4jElementUtil.elements("createSql", indexElement));
					dropSqlStatements = getIndexSqlStatementMap("drop", indexName, currentDialectType, Dom4jElementUtil.elements("dropSql", indexElement));
					if(!createSqlStatements.keySet().equals(dropSqlStatements.keySet())) {
						throw new IndexConfigurationException("索引[" + indexName + "]的create sql语句["+createSqlStatements.size()+"个]["+createSqlStatements.keySet()+"]和drop sql语句["+dropSqlStatements.size()+"个]["+dropSqlStatements.keySet()+"]不匹配");
					}
					
					tableMetadata.addIndex(new Index(tableMetadata.getName(), indexName, createSqlStatements, dropSqlStatements));
				}
			}
		}
	}
	
	// 获取索引sql语句map
	private Map<DialectType, String> getIndexSqlStatementMap(String description, String indexName, DialectType currentDialectType, List<Element> sqlElements) {
		if(sqlElements == null) {
			throw new NullPointerException(description + "索引[" + indexName + "]的sql语句不能为空");
		}
		Map<DialectType, String> sqlStatements = new HashMap<DialectType, String>(sqlElements.size());
		for (Element se : sqlElements) {
			putIndexSqlStatement(description, indexName, se.attributeValue("dialect"), se.getTextTrim(), currentDialectType, sqlStatements);
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
				tableMetadata.setPrimaryKeyHandler(primaryKeyHandler, Boolean.parseBoolean(primaryKeyHandlerElement.attributeValue("coverValue")));
				
				// 如果是序列类型, 则去解析<sequence>元素
				if(primaryKeyHandler instanceof SequencePrimaryKeyHandler) {
					setPrimaryKeySequence(primaryKeyHandlerElement.element("sequence"));
				}
			}
		}
	}
	
	/**
	 * 设置主键序列配置对象
	 * @param sequenceElement
	 */
	private void setPrimaryKeySequence(Element sequenceElement) {
		// 因为主键序列只支持单列主键, 所以这里获取唯一的主键列, 并将其标识为主键序列
		ColumnMetadata primaryKeyColumn = tableMetadata.getPrimaryKeyColumnByCode(tableMetadata.getPrimaryKeyColumnCodes().iterator().next());
		primaryKeyColumn.set2PrimaryKeySequence();
		
		// 创建主键序列对象(根据配置创建对象或创建默认的对象)
		String primaryKeySequenceName = null;
		String createSqlStatement = null;
		String dropSqlStatement = null;
		if(sequenceElement != null) {
			primaryKeySequenceName = sequenceElement.attributeValue("name");
			
			Element elem = sequenceElement.element("createSql");
			createSqlStatement = elem==null?null:elem.getTextTrim();
			
			elem = sequenceElement.element("dropSql");
			dropSqlStatement = elem==null?null:elem.getTextTrim();
		}
		PrimaryKeySequence primaryKeySequence = 
				EnvironmentContext.getDialect().getDBObjectHandler().createPrimaryKeySequence(
						primaryKeySequenceName, createSqlStatement, dropSqlStatement, tableMetadata.getName(), primaryKeyColumn);
		tableMetadata.setPrimaryKeySequence(primaryKeySequence);
	}
	
	/**
	 * 设置配置的列验证器
	 * @param validatorsElement
	 */
	private void setColumnValidator(Element validatorsElement) {
		Map<String, ValidatorHandler> validatorHandlerMap = getValidatorHandlerMap(validatorsElement);
		tableMetadata.getDeclareColumns().forEach(column -> {
			tableMetadata.setValidateColumn(column.setValidatorHandler(getValidatorHandler(column.getName(), validatorHandlerMap)));
		});
	}
	
	/**
	 * 获取配置的ValidatorHandler集合
	 * @param validatorsElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, ValidatorHandler> getValidatorHandlerMap(Element validatorsElement) {
		if(validatorsElement != null) {
			List<Element> validatorElements = validatorsElement.selectNodes("validator[@name!='']");
			if(com.douglei.tools.utils.Collections.unEmpty(validatorElements)) {
				Map<String, ValidatorHandler> validatorMap = new HashMap<String, ValidatorHandler>(validatorElements.size());
				
				ValidatorHandler handler = null;
				for (Element ve : validatorElements) {
					handler = getValidatorHandler(ve);
					validatorMap.put(processValidationColumnName(handler.getName()), handler);
				}
				return validatorMap;
			}
		}
		return Collections.emptyMap();
	}
	
	/**
	 * 获取validatorHandler实例
	 * @param validatorElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ValidatorHandler getValidatorHandler(Element validatorElement) {
		String name = validatorElement.attributeValue("name");
		if(StringUtil.notEmpty(name)) {
			ValidatorHandler handler = new ValidatorHandler(name, true);
			List<Attribute> attributes = validatorElement.attributes();
			if(attributes.size() > 1) {
				attributes.forEach(attribute -> {
					if(!"name".equals(attribute.getName())) {
						handler.addValidator(attribute.getName(), attribute.getValue());
					}
				});
			}
			return handler;
		}
		throw new NullPointerException("<validator>元素中的name属性值不能为空");
	}
	
	// 处理要验证的列名
	private String processValidationColumnName(String columnName) {
		tableMetadata.validateColumnExistsByName(columnName);
		return columnName.toUpperCase();
	}
	
	/**
	 * 获取指定name的ValidatorHandler
	 * @param name
	 * @param validatorHandlerMap
	 * @return
	 */
	private ValidatorHandler getValidatorHandler(String name, Map<String, ValidatorHandler> validatorHandlerMap) {
		if(validatorHandlerMap.isEmpty()) {
			return new ValidatorHandler(name);
		}
		ValidatorHandler handler = validatorHandlerMap.get(name);
		if(handler == null) {
			handler = new ValidatorHandler(name);
		}
		return handler;
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
