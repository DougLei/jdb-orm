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
 * 运行时映射配置 上下文
 * @author DougLei
 */
public class RunMappingConfigurationContext {
	private static final ThreadLocal<RunMappingConfiguration> RUN_MAPPING_CONFIGURATION = new ThreadLocal<RunMappingConfiguration>();
	private static RunMappingConfiguration getRunMappingConfiguration() {
		RunMappingConfiguration runMappingConfiguration = RUN_MAPPING_CONFIGURATION.get();
		if(runMappingConfiguration == null) {
			runMappingConfiguration = new RunMappingConfiguration();
			RUN_MAPPING_CONFIGURATION.set(runMappingConfiguration);
		}
		return runMappingConfiguration;
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
			RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
			if(runMappingConfiguration.createTableMappings == null) {
				runMappingConfiguration.createTableMappings = new ArrayList<Mapping>(10);
			}
			runMappingConfiguration.createTableMappings.add(mapping);
		}
	}
	
	/**
	 * 执行create table
	 * @param dataSourceWrapper
	 */
	public static void executeCreateTable(DataSourceWrapper dataSourceWrapper) {
		if(RUN_MAPPING_CONFIGURATION.get() != null) {
			RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
			if(runMappingConfiguration.createTableMappings != null) {
				TableHandler.singleInstance().create(dataSourceWrapper, runMappingConfiguration.createTableMappings);
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
			RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
			if(runMappingConfiguration.dropTableMappings == null) {
				runMappingConfiguration.dropTableMappings = new ArrayList<Mapping>(4);
			}
			runMappingConfiguration.dropTableMappings.add(mapping);
		}
	}
	
	/**
	 * 执行drop table
	 * @param dataSourceWrapper
	 */
	public static void executeDropTable(DataSourceWrapper dataSourceWrapper) {
		if(RUN_MAPPING_CONFIGURATION.get() != null) {
			RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
			if(runMappingConfiguration.dropTableMappings != null) {
				TableHandler.singleInstance().drop(dataSourceWrapper, runMappingConfiguration.dropTableMappings);
			}
		}
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 记录当前解析的sql content的type
	 * @param sqlContentType
	 */
	public static void setCurrentSqlContentType(SqlContentType sqlContentType) {
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		runMappingConfiguration.sqlContentType = sqlContentType;
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
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		runMappingConfiguration.sqlValidatorMap = sqlValidatorMap;
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
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		runMappingConfiguration.executeMappingDescription = executeMappingDescription;
	}
	
	/**
	 * 获取当前执行的映射描述
	 */
	public static String getCurrentExecuteMappingDescription() {
		return getRunMappingConfiguration().executeMappingDescription;
	}
}

/**
 * 运行时映射配置
 * @author DougLei
 */
class RunMappingConfiguration {
	List<Mapping> createTableMappings;// 记录create table mapping对象集合
	List<Mapping> dropTableMappings;// 记录drop table mapping对象集合
	
	SqlContentType sqlContentType;// 记录每个sql content的type
	Map<String, ValidatorHandler> sqlValidatorMap;// 记录sql的验证器map集合
	
	String executeMappingDescription;// 记录执行的每个映射描述
}
