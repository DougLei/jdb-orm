package com.douglei.orm.sql.statement.util;

/**
 * 命名转换工具类
 * @author DougLei
 */
public class NameConvertUtil {
	private static final char SEPARATOR = '_'; // 分隔符
	
	/**
	 * 列名 ==> 属性名
	 * @param columnName
	 * @return
	 */
	public static String column2Property(String columnName) {
		// 记录后面分隔符的下标
		int endIndex = columnName.length();
		while(endIndex > 0 && columnName.charAt(endIndex-1) == SEPARATOR)
			endIndex--;
		if(endIndex == 0) // 全是分隔符, 直接返回 
			return columnName;
		
		// 开始转换name
		StringBuilder sb = new StringBuilder(columnName.length());
		
		// 首先处理前面是分隔符的字符, 直接拼接
		int index = 0;
		char c;
		while((c = columnName.charAt(index++)) == SEPARATOR) 
			sb.append(c);
		
		// 针对第一个非分隔符进行转小写处理
		if(c>64 && c<91)
			c += 32;
		sb.append(c);
		
		// 循环拼接其他字符
		byte scount = 0; // 记录每次连续分隔符的数量
		while(index < endIndex) {
			c = columnName.charAt(index++);
			
			if(c == SEPARATOR) {
				scount++;
				continue;
			}
			
			if(scount == 1) { // 一个分隔符, 对字符进行转大写
				if(c>96 && c<123)
					c -= 32;
				scount=0;
			}else { 
				if(scount > 0) { // 多个连续分隔符, 舍弃第一个后, 其他的全部拼接
					while(--scount > 0)  
						sb.append(SEPARATOR);
				}
				
				if(c>64 && c<91) // 对字符进行转小写处理
					c += 32;
			}
			sb.append(c);
		}
		
		// 拼接最后的分隔符
		while(endIndex++ < columnName.length()) 
			sb.append(SEPARATOR);
		return sb.toString();
	}
	
	/**
	 * 属性名 ==> 列名
	 * @param propertyName
	 * @return
	 */
	public static String property2Column(String propertyName) {
		// 记录后面分隔符的下标
		int endIndex = propertyName.length();
		while(endIndex > 0 && propertyName.charAt(endIndex-1) == SEPARATOR)
			endIndex--;
		if(endIndex == 0) // 全是分隔符, 直接返回 
			return propertyName;
		
		// 开始转换name
		StringBuilder sb = new StringBuilder(propertyName.length() + 5);
		
		// 首先处理前面是分隔符的字符, 直接拼接
		int index = 0;
		char c;
		while((c = propertyName.charAt(index++)) == SEPARATOR) 
			sb.append(c);
		
		// 针对第一个非分隔符进行转大写处理
		if(c>96 && c<123)
			c -= 32;
		sb.append(c);
		
		// 循环拼接其他字符
		byte scount = 0; // 记录每次连续分隔符的数量
		while(index < endIndex) {
			c = propertyName.charAt(index++);
			
			if(c == SEPARATOR) {
				scount++;
				continue;
			}else if(c>64 && c<91 || scount>0) { // 大写字符, 或是之前有连续分隔符的, 都要追加一个分隔符
				scount++;
			}
			
			// 拼接分隔符
			if(scount>0) {
				while(scount-- > 0)
					sb.append(SEPARATOR);
			}
			
			// 将字符转大写
			if(c>96 && c<123)
				c -= 32;
			sb.append(c);
		}
		
		// 拼接最后的分隔符
		while(endIndex++ < propertyName.length()) 
			sb.append(SEPARATOR);
		return sb.toString();
	}
	
	
	// 测试用main方法
//	public static void main(String[] args) {
//		System.out.println(column2Property("___"));
//		System.out.println(column2Property("USER_NAME"));
//		System.out.println(column2Property("__USER_NAME__"));
//		System.out.println(column2Property("_USER___NAME_"));
//		
//		System.out.println("\n----------------------------------------------------\n");
//		
//		System.out.println(property2Column("___"));
//		System.out.println(property2Column("userName"));
//		System.out.println(property2Column("__userName__"));
//		System.out.println(property2Column("_user__name_"));
//	}
}
