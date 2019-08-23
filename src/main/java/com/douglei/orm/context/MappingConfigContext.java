package com.douglei.orm.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.core.dialect.db.table.TableHandler;
import com.douglei.orm.core.metadata.sql.SqlContentType;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.tools.utils.Collections;

/**
 * 
 * @author DougLei
 */
public class MappingConfigContext {
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
	public static void setCurrentSqlContentType(SqlContentType sqlContentType) {
		getSqlMappingConfig().setSqlContentType(sqlContentType);
	}
	
	/**
	 * 获取当前解析的sql content的type
	 * @return
	 */
	public static SqlContentType getCurrentSqlContentType() {
		return getSqlMappingConfig().getSqlContentType();
	}
	
	/**
	 * 记录当前解析的sql验证器集合
	 * @param sqlValidatorHandlerMap
	 */
	public static void setCurrentSqlValidatorMap(Map<String, ValidatorHandler> sqlValidatorHandlerMap) {
		getSqlMappingConfig().setSqlValidatorHandlerMap(sqlValidatorHandlerMap);
	}
	
	/**
	 * 获取当前解析的sql验证器集合
	 * @return
	 */
	public static Map<String, ValidatorHandler> getCurrentSqlValidatorHandlerMap() {
		return getSqlMappingConfig().getSqlValidatorHandlerMap();
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
		if(tmc!=null) tmc.destroy();
		if(smc!=null) smc.destroy();
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
	
	public void destroy() {
		Collections.clear(sqlValidatorHandlerMap);
	}
}
