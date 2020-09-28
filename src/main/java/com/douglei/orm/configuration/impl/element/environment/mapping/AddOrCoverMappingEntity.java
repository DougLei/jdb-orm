package com.douglei.orm.configuration.impl.element.environment.mapping;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingOP;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.ParseMappingException;
import com.douglei.orm.configuration.impl.element.environment.mapping.procedure.ProcedureMappingImpl;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.SqlMappingImpl;
import com.douglei.orm.configuration.impl.element.environment.mapping.table.TableMappingImpl;
import com.douglei.orm.configuration.impl.element.environment.mapping.view.ViewMappingImpl;
import com.douglei.tools.instances.scanner.impl.ResourceScanner;
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
	public AddOrCoverMappingEntity(String content, MappingType type) {
		this(content, type, true);
	}
	public AddOrCoverMappingEntity(String filepath, boolean opStruct) {
		this.filepath = filepath;
		super.type = MappingType.toValueByFile(filepath);
		super.opStruct = opStruct;
	}
	public AddOrCoverMappingEntity(String content, MappingType type, boolean opStruct) {
		this.content = content;
		super.type = type;
		super.opStruct = opStruct;
	}
	
	@Override
	public boolean parseMapping() throws ParseMappingException {
		logger.debug("开始解析{}类型的映射xml", type.getName());
		InputStream input = filepath != null ? ResourceScanner.readByScanPath(filepath) : new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		try {
			if(type == MappingType.SQL) {
				org.w3c.dom.Document sqlDocument = MappingResolverContext.getDocumentBuilder().parse(input);
				org.w3c.dom.Element sqlRootElement = sqlDocument.getDocumentElement();
				super.mapping = new SqlMappingImpl(sqlRootElement);
			} else {
				org.dom4j.Document document = MappingResolverContext.getSAXReader().read(input);
				org.dom4j.Element rootElement = document.getRootElement();
				
				if(type == MappingType.TABLE) {
					super.mapping = new TableMappingImpl(rootElement);
				}else if(type == MappingType.VIEW) {
					super.mapping = new ViewMappingImpl(rootElement);
				}else if(type == MappingType.PROCEDURE) {
					super.mapping = new ProcedureMappingImpl(rootElement);
				}
			}
			super.code = super.mapping.getCode();
			logger.debug("结束解析{}类型的映射xml", type.getName());
			return true;
		} catch(Exception e){
			logger.error("解析映射xml[{}]时, 出现异常: {}", (filepath != null ? filepath : content), ExceptionUtil.getExceptionDetailMessage(e));
			throw new ParseMappingException("在解析映射xml时, 出现异常", e);
		}finally {
			CloseUtil.closeIO(input);
		}
	}
	
	@Override
	public MappingOP getOp() {
		return MappingOP.ADD_OR_COVER;
	}
}
