package com.jh.xysp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.xysp.cache.JedisUtil;
import com.jh.xysp.dao.ShopCategoryDao;
import com.jh.xysp.entity.ShopCategory;
import com.jh.xysp.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;
    
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();

        // ����redis��key
        String key = SCLISTKEY;
        // ����jackson����ת��������
        ObjectMapper mapper = new ObjectMapper();

        // ����mapper�еĲ�ѯ������ƴװshopcategory��key
        if (shopCategoryCondition == null) {
            // ��ѯ����Ϊ�գ��г����е���ҳ���࣬��parentIdΪ�յĵ������
            key = key + "_allfirstlevelshopcategory";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getShopCategoryParentId() != null && shopCategoryCondition.getShopCategoryParentId().getShopCategoryId() != null) {
            // �г�ĳ��parentId�������������
            key = key + "_parent" + shopCategoryCondition.getShopCategoryParentId().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            // �г����е����࣬���������ĸ���
            key = key + "_allsecondlevelshopcategory";
        }

        // ��������в��������DB�в�ѯ�����浽redis��
        if (!jedisKeys.exists(key)) {
            try {
                // ��DB�м���
                shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
                // ����ص�ʵ���༯��ת����string,����redis�����Ӧ��key��
                String jsonString = mapper.writeValueAsString(shopCategoryList);
                jedisStrings.set(key, jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // ����ֱ�Ӵ�redis�л�ȡ
            try {
                String jsonString = jedisStrings.get(key);
                // ָ��Ҫ��stringת���ɵļ�������
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
                // �����key��Ӧ��value��ĵ�stringת����java�����ʵ���༯��
                shopCategoryList = mapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return shopCategoryList;
    }
}
