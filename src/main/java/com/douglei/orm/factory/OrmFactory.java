package com.douglei.orm.factory;

import com.douglei.orm.factory.data.validator.DataValidatorHandler;
import com.douglei.orm.factory.dynamic.mapping.DynamicMappingHandler;
import com.douglei.orm.factory.sessionfactory.SessionFactory;

/**
 * 
 * @author DougLei
 */
public interface OrmFactory {
	
	/**
	 * 
	 * @return
	 */
	SessionFactory getSessionFactory();
	
	/**
	 * 
	 * @return
	 */
	DynamicMappingHandler getDynamicMappingHandler();
	
	/**
	 * 
	 * @return
	 */
	DataValidatorHandler getDataValidatorHandler();
}
