package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.models.Page;
import com.atguigu.crm.models.PropertyFilter;
import com.atguigu.crm.utils.QueryParamProcessUtils;

@Service
public class SalesChanceService {
	
	@Autowired
	private SalesChanceMapper chanceMapper;
	
	/**
	 * 不带查询条件的分页
	 * @param pageNoStr
	 * @param createBy
	 * @param status
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<SalesChance> getPage(String pageNoStr, User createBy, int status) {
		
		//System.out.println(createBy.getId());
		
		int totalRecordNo = (int) chanceMapper.getTotalRecordNo(createBy , status);
		
		Page<SalesChance> page = new Page<>(pageNoStr, totalRecordNo, Page.PAGE_SIZE);
		int pageNo = page.getPageNo();
		
		int fromIndex = (pageNo-1)*Page.PAGE_SIZE +1;
		int endIndex = fromIndex + Page.PAGE_SIZE;
		
		List<SalesChance> list = chanceMapper.getPageList(createBy , status , fromIndex , endIndex);
		
		page.setList(list);
		
		return page;
	}
	
	@Transactional
	public void save(SalesChance salesChance) {
		
		chanceMapper.save(salesChance);
	}

	@Transactional
	public void delete(Integer id) {
		chanceMapper.delete(id);
		
	}

	@Transactional(readOnly=true)
	public SalesChance getById(Integer id) {
		
		return chanceMapper.getById(id);
	}
	
	@Transactional
	public void update(SalesChance salesChance) {
		
		chanceMapper.update(salesChance);
	}

	@Transactional
	public Page<SalesChance> getPage(String pageNoStr,
			Map<String, Object> params) {
		//params 如---- key : LIKES_custName/EQO_createBy （需要转换）; value = "AA"(value可用不用转)
		//1.将parames转换为PropertyFilter的集合
		List<PropertyFilter> filters = QueryParamProcessUtils.parseHandlerParamsToPropertyFilters(params);
		
		//2.将PropertyFilter的集合转换成myBatis可用的params;
		Map<String , Object> myBatisParams = QueryParamProcessUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		int totalRecordNo = (int) chanceMapper.getTotalRecordNo2(myBatisParams);
		
		Page<SalesChance> page = new Page<>(pageNoStr, totalRecordNo, Page.PAGE_SIZE);
		int pageNo = page.getPageNo();
		
		int fromIndex = (pageNo-1)*Page.PAGE_SIZE +1;
		int endIndex = fromIndex + Page.PAGE_SIZE;
		myBatisParams.put("fromIndex", fromIndex);
		myBatisParams.put("endIndex", endIndex);
		
		List<SalesChance> list = chanceMapper.getPageList2(myBatisParams);
		
		page.setList(list);
		
		return page;
		
	}
	
}
