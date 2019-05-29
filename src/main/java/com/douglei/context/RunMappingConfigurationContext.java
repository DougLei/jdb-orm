package com.douglei.context;

import java.util.ArrayList;
import java.util.List;

import com.douglei.database.dialect.db.table.TableCreator;
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
	 * 添加新的TableCreator
	 * @param tableCreator
	 */
	public static void addTableCreator(TableCreator tableCreator) {
		if(tableCreator == null) {
			return;
		}
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		if(runMappingConfiguration.tableCreators == null) {
			runMappingConfiguration.tableCreators = new ArrayList<TableCreator>(10);
		}
		runMappingConfiguration.tableCreators.add(tableCreator);
	}
	
	/**
	 * 获取所有的TableCreator
	 * @return
	 */
	public static List<TableCreator> getTableCreators() {
		return getRunMappingConfiguration().tableCreators;
	}
}
