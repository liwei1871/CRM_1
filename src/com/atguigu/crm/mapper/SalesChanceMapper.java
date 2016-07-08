package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;

public interface SalesChanceMapper {
	
	
	
	List<SalesChance> getPageList2(Map<String,Object> params);
	
	long getTotalRecordNo2(Map<String,Object> params);
	
	long getTotalRecordNo(@Param("createBy") User createBy, 
						  @Param("status") int status);

	List<SalesChance> getPageList(@Param("createBy") User createBy, 
			  					  @Param("status") int status, 
			  					  @Param("fromIndex") int fromIndex,
			  					  @Param("endIndex") int endIndex);

	void save(SalesChance salesChance);

	void delete(@Param("id") Integer id);

	SalesChance getById(@Param("id") Integer id);

	void update(SalesChance salesChance);
	
	
	
}
