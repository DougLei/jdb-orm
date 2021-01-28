package com.douglei.orm.mapping.handler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
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
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSqlEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSqls;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.purpose.QueryPurposeEntity;

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
				case DELETE: // 判断是否存在指定code的映射, 再判断存在的映射是否可以被删除, 最后获取supportOpDatabaseObject为true的类型的映射即可
					property = mappingContainer.getMappingProperty(mappingEntity.getCode());
					if(property == null)
						throw new NullPointerException("不存在code为"+mappingEntity.getCode()+"的映射, 无法删除"); 
					if(!property.supportDelete())
						throw new ParseMappingException("名为["+mappingEntity.getCode()+"]的映射禁止被删除");
					
					((DeleteMappingEntity)mappingEntity).setType(property.getType());
					((DeleteMappingEntity)mappingEntity).setOrder(property.getOrder());
					if(mappingEntity.getType().supportOpDatabaseObject()) 
						((DeleteMappingEntity)mappingEntity).setMapping(mappingContainer.getMapping(mappingEntity.getCode()));
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
			Mapping exMapping = null;
			for (MappingEntity mappingEntity : mappingEntities) {
				logger.debug("操作: {}", mappingEntity);
				
				switch (mappingEntity.getOp()) {
					case ADD_OR_COVER: 
						exMapping = addMapping(mappingEntity);
						
						if(mappingEntity.opDatabaseObject() && mappingEntity.getType().supportOpDatabaseObject()) 
							createObject(mappingEntity.getType().getName(), mappingEntity.getMapping().getMetadata(), exMapping==null?null:exMapping.getMetadata());
						break;
					case DELETE: 
						exMapping = deleteMapping(mappingEntity.getCode());
						
						if(mappingEntity.opDatabaseObject() && mappingEntity.getType().supportOpDatabaseObject()) 
							deleteObject(mappingEntity, exMapping.getMetadata());
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
	private void createObject(String type, AbstractMetadata metadata, AbstractMetadata exMetadata) throws Exception {
		switch(type) {
			case MappingTypeConstants.TABLE:
				ObjectHandlerPackageContext.getTableObjectHandler().create((TableMetadata)metadata, (TableMetadata)exMetadata);
				break;
			case MappingTypeConstants.VIEW:
				ObjectHandlerPackageContext.getViewObjectHandler().create((ViewMetadata)metadata, (ViewMetadata)exMetadata);
				break;
			case MappingTypeConstants.PROCEDURE:
				ObjectHandlerPackageContext.getProcedureObjectHandler().create((ProcedureMetadata)metadata, (ProcedureMetadata)exMetadata);
				break;
		}
	}
	
	// 添加或覆盖映射; 如果是添加, 返回null, 如果是覆盖, 返回被覆盖的mapping;
	private Mapping addMapping(MappingEntity entity) {
		Mapping mapping = entity.getMapping();
		MappingProperty exMappingProperty = mappingContainer.addMappingProperty(mapping.getProperty());
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
		
		if(mapping.getMetadata().isUpdateName()) 
			exMapping = deleteMapping(mapping.getMetadata().getOldName());
		return exMapping;
	}
	
	// 删除对象
	private void deleteObject(MappingEntity mappingEntity, AbstractMetadata exMetadata) throws SQLException {
		switch(mappingEntity.getType().getName()) {
			case MappingTypeConstants.TABLE:
				ObjectHandlerPackageContext.getTableObjectHandler().delete((TableMetadata)mappingEntity.getMapping().getMetadata(), null);
				break;
			case MappingTypeConstants.VIEW:
				ObjectHandlerPackageContext.getViewObjectHandler().delete(mappingEntity.getCode(), (ViewMetadata)exMetadata);
				break;
			case MappingTypeConstants.PROCEDURE:
				ObjectHandlerPackageContext.getProcedureObjectHandler().delete(mappingEntity.getCode(), (ProcedureMetadata)exMetadata);
				break;
		}
	}

	// 删除映射; 返回被删除的映射实例, 如果没有映射, 返回null
	private Mapping deleteMapping(String code) {
		MappingProperty exMappingProperty = mappingContainer.deleteMappingProperty(code);
		if(exMappingProperty != null) 
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING_PROPERTY, exMappingProperty, null);
		
		Mapping exMapping = mappingContainer.deleteMapping(code);
		if(exMapping != null) 
			RollbackRecorder.record(RollbackExecMethod.EXEC_ADD_MAPPING, exMapping, null);
		return exMapping;
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
	public MappingProperty getMappingProperty(String code) {
		return mappingContainer.getMappingProperty(code);
	}
	
	/**
	 * 获取指定code和type的映射 
	 * @param code 
	 * @param type
	 * @return
	 */
	public Mapping getMapping(String code, String type) {
		Mapping mapping =  mappingContainer.getMapping(code);
		if(mapping == null)
			throw new NullPointerException("不存在code为"+code+"的mapping");
		if(type.equals(mapping.getType()))
			return mapping;
		throw new MappingMismatchingException(code, type);
	}
	
	/**
	 * 获取表元数据实例
	 * @param code 值分两种, 1.对应类的全路径(com.test.SysUser); 2.对应的表名, 表名必须全大写(SYS_USER); 如果在映射文件中配置了class, 则必须传入类的全路径; 否则必须传入表名
	 * @return
	 */
	public TableMetadata getTableMetadata(String code) {
		return (TableMetadata) getMapping(code, MappingTypeConstants.TABLE).getMetadata();
	}
	
	/**
	 * 获取sql元数据实例
	 * @param namespace
	 * @return
	 */
	public SqlMetadata getSqlMetadata(String namespace) {
		return (SqlMetadata) getMapping(namespace, MappingTypeConstants.SQL).getMetadata();
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
		return new ExecutableSqlEntity(new ExecutableSqls(purposeEntity, sqlMetadata, name, sqlParameter));
	}
}
