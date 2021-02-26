package com.douglei.orm.mapping.handler.entity.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingOP;
import com.douglei.orm.mapping.handler.entity.ParseMappingException;
import com.douglei.orm.metadata.type.MetadataTypeContainer;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;
import com.douglei.tools.CloseUtil;
import com.douglei.tools.ExceptionUtil;
import com.douglei.tools.file.scanner.impl.ResourceScanner;

/**
 * 
 * @author DougLei
 */
public class AddOrCoverMappingEntity extends MappingEntity {
	private static final Logger logger = LoggerFactory.getLogger(AddOrCoverMappingEntity.class);

	private String filepath; // 从文件获取映射
	private String content; // 从内容获取映射
	
	public AddOrCoverMappingEntity(String filepath) {
		this(filepath, true);
	}
	public AddOrCoverMappingEntity(String filepath, boolean opDatabaseObject) {
		this.filepath = filepath;
		super.type = MetadataTypeContainer.getMappingTypeByFile(filepath);
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 
	 * @param content
	 * @param type 通过 {@link MetadataTypeNameConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 */
	public AddOrCoverMappingEntity(String content, String type) {
		this(content, type, true);
	}
	/**
	 * 
	 * @param content
	 * @param type 通过 {@link MetadataTypeNameConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 * @param opDatabaseObject
	 */
	public AddOrCoverMappingEntity(String content, String type, boolean opDatabaseObject) {
		this.content = content;
		super.type = MetadataTypeContainer.getMappingTypeByName(type);
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 解析mapping
	 */
	public void parseMapping() throws ParseMappingException {
		logger.debug("开始解析{}类型的映射", super.type.getName());
		try (InputStream input = filepath != null ? ResourceScanner.readByScanPath(filepath) : new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))){
			super.mapping = super.type.parse(input);
			super.code = super.mapping.getCode();
			super.order = super.mapping.getProperty().getOrder();
			logger.debug("结束解析{}类型, code={}的映射", super.type.getName(), super.code);
		} catch(Exception e){
			logger.error("解析映射[{}]时, 出现异常: {}", (filepath != null ? filepath : content), ExceptionUtil.getExceptionDetailMessage(e));
			throw new ParseMappingException("在解析映射时, 出现异常", e);
		}
	}
	
	@Override
	public MappingOP getOp() {
		return MappingOP.ADD_OR_COVER;
	}
}
