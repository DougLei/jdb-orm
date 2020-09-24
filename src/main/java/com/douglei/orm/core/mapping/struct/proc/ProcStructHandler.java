package com.douglei.orm.core.mapping.struct.proc;

import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.view.ViewStructHandler;

/**
 * 存储过程结构处理器
 * @author DougLei
 */
public class ProcStructHandler extends ViewStructHandler{

	public ProcStructHandler(DBConnection connection) {
		super(connection);
	}
}
