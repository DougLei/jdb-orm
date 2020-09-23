package com.douglei.orm.configuration.environment.property;

import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.metadata.sql.SqlParameterConfigHolder;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.sql.statement.entity.ColumnNameConverter;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	/**
	 * - 以下具体的功能, 参考《jdb-orm框架配置结构设计.conf.xml》文件中, property的配置
	 */
	
	public String getConfigurationId();
	
	public Dialect getDialect();
	
	public boolean enableStatementCache();
	
	public boolean enableTableSessionCache();
	
	public MappingContainer getMappingContainer();
	
	public boolean clearMappingContainerOnStart();
	
	public CreateMode getTableCreateMode();
	
	public boolean enableColumnStructUpdateValidate();
	
	public SqlParameterConfigHolder getSqlParameterConfigHolder();
	
	public ColumnNameConverter getColumnNameConverter();
}
