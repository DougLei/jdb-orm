package com.douglei.orm.core.metadata.view;

import org.apache.commons.codec.digest.DigestUtils;

import com.douglei.orm.core.metadata.AbstractMetadata;
import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata implements Metadata{
	private String content; // 具体的内容
	private String signature; // 内容签名
	
	public ViewMetadata(String name, String oldName, CreateMode createMode, String content) {
		super(name, oldName, createMode);
		this.content = content;
		this.signature = DigestUtils.md5Hex(content);
	}
	
	@Override
	public String getCode() {
		return name;	}

	public String getContent() {
		return content;
	}
	public String getSignature() {
		return signature;
	}
}
