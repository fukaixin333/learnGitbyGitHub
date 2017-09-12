package com.citic.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.citic.server.ApplicationCFG;
import com.citic.server.SpringContextHolder;
import com.citic.server.service.cacheload.BaseCache;
import com.citic.server.service.cacheload.CacheFactory;

/**
 * @author hubaiqing
 */

@Service("cacheService")
public class CacheService {
	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
	
	private String _cacheName = "serverCache";
	
	public CacheService() {
	}
	
	/**
	 * @return
	 */
	public EhCacheCacheManager getCacheManager() {
		EhCacheCacheManager cm = null;
		if (cm == null) {
			cm = (EhCacheCacheManager) SpringContextHolder.getBean("cacheManager");
			
			ApplicationCFG applicationCFG = (ApplicationCFG) SpringContextHolder.getBean("applicationCFG");
			String cname = applicationCFG.getServer_cachename();
			if (!(cname == null || cname.length() == 0)) {
				_cacheName = cname;
			}
		}
		return cm;
	}
	
	public void putCache(String key, Object obj) {
		this.putCache(_cacheName, key, obj);
	}
	
	private void putCache(String cacheName, String key, Object obj) {
		try {
			// 获取缓存
			Cache cache = (Cache) this.getCacheManager().getCache(cacheName);
			cache.put(key, obj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("增加缓存：" + cacheName + "," + key + "过程失败,缓存内容＝" + obj.toString());
		}
	}
	
	public <T> T getCache(String key, Class<T> _class) {
		return this.getCache(_cacheName, key, _class);
	}
	
	private <T> T getCache(String cacheName, String key, Class<T> _class) {
		// 获取缓存
		Cache cache = (Cache) this.getCacheManager().getCache(cacheName);
		T cacheObj = null;
		try {
			if (cache == null) {
				throw new Exception("缓存:" + cacheName + "未被加载，需要重新初始化");
			}
			
			cacheObj = cache.get(key, _class);
			if (cacheObj == null) {
				throw new Exception("缓存:" + cacheName + "," + key + "未被加载，需要重新初始化");
			}
		} catch (Exception e) {
			// 这个过程可能是正常报错！
			this.initCache(cacheName, key);
			try {
				cacheObj = cache.get(key, _class);
			} catch (Exception ee) {
				ee.printStackTrace();
				logger.error("缓存:" + cacheName + "," + key + "没能取到！");
			}
		}
		
		return cacheObj;
	}
	
	public void removeCache(String key) {
		this.removeCache(_cacheName, key);
	}
	
	public void removeCache(String cacheName, String key) {
		try {
			// 获取缓存
			Cache cache = (Cache) this.getCacheManager().getCache(cacheName);
			cache.evict(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("缓存:" + cacheName + "," + key + "清理失败!");
		}
		
	}
	
	public void clearAllCache() {
		this.removeCache(_cacheName);
	}
	
	public void clearAllCache(String cacheName) {
		try {
			// 获取缓存
			Cache cache = (Cache) this.getCacheManager().getCache(cacheName);
			
			cache.clear();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("清理全部缓存:" + cacheName + "过程失败!");
		}
	}
	
	/**
	 * 所有需要缓存的对象进行初始化
	 * 
	 * @param cacheName
	 * @param key
	 */
	private void initCache(String cacheName, String key) {
		try {
			// 获取缓存
			// Cache cache = (Cache) this.getCacheManager().getCache(cacheName);
			CacheFactory cacheFactory = new CacheFactory();
			BaseCache baseCache = cacheFactory.getInstance(SpringContextHolder.getApplicationContext(), key);
			Object cacheObj = baseCache.getCacheByName();
			if (cacheObj != null) {
				this.putCache(key, cacheObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("缓存：" + cacheName + "," + key + "初始化过程失败!");
		}
	}
}
