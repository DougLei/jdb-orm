package com.douglei.orm.mapping.handler.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeContainer;
import com.douglei.tools.ExceptionUtil;
import com.douglei.tools.file.scanner.impl.ResourceScanner;

/**
 * 
 * @author DougLei
 */
public class AddOrCoverMappingEntity extends MappingEntity {
	private static final Logger logger = LoggerFactory.getLogger(AddOrCoverMappingEntity.class);

	private String filepath; // 从文件获取映射(基于java resource)
	private String content; // 从内容获取映射
	private MappingProperty mappingProperty; // 映射属性实例
	private Mapping mapping; // 映射实例
	
	/**
	 * 
	 * @param filepath 映射的配置资源文件路径(基于java resource)
	 */
	public AddOrCoverMappingEntity(String filepath) {
		this(filepath, true);
	}
	/**
	 * 
	 * @param filepath 映射的配置资源文件路径(基于java resource)
	 * @param opDatabaseObject 是否操作数据库对象
	 */
	public AddOrCoverMappingEntity(String filepath, boolean opDatabaseObject) {
		this.filepath = filepath;
		super.type = MappingTypeContainer.getMappingTypeByFile(filepath);
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 
	 * @param content 映射的配置内容
	 * @param type 映射类型
	 */
	public AddOrCoverMappingEntity(String content, String type) {
		this(content, type, true);
	}
	/**
	 * 
	 * @param content 映射的配置内容
	 * @param type 映射类型
	 * @param opDatabaseObject 是否操作数据库对象
	 */
	public AddOrCoverMappingEntity(String content, String type, boolean opDatabaseObject) {
		this.content = content;
		super.type = MappingTypeContainer.getMappingTypeByName(type);
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 解析mapping
	 * @throws ParseMappingException
	 */
	public void parseMapping() throws ParseMappingException {
		logger.debug("开始解析{}类型的映射", type.getName());
		try (InputStream input = (filepath!=null)?ResourceScanner.read(filepath):new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))){
			MappingSubject ms = super.type.parse(this, input);
			this.mappingProperty = ms.getMappingProperty();
			this.mapping = ms.getMapping();
			logger.debug("结束解析{}类型的映射", type.getName());
		} catch(Exception e){
			logger.error("解析映射[{}]时, 出现异常: {}", (filepath!=null?filepath:content), ExceptionUtil.getStackTrace(e));
			throw new ParseMappingException("在解析映射时, 出现异常", e);
		}
	}
	
	@Override
	public Mode getMode() {
		return Mode.ADD_OR_COVER;
	}
	
	@Override
	public String getCode() {
		return mapping.getCode();
	}
	
	@Override
	public int getOrder() {
		return mappingProperty.getOrder();
	}
	
	/**
	 * 获取映射属性实例
	 * @return
	 */
	public MappingProperty getMappingProperty() {
		return mappingProperty;
	}
	
	/**
	 * 获取映射实例
	 * @return
	 */
	public Mapping getMapping() {
		return mapping;
	}
	
	@Override
	public String toString() {
		if(filepath != null)
			return "AddOrCoverMappingEntity [filepath="+filepath+", opDatabaseObject="+opDatabaseObject+"]";
		return "AddOrCoverMappingEntity [content="+content+", type="+type+", opDatabaseObject="+opDatabaseObject+"]";
	}
}