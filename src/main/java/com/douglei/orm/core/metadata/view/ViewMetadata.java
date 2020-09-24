package com.douglei.orm.core.metadata.view;

import com.douglei.orm.core.metadata.AbstractMetadata;
import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata implements Metadata{
	protected String content; // 具体的内容
	
	public ViewMetadata(String name, String oldName, CreateMode createMode, String content) {
		super(name, oldName, createMode);
		this.content = content;
	}
	
	@Override
	public String getCode() {
		return name;	}
}
