package com.douglei.orm.dialect.dbobject;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.dialect.dbobject.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class DBObjectHandler {
	
	/**
	 * 支持存储过程直接返回 ResultSet
	 * 即存储过程中编写 select语句, 执行该存储过程后, 会展示出该select结果集, 例如sqlserver数据库, mysql数据库
	 * 但是例如oracle数据库, 则是必须通过输出参数才能返回结果集(cursor类型)
	 * 默认是true
	 * @return
	 */
	public boolean supportProcedureDirectlyReturnResultSet() {
		return true;
	}
	
	/**
	 * 数据库对象名的最大长度
	 * @return
	 */
	protected abstract short nameMaxLength();
	
	/**
	 * 	验证数据库object名称
	 * @param name
	 * @throws DBObjectNameException
	 */
	public void validateObjectName(String name) {
		if(name.length() > nameMaxLength()) 
			throw new IllegalArgumentException(EnvironmentContext.getDialect().getType().getName() + "数据库的对象名称["+name+"], 其长度超过了限制的"+nameMaxLength()+"个字符");
	}
	
	/**
	 * 	修正数据库object名称
	 * 	如果dbObjectName的长度, 大于nameMaxLength(), 则提前部分数据, 做为新的dbObjectName
	 * 
	 * 	提取逻辑:
	 * 		第一个下划线前的所有字母加_，再加后续每个单词的首字母，然后加_，加后续每个单词的尾字母，最后加_，再加名称的总数量，均大写
	 * 		例如: SYS_USER_LINE，提取结果为SYS_UL_RE_13
	 * @param name
	 * @return
	 */
	public String correctObjectName(String name) {
		if(name.length() > nameMaxLength()) {
			name = StringUtil.trim(name, '_');
			
			StringBuilder sb = new StringBuilder(name.length());
			StringBuilder suffix = new StringBuilder(name.length());
			
			String[] nameArr = name.split("_");
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
			sb.append("_").append(suffix).append("_").append(name.length());
			if(sb.length() > nameMaxLength()) 
				throw new IllegalArgumentException("["+name+"]经过correctObjectName()后为["+sb.toString()+"], 其长度依然大于"+ EnvironmentContext.getDialect().getType().getName() + "数据库对象命名限制的最大字符长度("+nameMaxLength()+")");
			return sb.toString();
		}
		return name;
	}

	/**
	 * 创建主键序列对象
	 * @param name 序列名
	 * @param createSql 创建序列的sql语句
	 * @param dropSql 删除序列的sql语句
	 * @param tableName 表名
	 * @param primaryKeyColumn 主键列
	 * @return
	 */
	public abstract PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn);
}
