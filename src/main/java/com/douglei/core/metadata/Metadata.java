package com.douglei.core.metadata;

/**
 * 
 * @author DougLei
 */
public interface Metadata {
	
	/**
	 * 唯一编码值
	 * @return
	 */
	String getCode();
	
	MetadataType getMetadataType();
}
