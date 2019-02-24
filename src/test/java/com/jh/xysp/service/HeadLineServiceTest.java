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
        // ״̬ 0 ������ 1 ����
        headLineConditon.setLineEnableStatus(0);

        // ��ѯ�����õ�ͷ����Ϣ
        List<HeadLine> headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("0<<<<" + headLine);
        }

        // ��ѯ���õ�ͷ����Ϣ
        headLineConditon.setLineEnableStatus(1);
        headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("**------>" + headLine);
        }

        // �ٴβ�ѯ ״̬Ϊ0 �� 1��ͷ����Ϣ ��ȷ���ӻ�����ȡ����
        // ��ѯ�����õ�ͷ����Ϣ
        headLineConditon.setLineEnableStatus(0);
        headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("0>>>>" + headLine);
        }

        // ��ѯ���õ�ͷ����Ϣ
        headLineConditon.setLineEnableStatus(1);
        headLineList = headLineService.getHeadLineList(headLineConditon);
        for (HeadLine headLine : headLineList) {
            System.out.println("||------>" + headLine);
        }

    }
}
