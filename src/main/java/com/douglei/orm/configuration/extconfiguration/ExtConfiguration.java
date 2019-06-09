package com.douglei.orm.configuration.extconfiguration;

import java.util.List;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;

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
