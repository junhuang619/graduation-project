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
		// ����Key
        String key = HEADLINEKEY;
        // ����jackson����ת��������
        ObjectMapper mapper = new ObjectMapper();
     // ����mapper�еĲ�ѯ���� ƴװkey
        // ���ݲ�ͬ���������治ͬ��keyֵ ������3�ֻ��� headline_0 headline_1 �� headline �������ԱȨ�޲���
        if (headLineConditon != null && headLineConditon.getLineEnableStatus() != null) {
            key = key + "_" + headLineConditon.getLineEnableStatus();
        }// ��������ڣ������ݿ��л�ȡ���ݣ�Ȼ��д��redis
        if(!jedisKeys.exists(key)){
            try {
                // ��DB�л�ȡ����
                headLineList = headLineDao.selectHeadLineList(headLineConditon);
                // ����ص�ʵ���༯��ת����string,����redis�����Ӧ��key��
                String jsonString = mapper.writeValueAsString(headLineList);
                jedisStrings.set(key, jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // ����ֱ�Ӵ�redis�л�ȡ
            try {
                // �����ڣ���ֱ�Ӵ�redis����ȡ����Ӧ����
                String jsonString = jedisStrings.get(key);
                // ָ��Ҫ��stringת���ɵļ�������
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
                // �����key��Ӧ��value��ĵ�stringת����java�����ʵ���༯��
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return headLineList;
	}
}
