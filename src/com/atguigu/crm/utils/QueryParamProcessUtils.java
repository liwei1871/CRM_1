package com.atguigu.crm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.atguigu.crm.models.PropertyFilter;

/**
 * 查询条件参数处理工具类
 */
public class QueryParamProcessUtils {
	
	/**
	 * 将parames转换为PropertyFilter的集合
	 * @param params 
	 * params 如---- 
	 * key : LIKES_custName/EQO_createBy （需要转换）; 
	 * value = "AA"(value可用不用转)
	 * @return
	 */
	public static List<PropertyFilter> parseHandlerParamsToPropertyFilters(Map<String,Object> params){
		
		List<PropertyFilter> filters = new ArrayList<>();
		
		for (Entry<String,Object> entry : params.entrySet()) {
			String filedName = entry.getKey();// LIKES_custName/EQO_createBy （需要转换,传给propertyfilter的构造器进行处理）
			
			Object filedValue = entry.getValue();
			
			PropertyFilter filter = new PropertyFilter(filedName,filedValue);
			
			filters.add(filter);
			
		}
		
		return filters;
	}
	
	/**
	 * 2.将PropertyFilter的集合转换成myBatis可用的params;
	 * @param filters
	 * @return
	 */
	public static Map<String,Object> parsePropertyFiltersToMyBatisParmas(List<PropertyFilter> filters){
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		for (PropertyFilter filter : filters) {
			
			String propertyName = filter.getPropertyName();
			Object propertyVal = filter.getPropertyVal();
			
			params.put(propertyName, propertyVal);
			
		}
		
		return params;
	}

	public static String encodeParamsToQueryString(Map<String, Object> params) {
		
		StringBuilder builder = new StringBuilder();
		
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if(value == null || value.toString().trim().equals("")){
				continue;
			}
			
			builder.append("&")
					.append("search_")
					.append(key)
					.append("=")
					.append(value);
		}
		
		return builder.toString();
	}
	
}
