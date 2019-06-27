package com.douglei.orm.context;

import java.util.ArrayList;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.core.dialect.db.table.handler.TableHandler;
import com.douglei.orm.core.metadata.sql.SqlContentType;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.metadata.table.TableMetadata;

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
	
	// 是否要注册表映射
	private static boolean isRegisterTableMapping(Mapping mapping) {
		return mapping.getMappingType() == MappingType.TABLE && ((TableMetadata)mapping.getMetadata()).getCreateMode() != CreateMode.NONE;
	}
	
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
	
	/**
	 * 记录当前执行的映射描述
	 * @param executeMappingDescription
	 */
	public static void setCurrentExecuteMappingDescription(String executeMappingDescription) {
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		runMappingConfiguration.executeMappingDescription = executeMappingDescription;
	}
	
	/**
	 * 记录当前执行的映射描述
	 */
	public static String getCurrentExecuteMappingDescription() {
		return getRunMappingConfiguration().executeMappingDescription;
	}
}
