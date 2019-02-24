package com.jh.xysp.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.LocalAuth;

public interface LocalAuthDao {
	/**
	 * 根据用户名和密码查询用户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);
	/**根据用户id查询用户
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId")Long userId);
	/**添加平台账号
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	/**更改用户密码
	 * @param userId
	 * @param username
	 * @param password
	 * @param newpassword
	 * @param lastEditTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
			@Param("password") String password,@Param("newPassword")String newPassword,@Param("lastEditTime")Date lastEditTime);
	
}
