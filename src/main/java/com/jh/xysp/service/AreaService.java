package com.jh.xysp.service;

import java.util.List;


import com.jh.xysp.entity.Area;

public interface AreaService {
    public static final String AREALISTKEY = "arealist";
	/**�õ������б�
	 * @return
	 */
	List<Area> getAreaList();
}
