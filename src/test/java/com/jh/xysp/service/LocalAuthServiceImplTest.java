package com.jh.xysp.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import com.jh.xysp.BaseTest;
import com.jh.xysp.dto.LocalAuthExecution;
import com.jh.xysp.entity.LocalAuth;
import com.jh.xysp.entity.PersonInfo;
import com.jh.xysp.util.MD5;

public class LocalAuthServiceImplTest extends BaseTest{
	@Autowired
    private LocalAuthService localAuthService;
	@Test
    public void testQueryLocalAuthByUserNameAndPwd() {
        LocalAuth localAuth = localAuthService.queryLocalAuthByUserNameAndPwd("junhuang", "junhuang");
        assertEquals(MD5.getMd5("junhuang"), localAuth.getLocalPassWord());
        System.out.println(localAuth.toString());
    }
	@Test
	@Ignore
	public void testModifyLocalAuth() {
		LocalAuthExecution lae=localAuthService.modifyLocalAuth(2L, "two", "newtwo", "two");
		LocalAuth localAuth=localAuthService.queryLocalAuthByUserNameAndPwd("two", "two");
		System.out.println(localAuth.getLocalUserName());
	}
}
