package com.douglei.database.dialect.datatype.classtype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerException;
import com.douglei.database.dialect.datatype.classtype.impl.BlobDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.ClobDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.DateDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.DoubleDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.IntegerDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class ClassDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(ClassDataTypeHandlerMapping.class);
	
	private final Map<String, ClassDataTypeHandler> CODE_DATATYPE_HANDLER_MAP = new HashMap<String, ClassDataTypeHandler>(16);
	private final Map<Class<?>, ClassDataTypeHandler> SUPPORTCLASS_DATATYPE_HANDLER_MAP = new HashMap<Class<?>, ClassDataTypeHandler>(16);
	
	private static final StringDataTypeHandler string_ = new StringDataTypeHandler();
	private static final IntegerDataTypeHandler integer_ = new IntegerDataTypeHandler();
	private static final DoubleDataTypeHandler double_ = new DoubleDataTypeHandler();
	private static final DateDataTypeHandler date_ = new DateDataTypeHandler();
	private static final ClobDataTypeHandler clob_ = new ClobDataTypeHandler();
	private static final BlobDataTypeHandler blob_ = new BlobDataTypeHandler();
	
	public ClassDataTypeHandlerMapping() {
		register(string_);
		register(integer_);
		register(double_);
		register(date_);
		register(clob_);
		register(blob_);
	}
	
	public void register(ClassDataTypeHandler classDataTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("register {}", classDataTypeHandler.toString());
		}
		CODE_DATATYPE_HANDLER_MAP.put(classDataTypeHandler.getCode(), classDataTypeHandler);
		
		Class<?>[] supportClasses = classDataTypeHandler.supportClasses();
		if(supportClasses != null && supportClasses.length > 0) {
			for (Class<?> sc : supportClasses) {
				SUPPORTCLASS_DATATYPE_HANDLER_MAP.put(sc, classDataTypeHandler);
			}
		}
	}
	
	/**
	 * 根据code, 获取DataTypeHandler实例
	 * @param code
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByClassType(String code) {
		ClassDataTypeHandler dataTypeHandler = CODE_DATATYPE_HANDLER_MAP.get(code);
		if(dataTypeHandler == null) {
			logger.debug("没有获取到code=[{}]的DataTypeHandler实例, 尝试加载该自定义ClassDataTypeHandler实现子类", code);
			Object obj = ConstructorUtil.newInstance(code);
			if(!(obj instanceof ClassDataTypeHandler)) {
				throw new ClassCastException("code=["+code+"]的类必须继承["+ClassDataTypeHandler.class.getName()+"]");
			}
			dataTypeHandler = (ClassDataTypeHandler) obj;
			dynamicRegister(dataTypeHandler);
		}
		return dataTypeHandler;
	}
	private void dynamicRegister(ClassDataTypeHandler classDataTypeHandler) {
		String code = classDataTypeHandler.getCode();
		if(CODE_DATATYPE_HANDLER_MAP.containsKey(code)) {
			throw new RepeatedDataTypeHandlerCodeException("[dynamicRegister] 已经存在code="+code+" 的ClassDataTypeHandler实例, " + CODE_DATATYPE_HANDLER_MAP.get(code).toString());
		}
		register(classDataTypeHandler);
	}
	
	/**
	 * 根据value的classType, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	public DataTypeHandler getDataTypeHandlerByClassType(Object value) {
		ClassDataTypeHandler dataTypeHandler = SUPPORTCLASS_DATATYPE_HANDLER_MAP.get(value.getClass());
		if(dataTypeHandler == null) {
			throw new UnsupportDataTypeHandlerException("目前无法处理class=["+value.getClass().getName()+"]的数据类型");
		}
		return dataTypeHandler;
	}
}
