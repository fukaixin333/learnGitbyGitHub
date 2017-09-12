package com.citic.server.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.citic.server.SpringContextHolder;
import com.citic.server.net.mapper.PollingTaskMapper;
import com.citic.server.service.CacheService;
import com.citic.server.service.domain.MC20_Task_Fact1;
import com.citic.server.service.domain.MC21_task;
import com.citic.server.utils.StrUtils;

/**
 * Basic轮询任务
 * 
 * @author Liu Xuanfei
 * @date 2016年4月7日 下午2:40:09
 */
public abstract class AbstractPollingTask implements IPollingTask {
	
	private final HashMap<String, MC21_task> taskClassDefCache = new HashMap<String, MC21_task>(); // 缓存任务配置
	private final List<String> receivedTaskKeyCache = new ArrayList<String>(); // 缓存接收过的任务
	
	private boolean _refreshCache = true; // 是否刷新缓存
	private long lastRefreshCacheTime = -1; // 上一次更新缓存时间
	private long refresh_cache_period = 2 * 3600 * 1000; // 缓存刷新间隔时间(ms)
	
	/** 是否立即执行（在服务器第一次启动时，往往需要立即执行一次，忽略间隔时间） */
	private boolean executeImmediately = true;
	protected int executeActionNumber = 0; // Action执行次数（每分钟执行一次）
	private String executeActionMarker = null;
	
	@Autowired
	protected PollingTaskMapper service;
	
	/** *** */
	protected String organkey;
	
	/**
	 * 任务处理逻辑
	 */
	public abstract void executeAction();
	
	/**
	 * 用于缓存的任务对象（参考ServerEnvironment中的常量）
	 */
	protected abstract String getTaskType();
	
	protected abstract String getExecutePeriodExpression();
	
	@Override
	public IPollingTask initPollingTask() throws Exception {
		return this;
	}
	
	@Override
	public void execute() {
		beforeExecuteAction();
		if (validateExecuteActionPeriod()) {
			executeAction(); // 执行任务处理逻辑
		}
		afterExecuteAction();
	}
	
	protected void beforeExecuteAction() {
		if (_refreshCache) {
			refreshCache0(); // 更新缓存
		}
	}
	
	protected void afterExecuteAction() {
	}
	
	private void refreshCache0() {
		if (StringUtils.isBlank(getTaskType())) {
			return;
		}
		
		if (lastRefreshCacheTime < 0 || (System.currentTimeMillis() - lastRefreshCacheTime) >= refresh_cache_period) {
			// 缓存任务配置信息
			taskClassDefCache.clear();
			List<MC21_task> tasks = service.queryMC21_Task(getTaskType());
			String code;
			for (MC21_task task : tasks) {
				code = task.getTxCode();
				if (code == null || code.equals("")) {
					continue;
				}
				if (taskClassDefCache.containsKey(code)) {
					throw new RuntimeException("Multiple task defined for code '" + code + "'.");
				}
				taskClassDefCache.put(code, task);
			}
			
			// 缓存已接收且尚未反馈结果的任务信息
			receivedTaskKeyCache.clear();
			Collection<String> c = getReceivedTaskKeyForCaching();
			if (c != null) {
				receivedTaskKeyCache.addAll(c);
			}
			
			lastRefreshCacheTime = System.currentTimeMillis(); // 
			System.gc(); //
		}
	}
	
	/**
	 * 获取需要缓存的任务主键
	 * <p>
	 * 默认缓存{@link #getTaskType()}指定渠道的Task1所有接收到的任务主键。
	 * 
	 * @return The primary key used for caching
	 * @author liuxuanfei
	 * @date 2017/07/11 12:58:30
	 */
	protected List<String> getReceivedTaskKeyForCaching() {
		List<MC20_Task_Fact1> taskFactList = service.queryMC20_Task_Fact1(getTaskType());
		if (taskFactList == null || taskFactList.size() == 0) {
			return null;
		}
		
		List<String> caching = new ArrayList<String>(taskFactList.size());
		for (MC20_Task_Fact1 taskFact : taskFactList) {
			caching.add(taskFact.getTaskKey());
		}
		return caching;
	}
	
	public boolean validateExecuteActionPeriod() {
		// 如果是立即执行，通常是在应用程序第一次启动时，需要忽略间隔时间。
		// 例如：系统测试时、生产出现Bug需要重启服务时等。
		if (executeImmediately) {
			executeImmediately = false;
			
			// 定时模式（每*）时，标记立即执行的时间，否则重启服务后的（每*）会执行两次。
			// 某些情境（每*）执行两次会导致应用程序错误（比如动态查询的主键冲突等）。
			Calendar calendar = Calendar.getInstance(Locale.CHINESE);
			calendar.setTimeInMillis(System.currentTimeMillis());
			executeActionMarker = calendar.get(Calendar.YEAR) + "_" + calendar.get(Calendar.DAY_OF_YEAR);
			return true;
		}
		
		String expression = getExecutePeriodExpression();
		if (StringUtils.isBlank(expression)) {
			throw new IllegalArgumentException("The expression must not be null, empty or whitespace.");
			// return true;
		}
		
		if (!expression.contains("|")) {
			executeActionNumber++;
			if (Integer.parseInt(expression) <= executeActionNumber) {
				executeActionNumber = 0;
				return true;
			}
		} else {
			String[] tokens = StringUtils.split(expression, "|");
			if ("每天".equals(tokens[0])) {
				if (StringUtils.isNotBlank(tokens[1])) {
					Calendar calendar = Calendar.getInstance(Locale.CHINESE);
					calendar.setTimeInMillis(System.currentTimeMillis());
					String timeMark = calendar.get(Calendar.YEAR) + "_" + calendar.get(Calendar.DAY_OF_YEAR);
					if (!StringUtils.equals(executeActionMarker, timeMark)) {
						SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.CHINESE);
						try {
							Calendar defCalendar = Calendar.getInstance();
							defCalendar.setTime(format.parse(tokens[1]));
							int hour = calendar.get(Calendar.HOUR_OF_DAY);
							int defHour = defCalendar.get(Calendar.HOUR_OF_DAY);
							if (hour > defHour || (hour == defHour && calendar.get(Calendar.MINUTE) >= defCalendar.get(Calendar.MINUTE))) {
								executeActionMarker = timeMark;
								return true;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				throw new IllegalArgumentException("Invalid type " + tokens[0] + ". Must be one of: -, 每天.");
			}
		}
		
		return false;
	}
	
	public void setRefreshCache(boolean bool) {
		this._refreshCache = bool;
	}
	
	public void setRefreshCachePeriod(long period) {
		this.refresh_cache_period = period;
	}
	
	public void setExecuteImmediately(boolean immediately) {
		this.executeImmediately = immediately;
	}
	
	public boolean isMessageReceived(String taskKey) {
		return receivedTaskKeyCache.contains(taskKey);
	}
	
	public boolean isHasTaskClass(String txCode) {
		if (taskClassDefCache.isEmpty()) {
			return false;
		}
		return taskClassDefCache.containsKey(txCode);
	}
	
	public MC21_task getTaskClassDef(String txCode) {
		return taskClassDefCache.get(txCode);
	}
	
	public void cacheReceivedTaskKey(String taskKey) {
		if (taskKey == null || taskKey.equals("")) {
			return;
		}
		receivedTaskKeyCache.add(taskKey);
	}
	
	@SuppressWarnings("rawtypes")
	protected String getOrganKey() {
		if (this.organkey == null) {
			CacheService cacheService = SpringContextHolder.getBean("cacheService"); // 缓存服务
			HashMap sysParaMap = ((HashMap) cacheService.getCache("sysParaDetail", HashMap.class));
			this.organkey = StrUtils.null2String((String) sysParaMap.get("innerOrgKey"));
		}
		return this.organkey;
	}
}
