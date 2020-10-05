package com.douglei.orm.mapping.handler.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.type.MappingTypeHandler;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;
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
	
	public AddOrCoverMappingEntity(String filepath) {
		this(filepath, true);
	}
	public AddOrCoverMappingEntity(String filepath, boolean opDatabaseStruct) {
		this.filepath = filepath;
		super.type = MappingTypeHandler.getMappingTypeByFile(filepath);
		super.opDatabaseStruct = opDatabaseStruct;
	}
	
	/**
	 * 
	 * @param content
	 * @param typeName 通过 {@link MappingTypeNameConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 */
	public AddOrCoverMappingEntity(String content, String typeName) {
		this(content, typeName, true);
	}
	/**
	 * 
	 * @param content
	 * @param typeName 通过 {@link MappingTypeNameConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 * @param opDatabaseStruct
	 */
	public AddOrCoverMappingEntity(String content, String typeName, boolean opDatabaseStruct) {
		this.content = content;
		super.type = MappingTypeHandler.getMappingTypeByName(typeName);
		super.opDatabaseStruct = opDatabaseStruct;
	}
	
	@Override
	public boolean parseMapping() throws ParseMappingException {
		logger.debug("开始解析{}类型的映射", type.getName());
		InputStream input = filepath != null ? ResourceScanner.readByScanPath(filepath) : new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		try {
			super.mapping = type.parse(input);
			super.code = super.mapping.getCode();
			logger.debug("结束解析{}类型, code={}的映射", type.getName(), super.code);
			return true;
		} catch(Exception e){
			logger.error("解析映射[{}]时, 出现异常: {}", (filepath != null ? filepath : content), ExceptionUtil.getExceptionDetailMessage(e));
			throw new ParseMappingException("在解析映射时, 出现异常", e);
		}finally {
			CloseUtil.closeIO(input);
		}
	}
	
	@Override
	public MappingOP getOp() {
		return MappingOP.ADD_OR_COVER;
	}
}
