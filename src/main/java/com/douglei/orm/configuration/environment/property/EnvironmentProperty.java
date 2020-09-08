package com.douglei.orm.configuration.environment.property;

import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
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
	
	public String getId();
	
	public Dialect getDialect();
	
	public boolean enableStatementCache();
	
	public boolean enableTableSessionCache();
	
	public MappingStore getMappingStore();
	
	public boolean clearMappingStoreOnStart();
	
	public CreateMode getTableCreateMode();
	
	public boolean enableDataValidate();
	
	public String getSerializationFileRootPath();
	
	public boolean enableColumnStructUpdateValidate();
	
	public SqlParameterConfigHolder getSqlParameterConfigHolder();
	
	public ColumnNameConverter getColumnNameConverter();
}
