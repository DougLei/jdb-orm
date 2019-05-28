package com.douglei.context;

import com.douglei.database.metadata.sql.SqlContentType;

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
	
	public static void setCurrentSqlContentType(SqlContentType sqlContentType) {
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		runMappingConfiguration.sqlContentType = sqlContentType;
	}
	public static SqlContentType getCurrentSqlContentType() {
		return getRunMappingConfiguration().sqlContentType;
	}
}
