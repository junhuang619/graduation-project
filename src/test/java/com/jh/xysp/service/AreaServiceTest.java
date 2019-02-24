package com.jh.xysp.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Autowired
    CacheService cacheService;
	@Test
	public void testGetAreaList() {
		// �״δ�db�м���
        List<Area> areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("||---->" + area.toString());
        }

        // �ٴβ�ѯ��redis�л�ȡ
        areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("**---->" + area.toString());
        }
        // �������
        cacheService.removeFromCache(AreaService.AREALISTKEY);

        // �ٴβ�ѯ ��db�л�ȡ
        areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("**---->" + area.toString());
        }

        // �ٴβ�ѯ��redis�л�ȡ
        areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("**---->" + area.toString());
        }
	}
}
