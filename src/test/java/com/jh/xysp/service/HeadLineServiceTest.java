package com.jh.xysp.service;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.HeadLine;

public class HeadLineServiceTest extends BaseTest{
	@Autowired
    private HeadLineService headLineService;

    @Test
    public void testQueryHeadLineList() throws IOException {
        HeadLine headLineConditon = new HeadLine();
        // 状态 0 不可用 1 可用
        headLineConditon.setLineEnableStatus(0);

        // 查询不可用的头条信息
        List<HeadLine> headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("0<<<<" + headLine);
        }

        // 查询可用的头条信息
        headLineConditon.setLineEnableStatus(1);
        headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("**------>" + headLine);
        }

        // 再次查询 状态为0 和 1的头条信息 ，确保从缓存中取数据
        // 查询不可用的头条信息
        headLineConditon.setLineEnableStatus(0);
        headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("0>>>>" + headLine);
        }

        // 查询可用的头条信息
        headLineConditon.setLineEnableStatus(1);
        headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("||------>" + headLine);
        }

    }
}
