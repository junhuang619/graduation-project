package com.jh.xysp.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jh.xysp.entity.Area;
import com.jh.xysp.service.AreaService;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger=LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/listArea", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAreas() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());

		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
}
