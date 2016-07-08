package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.models.Navigation;
import com.atguigu.crm.service.UserService;

@RequestMapping("/user")
@Controller
public class UserHandler {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@ResponseBody
	@RequestMapping(value="/navigate")
	public List<Navigation> navigate(HttpSession session){
		
		List<Navigation> navigations = new ArrayList<>();
		String contextPath = session.getServletContext().getContextPath();
		
		User user = (User)session.getAttribute("user");
		//全部子权限
		List<Authority> authorities = user.getRole().getAuthorities();
		
		//创建导航头
		Navigation top = new Navigation(Long.MAX_VALUE, "客户关系管理系统");
		navigations.add(top);
		
		//创建一个存放父导航的Map，用于存放父导航
		Map<Long,Navigation> parentNavigations = new HashMap<>();
		
		for (Authority authority : authorities) {
			//创建对应子权限对应的导航
			Navigation navigation = new Navigation(authority.getId(), authority.getDisplayName());
			navigation.setUrl(contextPath + authority.getUrl());
			
			//先从父导航中尝试获取当前导航的父导航
			//1.获取对应父权限
			Authority parentAuthority = authority.getParentAuthority(); 
			//2.根据父权限的id尝试从父导航Map集合中获取父导航
			Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
			
			//如果从Map集合中获取的为Null则说明还没有放入父导航，则需要新建父导航并放入，反之则已放入不需要再次放入
			if(parentNavigation == null){
				
				parentNavigation = new Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
				parentNavigation.setState("closed");
				
				parentNavigations.put(parentAuthority.getId(), parentNavigation);
				
				//父导航作为top的子导航
				top.getChildren().add(parentNavigation);
			}
			
			parentNavigation.getChildren().add(navigation);
		}
		
		return navigations;
	}
	
	
	@RequestMapping(value="/shiro-login" , method=RequestMethod.POST)
	public String shiroLogin(
			@RequestParam(value="name" , required=false) String name,
			@RequestParam(value="password" , required=false) String password,
			HttpSession session,
			RedirectAttributes attributes,
			Locale locale
				){
		//获取当前User
		Subject currentUser = SecurityUtils.getSubject();
		System.out.println(currentUser.getPrincipal());
		
		//检测当前用户是否被认证
		if(!currentUser.isAuthenticated()){
			//没有认证。即没有用户登录
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			token.setRememberMe(true);
			
			//执行登录调用Subject的login（usernamePasswordToken）的方法登录
			try {
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				//登录异常跳转至错误页面
				String code = "error.login";
				String message = messageSource.getMessage(code, null, locale);
				attributes.addFlashAttribute("message", message);
				return "redirect:/index";
				
			}
		}
		
		//可以通过调用 Subject 的 .getPrincipals().getPrimaryPrincipal() 获取到
		//在 realm 中创建 SimpleAuthenticationInfo 对象时的 principal 实例. 
		session.setAttribute("user", currentUser.getPrincipals().getPrimaryPrincipal());
		return "home/success";
	}
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	public String login(
			@RequestParam(value="name" , required=false) String name,
			@RequestParam(value="password" , required=false) String password,
			HttpSession session,
			RedirectAttributes attributes,
			Locale locale
				){
		User user= userService.login(name,password);
		
		if(user == null) {
			String code = "error.login";
			String message = messageSource.getMessage(code, null, locale);
			attributes.addFlashAttribute("message", message);
			return "redirect:/index";
		}
		
		session.setAttribute("loginUser", user);
		return "home/success";
	}
	
}
