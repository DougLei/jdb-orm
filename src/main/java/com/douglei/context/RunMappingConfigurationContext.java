package com.douglei.context;

import java.util.ArrayList;

import com.douglei.core.dialect.db.table.op.create.TableCreator;
import com.douglei.core.dialect.db.table.op.drop.TableDrop;
import com.douglei.core.metadata.sql.SqlContentType;
import com.douglei.core.metadata.table.CreateMode;
import com.douglei.core.metadata.table.TableMetadata;

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
		if(runMappingConfiguration.tableCreators == null) {
			runMappingConfiguration.tableCreators = new ArrayList<TableCreator>(10);
		}
		runMappingConfiguration.tableCreators.add(new TableCreator(tableMetadata));
	}
	
	/**
	 * 注册要创建的TableMetadata
	 * @param tableMetadata
	 */
	public static void registerDropTable(TableMetadata tableMetadata) {
		RunMappingConfiguration runMappingConfiguration = getRunMappingConfiguration();
		if(runMappingConfiguration.tableCreators == null) {
			runMappingConfiguration.tableCreators = new ArrayList<TableCreator>(5);
		}
		runMappingConfiguration.tableDrops.add(new TableDrop(tableMetadata));
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
}
