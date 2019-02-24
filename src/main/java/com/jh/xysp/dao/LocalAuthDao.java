package com.jh.xysp.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.LocalAuth;

public interface LocalAuthDao {
	/**
	 * �����û����������ѯ�û�
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);
	/**�����û�id��ѯ�û�
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalByUserId(@Param("userId")Long userId);
	/**���ƽ̨�˺�
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	/**�����û�����
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
