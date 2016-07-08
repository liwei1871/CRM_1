package com.atguigu.crm.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.models.Page;
import com.atguigu.crm.service.SalesChanceService;

@RequestMapping("/chance")
@Controller
public class SalesChanceHandler {

	@Autowired
	private SalesChanceService salesChanceService;
	
	
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String update(@PathVariable("id") Long id,
			SalesChance salesChance,
			@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
			RedirectAttributes attributes){
		salesChance.setId(id);
		salesChanceService.update(salesChance);
		attributes.addFlashAttribute("message", "修改成功!");
		
		return "redirect:/sale/chance/list?pageNo="+pageNo;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String toEditUI(
			@PathVariable("id") Integer id,
			Map<String,Object> map
			){
		SalesChance salesChance = salesChanceService.getById(id);
		map.put("chance", salesChance);
		return "sale/addUI";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id,
			@RequestParam(value="pageNo",required=false,defaultValue="1") Integer pageNo,
			RedirectAttributes attributes){
		
		salesChanceService.delete(id);
		attributes.addFlashAttribute("message", "删除成功!");
		
		return "redirect:/sale/chance/list?pageNo="+pageNo;
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String saveChance(SalesChance salesChance){
		
		salesChanceService.save(salesChance);
		
		return "redirect:/chance/list";
	}
	
	@RequestMapping(value="/" , method=RequestMethod.GET)
	public String toAddUI(Map<String,Object> map){
		
		SalesChance salesChance = new SalesChance();
		map.put("chance", salesChance);
		
		return "sale/addUI";
	}
	
	/**
	 * 待查询条件的分页，这里就不能显示method了
	 * 使用WebUtils.getParametersStartingWith(request, "search_")
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/list")
	public String showSalesChanceList(
			@RequestParam(value="pageNo",required=false) String pageNoStr,
			HttpServletRequest request,
			Map<String,Object> map
			){
		
		Map<String,Object> params = WebUtils.getParametersStartingWith(request, "search_");
		System.out.println(params);
		
		//为了点击翻页时可以携带参数则需要将params序列化为一个字符串，然后携带至翻页连接之后
		String queryString = encodeParamsToQueryString(params);
		System.out.println(queryString);
		
		map.put("queryString", queryString);

		User createBy = (User) request.getSession().getAttribute("loginUser");
		params.put("EQO_createBy", createBy);
		params.put("EQI_status", 1);
		
		
		Page<SalesChance> page = salesChanceService.getPage(pageNoStr, params);
		map.put("page", page);
		
		return "sale/mainUI"; 
		
	}
	
	
	/**
	 * 将从页面上批量获取的以search_开头的请求参数序列化后返回字符串的方法
	 * @param params
	 * @return
	 */
	private String encodeParamsToQueryString(Map<String, Object> params) {
		
		StringBuilder builder = new StringBuilder();
		
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if(val == null || val.toString().trim().equals("")){
				//没有输入该条件
				continue;
			}
			
			builder.append("&")
				   .append("search_")
				   .append(key)
				   .append("=")
				   .append(val);
		}
		
		return builder.toString();
	}

	/**
	 * 不带查询条件的分页
	 * @param pageNoStr
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/list2",method=RequestMethod.GET)
	public String showSalesChance(
			@RequestParam(value="pageNo",required=false) String pageNoStr,
			HttpSession session,
			Map<String,Object> map
			){
		
		User loginUser = (User) session.getAttribute("loginUser");
		
		Page<SalesChance> page = salesChanceService.getPage(pageNoStr, loginUser, 1);
		map.put("page", page);
		
		return "sale/mainUI"; 
	}
	
	/*@RequestMapping("/list")
	public String querySalesChance(
			@RequestParam(value="custName",required=false) String custName,
			@RequestParam(value="title",required=false) String title,
			@RequestParam(value="contact",required=false) String contact,
			Map<String,Object> map
			){
		List<SalesChance> salesChancesList = salesChanceService.getSalesChances(custName, title, contact);
		map.put("salesChancesList", salesChancesList);
		
		return "sale/mainUI";
	}*/
}
