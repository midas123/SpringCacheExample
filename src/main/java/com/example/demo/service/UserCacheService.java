package com.example.demo.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.controller.CacheController;
import com.example.demo.entity.UserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserCacheService {
	@Cacheable(value="UserInfoCache", key="#loginId", condition="#loginId != null") 
    public UserInfo getUserInfoCache(String loginId, String token) {
		log.info("Cache Start");
		log.info("LoginId: "+loginId);
		Date d = new Date();
		log.info("Cache end");
        return new UserInfo(loginId, token, new Timestamp(d.getTime()));
    }
	
	@CacheEvict(value="UserInfoCache", key="#loginId", condition="#loginId != null") 
    public UserInfo updateUserInfoCache(String loginId, String token) {
		log.info("Cache Start");
		log.info("LoginId: "+loginId);
		Date d = new Date();
		log.info("Cache end");
        return new UserInfo(loginId, token, new Timestamp(d.getTime()));
    }
	
	@CacheEvict(value = "UserInfoCache", allEntries = true)
	public void evictAllCacheValues() {}
}
