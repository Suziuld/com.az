package com.bjpowernode.core.common.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.bjpowernode.core.annotation.JeecgEntityTitle;
import com.bjpowernode.core.common.dao.IGenericBaseCommonDao;
import com.bjpowernode.core.common.dao.jdbc.JdbcDao;
import com.bjpowernode.core.common.exception.BusinessException;
import com.bjpowernode.core.common.hibernate.qbc.CriteriaQuery;
import com.bjpowernode.core.common.hibernate.qbc.DetachedCriteriaUtil;
import com.bjpowernode.core.common.hibernate.qbc.HqlQuery;
import com.bjpowernode.core.common.hibernate.qbc.PageList;
import com.bjpowernode.core.common.hibernate.qbc.PagerUtil;
import com.bjpowernode.core.common.model.common.DBTable;
import com.bjpowernode.core.common.model.json.DataGridReturn;
import com.bjpowernode.core.util.MyBeanUtils;
import com.bjpowernode.core.util.ToEntityUtil;
import com.bjpowernode.core.util.oConvertUtils;
import com.bjpowernode.tag.vo.datatable.DataTableReturn;

/**
 * 
 * ???????????? DAO???????????????
 * 
 *  
 * @date??? ?????????2012-12-7 ???????????????10:16:48
 * @param <T>
 * @param <PK>
 * @version 1.0
 */
@SuppressWarnings("hiding")
public abstract class GenericBaseCommonDao<T, PK extends Serializable>
		implements IGenericBaseCommonDao {
	/**
	 * ?????????Log4j???????????????
	 */
	private static final Logger logger = Logger
			.getLogger(GenericBaseCommonDao.class);
	/**
	 * ????????????sessionFactory??????,??????????????????(HibernateDaoSupport)
	 * **/
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		// ????????????????????????(Required)?????????????????????
		return sessionFactory.getCurrentSession();
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param entityName
	 *            ??????????????????
	 */
	private <T> void getProperty(Class entityName) {
		ClassMetadata cm = sessionFactory.getClassMetadata(entityName);
		String[] str = cm.getPropertyNames(); // ?????????????????????????????????
		for (int i = 0; i < str.length; i++) {
			String property = str[i];
			String type = cm.getPropertyType(property).getName(); // ????????????????????????
			com.bjpowernode.core.util.LogUtil.info(property + "---&gt;" + type);
		}
	}

	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	public List<DBTable> getAllDbTableName() {
		List<DBTable> resultList = new ArrayList<DBTable>();
		SessionFactory factory = getSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		for (String key : (Set<String>) metaMap.keySet()) {
			DBTable dbTable = new DBTable();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
					.get(key);
			dbTable.setTableName(classMetadata.getTableName());
			dbTable.setEntityName(classMetadata.getEntityName());
			Class<?> c;
			try {
				c = Class.forName(key);
				JeecgEntityTitle t = c.getAnnotation(JeecgEntityTitle.class);
				dbTable.setTableTitle(t != null ? t.name() : "");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			resultList.add(dbTable);
		}
		return resultList;
	}

	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	public Integer getAllDbTableSize() {
		SessionFactory factory = getSession().getSessionFactory();
		Map<String, ClassMetadata> metaMap = factory.getAllClassMetadata();
		return metaMap.size();
	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * ???????????????????????????.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		Assert.hasText(propertyName);
		return (List<T>) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * ????????????????????????????????????
	 */
	public <T> Serializable save(T entity) {
		try {
			Serializable id = getSession().save(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("??????????????????," + entity.getClass().getName());
			}
			return id;
		} catch (RuntimeException e) {
			logger.error("??????????????????", e);
			throw e;
		}

	}

	/**
	 * ??????????????????
	 * 
	 * @param <T>
	 * @param entitys
	 *            ???????????????????????????????????????
	 */
	public <T> void batchSave(List<T> entitys) {
		for (int i = 0; i < entitys.size(); i++) {
			getSession().save(entitys.get(i));
			if (i % 20 == 0) {
				// 20?????????????????????????????????????????????
				getSession().flush();
				getSession().clear();
			}
		}
		// ??????????????????----????????????20??????40????????????
		getSession().flush();
		getSession().clear();
	}

	/**
	 * ??????????????????????????????????????????
	 * 
	 * @param <T>
	 * 
	 * @param entity
	 */

	public <T> void saveOrUpdate(T entity) {
		try {
			getSession().saveOrUpdate(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("?????????????????????," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("?????????????????????", e);
			throw e;
		}
	}

	/**
	 * ?????????????????????????????????
	 */
	public <T> void delete(T entity) {
		try {
			getSession().delete(entity);
			getSession().flush();
			if (logger.isDebugEnabled()) {
				logger.debug("????????????," + entity.getClass().getName());
			}
		} catch (RuntimeException e) {
			logger.error("????????????", e);
			throw e;
		}
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		delete(get(entityName, id));
		getSession().flush();
	}

	/**
	 * ?????????????????????
	 * 
	 * @param <T>
	 * 
	 * @param entitys
	 */
	public <T> void deleteAllEntitie(Collection<T> entitys) {
		for (Object entity : entitys) {
			getSession().delete(entity);
			getSession().flush();
		}
	}

	/**
	 * ??????Id???????????????
	 */
	public <T> T get(Class<T> entityClass, final Serializable id) {

		return (T) getSession().get(entityClass, id);

	}

	/**
	 * ????????????????????????????????????
	 * 
	 * @param <T>
	 * @param entityName
	 * @param id
	 * @param lock
	 * @return
	 */
	public <T> T getEntity(Class entityName, Serializable id) {

		T t = (T) getSession().get(entityName, id);
		if (t != null) {
			getSession().flush();
		}
		return t;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo) {
		getSession().update(pojo);
		getSession().flush();
	}

	/**
	 * ?????????????????????
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(String className, Object id) {
		getSession().update(className, id);
		getSession().flush();
	}

	/**
	 * ????????????????????????
	 */
	public <T> void updateEntityById(Class entityName, Serializable id) {
		updateEntitie(get(entityName, id));
	}

	/**
	 * ??????hql ????????????????????????
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public List<T> findByQueryString(final String query) {

		Query queryObject = getSession().createQuery(query);
		List<T> list = queryObject.list();
		if (list.size() > 0) {
			getSession().flush();
		}
		return list;

	}

	/**
	 * ??????hql??????????????????
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> T singleResult(String hql) {
		T t = null;
		Query queryObject = getSession().createQuery(hql);
		List<T> list = queryObject.list();
		if (list.size() == 1) {
			getSession().flush();
			t = list.get(0);
		} else if (list.size() > 0) {
			throw new BusinessException("???????????????:" + list.size() + "??????1");
		}
		return t;
	}

	/**
	 * ??????hql ??????????????????HashMap??????
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public Map<Object, Object> getHashMapbyQuery(String hql) {

		Query query = getSession().createQuery(hql);
		List list = query.list();
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object[] tm = (Object[]) iterator.next();
			map.put(tm[0].toString(), tm[1].toString());
		}
		return map;

	}

	/**
	 * ??????sql????????????
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public int updateBySqlString(final String query) {

		Query querys = getSession().createSQLQuery(query);
		return querys.executeUpdate();
	}

	/**
	 * ??????sql????????????????????????
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public List<T> findListbySql(final String sql) {
		Query querys = getSession().createSQLQuery(sql);
		return querys.list();
	}

	/**
	 * ??????Criteria???????????????????????????
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param orderBy
	 * @param isAsc
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass, boolean isAsc,
			Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		if (isAsc) {
			criteria.addOrder(Order.asc("asc"));
		} else {
			criteria.addOrder(Order.desc("desc"));
		}
		return criteria;
	}

	/**
	 * ??????Criteria?????????????????????
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public <T> List<T> loadAll(final Class<T> entityClass) {
		Criteria criteria = createCriteria(entityClass);
		return criteria.list();
	}

	/**
	 * ????????????Criteria??????
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria;
	}

	/**
	 * ?????????????????????????????????. ?????????
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
			String propertyName, Object value, boolean isAsc) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * ??????????????????????????? ?????? ?????????????????????.
	 * 
	 * @return ???????????????????????????.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * ???????????????????????????????????????Query??????
	 * 
	 * @param session
	 *            Hibernate??????
	 * @param hql
	 *            HQL??????
	 * @param objects
	 *            ????????????
	 * @return Query??????
	 */
	public Query createQuery(Session session, String hql, Object... objects) {
		Query query = session.createQuery(hql);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i, objects[i]);
			}
		}
		return query;
	}

	/**
	 * ??????????????????
	 * 
	 * @param clas
	 * @param values
	 * @return
	 */
	public <T> int batchInsertsEntitie(List<T> entityList) {
		int num = 0;
		for (int i = 0; i < entityList.size(); i++) {
			save(entityList.get(i));
			num++;
		}
		return num;
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param <T>
	 * @param hql
	 * @param size
	 * @return
	 */
	/**
	 * ????????????????????????????????? ????????????like?????????????????????"%"+username+"%" Hibernate Query
	 * 
	 * @param hibernateTemplate
	 * @param hql
	 * @param valus
	 *            ???????????????????????????????????????????????????? ??????????????????????????????????????????null
	 * @return 2008-07-19 add by liuyang
	 */
	public List<T> executeQuery(final String hql, final Object[] values) {
		Query query = getSession().createQuery(hql);
		// query.setCacheable(true);
		for (int i = 0; values != null && i < values.length; i++) {
			query.setParameter(i, values[i]);
		}

		return query.list();

	}

	/**
	 * ????????????????????????
	 * 
	 * @param entityName
	 * @param exampleEntity
	 * @return
	 */

	public List findByExample(final String entityName,
			final Object exampleEntity) {
		Assert.notNull(exampleEntity, "Example entity must not be null");
		Criteria executableCriteria = (entityName != null ? getSession()
				.createCriteria(entityName) : getSession().createCriteria(
				exampleEntity.getClass()));
		executableCriteria.add(Example.create(exampleEntity));
		return executableCriteria.list();
	}

	// ?????????????????????????????????????????????????????????
	public Integer getRowCount(DetachedCriteria criteria) {
		return oConvertUtils.getInt(((Criteria) criteria
				.setProjection(Projections.rowCount())).uniqueResult(), 0);
	}

	/**
	 * ??????????????????
	 */
	public void callableStatementByName(String proc) {
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param clazz
	 * @return
	 */
	public int getCount(Class<T> clazz) {

		int count = DataAccessUtils.intResult(getSession().createQuery(
				"select count(*) from " + clazz.getName()).list());
		return count;
	}

	/**
	 * ??????????????????CriteriaQuery ?????????final int allCounts =
	 * oConvertUtils.getInt(criteria
	 * .setProjection(Projections.rowCount()).uniqueResult(), 0);
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// ??????Projection???OrderBy???????????????,?????????????????????Count??????
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// ???????????????????????????
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		int pageSize = cq.getPageSize();// ???????????????
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),
				pageSize);// ?????????
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		String toolBar = "";
		if (isOffset) {// ????????????
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
			if (cq.getIsUseimage() == 1) {
				toolBar = PagerUtil.getBar(cq.getMyAction(), cq.getMyForm(),
						allCounts, curPageNO, pageSize, cq.getMap());
			} else {
				toolBar = PagerUtil.getBar(cq.getMyAction(), allCounts,
						curPageNO, pageSize, cq.getMap());
			}
		} else {
			pageSize = allCounts;
		}
		return new PageList(criteria.list(), toolBar, offset, curPageNO,
				allCounts);
	}

	/**
	 * ??????JQUERY datatables DataTableReturn????????????
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
			final boolean isOffset) {

		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// ??????Projection???OrderBy???????????????,?????????????????????Count??????
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		// ???????????????????????????
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		int pageSize = cq.getPageSize();// ???????????????
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),
				pageSize);// ?????????
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		if (isOffset) {// ????????????
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
		} else {
			pageSize = allCounts;
		}
		DetachedCriteriaUtil.selectColumn(cq.getDetachedCriteria(), cq
				.getField().split(","), cq.getEntityClass(), false);
		return new DataTableReturn(allCounts, allCounts, cq.getDataTables()
				.getEcho(), criteria.list());
	}

	/**
	 * ??????easyui datagrid DataGridReturn????????????
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// ??????Projection???OrderBy???????????????,?????????????????????Count??????
		Projection projection = impl.getProjection();
		final int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (StringUtils.isNotBlank(cq.getDataGrid().getSort())) {
			cq.addOrder(cq.getDataGrid().getSort(), cq.getDataGrid().getOrder());
		}

		// ???????????????????????????
		if (!cq.getOrdermap().isEmpty()) {
			cq.setOrder(cq.getOrdermap());
		}
		int pageSize = cq.getPageSize();// ???????????????
		int curPageNO = PagerUtil.getcurPageNo(allCounts, cq.getCurPage(),
				pageSize);// ?????????
		int offset = PagerUtil.getOffset(allCounts, curPageNO, pageSize);
		if (isOffset) {// ????????????
			criteria.setFirstResult(offset);
			criteria.setMaxResults(cq.getPageSize());
		} else {
			pageSize = allCounts;
		}
		// DetachedCriteriaUtil.selectColumn(cq.getDetachedCriteria(),
		// cq.getField().split(","), cq.getClass1(), false);
		List list = criteria.list();
		cq.getDataGrid().setResults(list);
		cq.getDataGrid().setTotal(allCounts);
		return new DataGridReturn(allCounts, list);
	}

	/**
	 * ??????????????????SqlQuery
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageList getPageListBySql(final HqlQuery hqlQuery,
			final boolean isToEntity) {

		Query query = getSession().createSQLQuery(hqlQuery.getQueryString());

		// query.setParameters(hqlQuery.getParam(), (Type[])
		// hqlQuery.getTypes());
		int allCounts = query.list().size();
		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO,
				hqlQuery.getPageSize());
		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		List list = null;
		if (isToEntity) {
			list = ToEntityUtil.toEntityList(query.list(),
					hqlQuery.getClass1(), hqlQuery.getDataGrid().getField()
							.split(","));
		} else {
			list = query.list();
		}
		return new PageList(hqlQuery, list, offset, curPageNO, allCounts);
	}

	/**
	 * ??????????????????HqlQuery
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageList getPageList(final HqlQuery hqlQuery,
			final boolean needParameter) {

		Query query = getSession().createQuery(hqlQuery.getQueryString());
		if (needParameter) {
			query.setParameters(hqlQuery.getParam(),
					(Type[]) hqlQuery.getTypes());
		}
		int allCounts = query.list().size();
		int curPageNO = hqlQuery.getCurPage();
		int offset = PagerUtil.getOffset(allCounts, curPageNO,
				hqlQuery.getPageSize());
		String toolBar = PagerUtil.getBar(hqlQuery.getMyaction(), allCounts,
				curPageNO, hqlQuery.getPageSize(), hqlQuery.getMap());
		query.setFirstResult(offset);
		query.setMaxResults(hqlQuery.getPageSize());
		return new PageList(query.list(), toolBar, offset, curPageNO, allCounts);
	}

	/**
	 * ??????CriteriaQuery??????List
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getListByCriteriaQuery(final CriteriaQuery cq, Boolean ispage) {
		Criteria criteria = cq.getDetachedCriteria().getExecutableCriteria(
				getSession());
		// ???????????????????????????
		if (cq.getOrdermap() != null) {
			cq.setOrder(cq.getOrdermap());
		}
		if (ispage)
			criteria.setMaxResults(cq.getPageSize());
		return criteria.list();

	}

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * ????????????????????????????????????????????????????????????
	 */
	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		// ????????????SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql);
	}

	/**
	 * ????????????????????????????????????????????????????????????
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		List<T> rsList = new ArrayList<T>();
		// ????????????SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);

		T po = null;
		for (Map<String, Object> m : mapList) {
			try {
				po = clazz.newInstance();
				MyBeanUtils.copyMap2Bean_Nobig(po, m);
				rsList.add(po);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rsList;
	}

	/**
	 * ????????????????????????????????????????????????????????????-?????????????????????
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		// ????????????SQL
		sql = JdbcDao.jeecgCreatePageSql(sql, page, rows);
		return this.jdbcTemplate.queryForList(sql, objs);
	}

	/**
	 * ????????????????????????????????????????????????????????????For JDBC
	 */
	public Long getCountForJdbc(String sql) {
		return this.jdbcTemplate.queryForLong(sql);
	}

	/**
	 * ????????????????????????????????????????????????????????????For JDBC-?????????????????????
	 * 
	 */
	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return this.jdbcTemplate.queryForLong(sql, objs);
	}

	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return this.jdbcTemplate.queryForList(sql, objs);
	}

	public Integer executeSql(String sql, List<Object> param) {
		return this.jdbcTemplate.update(sql, param);
	}

	public Integer executeSql(String sql, Object... param) {
		return this.jdbcTemplate.update(sql, param);
	}

	public Integer executeSql(String sql, Map<String, Object> param) {
		return this.namedParameterJdbcTemplate.update(sql, param);
	}
	public Object executeSqlReturnKey(final String sql, Map<String, Object> param) {
		Object keyValue = null;
		try{
			KeyHolder keyHolder = new GeneratedKeyHolder(); 
			SqlParameterSource sqlp  = new MapSqlParameterSource(param);
			this.namedParameterJdbcTemplate.update(sql,sqlp, keyHolder);
			keyValue = keyHolder.getKey().longValue();
		}catch (Exception e) {
			keyValue = null;
		}
		return keyValue;
	}
	public Integer countByJdbc(String sql, Object... param) {
		return this.jdbcTemplate.queryForInt(sql, param);
	}

	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		try {
			return this.jdbcTemplate.queryForMap(sql, objs);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * ??????hql ????????????????????????
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	/**
	 * ??????HQL??????????????????
	 * 
	 * @param hql
	 * @return
	 */
	public Integer executeHql(String hql) {
		Query q = getSession().createQuery(hql);
		return q.executeUpdate();
	}

	public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
			int maxResult) {
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		return criteria.list();
	}

	/**
	 * ????????????
	 */
	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return dc.getExecutableCriteria(getSession()).list();
	}
}
