package com.douglei.database.dialect.db.table;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.utils.CloseUtil;
import com.douglei.utils.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class TableHandler {
	private static final Logger logger = LoggerFactory.getLogger(TableHandler.class);
	
	/**
	 * 
	 * @param tableMetadata
	 */
	public TableCreator getTableCreator(TableMetadata tableMetadata) {
		switch(tableMetadata.getCreateMode()) {
			case NONE:
				return null;
			default:
//				return new TableCreator(tableMetadata);
				return null;
		}
	}
	
	/**
	 * 执行create表操作
	 * @param dataSourceWrapper
	 * @param tableCreators
	 */
	public void executeCreate(DataSourceWrapper dataSourceWrapper, List<TableCreator> tableCreators) {
		if(tableCreators == null) {
			return;
		}
		Stack<TableDrop> tableDrops = null;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			statement = connection.createStatement();
			
//			for (TableCreator tableCreator : tableCreators) {
//				
//			}
		} catch (Exception e) {
			logger.error("create table时出现的异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			// TODO 准备回滚
			
			
			
		} finally {
			CloseUtil.closeDBConn(statement, connection);
			if(tableDrops != null && tableDrops.size() > 0) {
				tableDrops.clear();
			}
		}
	}
}
