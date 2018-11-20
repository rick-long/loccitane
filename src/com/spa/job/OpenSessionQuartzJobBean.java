package com.spa.job;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;
/*
 *   Created by Ivy on 2016/01/16.
 */
public abstract class OpenSessionQuartzJobBean extends QuartzJobBean {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected final void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		Session session = openSession(sessionFactory);
		SessionHolder sessionHolder = new SessionHolder(session);
		TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);

		try {
			executeJob(ctx);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			SessionHolder sessionHolder2 = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(sessionHolder2.getSession());
		}
	}

	private Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		try {
			Session session = sessionFactory.openSession();
			session.setFlushMode(FlushMode.MANUAL);
			return session;
		} catch (HibernateException ex) {
			throw new DataAccessResourceFailureException("Could not open Hibernate Session", ex);
		}
	}


	protected abstract void executeJob(JobExecutionContext ctx) throws JobExecutionException;

}
