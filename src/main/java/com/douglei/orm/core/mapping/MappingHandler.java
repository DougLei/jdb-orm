package com.douglei.orm.core.mapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.ParseMappingException;
import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
import com.douglei.orm.configuration.impl.element.environment.mapping.AddOrCoverMappingEntity;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.mapping.struct.sql.SqlStructHandler;
import com.douglei.orm.core.mapping.struct.table.TableStructHandler;
import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 映射处理器
 * @author DougLei
 */
public class MappingHandler {
	private static final Logger logger = LoggerFactory.getLogger(MappingHandler.class);
	private MappingContainer mappingContainer;
	private TableStructHandler tableStructHandler;
	private SqlStructHandler sqlStructHandler;
	
	// 在一次操作多个映射时, 需要对其进行优先级排序, 从优先级高的执行到优先级低的
	private static final Comparator<MappingEntity> priorityComparator = new Comparator<MappingEntity>() {
		@Override
		public int compare(MappingEntity o1, MappingEntity o2) {
			if(o1.getType().getPriority() == o2.getType().getPriority())
				return 0;
			if(o1.getType().getPriority() < o2.getType().getPriority())
				return -1;
			return 0;
		}
	};
	
	public MappingHandler(MappingContainer mappingContainer, DataSourceWrapper dataSourceWrapper, TableSqlStatementHandler tableSqlStatementHandler) {
		this.mappingContainer = mappingContainer;
		this.tableStructHandler = new TableStructHandler(dataSourceWrapper, tableSqlStatementHandler);
		this.sqlStructHandler = new SqlStructHandler();
	}

	// 解析映射实体
	private void parseMappingEntities(List<MappingEntity> mappingEntities) throws ParseMappingException {
		for (MappingEntity mappingEntity : mappingEntities) {
			logger.debug("解析: {}", mappingEntity);
			if(!mappingEntity.parseMapping()) 
				mappingEntity.setMapping(mappingContainer.getMapping(mappingEntity.getCode()));
		}
		
		if(mappingEntities.size() > 1)
			Collections.sort(mappingEntities, priorityComparator);
	}
	
	/**
	 * 操作映射
	 * @param mappingEntities
	 * @throws MappingExecuteException 
	 */
	public void execute(List<MappingEntity> mappingEntities) throws MappingExecuteException {
		logger.debug("操作映射开始");
		try {
			parseMappingEntities(mappingEntities);
			for (MappingEntity mappingEntity : mappingEntities) {
				logger.debug("操作: {}", mappingEntity);
				
				switch (mappingEntity.getOp()) {
					case ADD_OR_COVER: 
						if(mappingEntity.opStruct()) {
							if(mappingEntity.getType() == MappingType.TABLE)
								tableStructHandler.createTable((TableMetadata)mappingEntity.getMapping().getMetadata());
							else 
								sqlStructHandler.createObject((TableMetadata)mappingEntity.getMapping().getMetadata());
						}
						addMapping(mappingEntity.getMapping());
						break;
					case DELETE: 
						if(mappingEntity.opStruct()) {
							if(mappingEntity.getType() == MappingType.TABLE) 
								tableStructHandler.deleteTable((TableMetadata)mappingEntity.getMapping().getMetadata());
							else
								sqlStructHandler.delete((TableMetadata)mappingEntity.getMapping().getMetadata());
						}
						deleteMapping(mappingEntity.getCode());
						break;
				}
			}
		} catch (Exception executeException) {
			try {
				logger.debug("操作映射时出现异常, 开始回滚: {}", executeException);
				rollback();
			} catch (Exception rollbackException) {
				logger.debug("回滚时出现异常, 很绝望: {}", rollbackException);
				executeException.addSuppressed(rollbackException);
			}
			throw new MappingExecuteException("在操作映射时出现异常", executeException);
		} finally {
			RollbackRecorder.clear();
			tableStructHandler.resetting();
			MappingResolverContext.destroy();
			logger.debug("操作映射结束");
		}
	}
	
	/**
	 * 添加或覆盖映射
	 * @param mapping
	 */
	private void addMapping(Mapping mapping) {
		Mapping exMapping = mappingContainer.addMapping(mapping);
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
		Mapping exMapping = mappingContainer.deleteMapping(mappingCode);
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
				list.get(i).executeRollback(tableStructHandler, mappingContainer);
		}
	}
	
	
	/**
	 * 判断是否存在指定code的映射
	 * @param code
	 * @return
	 */
	public boolean exists(String code) {
		return mappingContainer.exists(code);
	}
}
