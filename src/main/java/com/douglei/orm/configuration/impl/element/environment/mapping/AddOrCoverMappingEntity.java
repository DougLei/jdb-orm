package com.douglei.orm.configuration.impl.element.environment.mapping;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingOP;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.ParseMappingException;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.SqlMappingImpl;
import com.douglei.orm.configuration.impl.element.environment.mapping.table.TableMappingImpl;
import com.douglei.tools.instances.scanner.FileScanner;
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
	
	public AddOrCoverMappingEntity(String filepath) throws ParseMappingException {
		this(filepath, true);
	}
	public AddOrCoverMappingEntity(String content, MappingType type) throws ParseMappingException {
		this(content, type, true);
	}
	public AddOrCoverMappingEntity(String filepath, boolean opStruct) throws ParseMappingException {
		this.filepath = filepath;
		super.type = MappingType.toValueByFile(filepath);
		super.opStruct = opStruct;
	}
	public AddOrCoverMappingEntity(String content, MappingType type, boolean opStruct) throws ParseMappingException {
		this.content = content;
		super.type = type;
		super.opStruct = opStruct;
	}
	
	@Override
	public Mapping parseMapping() throws ParseMappingException {
		String configDescription = filepath != null ? filepath : content;
		InputStream input = filepath != null ? FileScanner.readByScanPath(filepath) : new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		try {
			switch(type) {
				case TABLE:
					org.dom4j.Document tableDocument = MappingResolverContext.getTableMappingReader().read(input);
					org.dom4j.Element tableRootElement = tableDocument.getRootElement();
					super.mapping = new TableMappingImpl(configDescription, tableRootElement);
					break;
				case SQL:
					org.w3c.dom.Document sqlDocument = MappingResolverContext.getSqlMappingReader().parse(input);
					org.w3c.dom.Element sqlRootElement = sqlDocument.getDocumentElement();
					super.mapping = new SqlMappingImpl(configDescription, sqlRootElement);
					break;
			}
			super.code = super.mapping.getCode();
			return super.mapping;
		} catch(Exception e){
			logger.error("在解析映射xml[{}]时, 出现异常:{}", configDescription, ExceptionUtil.getExceptionDetailMessage(e));
			throw new ParseMappingException("在解析映射xml["+configDescription+"]时, 出现异常", e);
		}finally {
			CloseUtil.closeIO(input);
		}
	}
	
	@Override
	public void setMapping(Mapping mapping) { // 解析获取即可, 不需要外部设置, 所以不实现该方法
	}
	
	@Override
	public MappingOP getOp() {
		return MappingOP.ADD_OR_COVER;
	}
}
