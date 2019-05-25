package com.douglei.database.dialect.datatype.classtype;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.RepeatedDataTypeHandlerException;
import com.douglei.database.dialect.datatype.UnsupportDataTypeHandlerException;
import com.douglei.database.dialect.datatype.classtype.impl.BlobDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.ClobDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.DateDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.DoubleDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.IntegerDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.LongDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.ShortDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class ClassDataTypeHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(ClassDataTypeHandlerMapping.class);
	
	private final Map<String, ClassDataTypeHandler> CODE_DATATYPE_HANDLER_MAP = new HashMap<String, ClassDataTypeHandler>(10);
	private final Map<Class<?>, ClassDataTypeHandler> SUPPORTCLASS_DATATYPE_HANDLER_MAP = new HashMap<Class<?>, ClassDataTypeHandler>(10);
	
	public ClassDataTypeHandlerMapping() {
		register(StringDataTypeHandler.singleInstance());
		register(IntegerDataTypeHandler.singleInstance());
		register(LongDataTypeHandler.singleInstance());
		register(ShortDataTypeHandler.singleInstance());
		register(DoubleDataTypeHandler.singleInstance());
		register(DateDataTypeHandler.singleInstance());
		register(ClobDataTypeHandler.singleInstance());
		register(BlobDataTypeHandler.singleInstance());
	}
	
	public void register(ClassDataTypeHandler classDataTypeHandler) {
		if(logger.isDebugEnabled()) {
			logger.debug("register {}", classDataTypeHandler.toString());
		}
		String code = classDataTypeHandler.getCode();
		if(CODE_DATATYPE_HANDLER_MAP.containsKey(code)) {
			throw new RepeatedDataTypeHandlerException("[dynamicRegister] ["+classDataTypeHandler.toString()+"] 和 ["+CODE_DATATYPE_HANDLER_MAP.get(code).toString()+"] 的code值相同, 请修改");
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
		ClassDataTypeHandler dataTypeHandler = CODE_DATATYPE_HANDLER_MAP.get(code.toLowerCase());
		if(dataTypeHandler == null) {
			logger.debug("没有获取到code=[{}]的DataTypeHandler实例, 尝试加载该自定义ClassDataTypeHandler实现子类", code);
			Object obj = null;
			try {
				obj = ConstructorUtil.newInstance(code);
			} catch (Exception e) {
				throw new UnsupportDataTypeHandlerException("目前无法处理ClassDataTypeHandler code=["+code+"]的数据类型");
			}
			if(!(obj instanceof ClassDataTypeHandler)) {
				throw new ClassCastException("code=["+code+"]的类必须继承["+ClassDataTypeHandler.class.getName()+"]");
			}
			dataTypeHandler = (ClassDataTypeHandler) obj;
			register(dataTypeHandler);
		}
		return dataTypeHandler;
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
