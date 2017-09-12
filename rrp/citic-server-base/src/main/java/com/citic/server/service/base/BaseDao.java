/**========================================================
 * Copyright (c) 2014-2015 by CITIC All rights reserved.
 * Created Date : 2015年3月12日 下午9:55:17
 * Description:
 *
 *=========================================================
 */
package com.citic.server.service.base;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @author gaojianxin
 *
 */
public class BaseDao extends SqlSessionDaoSupport {

	/**
	 * 插入一条数据到数据库中
	 *
	 * @param mapperId
	 * @param parmObj
	 * @return
	 */
	public int insert(String mapperId, Object parmObj) {
		int result = getSqlSession().insert(mapperId, parmObj);
		return result;
	}

	/**
	 * 删除数据
	 *
	 * @param mapperId
	 * @param parmObj
	 * @return
	 */
	public int delete(String mapperId, Object parmObj) {
		return getSqlSession().delete(mapperId, parmObj);
	}

	/**
	 *
	 * @param mapperId
	 * @param parmObj
	 * @return
	 */
	public int update(String mapperId, Object parmObj) {
		return getSqlSession().update(mapperId, parmObj);

	}

	/**
	 * 查询单条记录
	 *
	 * @param mapperId
	 * @param parmObj
	 * @return
	 */
	public <T> T selectOne(String mapperId, Object parmObj) {

		return getSqlSession().selectOne(mapperId, parmObj);

	}

	/**
	 * 查询多条记录
	 *
	 * @param mapperId
	 * @param parmObj
	 * @return
	 */
	public <T> List<T> selectList(String mapperId, Object parmObj) {
		SqlSession sqlSession = getSqlSession();
		List<T> list = sqlSession.selectList(mapperId, parmObj);

		return list;
	}

}
