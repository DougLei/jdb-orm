package com.douglei.orm.factory.dynamic.mapping;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public interface DynamicMappingHandler {
	
	/**
	 * <pre>
	 * 	动态添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param entity
	 */
	void dynamicAddMapping(DynamicMapping entity);
	/**
	 * <pre>
	 * 	动态批量添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param entities
	 */
	void dynamicBatchAddMapping(List<DynamicMapping> entities);
	
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param entity
	 */
	void dynamicCoverMapping(DynamicMapping entity);
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param entities
	 */
	void dynamicBatchCoverMapping(List<DynamicMapping> entities);
	
	/**
	 * <pre>
	 * 	动态删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCode
	 */
	void dynamicRemoveMapping(String mappingCode);
	/**
	 * <pre>
	 * 	动态批量删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCodes
	 */
	void dynamicBatchRemoveMapping(List<String> mappingCodes);
}
