package com.rci.omega2.ejb.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.rci.omega2.entity.BaseEntity;

public class BaseDAO {

	private EntityManager em;
	
	protected EntityManager getEntityManager() {
		return em;
	}
   
	public void setEntityManager(EntityManager em){
        this.em = em; 
    }
    

	public <E extends BaseEntity> void save(E e) throws Exception{
		em.persist(e);
		em.flush();
	}

	public <E extends BaseEntity> void remove(E e) throws Exception{
		em.remove(e);
		em.flush();
	}

	public <E extends BaseEntity> E update(E e) throws Exception{
		E obj = em.merge(e);
		em.flush();
		return obj;
	}
	
	public <E extends BaseEntity> E find(Class<E> e, Object id)throws Exception{
		return em.find(e, id);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends BaseEntity> List<E> findAll(Class<E> e)throws Exception{
		Query query = em.createQuery("SELECT e FROM " + e.getSimpleName() + " e ");
		return query.getResultList();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
    public <E extends BaseEntity> List<E> findByCriteria(Class<E> e, Map<String, Object> mapString)throws Exception{
		Session session = this.getEntityManager().unwrap(Session.class);
		Criteria criteria = session.createCriteria(e);
		
		for (Map.Entry<String, Object> objeto : mapString.entrySet()) {
			if (objeto.getValue() != null) {
				String key = objeto.getKey();
				
				if (objeto.getValue() instanceof String) {
					if (((String) objeto.getValue()).contains("%")) {
						criteria.add(Restrictions.like(key, (String) objeto.getValue()));
					} else {
						criteria.add(Restrictions.eq(key, (String) objeto.getValue()));
					}
				} else {
					criteria.add(Restrictions.eq(key, objeto.getValue()));
				}
			}
		}
		
		return criteria.list();
	}
	
}
