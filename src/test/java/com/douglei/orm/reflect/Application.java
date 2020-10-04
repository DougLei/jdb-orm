package com.douglei.orm.reflect;

import java.io.File;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

public class Application {
	public static void main(String[] args) throws Exception {
//		Class<?> clz = Class.forName("com.douglei.test.reflect.Entity");
//		System.out.println(clz.getMethod("singleInstance").invoke(null));
//		System.out.println(clz.getMethod("singleInstance").invoke(null));
//		System.out.println(clz.getMethod("singleInstance").invoke(null));
//		System.out.println(clz.getMethod("singleInstance").invoke(null));
		
		TableMetadata table = JdkSerializeProcessor.deserializeFromFile(TableMetadata.class, new File("C:\\Users\\Administrator.USER-20190410XF\\Desktop\\SMT_BASE_CFG_PROPERTY_ARR.orm"));
		System.out.println(table);
	}
}
