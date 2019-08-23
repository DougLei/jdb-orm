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

/**
 * 
 * @author DougLei
 */
public class MappingConfigContext {
	private static final ThreadLocal<MappingConfig> mappingConfig = new ThreadLocal<MappingConfig>();
	private static MappingConfig getRunMappingConfiguration() {
		MappingConfig mc = mappingConfig.get();
		if(mc == null) {
			mc = new MappingConfig();
			mappingConfig.set(mc);
		}
		return mc;
	}
	
	// 是否注册表映射
	private static boolean isRegisterTableMapping(Mapping mapping) {
		return mapping.getMappingType() == MappingType.TABLE && ((TableMetadata)mapping.getMetadata()).getCreateMode() != CreateMode.NONE;
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 注册要create的TableMapping
	 * @param mapping
	 */
	public static void registerCreateTableMapping(Mapping mapping) {
		if(isRegisterTableMapping(mapping)) {
			MappingConfig mc = getRunMappingConfiguration();
			if(mc.createTableMappings == null) {
				mc.createTableMappings = new ArrayList<Mapping>(10);
			}
			mc.createTableMappings.add(mapping);
		}
	}
	
	/**
	 * 执行create table
	 * @param dataSourceWrapper
	 */
	public static void executeCreateTable(DataSourceWrapper dataSourceWrapper) {
		if(mappingConfig.get() != null) {
			MappingConfig mc = getRunMappingConfiguration();
			if(mc.createTableMappings != null) {
				TableHandler.singleInstance().create(dataSourceWrapper, mc.createTableMappings);
			}
		}
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 注册要drop的TableMapping
	 * @param mapping
	 */
	public static void registerDropTableMapping(Mapping mapping) {
		if(isRegisterTableMapping(mapping)) {
			MappingConfig mc = getRunMappingConfiguration();
			if(mc.dropTableMappings == null) {
				mc.dropTableMappings = new ArrayList<Mapping>(4);
			}
			mc.dropTableMappings.add(mapping);
		}
	}
	
	/**
	 * 执行drop table
	 * @param dataSourceWrapper
	 */
	public static void executeDropTable(DataSourceWrapper dataSourceWrapper) {
		if(mappingConfig.get() != null) {
			MappingConfig mc = getRunMappingConfiguration();
			if(mc.dropTableMappings != null) {
				TableHandler.singleInstance().drop(dataSourceWrapper, mc.dropTableMappings);
			}
		}
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 记录当前解析的sql content的type
	 * @param sqlContentType
	 */
	public static void setCurrentSqlContentType(SqlContentType sqlContentType) {
		MappingConfig mc = getRunMappingConfiguration();
		mc.sqlContentType = sqlContentType;
	}
	
	/**
	 * 获取当前解析的sql content的type
	 * @return
	 */
	public static SqlContentType getCurrentSqlContentType() {
		return getRunMappingConfiguration().sqlContentType;
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 记录当前解析的sql验证器集合
	 * @param sqlContentType
	 */
	public static void setCurrentSqlValidatorMap(Map<String, ValidatorHandler> sqlValidatorMap) {
		MappingConfig mc = getRunMappingConfiguration();
		mc.sqlValidatorMap = sqlValidatorMap;
	}
	
	/**
	 * 获取当前解析的sql验证器集合
	 * @return
	 */
	public static Map<String, ValidatorHandler> getCurrentSqlValidatorMap() {
		return getRunMappingConfiguration().sqlValidatorMap;
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 记录当前执行的映射描述
	 * @param executeMappingDescription
	 */
	public static void setCurrentExecuteMappingDescription(String executeMappingDescription) {
		MappingConfig mc = getRunMappingConfiguration();
		mc.executeMappingDescription = executeMappingDescription;
	}
	
	/**
	 * 获取当前执行的映射描述
	 */
	public static String getCurrentExecuteMappingDescription() {
		return getRunMappingConfiguration().executeMappingDescription;
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 销毁
	 */
	public static void destroy() {
		mappingConfig.remove();
	}
}

/**
 * 
 * @author DougLei
 */
class MappingConfig {
	List<Mapping> createTableMappings;// 记录create table mapping对象集合
	List<Mapping> dropTableMappings;// 记录drop table mapping对象集合
	
	SqlContentType sqlContentType;// 记录每个sql content的type
	Map<String, ValidatorHandler> sqlValidatorMap;// 记录sql的验证器map集合
	
	String executeMappingDescription;// 记录执行的每个映射描述
}
