package com.jh.xysp.service;

import java.util.List;


import com.jh.xysp.entity.Area;

public interface AreaService {
    public static final String AREALISTKEY = "arealist";
	/**得到区域列表
	 * @return
	 */
	List<Area> getAreaList();
}
