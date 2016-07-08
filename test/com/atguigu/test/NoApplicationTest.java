package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.atguigu.crm.models.PropertyFilter;

public class NoApplicationTest {
	
	
	
	@Test
	public void test() {
		PropertyFilter filter = new PropertyFilter("LIKES_custName" , "QQQ");
		System.out.println(filter.getPropertyName());
		System.out.println(filter.getPropertyVal());
		System.out.println(filter.getMatchType());
		System.out.println(filter.getPropertyType());
	}

}
