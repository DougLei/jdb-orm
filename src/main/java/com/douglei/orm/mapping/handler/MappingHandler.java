package com.douglei.orm.mapping.handler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.configuration.environment.datasource.DataSourceEntity;
import com.douglei.orm.configuration.environment.mapping.MappingConfiguration;
import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingContainer;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.handler.entity.DeleteMappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.ParseMappingException;
import com.douglei.orm.mapping.handler.object.DBObjectHandlerContext;
import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackExecutor;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.metadata.AbstractDBObjectMetadata;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSqlEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSqlHolder;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.QueryPurposeEntity;

/**
 * 映射处理器
 * @author DougLei
 */
public class MappingHandler {
	private static final Logger logger = LoggerFactory.getLogger(MappingHandler.class);
	private MappingConfiguration configuration; // 映射配置
	private MappingContainer container; // 映射容器
	private DataSourceEntity dataSource;
	
	/**
	 * 
	 * @param configuration 映射配置
	 * @param container 映射容器
	 * @param dataSource 数据源
	 */
	public MappingHandler(MappingConfiguration configuration, MappingContainer container, DataSourceEntity dataSource) {
		this.configuration = configuration;
		this.container = container;
		this.dataSource = dataSource;
	}
	
	// 解析映射实体
	private void parseMappingEntities(List<MappingEntity> mappingEntities) throws ParseMappingException {
		MappingProperty property = null;
		for(MappingEntity mappingEntity : mappingEntities) {
			logger.debug("解析: {}", mappingEntity);
			switch (mappingEntity.getMode()) {
				case ADD_OR_COVER: 
					((AddOrCoverMappingEntity)mappingEntity).parseMapping();
					
					// 启用属性
					if(mappingEntity.isEnableProperty()) {
						property = container.getMappingProperty(mappingEntity.getCode());
						if(property != null && !property.supportCover())
							throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射已存在, 且禁止被覆盖");
						break;
					}
					
					// 未启用属性
					if(container.exists(mappingEntity.getCode())) 
						throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射已存在, 且禁止被覆盖");
					break;
				case DELETE: 
					
					// 启用属性
					if(mappingEntity.isEnableProperty()) {
						property = container.getMappingProperty(mappingEntity.getCode());
						if(property == null)
							throw new NullPointerException("不存在code为"+mappingEntity.getCode()+"的映射, 无法删除"); 
						if(!property.supportDelete())
							throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射禁止被删除");
						
						((DeleteMappingEntity)mappingEntity).setMappingPropertyValue(property);
						break;
					}
					
					// 未启用属性
					if(container.exists(mappingEntity.getCode()))
						throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射禁止被删除");
					throw new NullPointerException("不存在code为"+mappingEntity.getCode()+"的映射, 无法删除"); 
			}
		}
		
		if(mappingEntities.size() > 1)
			Collections.sort(mappingEntities, comparator);
	}
	
	// 在一次操作多个映射时, 需要对其进行排序, 先删再加, 删除时倒序删, 添加时正序加
	private static final Comparator<MappingEntity> comparator = new Comparator<MappingEntity>() {
		@Override
		public int compare(MappingEntity o1, MappingEntity o2) {
			if(o1.getMode().getPriority() < o2.getMode().getPriority())
				return -1;
			if(o1.getMode().getPriority() > o2.getMode().getPriority())
				return 1;
			return o1.getMode().sort(o1, o2);
		}
	};
	
	/**
	 * 操作映射
	 * @param mappingEntities
	 * @throws MappingHandleException 
	 */
	public void execute(MappingEntity... mappingEntities) throws MappingHandleException {
		execute(Arrays.asList(mappingEntities));
	}
	
	/**
	 * 操作映射
	 * @param mappingEntities
	 * @throws MappingHandleException 
	 */
	public synchronized void execute(List<MappingEntity> mappingEntities) throws MappingHandleException {
		logger.debug("操作映射开始");
		try {
			parseMappingEntities(mappingEntities);
			
			DBObjectHandlerContext.init(dataSource);
			
			for (MappingEntity entity : mappingEntities) {
				logger.debug("操作: {}", entity);
				
				switch (entity.getMode()) {
					case ADD_OR_COVER: 
						Mapping coveredMapping = addMapping((AddOrCoverMappingEntity)entity);
						
						if(entity.opDatabaseObject() && entity.getType().supportOpDatabaseObject()) 
							createDBObject(entity.getType(), (AbstractDBObjectMetadata)((AddOrCoverMappingEntity)entity).getMapping().getMetadata(), coveredMapping==null?null:coveredMapping.getMetadata());
						break;
					case DELETE: 
						Mapping deletedMapping = deleteMapping(entity.isEnableProperty(), entity.getCode());
						
						if(entity.opDatabaseObject() && entity.getType().supportOpDatabaseObject()) 
							deleteDBObject(entity.getType().getName(), (AbstractDBObjectMetadata)deletedMapping.getMetadata());
						break;
				}
			}
		} catch (Exception e) {
			try {
				logger.error("操作映射时出现异常, 开始回滚: {}", e);
				rollback();
			} catch (Exception r) {
				logger.error("回滚时又出现异常: {}", r);
				e.addSuppressed(r);
			}
			throw new MappingHandleException("在操作映射时出现异常", e);
		} finally {
			MappingParseToolContext.destroy();
			DBObjectHandlerContext.destroy();
			RollbackRecorder.destroy();
			logger.debug("操作映射结束");
		}
	}
	
	/**
	 * 添加或覆盖映射; 如果是添加, 返回null, 如果是覆盖, 返回被覆盖的mapping;
	 * @param entity
	 * @return
	 */
	private Mapping addMapping(AddOrCoverMappingEntity entity) {
		if(entity.isEnableProperty()) {
			MappingProperty coveredMappingProperty = container.addMappingProperty(entity.getMappingProperty());
			if (coveredMappingProperty == null) {
				RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_MAPPING_PROPERTY, entity.getMappingProperty().getCode(), null);
			}else {
				RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING_PROPERTY, coveredMappingProperty, null);
			}
		}
		
		Mapping coveredMapping = container.addMapping(entity.getMapping());
		if (coveredMapping == null) {
			RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_MAPPING, entity.getMapping().getCode(), null);
		}else {
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, coveredMapping, null);
		}
		
		// 如果改了名字, 上面的coveredMapping是无法覆盖的, 这里进行删除
		if(entity.getMapping().getMetadata().isUpdateName()) 
			return deleteMapping(entity.isEnableProperty(), entity.getMapping().getMetadata().getOldName());
		return coveredMapping;
	}
	
	/**
	 * 创建对象
	 * @param entity
	 * @param coveredMetadata 
	 * @throws Exception
	 */
	private void createDBObject(MappingType type, AbstractDBObjectMetadata addMetadata, AbstractMetadata coveredMetadata) throws Exception {
		if(addMetadata.getCreateMode() == CreateMode.NONE)
			return;
		
		switch(type.getName()) {
			case MappingTypeNameConstants.TABLE:
				DBObjectHandlerContext.getTableObjectHandler().create((TableMetadata)addMetadata, (TableMetadata)coveredMetadata);
				break;
			case MappingTypeNameConstants.VIEW:
				DBObjectHandlerContext.getViewObjectHandler().create((ViewMetadata)addMetadata, (ViewMetadata)coveredMetadata);
				break;
			case MappingTypeNameConstants.PROCEDURE:
				DBObjectHandlerContext.getProcedureObjectHandler().create((ProcedureMetadata)addMetadata, (ProcedureMetadata)coveredMetadata);
				break;
		}
	}
	
	/**
	 * 删除映射; 返回被删除的映射实例, 如果没有映射, 返回null
	 * @param enableProperty
	 * @param code
	 * @return
	 */
	private Mapping deleteMapping(boolean enableProperty, String code) {
		if(enableProperty) {
			MappingProperty deletedMappingProperty = container.deleteMappingProperty(code);
			if(deletedMappingProperty != null) 
				RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING_PROPERTY, deletedMappingProperty, null);
		}
		
		Mapping deletedMapping = container.deleteMapping(code);
		if(deletedMapping != null) 
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, deletedMapping, null);
		return deletedMapping;
	}
	
	/**
	 * 删除对象
	 * @param type
	 * @param deletedMetadata
	 * @throws SQLException
	 */
	private void deleteDBObject(String type, AbstractDBObjectMetadata deletedMetadata) throws SQLException {
		if(deletedMetadata.getCreateMode() == CreateMode.NONE)
			return;
		
		switch(type) {
			case MappingTypeNameConstants.TABLE:
				DBObjectHandlerContext.getTableObjectHandler().delete((TableMetadata)deletedMetadata);
				break;
			case MappingTypeNameConstants.VIEW:
				DBObjectHandlerContext.getViewObjectHandler().delete((ViewMetadata)deletedMetadata);
				break;
			case MappingTypeNameConstants.PROCEDURE:
				DBObjectHandlerContext.getProcedureObjectHandler().delete((ProcedureMetadata)deletedMetadata);
				break;
		}
	}

	/**
	 * 回滚
	 * @throws SQLException 
	 */
	private void rollback() throws SQLException {
		LinkedList<RollbackExecutor> list = RollbackRecorder.getRollbackExecutorList();
		if(list != null) {
			while(!list.isEmpty())
				list.removeLast().executeRollback(container);
		}
	}
	
	/**
	 * 判断是否存在指定code的映射(非映射属性)
	 * @param code
	 * @return
	 */
	public boolean exists(String code) {
		return container.exists(code);
	}
	
	/**
	 * 获取指定code的映射属性, 如不存在则返回null
	 * @param code
	 * @return
	 */
	public MappingProperty getMappingProperty(String code) {
		return container.getMappingProperty(code);
	}
	
	/**
	 * 获取指定code和type的映射 
	 * @param code 
	 * @param type
	 * @param validate 是否对获取的结果进行验证
	 * @return
	 */
	public Mapping getMapping(String code, String type, boolean validate) {
		Mapping mapping =  container.getMapping(code);
		if(validate) {
			if(mapping == null)
				throw new MappingHandleException("不存在code为"+code+"的mapping");
			if(!type.equals(mapping.getType()))
				throw new MappingHandleException("code为"+code+"的mapping不是["+type+"]类型");
		}
		return mapping;
	}
	
	/**
	 * 获取table元数据实例
	 * @param code 值分两种, 1.对应类的全路径(com.test.SysUser); 2.对应的表名, 表名必须全大写(SYS_USER); 如果在映射文件中配置了class, 则必须传入类的全路径; 否则必须传入表名
	 * @return
	 */
	public TableMetadata getTableMetadata(String code) {
		return (TableMetadata) getMapping(code, MappingTypeNameConstants.TABLE, true).getMetadata();
	}
	
	/**
	 * 获取sql元数据实例
	 * @param namespace
	 * @return
	 */
	public SqlMetadata getSqlMetadata(String namespace) {
		return (SqlMetadata) getMapping(namespace, MappingTypeNameConstants.SQL, true).getMetadata();
	}
	
	/**
	 * 获取sql-query元数据实例
	 * @param name
	 * @return
	 */
	public SqlQueryMetadata getSqlQueryMetadata(String name) {
		return (SqlQueryMetadata) getMapping(name, MappingTypeNameConstants.SQL_QUERY, true).getMetadata();
	}
	
	/**
	 * 获取指定namespace和name的可执行sql实体
	 * @param purposeEntity 创建对应的用途实例传入, 或使用已有的默认实例, 例 {@link QueryPurposeEntity}
	 * @param namespace
	 * @param name 
	 * @param sqlParameter 输入参数
	 * @return
	 */
	public ExecutableSqlEntity getExecutableSqlEntity(PurposeEntity purposeEntity, String namespace, String name, Object sqlParameter){
		SqlMetadata sqlMetadata = getSqlMetadata(namespace);
		return new ExecutableSqlEntity(new ExecutableSqlHolder(purposeEntity, sqlMetadata, name, sqlParameter));
	}
	
	/**
	 * 获取映射的配置信息
	 * @return
	 */
	public MappingConfiguration getMappingConfiguration() {
		return configuration;
	}

	/**
	 * 卸载映射处理器
	 */
	public void uninstall() {
		if(container != null) {
			container.clear();
			container = null;
		}
	}
}
