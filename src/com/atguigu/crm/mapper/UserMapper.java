package com.atguigu.crm.mapper;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.User;

public interface UserMapper {

	User getUserByName(@Param("name") String name);

}
