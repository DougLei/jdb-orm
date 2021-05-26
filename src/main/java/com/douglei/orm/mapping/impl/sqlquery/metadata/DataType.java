package com.douglei.orm.mapping.impl.sqlquery.metadata;

import com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl.Operator;

/**
 * 
 * @author DougLei
 */
public enum DataType {
	
	/**
	 * 字符串
	 */
	STRING {
		@Override
		public boolean support(Operator operator) {
			switch (operator) {
				case EQ:
				case NE:
					
				case LIKE:
				case NLIKE:
					
				case IN:
				case NIN:
					
				case ORDER:
				case RESULT:
					return true;
				default:
					return false;
			}
		}
	},
	
	/**
	 * 数字
	 */
	NUMBER,
	
	/**
	 * 日期
	 */
	DATETIME;
	
	
	/**
	 * 是否支持指定的操作
	 * @param operator
	 * @return
	 */
	public boolean support(Operator operator) {
		switch (operator) {
			case EQ:
			case NE:
			case GE:
			case GT:
			case LE:
			case LT:
				
			case BTN:
			case NBTN:
				
			case IN:
			case NIN:
				
			case ORDER:
			case RESULT:
				return true;
			default:
				return false;
		}
	}
}
