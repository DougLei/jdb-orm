package com.douglei.orm.configuration.ext.configuration;

import java.util.List;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.ext.configuration.datatypehandler.ExtDataTypeHandler;

/**
 * 扩展信息配置接口
 * @author DougLei
 */
public interface ExtConfiguration extends SelfProcessing{
	
	/**
	 * 获取ExtDataTypeHandler集合
	 * @return
	 */
	List<ExtDataTypeHandler> getExtDataTypeHandlerList();
}
