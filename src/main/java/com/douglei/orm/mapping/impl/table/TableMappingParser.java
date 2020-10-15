package com.douglei.orm.mapping.impl.table;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.impl.MappingImportDataContext;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.table.exception.ConstraintConfigurationException;
import com.douglei.orm.mapping.impl.table.exception.PrimaryKeyHandlerConfigurationException;
import com.douglei.orm.mapping.impl.table.exception.RepeatedPrimaryKeyException;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.Constraint;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintType;
import com.douglei.orm.mapping.impl.table.metadata.Index;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.impl.table.metadata.parser.ColumnMetadataParser;
import com.douglei.orm.mapping.impl.table.metadata.parser.TableMetadataParser;
import com.douglei.orm.mapping.impl.table.metadata.pk.PrimaryKeyHandler;
import com.douglei.orm.mapping.impl.table.metadata.pk.PrimaryKeyHandlerContext;
import com.douglei.orm.mapping.impl.table.metadata.pk.impl.SequencePrimaryKeyHandler;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.validator.ValidateHandler;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.util.Dom4jUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
class TableMappingParser extends MappingParser<TableMapping>{
	private static TableMetadataParser tableMetadataParser = new TableMetadataParser();
	private static ColumnMetadataParser columnMetadataParser = new ColumnMetadataParser();
	
	private TableMetadata tableMetadata;
	
	@Override
	public TableMapping parse(InputStream input) throws Exception {
		Document document = MappingParserContext.getSAXReader().read(input);
		Element rootElement = document.getRootElement();
		
		Element tableElement = Dom4jUtil.getElement(MappingTypeConstants.TABLE, rootElement);
		tableMetadata = tableMetadataParser.parse(tableElement);
		
		addColumnMetadata(getColumnElements(tableElement));
		addConstraint(tableElement.element("constraints"));
		addIndex(tableElement.element("indexes"));
		setPrimaryKeyHandler(tableElement.element("primaryKeyHandler"));
		setColumnValidator(tableElement.element("validators"));
		
		return new TableMapping(tableMetadata, parseFeature(tableMetadata.getCode(), MappingTypeConstants.TABLE, rootElement.element("feature")));
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
		Element columnsElement = tableElement.element("columns");
		List<Element> localColumnElements = columnsElement==null?Collections.emptyList():columnsElement.elements("column");
		
		// 解析<import-columns>
		Element importColumnsElement = tableElement.element("import-columns");
		if(importColumnsElement != null) {
			List<Element> importColumnElements = getImportColumnElements(importColumnsElement);
			if(importColumnElements != null) {
				columnElements = new ArrayList((localColumnElements.isEmpty()?0:localColumnElements.size()) + importColumnElements.size());
				addToColumnElements(localColumnElements, columnElements);
				addToColumnElements(importColumnElements, columnElements);
			}
		}
		
		if(columnElements == null) 
			columnElements = localColumnElements;
		if(columnElements.isEmpty()) 
			throw new NullPointerException("<columns>元素下至少配置一个<column>元素, 或通过<import-columns path=\"\">导入列");
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
		if(StringUtil.notEmpty(importColumnFilePath)) 
			return MappingImportDataContext.getImportColumnElements(importColumnFilePath);
		return null;
	}
	
	/**
	 * 将sourceColumnElements集合中的数据, 添加到columnElements
	 * @param sourceColumnElements
	 * @param columnElements
	 */
	private void addToColumnElements(List<Element> sourceColumnElements, List<Element> columnElements) {
		if(sourceColumnElements.isEmpty()) 
			return;
		for (Element sce : sourceColumnElements) 
			columnElements.add(sce);
	}

	/**
	 * 添加列元数据
	 * @param columnsElement
	 * @throws MetadataParseException 
	 */
	private void addColumnMetadata(List<Element> columnElements) throws MetadataParseException {
		ColumnMetadata columnMetadata = null;
		for (Element element : columnElements) {
			columnMetadata = columnMetadataParser.parse(element);
			if(columnMetadata.isPrimaryKey() && tableMetadata.getPrimaryKeyColumns_() != null) 
				throw new RepeatedPrimaryKeyException("主键配置重复, 通过<column>只能将单个列配置为主键, 如果需要配置联合主键, 请通过<constraint type='primary_key'>元素配置");
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
			List<Element> elements = constraintsElement.elements("constraint");
			if(elements.isEmpty())
				return;
			
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
				if(columnNames.size() > 1 && !constraintType.supportComposite()) {
					throw new ConstraintConfigurationException("不支持给多个列添加复合["+constraintType.name()+"]约束");
				}
				
				constraint = new Constraint(constraintType, tableMetadata.getName());
				switch(constraintType) {
					case PRIMARY_KEY:
						for(Attribute columnName: columnNames) {
							columnMetadata = tableMetadata.getColumnByName(columnName.getValue().toUpperCase(), true);
							columnMetadata.setPrimaryKeyConstraint(true);
							constraint.addColumn(columnMetadata);
						}
						break;
					case UNIQUE:
						for(Attribute columnName: columnNames) {
							columnMetadata = tableMetadata.getColumnByName(columnName.getValue().toUpperCase(), true);
							isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
							columnMetadata.setUniqueConstraint(true);
							constraint.addColumn(columnMetadata);
						}
						break;
					case DEFAULT_VALUE:
						columnMetadata = tableMetadata.getColumnByName(columnNames.get(0).getValue().toUpperCase(), true);
						isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
						columnMetadata.setDefaultValue(constraintElement.attributeValue("value"));
						if(columnMetadata.getDefaultValue() == null) {
							throw new NullPointerException("配置默认值约束, 默认值不能为空");
						}
						constraint.addColumn(columnMetadata);
						break;
					case CHECK:
						columnMetadata = tableMetadata.getColumnByName(columnNames.get(0).getValue().toUpperCase(), true);
						isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
						columnMetadata.setCheckConstraint(constraintElement.attributeValue("expression"));
						if(columnMetadata.getCheck() == null) {
							throw new NullPointerException("配置检查约束, 检查约束的表达式不能为空");
						}
						constraint.addColumn(columnMetadata);
						break;
					case FOREIGN_KEY:
						columnMetadata = tableMetadata.getColumnByName(columnNames.get(0).getValue().toUpperCase(), true);
						isAlreadyExistsPrimaryKeyConstraint(columnMetadata, constraintType);
						columnMetadata.setForeginKeyConstraint(constraintElement.attributeValue("fkTableName"), constraintElement.attributeValue("fkColumnName"));
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
	
	// 验证指定列是否已经存在主键约束
	private void isAlreadyExistsPrimaryKeyConstraint(ColumnMetadata column, ConstraintType ct) {
		if(column.isPrimaryKey()) 
			throw new ConstraintConfigurationException("列["+column.getName()+"]为主键列, 禁止配置["+ct.name()+"]约束");
	}
	
	/**
	 * 添加索引
	 * @param indexesElement
	 */
	@SuppressWarnings("unchecked")
	private void addIndex(Element indexesElement) {
		if(indexesElement != null) {
			List<Element> indexElements = indexesElement.elements("index");
			if(indexElements.isEmpty())
				return;
			
			String indexName, createSqlStatement, dropSqlStatement;
			String currentDialect = EnvironmentContext.getDialect().getType().getName();
			
			for (Element indexElement : indexElements) {
				if(StringUtil.isEmpty(indexName = indexElement.attributeValue("name")))
					throw new NullPointerException("索引名不能为空");
				
				createSqlStatement = getIndexSqlStatement("create", indexElement, currentDialect, indexName);
				dropSqlStatement = getIndexSqlStatement("drop", indexElement, currentDialect, indexName);
				tableMetadata.addIndex(new Index(tableMetadata.getName(), indexName, createSqlStatement, dropSqlStatement));
			}
		}
	}
	
	// 获取索引指定key的sql语句
	@SuppressWarnings("unchecked")
	private String getIndexSqlStatement(String key, Element indexElement, String currentDialect, String indexName) {
		List<Element> sqlElements = indexElement.elements(key + "Sql");
		if(!sqlElements.isEmpty()) {
			String tmp;
			for (Element sqlElement : sqlElements) {
				tmp = sqlElement.attributeValue("dialect");
				if((StringUtil.isEmpty(tmp) || tmp.toUpperCase().indexOf(currentDialect) != -1) && StringUtil.notEmpty(tmp = sqlElement.getTextTrim()))
					return tmp;
			}
		}
		throw new NullPointerException("索引" + indexName + "的 "+key+"Sql语句不能为空");
	}
	
	/**
	 * 设置主键处理器
	 * @param primaryKeyHandlerElement
	 */
	private void setPrimaryKeyHandler(Element primaryKeyHandlerElement) {
		if(tableMetadata.getPrimaryKeyColumns_() != null && primaryKeyHandlerElement != null) {
			// 获取主键处理器
			PrimaryKeyHandler primaryKeyHandler = PrimaryKeyHandlerContext.getHandler(primaryKeyHandlerElement.attributeValue("type"));
			if(primaryKeyHandler != null) {
				if(!primaryKeyHandler.supportMultiColumns() && tableMetadata.getPrimaryKeyColumns_().size() > 1) 
					throw new PrimaryKeyHandlerConfigurationException("["+primaryKeyHandler.getClass().getName() +"]主键处理器不支持处理多个主键, 表=["+tableMetadata.getName()+"], 主键=["+tableMetadata.getPrimaryKeyColumns_().keySet()+"]");
				tableMetadata.setPrimaryKeyHandler(primaryKeyHandler);
				
				// 如果是序列类型, 则去解析<sequence>元素
				if(primaryKeyHandler instanceof SequencePrimaryKeyHandler) 
					setPrimaryKeySequence(primaryKeyHandlerElement.element("sequence"));
			}
		}
	}
	
	/**
	 * 设置主键序列配置对象
	 * @param sequenceElement
	 */
	private void setPrimaryKeySequence(Element sequenceElement) {
		// 因为主键序列只支持单列主键, 所以这里获取唯一的主键列, 并将其标识为主键序列
		ColumnMetadata primaryKeyColumn = tableMetadata.getPrimaryKeyColumns_().get(tableMetadata.getPrimaryKeyColumns_().keySet().iterator().next());
		primaryKeyColumn.setPrimaryKeySequence();
		
		// 创建主键序列对象(根据配置创建对象或创建默认的对象)
		String sequenceName = null;
		String createSqlStatement = null;
		String dropSqlStatement = null;
		if(sequenceElement != null) {
			sequenceName = sequenceElement.attributeValue("name");
			
			Element elem = sequenceElement.element("createSql");
			createSqlStatement = elem==null?null:elem.getTextTrim();
			
			elem = sequenceElement.element("dropSql");
			dropSqlStatement = elem==null?null:elem.getTextTrim();
		}
		PrimaryKeySequence primaryKeySequence = 
				EnvironmentContext.getDialect().getObjectHandler().createPrimaryKeySequence(
						sequenceName, createSqlStatement, dropSqlStatement, tableMetadata.getName(), primaryKeyColumn);
		tableMetadata.setPrimaryKeySequence(primaryKeySequence);
	}
	
	/**
	 * 设置配置的列验证器
	 * @param validatorsElement
	 */
	private void setColumnValidator(Element validatorsElement) {
		Map<String, ValidateHandler> validateHandlerMap = getValidateHandlerMap(validatorsElement);
		boolean existsPrimaryKeyHandler = tableMetadata.existsPrimaryKeyHandler();
		tableMetadata.getDeclareColumns().forEach(column -> {
			tableMetadata.setValidateColumn(column.setValidateHandler(existsPrimaryKeyHandler, getValidateHandler(column.getCode(), validateHandlerMap)));
		});
	}
	
	/**
	 * 获取配置的ValidateHandler集合
	 * @param validatorsElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, ValidateHandler> getValidateHandlerMap(Element validatorsElement) {
		if(validatorsElement != null) {
			List<Element> validatorElements = validatorsElement.selectNodes("validator[@name!='']");
			if(validatorElements != null && !validatorElements.isEmpty()) {
				Map<String, ValidateHandler> validatorMap = new HashMap<String, ValidateHandler>();
				
				ValidateHandler handler = null;
				for (Element ve : validatorElements) {
					handler = getValidateHandler(ve);
					validatorMap.put(handler.getCode(), handler);
				}
				return validatorMap;
			}
		}
		return Collections.emptyMap();
	}
	
	/**
	 * 获取validateHandler实例
	 * @param validatorElement
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ValidateHandler getValidateHandler(Element validatorElement) {
		String code = validatorElement.attributeValue("code");
		if(StringUtil.notEmpty(code)) {
			if(tableMetadata.getColumns_().get(code) == null)
				throw new NullPointerException("配置验证器时, 不存在code="+code+"的列");
			
			ValidateHandler handler = new ValidateHandler(code, true);
			List<Attribute> attributes = validatorElement.attributes();
			if(attributes.size() > 1) {
				attributes.forEach(attribute -> {
					if(!"code".equals(attribute.getName())) 
						handler.addValidator(attribute.getName(), attribute.getValue());
				});
			}
			return handler;
		}
		throw new NullPointerException("<validator>元素中的code属性值不能为空");
	}
	
	/**
	 * 获取指定code的ValidateHandler
	 * @param columnCode
	 * @param validateHandlerMap
	 * @return
	 */
	private ValidateHandler getValidateHandler(String columnCode, Map<String, ValidateHandler> validateHandlerMap) {
		if(validateHandlerMap.isEmpty()) {
			return new ValidateHandler(columnCode);
		}
		ValidateHandler handler = validateHandlerMap.get(columnCode);
		if(handler == null) {
			handler = new ValidateHandler(columnCode);
		}
		return handler;
	}
}
