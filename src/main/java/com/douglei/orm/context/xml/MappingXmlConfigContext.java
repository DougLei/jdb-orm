package com.douglei.orm.context.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.XmlSqlContentMetadataValidate;
import com.douglei.orm.core.dialect.db.table.TableHandler;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlContentType;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.tools.utils.Collections;

/**
 * 
 * @author DougLei
 */
public class MappingXmlConfigContext {
	private static final ThreadLocal<MappingConfig> mappingConfig = new ThreadLocal<MappingConfig>();
	private static MappingConfig getMappingConfig() {
		MappingConfig mc = mappingConfig.get();
		if(mc == null) {
			mc = new MappingConfig();
			mappingConfig.set(mc);
		}
		return mc;
	}

	
	// -----------------------------------------------------------------------------------------
	// 获取表映射配置对象
	private static TableMappingConfig getTableMappingConfig() {
		return getMappingConfig().getTableMappingConfig();
	}
	
	// 是否注册表映射
	private static boolean isRegisterTableMapping(Mapping mapping) {
		return mapping.getMappingType() == MappingType.TABLE && ((TableMetadata)mapping.getMetadata()).getCreateMode() != CreateMode.NONE;
	}
	
	/**
	 * 添加要create的TableMapping
	 * @param mapping
	 */
	public static void addCreateTableMapping(Mapping mapping) {
		if(isRegisterTableMapping(mapping)) {
			getTableMappingConfig().addCreateTableMapping(mapping);
		}
	}
	
	/**
	 * 添加要drop的TableMapping
	 * @param mapping
	 */
	public static void addDropTableMapping(Mapping mapping) {
		if(isRegisterTableMapping(mapping)) {
			getTableMappingConfig().addDropTableMapping(mapping);
		}
	}
	
	/**
	 * 执行create table
	 * @param dataSourceWrapper
	 */
	public static void executeCreateTable(DataSourceWrapper dataSourceWrapper) {
		if(getMappingConfig().existsTableMappingConfig() && getTableMappingConfig().existsCreateTable()) {
			TableHandler.singleInstance().create(dataSourceWrapper, getTableMappingConfig().getCreateTables());
		}
	}
	
	/**
	 * 执行drop table
	 * @param dataSourceWrapper
	 */
	public static void executeDropTable(DataSourceWrapper dataSourceWrapper) {
		if(getMappingConfig().existsTableMappingConfig() && getTableMappingConfig().existsDropTable()) {
			TableHandler.singleInstance().drop(dataSourceWrapper, getTableMappingConfig().getDropTables());
		}
	}
	
	// -----------------------------------------------------------------------------------------
	// 获取sql映射配置对象
	private static SqlMappingConfig getSqlMappingConfig() {
		return getMappingConfig().getSqlMappingConfig();
	}
	
	/**
	 * 记录当前解析的sql content的type
	 * @param sqlContentType
	 */
	public static void setSqlContentType(SqlContentType sqlContentType) {
		getSqlMappingConfig().setSqlContentType(sqlContentType);
	}
	
	/**
	 * 获取当前解析的sql content的type
	 * @return
	 */
	public static SqlContentType getSqlContentType() {
		return getSqlMappingConfig().getSqlContentType();
	}
	
	/**
	 * 记录当前解析的sql验证器集合
	 * @param sqlValidatorHandlerMap
	 */
	public static void setSqlValidatorMap(Map<String, ValidatorHandler> sqlValidatorHandlerMap) {
		getSqlMappingConfig().setSqlValidatorHandlerMap(sqlValidatorHandlerMap);
	}
	
	/**
	 * 获取当前解析的sql验证器集合
	 * @return
	 */
	public static Map<String, ValidatorHandler> getSqlValidatorHandlerMap() {
		return getSqlMappingConfig().getSqlValidatorHandlerMap();
	}
	
	/**
	 * 初始化当前解析的sql的sql-content容器
	 * @param sqlNode
	 */
	public static void initialSqlContentContainer(Node sqlNode) {
		getSqlMappingConfig().initialSqlContentContainer(sqlNode);
	}
	
	/**
	 * 是否存在指定name的sql-content
	 * @param sqlContentName
	 * @return
	 */
	public static boolean existsSqlContent(String sqlContentName) {
		return getSqlMappingConfig().existsSqlContent(sqlContentName);
	}
	
	/**
	 * 根据name, 获取sql-content实例
	 * @param sqlContentName
	 */
	public static SqlContentMetadata getSqlContent(String sqlContentName) {
		return getSqlMappingConfig().getSqlContent(sqlContentName);
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 销毁
	 */
	public static void destroy() {
		getMappingConfig().destroy();
		mappingConfig.remove();
	}
}

/**
 * 
 * @author DougLei
 */
class MappingConfig {
	private TableMappingConfig tmc;
	private SqlMappingConfig smc;
	
	public TableMappingConfig getTableMappingConfig() {
		if(tmc == null) {
			tmc = new TableMappingConfig();
		}
		return tmc;
	}
	public SqlMappingConfig getSqlMappingConfig() {
		if(smc == null) {
			smc = new SqlMappingConfig();
		}
		return smc;
	}
	
	public boolean existsTableMappingConfig() {
		return tmc != null;
	}
	public boolean existsSqlMappingConfig() {
		return smc != null;
	}

	public void destroy() {
		if(tmc!=null) {tmc.destroy(); tmc = null;}
		if(smc!=null) {smc.destroy(); smc = null;}
	}
}

/**
 * 
 * @author DougLei
 */
class TableMappingConfig {
	private List<Mapping> createTableMappings;// 记录create table mapping对象集合
	private List<Mapping> dropTableMappings;// 记录drop table mapping对象集合
	
	public void addCreateTableMapping(Mapping mapping) {
		if(createTableMappings == null) {
			createTableMappings = new ArrayList<Mapping>(10);
		}
		createTableMappings.add(mapping);
	}
	
	public void addDropTableMapping(Mapping mapping) {
		if(dropTableMappings == null) {
			dropTableMappings = new ArrayList<Mapping>(4);
		}
		dropTableMappings.add(mapping);
	}
	
	public boolean existsCreateTable() {
		return createTableMappings != null;
	}
	public List<Mapping> getCreateTables() {
		return createTableMappings;
	}
	public boolean existsDropTable() {
		return dropTableMappings != null;
	}
	public List<Mapping> getDropTables() {
		return dropTableMappings;
	}
	
	public void destroy() {
		Collections.clear(createTableMappings);
		Collections.clear(dropTableMappings);
	}
}

/**
 * 
 * @author DougLei
 */
class SqlMappingConfig {
	private SqlContentType sqlContentType;// 记录每个sql content的type
	private Map<String, ValidatorHandler> sqlValidatorHandlerMap;// 记录sql的验证器map集合
	private SqlContentContainer sqlContentContainer;// 记录sql content容器
	
	public SqlContentType getSqlContentType() {
		return sqlContentType;
	}
	public void setSqlContentType(SqlContentType sqlContentType) {
		this.sqlContentType = sqlContentType;
	}
	public Map<String, ValidatorHandler> getSqlValidatorHandlerMap() {
		return sqlValidatorHandlerMap;
	}
	public void setSqlValidatorHandlerMap(Map<String, ValidatorHandler> sqlValidatorHandlerMap) {
		Collections.clear(this.sqlValidatorHandlerMap);
		this.sqlValidatorHandlerMap = sqlValidatorHandlerMap;
	}
	public void initialSqlContentContainer(Node sqlNode) {
		destroySqlContentContainer();
		NodeList sqlContentNodeList = MappingXmlReaderContext.getSqlContentNodeList(sqlNode);
		if(sqlContentNodeList != null && sqlContentNodeList.getLength() > 0) {
			sqlContentContainer = new SqlContentContainer();
			for (int i=0;i<sqlContentNodeList.getLength();i++) {
				sqlContentContainer.put(sqlContentNodeList.item(i));
			}
		}
	}
	public boolean existsSqlContent(String sqlContentName) {
		if(sqlContentContainer != null) {
			return sqlContentContainer.existsSqlContent(sqlContentName);
		}
		return false;
	}
	public SqlContentMetadata getSqlContent(String sqlContentName) {
		if(sqlContentContainer != null) {
			return sqlContentContainer.getSqlContent(sqlContentName);
		}
		return null;
	}
	
	private void destroySqlContentContainer() {
		if(sqlContentContainer!=null) {sqlContentContainer.destroy(); sqlContentContainer = null;}
	}
	
	public void destroy() {
		Collections.clear(sqlValidatorHandlerMap);
		destroySqlContentContainer();
	}
}

/**
 * sql-content容器
 * @author DougLei
 */
class SqlContentContainer {
	private static final XmlSqlContentMetadataValidate sqlContentMetadataValidate = new XmlSqlContentMetadataValidate();
	
	private Map<String, Node> sqlContentNodeMap;// 记录sql-content node map集合
	private Map<String, SqlContentMetadata> sqlContentMap;// 记录sqlContent map集合
	
	public void put(Node sqlContentNode) {
		String name = sqlContentMetadataValidate.getName(sqlContentNode.getAttributes().getNamedItem("name"));
		if(sqlContentNodeMap == null) {
			sqlContentNodeMap = new HashMap<String, Node>(8);
		}else if(sqlContentNodeMap.containsKey(name)) {
			throw new RepeatedSqlContentNameException(name);
		}
		sqlContentNodeMap.put(name, sqlContentNode);
	}
	
	public boolean existsSqlContent(String sqlContentName) {
		if(sqlContentMap != null) {
			return sqlContentMap.containsKey(sqlContentName);
		}
		return false;
	}
	
	public SqlContentMetadata getSqlContent(String sqlContentName) {
		if(sqlContentNodeMap.containsKey(sqlContentName)) {
			SqlContentMetadata sqlContentMetadata = null;
			if(sqlContentMap != null) {
				sqlContentMetadata = sqlContentMap.get(sqlContentName);
			}
			if(sqlContentMetadata == null) {
				sqlContentMetadata = sqlContentMetadataValidate.doValidate(sqlContentNodeMap.get(sqlContentName));
				if(sqlContentMap == null) {
					sqlContentMap = new HashMap<String, SqlContentMetadata>(8);
				}
				sqlContentMap.put(sqlContentMetadata.getName(), sqlContentMetadata);
			}
			return sqlContentMetadata;
		}
		return null;
	}
	
	public void destroy() {
		Collections.clear(sqlContentNodeMap);
		Collections.clear(sqlContentMap);
	}
	
	private class RepeatedSqlContentNameException extends RuntimeException{
		private static final long serialVersionUID = 8489471705644931667L;
		public RepeatedSqlContentNameException(String sqlContentName) {
			super("重复配置了name=["+sqlContentName+"]的<sql-content>元素");
		}
	}
}
