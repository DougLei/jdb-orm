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
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.ParseMappingException;
import com.douglei.orm.mapping.handler.entity.impl.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.handler.entity.impl.DeleteMappingEntity;
import com.douglei.orm.mapping.handler.object.ObjectHandlerPackageContext;
import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackExecutor;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.QueryPurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.SqlExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.SqlExecutionEntity;

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
		MappingProperty property;
		for(MappingEntity mappingEntity : mappingEntities) {
			logger.debug("解析: {}", mappingEntity);
			switch (mappingEntity.getOp()) {
				case ADD_OR_COVER: // 解析映射, 并判断是否存在同code映射, 如果存在, 还要保证之前的映射可以被覆盖
					((AddOrCoverMappingEntity)mappingEntity).parseMapping();
					property = mappingContainer.getMappingProperty(mappingEntity.getCode());
					if(property != null && !property.supportCover())
						throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射已存在, 且禁止被覆盖");
					break;
				case DELETE: // 判断是否存在指定code的映射, 再判断存在的映射是否可以被删除, 最后获取table类型的映射即可
					property = mappingContainer.getMappingProperty(mappingEntity.getCode());
					if(property == null)
						throw new NullPointerException("不存在code为"+mappingEntity.getCode()+"的映射, 无法删除"); 
					if(!property.supportDelete())
						throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射禁止被删除");
					if(property.getType().equals(MappingTypeConstants.TABLE))
						((DeleteMappingEntity)mappingEntity).setMapping(mappingContainer.getMapping(mappingEntity.getCode()));
					break;
				case DELETE_DATABASE_OBJECT_ONLY:
					break;
			}
		}
		
		if(mappingEntities.size() > 1)
			Collections.sort(mappingEntities, comparator);
	}
	
	// 在一次操作多个映射时, 需要对其进行排序, 先删再加, 删除时倒序删, 添加时正序加
	private static final Comparator<MappingEntity> comparator = new Comparator<MappingEntity>() {
		@Override
		public int compare(MappingEntity o1, MappingEntity o2) {
			if(o1.getOp().getPriority() < o2.getOp().getPriority())
				return -1;
			if(o1.getOp().getPriority() > o2.getOp().getPriority())
				return 1;
			return o1.getOp().compare4Sort(o1, o2);
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
			
			ObjectHandlerPackageContext.initialize(dataSourceWrapper);
			for (MappingEntity mappingEntity : mappingEntities) {
				logger.debug("操作: {}", mappingEntity);
				
				switch (mappingEntity.getOp()) {
					case ADD_OR_COVER: 
						if(mappingEntity.opDatabaseObject() && mappingEntity.getType().supportOpDatabaseObject()) 
							createObject(mappingEntity);
						
						if(mappingEntity.getType().supportOpMappingContainer()) 
							addMapping(mappingEntity);
						break;
					case DELETE: 
					case DELETE_DATABASE_OBJECT_ONLY: 
						if(mappingEntity.opDatabaseObject() && mappingEntity.getType().supportOpDatabaseObject()) 
							deleteObject(mappingEntity);
						
						if(mappingEntity.getType().supportOpMappingContainer()) 
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
			ObjectHandlerPackageContext.destroy();
			MappingParserContext.destroy();
			logger.debug("操作映射结束");
		}
	}
	
	// 创建对象
	private void createObject(MappingEntity mappingEntity) throws Exception {
		switch(mappingEntity.getType().getName()) {
			case MappingTypeConstants.TABLE:
				ObjectHandlerPackageContext.getTableObjectHandler().create((TableMetadata)mappingEntity.getMapping().getMetadata());
				break;
			case MappingTypeConstants.VIEW:
				ObjectHandlerPackageContext.getViewObjectHandler().create((ViewMetadata)mappingEntity.getMapping().getMetadata());
				break;
			case MappingTypeConstants.PROCEDURE:
				ObjectHandlerPackageContext.getProcedureObjectHandler().create((ProcedureMetadata)mappingEntity.getMapping().getMetadata());
				break;
		}
	}
	
	// 添加或覆盖映射
	private void addMapping(MappingEntity entity) {
		Mapping mapping = entity.getMapping();
		if(mapping.getMetadata().isUpdateName()) 
			deleteMapping(mapping.getMetadata().getOldName());
		
		MappingProperty mappingProperty = mapping.getProperty();
		if(mappingProperty == null)
			mappingProperty = new MappingProperty(mapping.getCode(), mapping.getType());
		MappingProperty exMappingProperty = mappingContainer.addMappingProperty(mappingProperty);
		if (exMappingProperty == null) {
			RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_MAPPING_PROPERTY, mapping.getCode(), null);
		}else {
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING_PROPERTY, exMappingProperty, null);
		}
		
		Mapping exMapping = mappingContainer.addMapping(mapping);
		if (exMapping == null) {
			RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_MAPPING, mapping.getCode(), null);
		}else {
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, exMapping, null);
		}
	}
	
	// 删除对象
	private void deleteObject(MappingEntity mappingEntity) throws SQLException {
		switch(mappingEntity.getType().getName()) {
			case MappingTypeConstants.TABLE:
				ObjectHandlerPackageContext.getTableObjectHandler().delete((TableMetadata)mappingEntity.getMapping().getMetadata());
				break;
			case MappingTypeConstants.VIEW:
				ObjectHandlerPackageContext.getViewObjectHandler().delete(mappingEntity.getCode());
				break;
			case MappingTypeConstants.PROCEDURE:
				ObjectHandlerPackageContext.getProcedureObjectHandler().delete(mappingEntity.getCode());
				break;
		}
	}

	// 删除映射
	private void deleteMapping(String code) {
		MappingProperty exMappingProperty = mappingContainer.deleteMappingProperty(code);
		if(exMappingProperty != null) 
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING_PROPERTY, exMappingProperty, null);
		
		Mapping exMapping = mappingContainer.deleteMapping(code);
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
	 * 获取指定code的映射属性, 如不存在则返回null
	 * @param code
	 * @return
	 */
	public MappingProperty getProperty(String code) {
		return mappingContainer.getMappingProperty(code);
	}
	
	/**
	 * 获取指定code的映射, 如不存在则返回null; 
	 * <p><b>注意: 建议先调用getProperty(String)进行预处理, 再决定是否调用getMapping(String)方法</b></p>
	 * @param code 
	 * @return
	 */
	public Mapping getMapping(String code) {
		return mappingContainer.getMapping(code);
	}
	
	/**
	 * 获取指定namespace和name的sql映射执行实体
	 * @param purposeEntity 创建对应的用途实例传入, 或使用已有的默认实例, 例 {@link QueryPurposeEntity.DEFAULT}
	 * @param namespace
	 * @param name 
	 * @param sqlParameter 输入参数
	 * @return
	 */
	public SqlExecutionEntity getSqlMappingExecutionEntity(PurposeEntity purposeEntity, String namespace, String name, Object sqlParameter){
		SqlMetadata sqlMetadata = (SqlMetadata) getMapping(namespace).getMetadata();
		return new SqlExecutionEntity(new SqlExecuteHandler(purposeEntity, sqlMetadata, name, sqlParameter));
	}
}
