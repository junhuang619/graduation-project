package com.jh.xysp.dao;

import java.util.List;

import com.jh.xysp.entity.Area;

public interface AreaDao {
	
	/**列出区域列表
	 * @return arealist
	 */
	List<Area> queryArea();
}
