package com.douglei.orm.core.mapping.struct.procedure;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.mapping.rollback.RollbackExecMethod;
import com.douglei.orm.core.mapping.rollback.RollbackRecorder;
import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.StructHandler;
import com.douglei.orm.core.metadata.procedure.ProcedureMetadata;

/**
 * 存储过程结构处理器
 * @author DougLei
 */
public class ProcedureStructHandler extends StructHandler<ProcedureMetadata, String>{
	private static final Logger logger = LoggerFactory.getLogger(ProcedureStructHandler.class);

	public ProcedureStructHandler(DBConnection connection) {
		super(connection);
	}

	@Override
	public void create(ProcedureMetadata procedureMetadata) throws Exception {
		delete(procedureMetadata.getOldName()); // 不论怎样, 都先删除旧的
		validateNameExists(procedureMetadata); // 验证下新的名称是否可以使用
		
		connection.executeSql(procedureMetadata.getScript());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.dropProc(procedureMetadata.getName()), connection);
		
		serializationHandler.createFileDirectly(procedureMetadata, ProcedureMetadata.class);
	}

	@Override
	public void delete(String procName) throws SQLException {
		ProcedureMetadata deleted = (ProcedureMetadata) serializationHandler.deleteFile(procName, ProcedureMetadata.class);
		if(!connection.procExists(procName))
			return;
		
		String script; 
		if(deleted == null) {
			logger.info("不存在name为{}的存储过程序列化文件, 去数据库中查找视图内容", procName);
			script = connection.queryProcContent(procName);
		}else {
			script = deleted.getScript();
		}
		
		connection.executeSql(sqlStatementHandler.dropProc(procName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, script, connection);
	}
}
