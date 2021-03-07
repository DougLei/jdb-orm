package com.douglei.orm.mapping.impl.sql;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.ElseSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.ForeachSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.IfSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.IncludeSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.SetSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.SwitchSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.TextSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.TrimSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.WhereSqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.ElseSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.ForeachSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.IfSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.IncludeSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.SetSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.SwitchSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.TextSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.TrimSqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.impl.WhereSqlNodeParser;

/**
 * 
 * @author DougLei
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SqlNodeContainer {
	private static final Map<String, SqlNodeParser> PARSER_CONTAINER = new HashMap<String, SqlNodeParser>();
	private static final Map<String, SqlNodeExecutor> EXECUTOR_CONTAINER = new HashMap<String, SqlNodeExecutor>();
	
	static {
		// parser
		ElseSqlNodeParser elseSqlNodeParser = new ElseSqlNodeParser();
		PARSER_CONTAINER.put(elseSqlNodeParser.getType().getName(), elseSqlNodeParser);
		
		ForeachSqlNodeParser foreachSqlNodeParser = new ForeachSqlNodeParser();
		PARSER_CONTAINER.put(foreachSqlNodeParser.getType().getName(), foreachSqlNodeParser);
		
		IfSqlNodeParser ifSqlNodeParser = new IfSqlNodeParser();
		PARSER_CONTAINER.put(ifSqlNodeParser.getType().getName(), ifSqlNodeParser);
		
		IncludeSqlNodeParser includeSqlNodeParser = new IncludeSqlNodeParser();
		PARSER_CONTAINER.put(includeSqlNodeParser.getType().getName(), includeSqlNodeParser);
		
		SetSqlNodeParser setSqlNodeParser = new SetSqlNodeParser();
		PARSER_CONTAINER.put(setSqlNodeParser.getType().getName(), setSqlNodeParser);
		
		SwitchSqlNodeParser switchSqlNodeParser = new SwitchSqlNodeParser();
		PARSER_CONTAINER.put(switchSqlNodeParser.getType().getName(), switchSqlNodeParser);
		
		TextSqlNodeParser textSqlNodeParser = new TextSqlNodeParser();
		PARSER_CONTAINER.put(textSqlNodeParser.getType().getName(), textSqlNodeParser);
		
		TrimSqlNodeParser trimSqlNodeParser = new TrimSqlNodeParser();
		PARSER_CONTAINER.put(trimSqlNodeParser.getType().getName(), trimSqlNodeParser);
		
		WhereSqlNodeParser whereSqlNodeParser = new WhereSqlNodeParser();
		PARSER_CONTAINER.put(whereSqlNodeParser.getType().getName(), whereSqlNodeParser);
		
		// executor
		ElseSqlNodeExecutor elseSqlNodeExecutor = new ElseSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(elseSqlNodeExecutor.getType().getName(), elseSqlNodeExecutor);
		
		ForeachSqlNodeExecutor foreachSqlNodeExecutor = new ForeachSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(foreachSqlNodeExecutor.getType().getName(), foreachSqlNodeExecutor);
		
		IfSqlNodeExecutor ifSqlNodeExecutor = new IfSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(ifSqlNodeExecutor.getType().getName(), ifSqlNodeExecutor);
		
		IncludeSqlNodeExecutor includeSqlNodeExecutor = new IncludeSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(includeSqlNodeExecutor.getType().getName(), includeSqlNodeExecutor);
		
		SetSqlNodeExecutor setSqlNodeExecutor = new SetSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(setSqlNodeExecutor.getType().getName(), setSqlNodeExecutor);
		
		SwitchSqlNodeExecutor switchSqlNodeExecutor = new SwitchSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(switchSqlNodeExecutor.getType().getName(), switchSqlNodeExecutor);
		
		TextSqlNodeExecutor textSqlNodeExecutor = new TextSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(textSqlNodeExecutor.getType().getName(), textSqlNodeExecutor);
		
		TrimSqlNodeExecutor trimSqlNodeExecutor = new TrimSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(trimSqlNodeExecutor.getType().getName(), trimSqlNodeExecutor);
		
		WhereSqlNodeExecutor whereSqlNodeExecutor = new WhereSqlNodeExecutor();
		EXECUTOR_CONTAINER.put(whereSqlNodeExecutor.getType().getName(), whereSqlNodeExecutor);
	}
	
	/**
	 * 解析SqlNode
	 * @param node
	 * @return
	 */
	public static SqlNode parse(Node node) {
		if(node.getNodeType() == Node.COMMENT_NODE)
			return null;
		return (SqlNode)  PARSER_CONTAINER.get(node.getNodeName()).parse(node);
	}

	/**
	 * 获取SqlNode执行器
	 * @param sqlNode
	 * @return
	 */
	public static SqlNodeExecutor<SqlNode> getExecutor(SqlNode sqlNode) {
		return EXECUTOR_CONTAINER.get(sqlNode.getType().getName());
	}
}
