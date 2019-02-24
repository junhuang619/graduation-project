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

        // 定义redis中key
        String key = SCLISTKEY;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();

        // 根据mapper中的查询条件，拼装shopcategory的key
        if (shopCategoryCondition == null) {
            // 查询条件为空，列出所有的首页大类，即parentId为空的店铺类别
            key = key + "_allfirstlevelshopcategory";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getShopCategoryParentId() != null && shopCategoryCondition.getShopCategoryParentId().getShopCategoryId() != null) {
            // 列出某个parentId下面的所有子类
            key = key + "_parent" + shopCategoryCondition.getShopCategoryParentId().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            // 列出所有的子类，不管属于哪个类
            key = key + "_allsecondlevelshopcategory";
        }

        // 如果缓存中不出在则从DB中查询并缓存到redis中
        if (!jedisKeys.exists(key)) {
            try {
                // 从DB中加载
                shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
                // 将相关的实体类集合转换成string,存入redis里面对应的key中
                String jsonString = mapper.writeValueAsString(shopCategoryList);
                jedisStrings.set(key, jsonString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 否则直接从redis中获取
            try {
                String jsonString = jedisStrings.get(key);
                // 指定要将string转换成的集合类型
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
                // 将相关key对应的value里的的string转换成java对象的实体类集合
                shopCategoryList = mapper.readValue(jsonString, javaType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return shopCategoryList;
    }
}
