package com.douglei.orm.configuration.environment.property;

import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.metadata.sql.SqlParameterConfigHolder;
import com.douglei.orm.core.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public String getId();
	
	public Dialect getDialect();
	
	public boolean enableStatementCache();
	
	public boolean enableTableSessionCache();
	
	public MappingStore getMappingStore();
	
	public boolean clearMappingStoreOnStart();
	
	public CreateMode getTableCreateMode();
	
	public boolean enableDataValidate();
	
	public boolean enableTableDynamicUpdate();
	
	public String getSerializationFileRootPath();
	
	public boolean enableColumnDynamicUpdateValidate();
	
	public byte dynamicMappingOnceMaxCount();
	
	public SqlParameterConfigHolder getSqlParameterConfigHolder();
}
