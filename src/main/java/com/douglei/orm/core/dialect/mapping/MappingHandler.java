package com.douglei.orm.core.dialect.mapping;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.ParseMappingException;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 映射处理器
 * @author DougLei
 */
public class MappingHandler {
	private MappingStore mappingStore;
	private TableStructHandler tableStructHandler;
	
	public MappingHandler(MappingStore mappingStore, DataSourceWrapper dataSourceWrapper, TableSqlStatementHandler tableSqlStatementHandler) {
		this.mappingStore = mappingStore;
		this.tableStructHandler = new TableStructHandler(dataSourceWrapper, tableSqlStatementHandler);
	}

	// 解析映射实体
	private void parseMappingEntities(Collection<MappingEntity> mappingEntities) throws ParseMappingException {
		for (MappingEntity mappingEntity : mappingEntities) {
			if(mappingEntity.parseMapping() == null) 
				mappingEntity.setMapping(mappingStore.getMapping(mappingEntity.getCode()));
		}
	}
	
	/**
	 * 操作映射
	 * @param mappingEntities
	 * @throws MappingExecuteException 
	 */
	public void execute(Collection<MappingEntity> mappingEntities) throws MappingExecuteException {
		try {
			parseMappingEntities(mappingEntities);
			for (MappingEntity mappingEntity : mappingEntities) {
				switch (mappingEntity.getOp()) {
					case ADD_OR_COVER: 
						if(mappingEntity.getType() == MappingType.TABLE)
							tableStructHandler.createTable((TableMetadata)mappingEntity.getMapping().getMetadata());
						addMapping(mappingEntity.getMapping());
						break;
					case DELETE: 
						if(mappingEntity.getType() == MappingType.TABLE) 
							tableStructHandler.deleteTable((TableMetadata)mappingEntity.getMapping().getMetadata());
						deleteMapping(mappingEntity.getCode());
						break;
				}
			}
		} catch (Exception executeException) {
			try {
				rollback();
			} catch (Exception rollbackException) {
				executeException.addSuppressed(rollbackException);
			}
			throw new MappingExecuteException("在操作mapping时出现异常", executeException);
		} finally {
			RollbackRecorder.clear();
			tableStructHandler.resetting();
			MappingResolverContext.destroy();
		}
	}
	
	/**
	 * 添加或覆盖映射
	 * @param mapping
	 */
	private void addMapping(Mapping mapping) {
		Mapping exMapping = mappingStore.addMapping(mapping);
		if (exMapping == null) {
			RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_MAPPING, mapping.getCode());
		}else {
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, exMapping);
		}
	}
	
	/**
	 * 删除映射
	 * @param mappingCode
	 */
	private void deleteMapping(String mappingCode) {
		Mapping exMapping = mappingStore.deleteMapping(mappingCode);
		if(exMapping != null) 
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, exMapping);
	}
	
	/**
	 * 回滚
	 * @throws SQLException 
	 */
	private void rollback() throws SQLException {
		List<RollbackExecutor> list = RollbackRecorder.getRollbackExecutorList();
		if(list != null) {
			for(int i=list.size()-1;i>=0;i--) 
				list.get(i).executeRollback(tableStructHandler, mappingStore);
		}
	}
}
