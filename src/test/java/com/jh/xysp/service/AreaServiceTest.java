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
		// 首次从db中加载
        List<Area> areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("||---->" + area.toString());
        }

        // 再次查询从redis中获取
        areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("**---->" + area.toString());
        }
        // 清除缓存
        cacheService.removeFromCache(AreaService.AREALISTKEY);

        // 再次查询 从db中获取
        areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("**---->" + area.toString());
        }

        // 再次查询从redis中获取
        areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println("**---->" + area.toString());
        }
	}
}
