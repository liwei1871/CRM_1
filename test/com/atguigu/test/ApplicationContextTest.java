package com.atguigu.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.mapper.UserMapper;

public class ApplicationContextTest {

	private ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
	private UserMapper userMapper = ioc.getBean(UserMapper.class);
	private SalesChanceMapper chanceMapper = ioc.getBean(SalesChanceMapper.class);
	
	
	@Test
	public void testSub(){
		String matchAndType = "LIKES";
		
		String matchTypeStr = matchAndType.substring(0,matchAndType.length()-1);//包左不包含右
		
		String propertyTypeStr = matchAndType.substring(matchAndType.length()-1);
		
		System.out.println("matchTypeStr = " + matchTypeStr);
		System.out.println("propertyTypeStr = " + propertyTypeStr);
	}
	
	@Test
	public void testPageLists(){
		User createBy = new User();
		createBy.setId(21L);
		
		List<SalesChance> list = chanceMapper.getPageList(createBy, 1, 1, 4);
		System.out.println(list.size());
	}
	
	
	@Test
	public void testTotalRecordNo(){
		User createBy = new User();
		createBy.setId(21L);
		
		long totalRecordNo = chanceMapper.getTotalRecordNo(createBy, 1);
		System.out.println(totalRecordNo);
		
	}
	
	@Test
	public void testUser(){
		User user = userMapper.getUserByName("admin");
		
		System.out.println(user.getPassword());
	}
	
	@Test
	public void test() throws SQLException {
		DataSource dataSource = ioc.getBean(DataSource.class);
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
	}

}
