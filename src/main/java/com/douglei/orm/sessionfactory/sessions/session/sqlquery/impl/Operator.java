package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

/**
 * 运算符
 * @author DougLei
 */
public enum Operator {
	EQ(){
		@Override
		public Operator getNegation() {
			return NE;
		}

		@Override
		public Operator optimizing(Object[] values) {
			if(values.length < 2)
				return this;
			
			int flag = -1; // -1表示values中都是null, -2表示values中部分是null, 大于等于0, 表示values中只有一个非null, 且记录的是其下标
			for(int i=0; i<values.length;i++) {
				if(values[i] == null)
					continue;
				
				if(flag == -1) {
					flag = i;
				}else {
					flag = -2;
					break;
				}
			}
			
			if(flag > -2) { // 要么都是null, 要么只有一个非null的值
				if(flag > -1)
					values[0] = values[flag];
				return this;
			}
			return IN;
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			if(values.length == 0 || values[0] == null)
				entity.appendConditionSQL(name + " IS NULL", name);
			else
				entity.appendConditionSQL(name + "=?", name, values[0]);
			return true;
		}
	},
	NE(){
		@Override
		public Operator getNegation() {
			return EQ;
		}

		@Override
		public Operator optimizing(Object[] values) {
			return EQ.optimizing(values).getNegation();
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			if(values.length == 0 || values[0] == null)
				entity.appendConditionSQL(name + " IS NOT NULL", name);
			else
				entity.appendConditionSQL(name + "<>?", name, values[0]);
			return true;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	GE(){
		@Override
		public Operator getNegation() {
			return LE;
		}
		
		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + ">=?", name, values[0]);
			return true;
		}
	},
	LE(){
		@Override
		public Operator getNegation() {
			return GE;
		}
		
		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + "<=?", name, values[0]);
			return true;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	GT(){
		@Override
		public Operator getNegation() {
			return LT;
		}
		
		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + ">?", name, values[0]);
			return true;
		}
	},
	LT(){
		@Override
		public Operator getNegation() {
			return GT;
		}
		
		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + "<?", name, values[0]);
			return true;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	LIKE(){
		@Override
		public Operator getNegation() {
			return NLIKE;
		}
		
		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + " LIKE ?", name, values[0]);
			return true;
		}
	},
	NLIKE(){
		@Override
		public Operator getNegation() {
			return LIKE;
		}
		
		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + " NOT LIKE ?", name, values[0]);
			return true;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	BTN(){
		@Override
		public Operator getNegation() {
			return NBTN;
		}

		@Override
		public Operator optimizing(Object[] values) {
			if(values.length == 0)
				throw new QuerySqlAssembleException(name() + " 运算符至少传入一个参数值");
			
			Object v1 = values[0];
			Object v2 = (values.length>1)?values[1]:null;
			
			if(v1 == null && v2 == null)
				throw new QuerySqlAssembleException(name() + " 运算符至少传入一个参数值");
			
			if(v1 != null) {
				if(v2 != null)
					return this;
				return GE;
			}
				
			values[0] = v2;
			return LE;
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + " BETWEEN ? AND ?", name, values[0], values[1]);
			return true;
		}
	},
	NBTN(){
		@Override
		public Operator getNegation() {
			return BTN;
		}

		@Override
		public Operator optimizing(Object[] values) {
			Operator result = BTN.optimizing(values);
			if(result == BTN)
				return this;
			
			if(result == GE)
				return LT;
			return GT;
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			entity.appendConditionSQL(name + " NOT BETWEEN ? AND ?", name, values[0], values[1]);
			return true;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	IN(){
		@Override
		public Operator getNegation() {
			return NIN;
		}

		@Override
		public Operator optimizing(Object[] values) {
			return EQ.optimizing(values);
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			StringBuilder sql = new StringBuilder(name.length()+5+values.length*2);
			sql.append(name).append(" IN (");
			for(int i=0; i<values.length;i++) {
				if(values[i] != null)
					sql.append("?,");
			}
			sql.setLength(sql.length()-1);
			sql.append(')');
			
			entity.appendConditionSQL(sql.toString(), name, values);
			return true;
		}
	},
	NIN(){
		@Override
		public Operator getNegation() {
			return IN;
		}

		@Override
		public Operator optimizing(Object[] values) {
			return EQ.optimizing(values).getNegation();
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			StringBuilder sql = new StringBuilder(name.length()+9+values.length*2);
			sql.append(name).append(" NOT IN (");
			for(int i=0; i<values.length;i++) {
				if(values[i] != null)
					sql.append("?,");
			}
			sql.setLength(sql.length()-1);
			sql.append(')');
			
			entity.appendConditionSQL(sql.toString(), name, values);
			return true;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	ORDER(){
		@Override
		public Operator getNegation() {
			return ORDER;
		}
		
		@Override
		public Operator optimizing(Object[] values) {
			return this;
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			String order = null;
			if(values.length > 0 && values[0] != null) {
				order = values[0].toString();
				if(!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc"))
					order = null;
			}
			entity.appendOrderbySQL(name, order);
			return false;
		}
	},
	
	
	// -------------------------------------------------------------------------------------
	RESULT(){
		@Override
		public Operator getNegation() {
			return RESULT;
		}
		
		@Override
		public Operator optimizing(Object[] values) {
			return this;
		}

		@Override
		public boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity) {
			String alias = null;
			if(values.length > 0 && values[0] != null)
				alias = values[0].toString();
			entity.appendResultSQL(name, alias);
			return false;
		}
	};
	

	// -------------------------------------------------------------------------------------
	/**
	 * 获取反向的操作运算符
	 * @return
	 */
	public abstract Operator getNegation();
	
	/**
	 * 根据实际值, 获取最优的操作运算符
	 * @param values
	 * @return
	 */
	public Operator optimizing(Object[] values) {
		if(values.length == 0 || values[0] == null)
			throw new QuerySqlAssembleException(name()+" 运算符必须传入一个参数值");
		return this;
	}
	
	/**
	 * 装配SQL
	 * @param name
	 * @param values
	 * @param entity
	 * @return 装配sql后, 是否支持拼接逻辑运算符
	 */
	public abstract boolean assembleSQL(String name, Object[] values, ExecutableQuerySql entity);
}
