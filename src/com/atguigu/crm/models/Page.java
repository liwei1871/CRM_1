package com.atguigu.crm.models;

import java.util.List;

public class Page<T> {

	private Integer pageNo;
	private int totalRecordNo;// 总记录数
	private int totalPageNo; // 总页数

	public static final int PAGE_SIZE = 3;

	private List<T> list;

	public Page() {
	}

	public Page(String pageNoStr, int totalRecordNo, int pageSize) {

		this.totalRecordNo = totalRecordNo;

		// 计算总页数
		this.totalPageNo = (totalRecordNo % pageSize == 0) ? (totalRecordNo / pageSize)
				: (totalRecordNo / pageSize + 1);

		this.pageNo = 1;

		try {
			this.pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {
		}

		if (pageNo > totalPageNo) {
			pageNo = totalPageNo;
		}

		if (pageNo < 1) {
			pageNo = 1;
		}
	}

	// 判断上下页是否存在
	public boolean isHasPrev() {
		return pageNo > 1;
	}

	public boolean isHasNext() {
		return pageNo < totalPageNo;
	}

	// 调转上下页
	public int getPrev() {
		if(isHasPrev()){
			return this.pageNo-1;
		}
		
		return this.pageNo;
	}

	public int getNext() {
		if(isHasNext()){
			return this.pageNo + 1;
		}
		return pageNo;
	}

	//-----------------getter/setter-------------------
	public Integer getPageNo() {
		return pageNo;
	}
	
	public int getTotalRecordNo() {
		return totalRecordNo;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	
}
