package com.douglei.orm.dialect.db.object;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class DBObjectHandler {
	
	/**
	 * 数据库对象名的最大长度
	 * @return
	 */
	protected abstract short nameMaxLength();
	
	/**
	 * 验证数据库object名称是否超长
	 * @param dbObjectName
	 * @return
	 */
	private boolean validateNameIsOverLength(String name) {
		return name.length() > nameMaxLength();
	}
	
	/**
	 * 	验证数据库object名称
	 * @param name
	 * @throws DBObjectNameException
	 */
	public void validateObjectName(String name) throws DBObjectNameException {
		if(validateNameIsOverLength(name)) 
			throw new DBObjectNameException(EnvironmentContext.getDialect().getType().name() + "数据库的资源命名名称["+name+"]长度不能超过"+nameMaxLength()+"个字符");
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
	public String fixObjectName(String name) {
		if(validateNameIsOverLength(name)) {
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
			if(sb.length() > nameMaxLength()) {
				throw new DBObjectNameException("["+name+"]经过fixDBObjectName()后为["+sb.toString()+"], 其长度依然大于"+ EnvironmentContext.getDialect().getType().name() + "数据库对象命名限制的最大字符长度("+nameMaxLength()+")");
			}
			return sb.toString();
		}
		return name;
	}

	/**
	 * 创建主键序列对象
	 * @param name 序列名, 如果序列名为空, 则要使用tableName自动生成序列名
	 * @param createSql 创建序列的sql语句
	 * @param dropSql 删除序列的sql语句
	 * @param tableName 表名
	 * @param primaryKeyColumn 主键列
	 * @return
	 */
	public abstract PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn);
}