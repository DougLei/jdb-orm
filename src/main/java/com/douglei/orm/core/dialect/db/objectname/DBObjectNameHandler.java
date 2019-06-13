package com.douglei.orm.core.dialect.db.objectname;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class DBObjectNameHandler {
	
	protected abstract short nameMaxLength();
	
	/**
	 * <pre>
	 * 	验证数据库object名称
	 * 	返回true表示验证通过, false表示验证失败
	 * </pre>
	 * @param dbObjectName
	 * @return
	 */
	private boolean validateDBObjectName_(String dbObjectName) {
		return dbObjectName.length() <= nameMaxLength();
	}
	
	/**
	 * <pre>
	 * 	验证数据库object名称
	 * 	验证失败时会抛出异常
	 * </pre>
	 * @param dbObjectName
	 * @throws DBObjectNameException
	 */
	public void validateDBObjectName(String dbObjectName) throws DBObjectNameException {
		if(!validateDBObjectName_(dbObjectName)) {
			throw new DBObjectNameException(DBRunEnvironmentContext.getDialect().getType().name() + "数据库的[表/列]名称["+dbObjectName+"]长度不能超过"+nameMaxLength()+"个字符");
		}
	}
	
	/**
	 * <pre>
	 * 	修正数据库object名称
	 * 	如果dbObjectName的长度, 大于nameMaxLength(), 则提前部分数据, 做为新的dbObjectName
	 * 
	 * 	提取逻辑:
	 * 		第一个下划线前的所有字母加_，再加后续每个单词的首字母，然后加_，加后续每个单词的尾字母，最后加_，再加名称的总数量，均大写
	 * 		例如: SYS_USER_LINE，提取结果为SYS_UL_RE_13
	 * </pre>
	 * @param dbObjectName
	 * @return
	 */
	public String fixDBObjectName(String dbObjectName) {
		if(validateDBObjectName_(dbObjectName)) {
			return dbObjectName;
		}
		dbObjectName = StringUtil.trimUnderline(dbObjectName);
		
		StringBuilder sb = new StringBuilder(dbObjectName.length());
		StringBuilder suffix = new StringBuilder(dbObjectName.length());
		
		String[] nameArr = dbObjectName.split("_");
		int length = nameArr.length;
		for(int i=0;i<length;i++){
			if(i == 0){
				sb.append(nameArr[i]).append("_");
			}else{
				if(StringUtil.notEmpty(nameArr[i])){
					sb.append(nameArr[i].substring(0, 1));
					suffix.append(nameArr[i].substring(nameArr[i].length()-1));
				}
			}
		}
		sb.append("_").append(suffix).append("_").append(dbObjectName.length());
		if(sb.length() > nameMaxLength()) {
			throw new DBObjectNameException("["+dbObjectName+"]经过fixDBObjectName()后为["+sb.toString()+"], 其长度依然大于"+ DBRunEnvironmentContext.getDialect().getType().name() + "数据库对象命名限制的最大字符长度("+nameMaxLength()+")");
		}
		return sb.toString();
	}
}