package com.rci.omega2.ejb.bo;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.rci.omega2.ejb.dao.BaseDAO;
import com.rci.omega2.ejb.dao.jdbc.JdbcBaseDAO;
import com.rci.omega2.ejb.exception.UnexpectedException;

public abstract class BaseBO {

    @PersistenceContext(unitName = "omega2PU")
    private EntityManager em;

    @Resource(mappedName = "java:/jboss/datasources/omega2DS")
    private DataSource jdbcDataSource;

    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    protected <E extends BaseDAO> E daoFactory(Class<E> e) throws UnexpectedException {
        try {
            E dao = e.newInstance();
            dao.setEntityManager(em);
            return dao;
        } catch (Exception ex) {
            throw new UnexpectedException(ex);
        }
    }

    protected BaseDAO daoFactory() throws Exception {
        return daoFactory(BaseDAO.class);
    }


    protected <E extends JdbcBaseDAO> E daoJdbcFactory(Class<E> e) throws UnexpectedException {
        try {
            E dao = e.newInstance();
            dao.setJdbcDataSource(jdbcDataSource);
            return dao;
        } catch (Exception ex) {
            throw new UnexpectedException(ex);
        }
    }

    protected JdbcBaseDAO daoJdbcFactory() throws Exception {
        return daoJdbcFactory(JdbcBaseDAO.class);
    }
    
    
}
