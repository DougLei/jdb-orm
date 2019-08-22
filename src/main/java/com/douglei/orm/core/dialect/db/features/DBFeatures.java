package com.douglei.orm.core.dialect.db.features;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;

/**
 * 数据库特性
 * @author DougLei
 */
public abstract class DBFeatures {
	
	/**
	 * <pre>
	 * 	支持存储过程直接返回 ResultSet
	 * 	即存储过程中编写 select语句, 执行该存储过程后, 会展示出该select结果集, 例如sqlserver数据库, mysql数据库
	 * 	像oracle数据库, 是必须通过输出参数才能返回结果集(cursor类型)
	 * 	默认是true
	 * </pre>
	 * @return
	 */
	public boolean supportProcedureDirectlyReturnResultSet() {
		return true;
	}

	
	/**
	 * 支持列类型转换的集合
	 */
	protected Map<DBDataType, DBDataType[]> supportColumnDBDataTypeConvertMap;
	
	/**
	 * 初始化支持列类型转换的集合
	 * 默认实现为空
	 */
	protected void initSupportColumnDBDataTypeConvertMap() {
		supportColumnDBDataTypeConvertMap = new HashMap<DBDataType, DBDataType[]>(1);
	}
	
	/**
	 * 是否支持改变列的数据类型
	 * 
	 * 从originDBDataType转换为targetDBDataType
	 * 
	 * @param originDBDataType
	 * @param targetDBDataType
	 * @return
	 */
	public boolean supportColumnDataTypeConvert(DBDataType originDBDataType, DBDataType targetDBDataType) {
		if(DBRunEnvironmentContext.getEnvironmentProperty().enableColumnDynamicUpdateValidation()) {
			if(supportColumnDBDataTypeConvertMap == null) {
				initSupportColumnDBDataTypeConvertMap();
			}
			if(supportColumnDBDataTypeConvertMap.size() > 0) {
				DBDataType[] supportTargetDBDataTypes = supportColumnDBDataTypeConvertMap.get(originDBDataType);
				if(supportTargetDBDataTypes != null && supportTargetDBDataTypes.length > 0) {
					for (DBDataType stdt : supportTargetDBDataTypes) {
						if(stdt.equalsTypeName(targetDBDataType)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
