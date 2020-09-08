package com.douglei.orm.configuration.environment.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类属性接口
 * @author DougLei
 */
@Target(ElementType.FIELD) // 表示注解的作用对象，ElementType.TYPE表示类，ElementType.METHOD表示方法...
@Retention(RetentionPolicy.RUNTIME) // 注解的保留机制，表示是运行时注解
public @interface FieldMetaData {
	
	/**
	 * 是否必须
	 * @return
	 */
	boolean isRequired() default false;
	
	/**
	 * 为空时，提示的信息
	 * @return
	 */
	String nullOfErrorMessage() default "不能为空";
}
