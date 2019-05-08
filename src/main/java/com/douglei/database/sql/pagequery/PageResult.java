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
		return "PageResult [pageNum=" + pageNum + ", pageSize=" + pageSize + ", totalCount=" + totalCount
				+ ", pageTotalNum=" + pageTotalNum + ", resultDatas="
				+ resultDatas + "]";
	}
}
