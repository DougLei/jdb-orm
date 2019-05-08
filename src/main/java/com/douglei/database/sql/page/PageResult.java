package com.douglei.database.sql.page;

import java.util.List;

/**
 * 分页查询结果对象
 * @author DougLei
 */
public class PageResult<T> {
	
	public PageResult(PageResult<? extends Object> other) {
		copyBaseData(other);
	}
	
	/**
	 * 将参数中的除resultDatas数据, 都同步到当前对象中
	 * @param other
	 */
	private void copyBaseData(PageResult<? extends Object> other) {
		this.pageNo = other.pageNo;
		this.pageSize = other.pageSize;
		this.totalCount = other.totalCount;
		
	}
	
	public PageResult(int pageNo, int pageSize, long totalCount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	/**
	 * 页数
	 */
	private int pageNo;
	/**
	 * 一页显示的数量
	 */
	private int pageSize;
	
	/**
	 * 总页数
	 */
	private int totalPageNo;
	/**
	 * 总数量
	 */
	private long totalCount;
	
	/**
	 * 结果集
	 */
	private List<T> resultDatas;

	
	public long getTotalCount() {
		return totalCount;
	}
	public List<T> getResultDatas() {
		return resultDatas;
	}
	public void setResultDatas(List<T> resultDatas) {
		this.resultDatas = resultDatas;
	}
	public int getPageNo() {
		return pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getTotalPageNo() {
		return totalPageNo;
	}
}
