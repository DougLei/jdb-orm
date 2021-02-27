package com.douglei.orm.mapping;

/**
 * 
 * @author DougLei
 */
public class MappingParseToolContext {
	private static final ThreadLocal<MappingParseTool> MAPPING_PARSE_TOOL_CONTEXT = new ThreadLocal<MappingParseTool>();
	
	/**
	 * 获取映射解析工具
	 * @return
	 */
	public static MappingParseTool getMappingParseTool() {
		MappingParseTool tool = MAPPING_PARSE_TOOL_CONTEXT.get();
		if(tool == null) {
			tool = new MappingParseTool();
			MAPPING_PARSE_TOOL_CONTEXT.set(tool);
		}
		return tool;
	}
	
	/**
	 * 销毁映射解析工具
	 */
	public static void destroy() {
		MAPPING_PARSE_TOOL_CONTEXT.remove();
	}
}