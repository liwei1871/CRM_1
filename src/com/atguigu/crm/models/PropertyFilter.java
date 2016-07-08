package com.atguigu.crm.models;

import javax.xml.crypto.Data;

/**
 * 
 * 属性过滤器 封装查询的属性信息
 */
public class PropertyFilter {

	// 属性名
	private String propertyName;
	// 属性值
	private Object propertyVal;
	// 目标属性的类型
	private Class propertyType;
	// 比较的方式（查询时sql如= LIKE等等）
	private MatchType matchType;

	public PropertyFilter(String filedName, Object filedValue) {

		// LIKES_custName/EQO_createBy （需要转换）
		String[] split = filedName.split("_");

		String propertyName = split[1]; // custName

		String matchAndType = split[0]; // LIKES

		String matchTypeStr = matchAndType.substring(0,
				matchAndType.length() - 1);// 包左不包含右

		String propertyTypeStr = matchAndType
				.substring(matchAndType.length() - 1);

		// -----------------
		this.propertyName = propertyName;
		this.propertyVal = filedValue;
		PropertyType propertyType = Enum.valueOf(PropertyType.class,
				propertyTypeStr);
		this.propertyType = propertyType.getPorpertyType();
		this.matchType = Enum.valueOf(MatchType.class, matchTypeStr);

	}

	// 枚举比较方式
	public enum MatchType {
		// 等于，大于，大于等于，小于，小于等于 ， LIKE
		EQ, GT, GE, LT, LE, LIKE,
	}

	// 枚举目标属性的类型
	public enum PropertyType {
		I(Integer.class), F(Float.class), S(String.class), D(Data.class), O(
				Object.class);

		private Class propertyType;

		// 构造器(枚举中只能有私有化构造器)
		private PropertyType(Class propertyType) {
			this.propertyType = propertyType;
		}

		public Class getPorpertyType() {
			return propertyType;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public void setPropertyVal(Object propertyVal) {
		this.propertyVal = propertyVal;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class propertyType) {
		this.propertyType = propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

}
