package com.citic.server.dict;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.citic.server.net.mapper.BB13_DictGroupMapper;
import com.citic.server.runtime.IllegalDataException;
import com.citic.server.service.CacheService;

/**
 * 数据字典码值转换组件
 * 
 * @author Liu Xuanfei
 * @date 2016年9月17日 下午9:19:04
 */
@Component
public class DictCoder {
	
	private Logger logger = LoggerFactory.getLogger(DictCoder.class);
	
	@Autowired
	private BB13_DictGroupMapper mapper;
	
	@Autowired
	@Qualifier("cacheService")
	private CacheService cacheService; // 缓存服务
	
	private HashMap<String, List<DictGroup>> groupMap = new HashMap<String, List<DictGroup>>();
	private HashMap<String, List<DictGroupItem>> itemMap = new HashMap<String, List<DictGroupItem>>();
	
	private HashMap<String, String> src2Dest = new HashMap<String, String>();
	private HashMap<String, String> dest2Src = new HashMap<String, String>();
	
	private boolean initialized = false;
	private boolean validateArtifactItemDefaultValue = true;
	
	public void transcode(List<DictBean> beanList, String taskType) {
		for (DictBean bean : beanList) {
			transcode(bean, taskType);
		}
	}
	
	/**
	 * 转码。将源码值转换成目标码值。
	 * 
	 * @param bean 待转码 JavaBean
	 * @param taskType 后缀，任务类别
	 * @return 转码后 JavaBean
	 */
	
	public DictBean transcode(DictBean bean, String taskType) {
		if (bean == null) {
			return null;
		}
		
		String groupId = bean.getGroupId();
		if (groupId == null || groupId.length() == 0) {
			return bean;
		}
		
		List<DictGroup> groupList = null;
		if (taskType == null || taskType.length() == 0) {
			groupList = groupMap.get(groupId);
		} else {
			groupList = groupMap.get(groupId + "_" + taskType);
			if (groupList == null || groupList.size() == 0) {
				groupList = groupMap.get(groupId);
			}
		}
		
		if (groupList == null || groupList.size() == 0) {
			return bean;
		}
		
		for (DictGroup group : groupList) {
			try {
				String fieldName = group.getFieldName();
				String srcVal = BeanUtils.getProperty(bean, fieldName);
				if (srcVal == null || srcVal.length() == 0) {
					continue;
				}
				
				String artifactId = group.getArtifactId();
				List<DictGroupItem> itemList = itemMap.get(artifactId);
				if (itemList == null || itemList.size() == 0) {
					continue;
				}
				
				String key = artifactId + "&" + srcVal;
				String destVal = src2Dest.get(key);
				if (destVal == null) {
					boolean findDefaultVal = false;
					for (DictGroupItem item : itemList) {
						String srcCode = item.getSrcCode();
						String flag = item.getFlag();
						if (srcVal.equals(srcCode)) {
							if ("1".equals(flag)) {
								destVal = item.getDestCode();
								if (validateArtifactItemDefaultValue) {
									if (findDefaultVal) {
										throw new IllegalDataException(item.toString(), "目标码值不唯一");
									}
								} else {
									break;
								}
								findDefaultVal = true;
							} else {
								if (findDefaultVal) {
									continue;
								}
								destVal = item.getDestCode();
							}
						}
					}
					src2Dest.put(key, destVal);
				}
				
				if (destVal == null) {
					logger.warn("码值转换失败，源码值 {}.{}.'{}' 缺少目标码值，请检查转码数据字典的完整性。", group.getGroupId(), group.getFieldName(), srcVal);
				} else {
					BeanUtils.setProperty(bean, fieldName, destVal);
				}
			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
			}
		}
		
		return bean;
	}
	
	public void reverse(List<DictBean> beanList, String taskType) {
		for (DictBean bean : beanList) {
			reverse(bean, taskType);
		}
	}
	
	/**
	 * 逆转码。将目标码值转换成源码值。
	 * <p>
	 * 如果<em>taskType</em>不为空，则转码时使用的GroupId会在{@link DictBean#getGroupId()}返回值的后面加上"_
	 * <em>taskType</em>"；否则，直接使用返回值作为GroupId。<br />
	 * 如果<em>taskType</em>不为空，且"*_<em>taskType</em>"作为GroupId没有在数据库进行配置，默认依然会取
	 * {@link DictBean#getGroupId()}返回值作为GroupId。<br />
	 * 
	 * @param bean 待转码 JavaBean
	 * @param taskType 后缀，任务类别
	 * @return 转码后 JavaBean
	 */
	@SuppressWarnings("unchecked")
	public DictBean reverse(DictBean bean, String taskType) {
		if (bean == null) {
			return null;
		}
		
		String groupId = bean.getGroupId();
		if (groupId == null || groupId.length() == 0) {
			return bean;
		}
		
		List<DictGroup> groupList = groupMap.get(groupId);
		if (taskType == null || taskType.length() == 0) {
			groupList = groupMap.get(groupId);
		} else {
			groupList = groupMap.get(groupId + "_" + taskType);
			if (groupList == null || groupList.size() == 0) {
				groupList = groupMap.get(groupId);
			}
		}
		
		if (groupList == null || groupList.size() == 0) {
			return bean;
		}
		
		for (DictGroup group : groupList) {
			try {
				String fieldName = group.getFieldName();
				String destVal = BeanUtils.getProperty(bean, fieldName);
				if (destVal == null || destVal.length() == 0) {
					continue;
				}
				
				String artifactId = group.getArtifactId();
				
				// 如果artifactId是特殊值（${}）
				String srcVal = null;
				if ("${ORGAN}".equals(artifactId)) {
					HashMap<String, String> organDetailMap = (HashMap<String, String>) cacheService.getCache("DorganDetail", HashMap.class);
					srcVal = organDetailMap.get(destVal);
				} else {
					List<DictGroupItem> itemList = itemMap.get(artifactId);
					if (itemList == null || itemList.size() == 0) {
						continue;
					}
					
					String key = artifactId + "&" + destVal;
					if (dest2Src.containsKey(key)) {
						srcVal = dest2Src.get(key);
					} else {
						boolean findDefaultVal = false;
						for (DictGroupItem item : itemList) {
							String destCode = item.getDestCode();
							String flag = item.getFlag();
							if (destVal.equals(destCode)) {
								if ("1".equals(flag)) {
									srcVal = item.getSrcCode();
									if (validateArtifactItemDefaultValue) {
										if (findDefaultVal) {
											throw new IllegalDataException(item.toString(), "源码值不唯一");
										}
									} else {
										break;
									}
									findDefaultVal = true;
								} else {
									if (findDefaultVal) {
										continue;
									}
									srcVal = item.getSrcCode();
								}
							}
						}
						dest2Src.put(key, srcVal);
					}
				}
				
				if (srcVal == null) {
					logger.warn("码值转换失败，目标码值 {}.{}.'{}' 缺少源码值，请检查转码数据字典的完整性。", group.getGroupId(), group.getFieldName(), destVal);
				} else {
					BeanUtils.setProperty(bean, fieldName, srcVal);
				}
			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
			}
		}
		
		return bean;
	}
	
	@PostConstruct
	private void initialize() {
		if (!initialized) {
			synchronized (this) {
				if (!initialized) {
					List<DictGroup> allGroupList = mapper.queryAllDictGroup();
					List<DictGroupItem> allItemList = mapper.queryAllDictGroupItem();
					
					if (allGroupList == null || allGroupList.size() == 0 || allItemList == null || allItemList.size() == 0) {
						logger.warn("转码数据字典未配置任何数据，请确认应用程序不使用 DictBean 或检查初始化数据的完整性。");
					}
					
					for (DictGroup group : allGroupList) {
						String groupId = group.getGroupId();
						List<DictGroup> groupList = groupMap.get(groupId);
						if (groupList == null) {
							groupList = new ArrayList<DictGroup>();
							groupMap.put(groupId, groupList);
						}
						groupList.add(group);
					}
					
					for (DictGroupItem item : allItemList) {
						String artifactId = item.getArtifactId();
						List<DictGroupItem> itemList = itemMap.get(artifactId);
						if (itemList == null) {
							itemList = new ArrayList<DictGroupItem>();
							itemMap.put(artifactId, itemList);
						}
						itemList.add(item);
					}
					
					initialized = true;
				}
			}
		}
	}
}
