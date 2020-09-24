package com.douglei.orm.core.mapping.struct.proc;

import java.sql.SQLException;

import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.StructHandler;
import com.douglei.orm.core.metadata.proc.ProcMetadata;

/**
 * 存储过程结构处理器
 * @author DougLei
 */
public class ProcStructHandler extends StructHandler<ProcMetadata, String>{

	public ProcStructHandler(DBConnection connection) {
		super(connection);
	}

	@Override
	public void create(ProcMetadata metadata) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String name) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
