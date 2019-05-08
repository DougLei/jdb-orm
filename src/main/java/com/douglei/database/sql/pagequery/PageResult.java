package com.douglei.database.sql.pagequery;

import java.util.List;

/**
 * 分页查询结果对象
 * @author DougLei
 */
public class PageResult<T> {
	
	/**
	 * 页数(当前第几页)
	 */
	private int pageNum;
	/**
	 * 一页显示的数量
	 */
	private int pageSize;
	/**
	 * 总数量
	 */
	private long totalCount;
	
	/**
	 * <pre>
	 * 	第一条数据的下标
	 * 	即分页查询结果集的第一条数据, 在整个查询结果集中, 其所在的下标, 下标值从1开始
	 * </pre>
	 */
	private int firstDataIndex;
	
	/**
	 * 总页数
	 */
	private int pageTotalNum;
	
	/**
	 * 结果集
	 */
	private List<T> resultDatas;
	
	public PageResult(PageResult<? extends Object> other) {
		copyBaseData(other);
	}
	
	// 将参数other中的除resultDatas数据, 都copy到当前对象中
	private void copyBaseData(PageResult<? extends Object> other) {
		this.pageNum = other.pageNum;
		this.pageSize = other.pageSize;
		this.totalCount = other.totalCount;
		this.pageTotalNum = other.pageTotalNum;
		this.firstDataIndex = other.firstDataIndex;
	}
	
	public PageResult(int pageNum, int pageSize, long totalCount) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		calcPageDataInfo();
	}
	
	// 计算各种分页数据信息
	private void calcPageDataInfo() {
		if(totalCount > 0) { // 只有有数据的时候, 才进行计算
			calcAndSetPageTotalNum();
			calcAndSetFirstDataIndex();
		}
	}
	
	// 计算并set pageTotalNum 总页数
	private void calcAndSetPageTotalNum() {
		pageTotalNum = (int)(totalCount/pageSize);
		if(totalCount%pageSize != 0) {
			pageTotalNum++;
		}
		if(pageNum > pageTotalNum) {
			throw new PageNumOutOfBoundsException("页数溢出, pageTotalNum="+pageTotalNum+", pageNum="+pageNum);
		}
	}
	
	// 计算并set firstDataIndex 第一条数据的下标
	private void calcAndSetFirstDataIndex() {
		firstDataIndex = (pageNum * pageSize) - (pageSize - 1);
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getPageTotalNum() {
		return pageTotalNum;
	}
	public int getFirstDataIndex() {
		return firstDataIndex;
	}
	public List<T> getResultDatas() {
		return resultDatas;
	}
	public void setResultDatas(List<T> resultDatas) {
		this.resultDatas = resultDatas;
	}
	
	/**
	 * 是否是第一页
	 * @return
	 */
	public boolean isFirstPageNum() {
		return pageNum == 1;
	}
	/**
	 * 是否是最后一页
	 * @return
	 */
	public boolean isLastPageNum() {
		return pageNum == pageTotalNum;
	}
	
	@Override
	public String toString() {
		return null;
	}
}
