package com.douglei.core.dialect.db.table.op;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.core.dialect.db.table.op.create.TableCreator;
import com.douglei.core.dialect.db.table.op.drop.TableDrop;

/**
 * 
 * @author DougLei
 */
public class TableOPHandler {
	private static final Logger logger = LoggerFactory.getLogger(TableOPHandler.class);
	private TableOPHandler() {}
	private static final TableOPHandler instance = new TableOPHandler();
	public static final TableOPHandler singleInstance() {
		return instance;
	}
	
	/**
	 * create表
	 * @param dataSourceWrapper
	 * @param tableCreators
	 */
	public void create(DataSourceWrapper dataSourceWrapper, List<TableCreator> tableCreators) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * drop表
	 * @param dataSourceWrapper
	 * @param tableDrops
	 */
	public void drop(DataSourceWrapper dataSourceWrapper, List<TableDrop> tableDrops) {
		// TODO Auto-generated method stub
		
	}
}
