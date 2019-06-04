package com.douglei.configuration;

/**
 * 自处理接口
 * @author DougLei
 */
public interface SelfProcessing {
	
	/**
	 * 进行销毁操作
	 * @throws DestroyException
	 */
	void destroy() throws DestroyException;
	
	/**
	 * 自检
	 * @throws SelfCheckingException
	 */
	void selfChecking() throws SelfCheckingException;
}
