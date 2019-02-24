package com.jh.xysp.service;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.dto.LocalAuthExecution;
import com.jh.xysp.entity.LocalAuth;
import com.jh.xysp.exception.LocalAuthOperationException;

public interface LocalAuthService {
	/**
	 * ͨ���˻������ȡƽ̨�˺���Ϣ
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalAuthByUserNameAndPwd(String userName, String password);

	LocalAuth queryLocalByUserId(Long userId);

	LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

	LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) throws LocalAuthOperationException;
}
