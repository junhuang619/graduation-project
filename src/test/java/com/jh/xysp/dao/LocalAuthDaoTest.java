package com.jh.xysp.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.LocalAuth;
import com.jh.xysp.entity.PersonInfo;
import com.jh.xysp.util.MD5;

public class LocalAuthDaoTest extends BaseTest{
	@Autowired
    LocalAuthDao localAuthDao;

    @Test
    @Ignore
    public void testQueryLocalByUserNameAndPwd() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd("junhuang", MD5.getMd5("junhuang"));
        System.out.println(localAuth.toString());
    }
    @Test
    @Ignore
    public void testInsertLocalAuth() {
    	LocalAuth localAuth=new LocalAuth();
    	PersonInfo personInfo=new PersonInfo();
    	personInfo.setUserId(2L);
    	localAuth.setPersonInfo(personInfo);
    	localAuth.setLocalUserName("two");
    	localAuth.setLocalPassWord("two");
    	localAuth.setLocalCreateTime(new Date());
    	int effectedNum=localAuthDao.insertLocalAuth(localAuth);
    	assertEquals(1, effectedNum);
    }
    @Test
    @Ignore
    public void testQueryLocalByUserId() {
    	LocalAuth localAuth=localAuthDao.queryLocalByUserId(1L);
    	assertEquals("jh",localAuth.getPersonInfo().getUserName());
    }
    @Test
    public void testUpdateLocalAuth() {
    	Date now =new Date();
    	int effectedNum=localAuthDao.updateLocalAuth(2L, "two", "two", "newtwo", now);
    	assertEquals(1, effectedNum);
    	LocalAuth localAuth =localAuthDao.queryLocalByUserId(2L);
    	System.out.println(localAuth.getLocalPassWord());
    }
}
