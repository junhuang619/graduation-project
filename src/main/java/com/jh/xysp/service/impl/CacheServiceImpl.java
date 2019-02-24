package com.jh.xysp.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.xysp.cache.JedisUtil;
import com.jh.xysp.service.CacheService;
@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
    JedisUtil.Keys jedisKeys;

	@Override
	public void removeFromCache(String keyPrefix) {
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
        for (String key : keySet) {
            jedisKeys.del(key);
        }
	}

}
