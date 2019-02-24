package com.jh.xysp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.xysp.cache.JedisUtil;
import com.jh.xysp.dao.AreaDao;
import com.jh.xysp.entity.Area;
import com.jh.xysp.exception.AreaOperationException;
import com.jh.xysp.service.AreaService;
@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;


	@Override
	public List<Area> getAreaList() {
		// 定义redis中的key
        String key = AREALISTKEY;
        // 定义接收对象
        List<Area> areaList = null;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)) {
        	areaList = areaDao.queryArea();
            String jsonString;
			try {
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
            jedisStrings.set(key, jsonString);
        }else {
        	String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new AreaOperationException(e.getMessage());
			}
        }
		return areaList;
	}

}
