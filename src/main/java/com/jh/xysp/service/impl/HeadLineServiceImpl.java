package com.jh.xysp.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.xysp.cache.JedisUtil;
import com.jh.xysp.dao.HeadLineDao;
import com.jh.xysp.entity.HeadLine;
import com.jh.xysp.service.HeadLineService;
@Service
public class HeadLineServiceImpl implements HeadLineService {

	@Autowired
    private HeadLineDao headLineDao;
	
	@Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

	
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineConditon) throws IOException {
		List<HeadLine> headLineList = new ArrayList<HeadLine>();
		// 定义Key
        String key = HEADLINEKEY;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
     // 根据mapper中的查询条件 拼装key
        // 根据不同的条件缓存不同的key值 这里有3种缓存 headline_0 headline_1 和 headline 方便管理员权限操作
        if (headLineConditon != null && headLineConditon.getLineEnableStatus() != null) {
            key = key + "_" + headLineConditon.getLineEnableStatus();
        }// 如果不存在，从数据库中获取数据，然后写入redis
        if(!jedisKeys.exists(key)){
            try {
                // 从DB中获取数据
                headLineList = headLineDao.selectHeadLineList(headLineConditon);
                // 将相关的实体类集合转换成string,存入redis里面对应的key中
                String jsonString = mapper.writeValueAsString(headLineList);
                jedisStrings.set(key, jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 否则直接从redis中获取
            try {
                // 若存在，则直接从redis里面取出相应数据
                String jsonString = jedisStrings.get(key);
                // 指定要将string转换成的集合类型
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
                // 将相关key对应的value里的的string转换成java对象的实体类集合
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return headLineList;
	}
}
