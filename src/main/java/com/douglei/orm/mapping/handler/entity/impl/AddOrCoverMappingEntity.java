package com.douglei.orm.mapping.handler.entity.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.MappingFeature;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingOP;
import com.douglei.orm.mapping.handler.entity.ParseMappingException;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.mapping.type.MappingTypeContainer;
import com.douglei.tools.instances.resource.scanner.impl.ResourceScanner;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class AddOrCoverMappingEntity extends MappingEntity {
	private static final Logger logger = LoggerFactory.getLogger(AddOrCoverMappingEntity.class);

	private String filepath; // 从文件获取映射
	private String content; // 从内容获取映射
	private MappingFeature feature = new MappingFeature(); // 映射的特性
	
	public AddOrCoverMappingEntity(String filepath) {
		this(filepath, true);
	}
	public AddOrCoverMappingEntity(String filepath, boolean opDatabaseObject) {
		this.filepath = filepath;
		super.type = MappingTypeContainer.getMappingTypeByFile(filepath);
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 
	 * @param content
	 * @param type 通过 {@link MappingTypeConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 */
	public AddOrCoverMappingEntity(String content, String type) {
		this(content, type, true);
	}
	/**
	 * 
	 * @param content
	 * @param type 通过 {@link MappingTypeConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 * @param opDatabaseObject
	 */
	public AddOrCoverMappingEntity(String content, String type, boolean opDatabaseObject) {
		this.content = content;
		super.type = MappingTypeContainer.getMappingTypeByName(type);
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 设置mapping是否支持被使用
	 * @param supportUsed
	 * @return
	 */
	public AddOrCoverMappingEntity setSupportUsed(boolean supportUsed) {
		feature.setSupportUsed(supportUsed);
		return this;
	}
	/**
	 * 设置mapping是否支持被覆盖
	 * @param supportCover
	 * @return
	 */
	public AddOrCoverMappingEntity setSupportCover(boolean supportCover) {
		feature.setSupportCover(supportCover);
		return this;
	}
	/**
	 * 设置mapping是否支持被删除
	 * @param supportDelete
	 * @return
	 */
	public AddOrCoverMappingEntity setSupportDelete(boolean supportDelete) {
		feature.setSupportDelete(supportDelete);
		return this;
	}
	/**
	 * 设置mapping的扩展特性, 可由第三方扩展
	 * @param extend
	 * @return
	 */
	public AddOrCoverMappingEntity setExtend(Object extend) {
		feature.setExtend(extend);
		return this;
	}
	
	/**
	 * 解析mapping
	 */
	public void parseMapping() throws ParseMappingException {
		logger.debug("开始解析{}类型的映射", super.type.getName());
		InputStream input = filepath != null ? ResourceScanner.readByScanPath(filepath) : new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		try {
			super.mapping = super.type.parse(input);
			super.code = super.mapping.getCode();
			feature.setCode(super.code);
			feature.setType(super.type.getName());
			logger.debug("结束解析{}类型, code={}的映射", super.type.getName(), super.code);
		} catch(Exception e){
			logger.error("解析映射[{}]时, 出现异常: {}", (filepath != null ? filepath : content), ExceptionUtil.getExceptionDetailMessage(e));
			throw new ParseMappingException("在解析映射时, 出现异常", e);
		}finally {
			CloseUtil.closeIO(input);
		}
	}
	
	public MappingFeature getFeature() {
		return feature;
	}
	
	@Override
	public MappingOP getOp() {
		return MappingOP.ADD_OR_COVER;
	}
}
