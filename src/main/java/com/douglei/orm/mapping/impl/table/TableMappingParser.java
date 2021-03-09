package com.douglei.orm.mapping.impl.table;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.DatabaseNameConstants;
import com.douglei.orm.dialect.datatype.DataTypeClassification;
import com.douglei.orm.dialect.datatype.db.DBDataTypeEntity;
import com.douglei.orm.dialect.datatype.db.DBDataTypeUtil;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.impl.table.metadata.AutoincrementPrimaryKey;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintType;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.AbstractMetadataParser;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidatorUtil;
import com.douglei.orm.sql.statement.util.NameConvertUtil;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
class TableMappingParser extends MappingParser{
	private static TableMetadataParser tableMetadataParser = new TableMetadataParser();
	private static ColumnMetadataParser columnMetadataParser = new ColumnMetadataParser();
	private static ConstraintMetadataParser constraintMetadataParser = new ConstraintMetadataParser();

	private TableMetadata tableMetadata;
	
	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		Element rootElement = MappingParseToolContext.getMappingParseTool().getSAXReader().read(input).getRootElement();
		
		// 解析TableMetadata
		Element tableElement = Dom4jUtil.getElement(MappingTypeNameConstants.TABLE, rootElement);
		tableMetadata = tableMetadataParser.parse(tableElement);
		
		addColumns(tableElement.element("columns"));
		addConstraints(tableElement);
		addValidators(tableElement.element("validators"));
		
		return buildMappingSubjectByDom4j(new TableMapping(tableMetadata), rootElement);
	}
	
	/**
	 * 添加列
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	private void addColumns(Element element) {
		if(element == null)
			throw new MetadataParseException("<table>下必须配置<columns>");
		
		List<Element> columnElements = element.elements("column");
		if(columnElements.isEmpty()) 
			throw new MetadataParseException("<table><columns>下至少配置一个<column>");
		
		List<ColumnMetadata> columns = new ArrayList<ColumnMetadata>(columnElements.size());
		for (Element columnElement : columnElements) {
			ColumnMetadata column = columnMetadataParser.parse(tableMetadata, columnElement);
			if(columns.indexOf(column) >=0) 
				throw new MetadataParseException(tableMetadata.getName()+"表中存在重复的列名"+column.getName());
			columns.add(column);
		}
		tableMetadata.setColumns(columns);
	}
	
	/**
	 * 添加约束
	 * @param tableElement
	 */
	@SuppressWarnings("unchecked")
	private void addConstraints(Element tableElement) {
		Element element = tableElement.element("constraints");
		if(element == null)
			return;
		
		List<Element> constraintElements = element.elements("constraint");
		if(constraintElements.isEmpty())
			return;
		
		boolean existsPK = false;
		List<ConstraintMetadata> constraints = new ArrayList<ConstraintMetadata>(constraintElements.size());
		for (Element constraintElement : constraintElements) {
			ConstraintMetadata constraint = constraintMetadataParser.parse(tableMetadata, constraintElement);
			if(constraints.indexOf(constraint) >=0) 
				throw new MetadataParseException(tableMetadata.getName()+"表中存在重复的约束名"+constraint.getName());
			
			if(constraint.getType().isPrimaryKey()) {
				if(existsPK)
					throw new MetadataParseException(tableMetadata.getName()+"表中配置了多个主键约束");
				existsPK = true;
				
				// 记录自增主键关联的列名
				if(constraint.getType() == ConstraintType.AUTO_INCREMENT_PRIMARY_KEY) {
					ColumnMetadata column = tableMetadata.getColumnMap4Name().get(constraint.getColumnNameList().get(0));
					tableMetadata.setAutoincrementPrimaryKey(new AutoincrementPrimaryKey(column.getName(), column.getCode(), constraint.getSequence()));
				}
			}
			constraints.add(constraint);
		}
		
		// 针对处理主键约束和默认值约束: 两种约束互斥, 且会影响column的nullable值; 其他三种约束不做处理
		boolean pk = false, df = false; // 当前列是否存在pk约束, 默认值约束
		for(ColumnMetadata column: tableMetadata.getColumns()) {
			for(ConstraintMetadata constraint: constraints) {
				if(constraint.getType() == ConstraintType.PRIMARY_KEY) {
					if(constraint.getColumnNameList().contains(column.getName())) {
						if(df)
							throw new MetadataParseException(tableMetadata.getName()+"表中的"+column.getName()+"列, 同时存在主键约束和默认值约束");
						pk = true;
						column.setNullable(false);
					}
				}else if(constraint.getType() == ConstraintType.DEFAULT_VALUE) {
					if(constraint.getColumnNameList().contains(column.getName())) {
						if(pk)
							throw new MetadataParseException(tableMetadata.getName()+"表中的"+column.getName()+"列, 同时存在主键约束和默认值约束");
						df = true;
						column.setNullable(true);
					}
				}
			}
			pk = df = false;
		}
		
		// 记录约束
		tableMetadata.setConstraints(constraints);
	}
	
	/**
	/**
	 * 添加验证器
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	private void addValidators(Element element) {
		if(element == null)
			return;
		
		List<Element> validatorElements = element.selectNodes("validator[@name!='']");
		if(validatorElements.isEmpty())
			return;
		
		List<Validator> validators = null;
		for (Element validatorElement : validatorElements) {
			ColumnMetadata columnMetadata = tableMetadata.getColumnMap4Name().get(validatorElement.attributeValue("name"));
			if(columnMetadata == null)
				throw new MetadataParseException(tableMetadata.getName()+"表中不存在名为"+validatorElement.attributeValue("name")+"的列, 无法为其添加验证器");
			
			// 获取验证器配置, 如果只有一个name配置, 则直接忽略
			List<Attribute> attributes = ((List<Attribute>)validatorElement.attributes());
			if(attributes.size() == 1)
				continue;
			
			// 解析ValidatorMetadata集合
			for (Attribute attribute : attributes) {
				if("name".equals(attribute.getName()))
					continue;
				
				Validator validator = ValidatorUtil.get(attribute.getName()).parse(attribute.getValue());
				if(validator == null)
					continue;
				
				if(validators == null)
					validators = new ArrayList<Validator>(attributes.size()-1);
				validators.add(validator);
			}
			
			if(validators == null) 
				continue;
			
			ValidatorUtil.sort(validators);
			columnMetadata.setValidators(validators);
			validators = null;
		}
	}
}

/**
 * TableMetadata解析器
 * @author DougLei
 */
class TableMetadataParser extends AbstractMetadataParser{

	/**
	 * 解析TableMetadata
	 * @param element
	 * @return
	 * @throws MetadataParseException
	 */
	public TableMetadata parse(Element element) throws MetadataParseException{
		// 解析name和oldName
		String name = getName(element);
		String oldName = getOldName(name, element);
		
		CreateMode createMode = getCreateMode(element);
		return new TableMetadata(name, oldName, element.attributeValue("class"), createMode);
	}
} 

/**
 * ColumnMetadata解析器
 * @author DougLei
 */
class ColumnMetadataParser extends AbstractMetadataParser{
	
	/**
	 * 解析ColumnMetadata
	 * @param tableMetadata
	 * @param element
	 * @return
	 * @throws MetadataParseException
	 */
	public ColumnMetadata parse(TableMetadata tableMetadata, Element element) throws MetadataParseException{
		// 解析name和oldName
		String name = getName(element);
		String oldName = getOldName(name, element);
		
		// 解析property
		String property = null;
		if(tableMetadata.getClassName() != null) {
			property = element.attributeValue("property");
			if(property == null)
				property = NameConvertUtil.column2Property(name);
		}
		
		// 解析DBDataTypeEntity
		String typeName = element.attributeValue("dbType");
		DataTypeClassification classification = DataTypeClassification.DB;
		
		if(StringUtil.isEmpty(typeName)) {
			typeName = element.attributeValue("dataType");
			if(StringUtil.isEmpty(typeName))
				typeName = "string";
			classification = DataTypeClassification.MAPPING;
		}
		DBDataTypeEntity dataTypeEntity = DBDataTypeUtil.get(classification, typeName, element.attributeValue("length"), element.attributeValue("precision"));
		
		// 创建ColumnMetadata实例
		return new ColumnMetadata(name, oldName, property, dataTypeEntity.getDBDataType(), dataTypeEntity.getLength(), dataTypeEntity.getPrecision(),
				!"false".equalsIgnoreCase(element.attributeValue("nullable")));
	}
}

/**
 * ConstraintMetadata解析器
 * @author DougLei
 */
class ConstraintMetadataParser {
	
	/**
	 * 解析ConstraintMetadata
	 * @param tableMetadata
	 * @param element
	 * @return
	 * @throws MetadataParseException
	 */
	@SuppressWarnings("unchecked")
	public ConstraintMetadata parse(TableMetadata tableMetadata, Element element) throws MetadataParseException{
		List<Attribute> attributes = element.selectNodes("column/@name");
		if(attributes.isEmpty()) 
			throw new MetadataParseException("<table><constraints><constraint>下至少配置一个<column>");
		
		ConstraintType type = ConstraintType.valueOf(element.attributeValue("type").toUpperCase());
		if(type == ConstraintType.PRIMARY_KEY && "true".equalsIgnoreCase(element.attributeValue("isAutoIncrement")))
			type = ConstraintType.AUTO_INCREMENT_PRIMARY_KEY;
		if((attributes.size() > 1 && !type.supportMultiColumn()) ) 
			throw new MetadataParseException(type.getSqlKey()+"约束不支持绑定多个列");
		
		// 记录当前约束要绑定的列名集合
		List<String> columnNames = new ArrayList<String>(attributes.size());
		for (Attribute attribute : attributes) {
			String columnName = attribute.getValue().toUpperCase();
			if(!tableMetadata.getColumnMap4Name().containsKey(columnName))
				throw new MetadataParseException(tableMetadata.getName()+"表中不存在名为"+columnName+"的列, 无法为其设置"+type.name()+"约束");
			if(columnNames.indexOf(columnName) >= 0) 
				throw new MetadataParseException(tableMetadata.getName()+"表中的"+type.name()+"约束, 存在重复的列名" + columnName);
			columnNames.add(columnName);
		}
		
		// 解析约束名, 未配置时自动生成一个
		int maxLength = EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getNameMaxLength();
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			StringBuilder sb = new StringBuilder(columnNames.size()*30);
			sb.append(type.getNamePrefix()).append('_').append(tableMetadata.getName()).append('_');
			for (int i=0;i<columnNames.size();i++) {
				sb.append(columnNames.get(i));
				if(i < columnNames.size()-1)
					sb.append('_');
			}
			
			// 自动生成的约束名长度大于数据库限制的长度, 进行压缩
			if(sb.length() > maxLength) {
				sb.setLength(3);
				appendCompressedName(sb, tableMetadata.getName());
				for (int i=0;i<columnNames.size();i++) 
					appendCompressedName(sb, columnNames.get(i));
			}
			name = sb.toString();
		}
		if(name.length() > maxLength)
			throw new MetadataParseException("数据库约束名["+name+"]长度超长, 长度应小于等于"+maxLength);
		
		
		// 创建ConstraintMetadata实例, 并根据类型设置相应的值
		ConstraintMetadata constraint = new ConstraintMetadata(name.toUpperCase(), type, columnNames);
		switch(type) {
			case AUTO_INCREMENT_PRIMARY_KEY:
				// 如果是Oracle数据库, 解析序列名, 未配置时自动生成一个
				if(EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) {
					String sequence = element.attributeValue("sequence");
					
					if(StringUtil.isEmpty(sequence)) {
						sequence = "SEQ_" + tableMetadata.getName();
						if(sequence.length() > maxLength) {
							StringBuilder sb = new StringBuilder(sequence.length());
							sb.append("SEQ_");
							appendCompressedName(sb, tableMetadata.getName());
							sequence = sb.toString();
						}
					}
					if(sequence.length() > maxLength)
						throw new MetadataParseException("Oracle数据库序列名["+sequence+"]长度超长, 长度应小于等于"+maxLength);
					constraint.setSequence(sequence);
				}
				break;
			case PRIMARY_KEY:
			case UNIQUE:
				break;
			case DEFAULT_VALUE:
				constraint.setDefaultValue(element.attributeValue("defaultValue"));
				break;
			case CHECK:
				constraint.setCheck(element.attributeValue("check"));
				break;
			case FOREIGN_KEY:
				constraint.setTableAndColumn(element.attributeValue("table"), element.attributeValue("column"));
				break;
		}
		return constraint;
	}
	
	/**
	 * append压缩后的name
	 * @param sb
	 * @param name
	 */
	private void appendCompressedName(StringBuilder sb, String name) {
		for(String chunk : name.split("_")) {
			if(chunk.length() == 0)
				continue;
			
			sb.append(chunk.charAt(0));
			
			if(chunk.length() > 2)
				sb.append(chunk.charAt(chunk.length()-1));
		}
		sb.append(name.length());
	}
}