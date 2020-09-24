package com.douglei.orm.core.mapping.struct.view;

import java.sql.SQLException;

import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.StructHandler;
import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 视图结构处理器
 * @author DougLei
 */
public class ViewStructHandler extends StructHandler<ViewMetadata, String>{

	public ViewStructHandler(DBConnection connection) {
		super(connection);
	}

	@Override
	public void create(ViewMetadata metadata) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String name) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
