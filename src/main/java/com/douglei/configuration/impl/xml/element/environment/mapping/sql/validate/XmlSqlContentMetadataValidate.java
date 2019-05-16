package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import org.dom4j.Element;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlContentMetadata;

/**
 * 
 * @author DougLei
 */
public class XmlSqlContentMetadataValidate implements MetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Element)obj);
	}

	private SqlContentMetadata doValidate(Element element) {
		// TODO
		
		return null;
	}
}
