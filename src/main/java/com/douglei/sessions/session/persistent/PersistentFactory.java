package com.douglei.sessions.session.persistent;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.table.TableMetadata;

/**
 * 
 * @author DougLei
 */
public class PersistentFactory {
	
	public static final PersistentObject buildPersistent(Metadata metadata, Object propertyObject) {
		switch(metadata.getMetadataType()) {
			case TABLE:
				return new PersistentTable((TableMetadata)metadata, propertyObject);
			case SQL:
				return null;
			default:
				return null;
		}
	}
}
