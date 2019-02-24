package com.jh.xysp.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jh.xysp.dao.LocalAuthDao;
import com.jh.xysp.dto.LocalAuthExecution;
import com.jh.xysp.entity.LocalAuth;
import com.jh.xysp.enums.LocalAuthStateEnum;
import com.jh.xysp.exception.LocalAuthOperationException;
import com.jh.xysp.service.LocalAuthService;
import com.jh.xysp.util.MD5;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
	@Autowired
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth queryLocalAuthByUserNameAndPwd(String userName, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, password);
	}

	@Override
	public LocalAuth queryLocalByUserId(Long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		if (localAuth == null || localAuth.getLocalPassWord() == null || localAuth.getLocalUserName() == null
				|| localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
		if (tempAuth != null) {
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setLocalCreateTime(new Date());
			localAuth.setLocalLastEditTime(new Date());
			localAuth.setLocalPassWord(MD5.getMd5(localAuth.getLocalPassWord()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("ÕËºÅ°ó¶¨Ê§°Ü");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) throws LocalAuthOperationException {
		if (userId != null && userName != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password),
						MD5.getMd5(newPassword), new Date());
				if (effectedNum <= 0) {
					throw new LocalAuthOperationException("¸üÐÂÃÜÂëÊ§°Ü");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new LocalAuthOperationException("¸üÐÂÃÜÂëÊ§°Ü£º" + e.getMessage());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}
}
