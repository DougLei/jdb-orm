package com.douglei.sessions.session.table.impl.persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.statement.impl.Parameter;
import com.douglei.sessions.session.persistent.Identity;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.sessions.session.persistent.State;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
import com.douglei.sessions.session.persistent.execution.ExecutionType;
import com.douglei.sessions.session.table.impl.persistent.execution.TableExecutionHolder;
import com.douglei.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class TablePersistentObject implements PersistentObject{
	private static final Logger logger = LoggerFactory.getLogger(TablePersistentObject.class);
	
	private TableMetadata tableMetadata;
	private Map<String, Object> propertyMap;
	private State state;
	
	@SuppressWarnings("unchecked")
	public TablePersistentObject(TableMetadata tableMetadata, Object propertyObject) {
		if(propertyObject instanceof Map) {
			logger.debug("propertyObject is Map type, 从该map中, 筛选出相关列的数据信息");
			propertyMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)propertyObject);
		}else {
			logger.debug("propertyObject is Object type, 从该object中, 通过java内省机制, 获取相关列的数据信息");
			propertyMap = IntrospectorUtil.getProperyValues(propertyObject, tableMetadata.getColumnMetadataCodes());
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取的最终propertyMap为: {}", propertyMap.toString());
		}
		if(propertyMap == null || propertyMap.size() == 0) {
			logger.debug("最终propertyMap为空");
			throw new NullPointerException("要操作的数据不能为空");
		}
		this.tableMetadata = tableMetadata;
	}
	/**
	 * 从propertyMap集合中, 筛选出相关列的数据信息
	 * @param tableMetadata
	 * @param propertyMap 
	 * @return
	 */
	private Map<String, Object> filterColumnMetadatasPropertyMap(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		Set<String> columnMetadataCodes = tableMetadata.getColumnMetadataCodes();
		Map<String, Object> resultPropertyMap = new HashMap<String, Object>(columnMetadataCodes.size());
		
		ColumnMetadata columnMetadata = null;
		Set<String> propertyMapKeys = propertyMap.keySet();
		for (String pmkey : propertyMapKeys) {
			columnMetadata = tableMetadata.getColumnMetadata(pmkey);
			if(columnMetadata != null) {
				resultPropertyMap.put(columnMetadata.getCode(), propertyMap.get(pmkey));
			}
		}
		return resultPropertyMap;
	}
	
	@Override
	public String getCode() {
		return tableMetadata.getCode();
	}
	
	private Identity id;
	@Override
	public Identity getId() {
		if(id == null) {
			List<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns();
			Object id;
			if(primaryKeyColumns.size() == 1) {
				id = propertyMap.get(primaryKeyColumns.get(0).getCode());
			}else {
				Map<String, Object> idMap = new HashMap<String, Object>(primaryKeyColumns.size());
				for (ColumnMetadata cm : primaryKeyColumns) {
					idMap.put(cm.getCode(), propertyMap.get(cm.getCode()));
				}
				id = idMap;
			}
			if(logger.isDebugEnabled()) {
				logger.debug("获取持久化对象id为: {}", id.toString());
			}
			this.id = new Identity(id);
		}
		return id;
	}
	
	@Override
	public String toString() {
		return propertyMap.toString();
	}
	@Override
	public State getState() {
		return state;
	}
	@Override
	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public ExecutionHolder getExecutionHolder(ExecutionType executionType) {
		switch(executionType) {
			case INSERT:
				return newInsertExecutionHolderInstance();
			case DELETE:
				return newDeleteExecutionHolderInstance();
			case UPDATE:
				return newUpdateExecutionHolderInstance();
		}
		return null;
	}
	
	private ExecutionHolder newInsertExecutionHolderInstance() {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into ").append(tableMetadata.getName()).append("(");
		
		StringBuilder values = new StringBuilder();
		values.append(" values(");
		
		int size = propertyMap.size();
		List<Parameter> parameters = new ArrayList<Parameter>(size);
		
		int index = 1;
		Object value = null;
		ColumnMetadata columnMetadata = null;
		Set<String> codes = propertyMap.keySet();
		for (String code : codes) {
			value = propertyMap.get(code);
			if(value != null) {// 只保存不为空的值
				columnMetadata = tableMetadata.getColumnMetadata(code);
				
				insertSql.append(columnMetadata.getName());
				values.append("?");
				parameters.add(new Parameter(value, columnMetadata.getDataType()));
				
				if(index < size) {
					insertSql.append(",");
					values.append(",");
				}
			}
			index++;
		}
		
		insertSql.append(")").append(values).append(")");
		return new TableExecutionHolder(insertSql.toString(), parameters);
	}
	
	private ExecutionHolder newDeleteExecutionHolderInstance() {
		StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("delete ").append(tableMetadata.getName()).append(" where ");
		
		List<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns();
		int size = primaryKeyColumns.size();
		
		List<Parameter> parameters = new ArrayList<Parameter>(size);
		
		int index = 1;
		for (ColumnMetadata pkColumn : primaryKeyColumns) {
			deleteSql.append(pkColumn.getName()).append("=?");
			parameters.add(new Parameter(propertyMap.get(pkColumn.getCode()), pkColumn.getDataType()));
			
			if(index < size) {
				deleteSql.append(" and ");
			}
			index++;
		}
		return new TableExecutionHolder(deleteSql.toString(), parameters);
	}
	
	private ExecutionHolder newUpdateExecutionHolderInstance() {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update ").append(tableMetadata.getName()).append(" set ");
		
		int size = propertyMap.size();
		List<Parameter> parameters = new ArrayList<Parameter>(size);
		
		// 处理update set值
		int index = 1;
		Set<String> codes = propertyMap.keySet();
		Object value = null;
		ColumnMetadata columnMetadata = null;
		for (String code : codes) {
			if(!tableMetadata.isPrimaryKeyColumn(code)) {
				value = propertyMap.get(code);
				if(value != null) {// 只修改不为空的值
					columnMetadata = tableMetadata.getColumnMetadata(code);
					
					updateSql.append(columnMetadata.getName()).append("=?");
					parameters.add(new Parameter(value, columnMetadata.getDataType()));
					
					if(index < size) {
						updateSql.append(",");
					}
				}
			}
			index++;
		}
		updateSql.append(" where ");
		
		// 处理where值
		List<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns();
		size = primaryKeyColumns.size();
		index = 1;
		for (ColumnMetadata pkColumn : primaryKeyColumns) {
			updateSql.append(pkColumn.getName()).append("=?");
			parameters.add(new Parameter(propertyMap.get(pkColumn.getCode()), pkColumn.getDataType()));
			
			if(index < size) {
				updateSql.append(" and ");
			}
			index++;
		}
		return new TableExecutionHolder(updateSql.toString(), parameters);
	}
}
