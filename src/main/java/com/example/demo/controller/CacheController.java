package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserCacheService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CacheController {
	@Autowired
	UserCacheService userCacheService;
	
	@Autowired
	CacheManager cacheManager;
	
	@GetMapping("/user/{loginId}/{token}")
	@ResponseBody
	public UserInfo getUser(@PathVariable String loginId, @PathVariable String token) {
		UserInfo user = userCacheService.getUserInfoCache(loginId, token);
		System.out.println(user);
		Timestamp now = new Timestamp(new Date().getTime()); 
		long nowt = now.getTime();
		long result = nowt - user.getLoginDate().getTime();
		log.info("user Login Elapsed time(millsec): "+result);
		return user;
	}
	
	@GetMapping("/update/user/{loginId}/{token}")
	@ResponseBody
	public UserInfo updateUser(@PathVariable String loginId, @PathVariable String token) {
		UserInfo user = userCacheService.updateUserInfoCache(loginId, token);
		System.out.println(user);
		return user;
	}
	
	 @GetMapping("/clearcache")
	 public void clearAllCaches() {
	    cacheManager.getCacheNames().stream()
	      .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	 }
	 
	 @GetMapping("/evictcache")
	 public void evictAllCaches() {
		 userCacheService.evictAllCacheValues();
	 }
	
}
