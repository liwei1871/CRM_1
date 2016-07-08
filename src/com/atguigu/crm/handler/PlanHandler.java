package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.utils.QueryParamProcessUtils;

@RequestMapping("/plan")
@Controller
public class PlanHandler {
	
	@RequestMapping(value="/list")
	public String list(@RequestParam(value="pageNo" ,required=false) String pageNo,
			HttpServletRequest request,
			Map<String , Object> map){
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		
		String queryString = QueryParamProcessUtils.encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		
		
		
		return "";
	}
	
}
