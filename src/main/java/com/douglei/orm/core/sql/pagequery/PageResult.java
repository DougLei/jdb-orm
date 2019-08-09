package com.douglei.orm.core.sql.pagequery;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页查询结果对象
 * @author DougLei
 */
public class PageResult<T> {
	private static final Logger logger = LoggerFactory.getLogger(PageResult.class);
	
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
	private long count;
	/**
	 * 总页数
	 */
	private int pageCount;
	/**
	 * 结果集
	 */
	private List<T> resultDatas;
	
	/**
	 * 将origin中的基础数据copy到新对象的属性中
	 * @param origin
	 */
	public PageResult(PageResult<? extends Object> origin) {
		copyBaseData(origin);
	}
	
	// 将参数other中的除resultDatas数据, 都copy到当前对象中
	private void copyBaseData(PageResult<? extends Object> origin) {
		this.pageNum = origin.pageNum;
		this.pageSize = origin.pageSize;
		this.count = origin.count;
		this.pageCount = origin.pageCount;
	}
	
	public PageResult(int pageNum, int pageSize, long count) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.count = count;
		processPageData();
	}
	
	// 处理分页数据
	private void processPageData() {
		// 只有有数据的时候, 才进行计算
		if(count > 0) {
			// 计算总页数
			pageCount = (int)(count/pageSize);
			if(count%pageSize != 0) {
				pageCount++;
			}

			// 判断请求的页数是否大于总页数, 如果大于总页数, 则修正并记录日志
			if(pageNum > pageCount) {
				pageNum = pageCount;
				logger.info("请求的页数溢出, pageCount={}, pageNum={}, 修正pageNum={}", pageCount, pageNum, pageCount);
			}
		}
	}
	
	public long getCount() {
		return count;
	}
	public int getPageNum() {
		return pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public List<T> getResultDatas() {
		if(resultDatas == null) {
			resultDatas = Collections.emptyList();
		}
		return resultDatas;
	}
	public void setResultDatas(List<T> resultDatas) {
		this.resultDatas = resultDatas;
	}
	
	/**
	 * 是否是第一页
	 * @return
	 */
	public boolean isFirstPage() {
		return pageNum == 1;
	}
	/**
	 * 是否是最后一页
	 * @return
	 */
	public boolean isLastPage() {
		return pageNum == pageCount;
	}

	@Override
	public String toString() {
		return "PageResult [pageNum=" + pageNum + ", pageSize=" + pageSize + ", count=" + count
				+ ", pageCount=" + pageCount + ", resultDatas="
				+ getResultDatas() + "]";
	}
}
