package com.douglei.orm.mapping;

import java.io.InputStream;

/**
 * 
 * @author DougLei
 */
public abstract class MappingParser<T extends Mapping> {
	
	/**
	 * 解析出mapping实例
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract T parse(InputStream input) throws Exception;
}
