package com.douglei.orm.sessionfactory.sessions.session.sql.purpose;

/**
 * 
 * @author DougLei
 */
public class QueryPurposeEntity extends PurposeEntity {
	public static final QueryPurposeEntity singleton = new QueryPurposeEntity();
	public static QueryPurposeEntity getSingleton() {
		return singleton;
	}
	
	private QueryPurposeEntity() {
		super(false, true);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.QUERY;
	}
}
