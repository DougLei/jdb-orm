package com.douglei.sessions.session.persistent;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.sessions.session.persistent.table.TablePersistentObject;

/**
 * 
 * @author DougLei
 */
public class PersistentFactory {
	
	public static final PersistentObject buildPersistent(Metadata metadata, Object propertyObject) {
		switch(metadata.getMetadataType()) {
			case TABLE:
				return new TablePersistentObject((TableMetadata)metadata, propertyObject);
			case SQL:
				return null;
			default:
				return null;
		}
	}
}
