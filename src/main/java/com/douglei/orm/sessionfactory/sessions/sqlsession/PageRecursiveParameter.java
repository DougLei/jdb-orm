package com.douglei.orm.sessionfactory.sessions.sqlsession;

/**
 * 
 * @author DougLei
 */
public class PageRecursiveParameter extends RecursiveParameter{
	private int pageNum; // 页数
	private int pageSize; // 一页显示的数量
	
	/**
	 * 
	 * @param pageNum 页数
	 * @param pageSize 一页显示的数量
	 */
	public PageRecursiveParameter(int pageNum, int pageSize) {
		this.pageNum = pageNum<0?1:pageNum;
		this.pageSize = pageSize<0?10:pageSize;
	}

	/**
	 * 获取页数
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
