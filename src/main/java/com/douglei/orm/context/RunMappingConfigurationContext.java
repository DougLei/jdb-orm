package com.douglei.orm.context;

import java.util.ArrayList;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.dialect.db.table.TableHandler;
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
	
	/**
	 * 注册要create的TableMetadata
	 * @param tableMetadata
	 */
	public static void registerCreateTable(TableMetadata tableMetadata) {
		if(tableMetadata.getCreateMode() == CreateMode.NONE) {
			return;
		}
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		if(runMappingConfiguration.createTables == null) {
			runMappingConfiguration.createTables = new ArrayList<TableMetadata>(10);
		}
		runMappingConfiguration.createTables.add(tableMetadata);
	}
	
	/**
	 * 执行create table
	 * @param dataSourceWrapper
	 */
	public static void executeCreateTable(DataSourceWrapper dataSourceWrapper) {
		if(RUN_MAPPING_CONFIGURATION.get() != null) {
			RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
			if(runMappingConfiguration.createTables != null) {
				TableHandler.singleInstance().create(dataSourceWrapper, runMappingConfiguration.createTables);
			}
		}
	}
	
	/**
	 * 注册要drop的TableMetadata
	 * @param tableMetadata
	 */
	public static void registerDropTable(TableMetadata tableMetadata) {
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		if(runMappingConfiguration.dropTables == null) {
			runMappingConfiguration.dropTables = new ArrayList<TableMetadata>(5);
		}
		runMappingConfiguration.dropTables.add(tableMetadata);
	}
	
	/**
	 * 执行drop table
	 * @param dataSourceWrapper
	 */
	public static void executeDropTable(DataSourceWrapper dataSourceWrapper) {
		if(RUN_MAPPING_CONFIGURATION.get() != null) {
			RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
			if(runMappingConfiguration.dropTables != null) {
				TableHandler.singleInstance().drop(dataSourceWrapper, runMappingConfiguration.dropTables);
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
