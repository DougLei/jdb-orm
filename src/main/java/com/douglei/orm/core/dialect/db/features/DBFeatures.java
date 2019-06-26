package com.douglei.orm.core.dialect.db.features;

import java.util.Map;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.DataType;

/**
 * 数据库特性
 * @author DougLei
 */
public abstract class DBFeatures {
	
	/**
	 * <pre>
	 * 	存储过程支持直接返回 ResultSet
	 * 	即存储过程中编写 select语句, 执行该存储过程后, 会展示出该select结果集, 例如sqlserver数据库, mysql数据库
	 * 	像oracle数据库, 是必须通过输出参数才能返回结果集(cursor类型)
	 * </pre>
	 * @return
	 */
	public abstract boolean procedureSupportDirectlyReturnResultSet();

	
	protected Map<DataType, DataType[]> supportColumnDataTypeConvertMap;
	protected abstract void initSupportColumnDataTypeConvertMap();
	
	/**
	 * 是否支持改变列的数据类型
	 * 
	 * 从originDataType转换为targetDataType
	 * 
	 * @param originDataType
	 * @param targetDataType
	 * @return
	 */
	public boolean supportColumnDataTypeConvert(DataType originDataType, DataType targetDataType) {
		if(DBRunEnvironmentContext.getEnableColumnDynamicUpdateValidation()) {
			if(supportColumnDataTypeConvertMap == null) {
				initSupportColumnDataTypeConvertMap();
			}
			DataType[] supportTargetDataTypes = supportColumnDataTypeConvertMap.get(originDataType);
			if(supportTargetDataTypes != null && supportTargetDataTypes.length > 0) {
				for (DataType tdt : supportTargetDataTypes) {
					if(tdt == targetDataType) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
