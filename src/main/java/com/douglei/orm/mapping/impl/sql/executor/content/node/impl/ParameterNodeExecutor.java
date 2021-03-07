package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.Map;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.configuration.environment.mapping.SqlMappingConfiguration;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.tools.OgnlUtil;
import com.douglei.tools.datatype.DataTypeValidateUtil;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class ParameterNodeExecutor {
	private ParameterNodeExecutor() {}
	public static final ParameterNodeExecutor SINGLETON = new ParameterNodeExecutor();
	
	/**
	 * 获取参数值
	 * @param parameter
	 * @param sqlParameter
	 * @param alias
	 * @return
	 */
	public Object getValue(ParameterNode parameter, Object sqlParameter, String alias) {
		String name = parameter.getName();
		if(alias != null && name.startsWith(alias+'.'))
			name = name.substring(alias.length()+1);
		
		boolean singleName = name.indexOf(".") == -1; 
		Object value = null;
		
		if(sqlParameter != null) {
			if(sqlParameter instanceof Map<?, ?> && singleName) {
				value = ((Map<?, ?>)sqlParameter).get(name); 
			}else if(DataTypeValidateUtil.isSimpleDataType(sqlParameter)){
				value = sqlParameter;
			}else {
				value = OgnlUtil.getObjectValue(name, sqlParameter);
			}
		}
		
		if(value == null && parameter.getDefaultValue() != null) {
			value = EnvironmentContext.getEnvironment().getMappingHandler().getMappingConfiguration().getSqlMappingConfiguration().getParameterDefaultValueHandler().getDefaultValue(parameter.getDefaultValue());
			
			if(singleName) {
				IntrospectorUtil.setValue(name, value, sqlParameter);
			}else {
				int dot = name.lastIndexOf(".");
				IntrospectorUtil.setValue(name.substring(dot+1), value, OgnlUtil.getObjectValue(name.substring(0, dot), sqlParameter));
			}
		}
		return value;
	}
	
	/**
	 * 替换sql
	 * @param sql
	 * @param parameter
	 * @param sqlParameter
	 * @param previousAlias
	 * @return
	 */
	public String replaceSql(String sql, ParameterNode parameter, Object sqlParameter, String alias) {
		SqlMappingConfiguration config = EnvironmentContext.getEnvironment().getMappingHandler().getMappingConfiguration().getSqlMappingConfiguration();
		return sql.replaceAll(
				config.getParameterPrefix() + parameter.getName() + config.getParameterSuffix(), 
				parameter.getPrefix() + getValue(parameter, sqlParameter, alias) + parameter.getSuffix());
	}
}
