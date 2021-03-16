package com.douglei.orm.sessionfactory.sessions.sqlsession;

/**
 * 
 * @author DougLei
 */
public class PageRecursiveEntity extends RecursiveEntity{
	private int pageNum; // 页数(当前第几页)
	private int pageSize; // 一页显示的数量
	
	/**
	 * 
	 * @param pageNum 页数
	 * @param pageSize 一页显示的数量
	 */
	public PageRecursiveEntity(int pageNum, int pageSize) {
		this.pageNum = pageNum<0?1:pageNum;
		this.pageSize = pageSize<0?10:pageSize;
	}

	/**
	 * 获取页数(当前第几页)
	 * @return
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * 获取一页显示的数量
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
}
