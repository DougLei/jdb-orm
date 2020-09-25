package com.douglei.orm.core.metadata.view;

import com.douglei.orm.core.metadata.AbstractMetadata;
import com.douglei.orm.core.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata implements Metadata{
	private static final long serialVersionUID = -3153924208599743639L;
	private String content; // 具体的内容
	
	public ViewMetadata(String name, String oldName, String content) {
		super(name, oldName);
		this.content = content;
	}
	
	@Override
	public String getCode() {
		return name;	}

	public String getContent() {
		return content;
	}
}
