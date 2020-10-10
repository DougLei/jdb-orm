package com.douglei.orm.mapping.handler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.environment.datasource.DataSourceWrapper;
import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.ParseMappingException;
import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackExecutor;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.handler.struct.StructHandlerPackageContext;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * 映射处理器
 * @author DougLei
 */
public class MappingHandler {
	private static final Logger logger = LoggerFactory.getLogger(MappingHandler.class);
	private MappingContainer mappingContainer;
	private DataSourceWrapper dataSourceWrapper;
	
	public MappingHandler(MappingContainer mappingContainer, DataSourceWrapper dataSourceWrapper) {
		this.mappingContainer = mappingContainer;
		this.dataSourceWrapper = dataSourceWrapper;
	}
	
	// 解析映射实体
	private void parseMappingEntities(List<MappingEntity> mappingEntities) throws ParseMappingException {
		for (MappingEntity mappingEntity : mappingEntities) {
			logger.debug("解析: {}", mappingEntity);
			if(mappingEntity.mappingIsRequired() && !mappingEntity.parseMapping()) 
				mappingEntity.setMapping(mappingContainer.getMapping(mappingEntity.getCode()));
		}
		
		if(mappingEntities.size() > 1)
			Collections.sort(mappingEntities, priorityComparator);
	}
	
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
	
	
	/**
	 * 操作映射
	 * @param mappingEntities
	 * @throws MappingHandlerException 
	 */
	public void execute(MappingEntity... mappingEntities) throws MappingHandlerException {
		execute(Arrays.asList(mappingEntities));
	}
	
	/**
	 * 操作映射
	 * @param mappingEntities
	 * @throws MappingHandlerException 
	 */
	public void execute(List<MappingEntity> mappingEntities) throws MappingHandlerException {
		logger.debug("操作映射开始");
		try {
			parseMappingEntities(mappingEntities);
			
			StructHandlerPackageContext.initialize(dataSourceWrapper);
			for (MappingEntity mappingEntity : mappingEntities) {
				logger.debug("操作: {}", mappingEntity);
				
				switch (mappingEntity.getOp()) {
					case ADD_OR_COVER: 
						if(mappingEntity.opDatabaseStruct() && mappingEntity.getType().opDatabaseStruct()) 
							createStruct(mappingEntity);
						
						if(mappingEntity.getType().opMappingContainer()) 
							addMapping(mappingEntity.getMapping());
						break;
					case DELETE: 
						if(mappingEntity.opDatabaseStruct() && mappingEntity.getType().opDatabaseStruct()) 
							deleteStruct(mappingEntity);
						
						if(mappingEntity.getType().opMappingContainer()) 
							deleteMapping(mappingEntity.getCode());
						break;
				}
			}
		} catch (Exception executeException) {
			try {
				logger.debug("操作映射时出现异常, 开始回滚: {}", executeException);
				rollback();
			} catch (Exception rollbackException) {
				logger.debug("回滚时又出现异常, 很绝望: {}", rollbackException);
				executeException.addSuppressed(rollbackException);
			}
			throw new MappingHandlerException("在操作映射时出现异常", executeException);
		} finally {
			RollbackRecorder.clear();
			StructHandlerPackageContext.destroy();
			MappingParserContext.destroy();
			logger.debug("操作映射结束");
		}
	}
	
	// 创建结构
	private void createStruct(MappingEntity mappingEntity) throws Exception {
		switch(mappingEntity.getType().getName()) {
			case MappingTypeNameConstants.TABLE:
				StructHandlerPackageContext.getTableStructHandler().create((TableMetadata)mappingEntity.getMapping().getMetadata());
				break;
			case MappingTypeNameConstants.VIEW:
				StructHandlerPackageContext.getViewStructHandler().create((ViewMetadata)mappingEntity.getMapping().getMetadata());
				break;
			case MappingTypeNameConstants.PROCEDURE:
				StructHandlerPackageContext.getProcStructHandler().create((ProcedureMetadata)mappingEntity.getMapping().getMetadata());
				break;
		}
	}
	
	// 添加或覆盖映射
	private void addMapping(Mapping mapping) {
		Mapping exMapping = mappingContainer.addMapping(mapping);
		if (exMapping == null) {
			RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_MAPPING, mapping.getCode(), null);
		}else {
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, exMapping, null);
		}
	}

	// 删除结构
	private void deleteStruct(MappingEntity mappingEntity) throws SQLException {
		switch(mappingEntity.getType().getName()) {
			case MappingTypeNameConstants.TABLE:
				StructHandlerPackageContext.getTableStructHandler().delete((TableMetadata)mappingEntity.getMapping().getMetadata());
				break;
			case MappingTypeNameConstants.VIEW:
				StructHandlerPackageContext.getViewStructHandler().delete(mappingEntity.getCode().toUpperCase());
				break;
			case MappingTypeNameConstants.PROCEDURE:
				StructHandlerPackageContext.getProcStructHandler().delete(mappingEntity.getCode().toUpperCase());
				break;
		}
	}

	// 删除映射
	private void deleteMapping(String mappingCode) {
		Mapping exMapping = mappingContainer.deleteMapping(mappingCode);
		if(exMapping != null) 
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, exMapping, null);
	}
	
	/**
	 * 回滚
	 * @throws SQLException 
	 */
	private void rollback() throws SQLException {
		LinkedList<RollbackExecutor> list = RollbackRecorder.getRollbackExecutorList();
		if(list != null) {
			while(!list.isEmpty())
				list.removeLast().executeRollback(mappingContainer);
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
	
	/**
	 * 获取指定code的映射, 如不存在则返回null
	 * @param code
	 * @return
	 */
	public Mapping get(String code) {
		return mappingContainer.getMapping(code);
	}
}
