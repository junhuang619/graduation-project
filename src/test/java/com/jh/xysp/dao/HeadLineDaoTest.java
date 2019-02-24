package com.jh.xysp.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.HeadLine;

public class HeadLineDaoTest extends BaseTest{
	@Autowired
    private HeadLineDao headLineDao;
	@Test
	public void testSelectHeadLineList() {
		List<HeadLine> headLine=headLineDao.selectHeadLineList(new HeadLine());
		assertEquals(4, headLine.size());
	}
}
