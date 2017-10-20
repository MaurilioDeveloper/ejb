package com.rci.omega2.ejb.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.ProposalEmailDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.entity.ProposalEntity;

public class ProposalDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProposalDAO.class);

    @SuppressWarnings("unchecked")
    public ProposalEmailDTO findEmailInformationByDossier(Long dossierId) throws UnexpectedException{
        
        try {
            LOGGER.debug(" >> INIT findEmailInformationByDossier ");
            
            ProposalEmailDTO dto = new ProposalEmailDTO();
            Query query =  super.getEntityManager()
                    .createQuery( "select case when d.importCodeOmega is null then d.id else d.importCodeOmega end as internNumber, "
                    + "ds.description as status, "
                    + "sa.namePerson as salesmanName, "
                    + "sa.email as salesmanEmail, "
                    + "s.description as dealershipName, "
                    + "c.email as clientEmail "
                    + "from DossierEntity d "
                    + "join d.salesman sa "
                    + "join d.customer c "
                    + "join d.structure s "
                    + "join d.dossierStatus ds "
                    + "where d.id = :dossierId ");
            query.setParameter("dossierId", dossierId);
            List<Object[]> data = query.getResultList();
            
            for(Object[] emailData : data){
                dto.setSimulationNumber(Long.valueOf(emailData[0].toString()));
                dto.setSimulationStatus(emailData[1].toString());
                dto.setSalesmanName(emailData[2].toString());
                dto.setSalesmanEmail(emailData[3].toString());
                dto.setDealershipName(emailData[4].toString());
                dto.setClientEmail(emailData[5].toString());           
            }
            
            LOGGER.debug(" >> END findEmailInformationByDossier ");
            return dto;
        
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    /**
     * 
     * @param adp
     * @return
     * @throws UnexpectedException
     */
    @SuppressWarnings("unchecked")
    public String getTabCode(String adp) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT getTabCode ");
            
            StringBuilder strQuery = new StringBuilder("");

            strQuery.append(" SELECT ds.tab FROM ProposalEntity pp  ");
            strQuery.append("       INNER JOIN pp.dossier dsr       ");
            strQuery.append("       INNER JOIN dsr.structure st     ");
            strQuery.append("       INNER JOIN st.dealership ds     ");
            strQuery.append("    WHERE pp.adp = :adp                ");

            Query query = this.getEntityManager().createQuery(strQuery.toString());
            query.setParameter("adp", adp);
            List<String> data = query.getResultList();

            if (!data.isEmpty()) {
                return data.get(0);
            }

            LOGGER.debug(" >> END getTabCode ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }            
            
    }

    @SuppressWarnings("unchecked")
    public ProposalEntity findProposalCompleted(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalCompleted ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select pe from ProposalEntity pe ");
            sql.append(" join fetch pe.dossier de ");
            sql.append(" join fetch de.structure stu ");
            sql.append(" join fetch stu.financialBrand fbd ");
            sql.append(" join fetch pe.financeType fte ");
            sql.append(" join fetch pe.listProposalService lps ");
            sql.append(" join fetch lps.service ss ");
            sql.append(" join fetch ss.serviceType st ");
            sql.append(" join fetch de.dossierVehicle dve ");
            sql.append(" join fetch dve.vehicleVersion vve ");
            sql.append(" where pe.adp is not null ");
            sql.append(" and de.id = :dossierId ");

            Query query = super.getEntityManager().createQuery(sql.toString());

            query.setParameter("dossierId", dossierId);

            List<ProposalEntity> ls = query.getResultList();

            
            LOGGER.debug(" >> END findProposalCompleted ");
            return ls.get(0);

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
            
    }
    
    @SuppressWarnings("unchecked")
    public ProposalEntity findProposalCompletedToPrint(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalCompletedToPrint ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" select pe from ProposalEntity pe ");
            sql.append(" join fetch pe.dossier de ");
            sql.append(" join fetch de.customer cto ");
            sql.append(" join fetch cto.address adrs ");
            sql.append(" join fetch cto.country cco ");
            sql.append(" join fetch adrs.province pvc ");
            sql.append(" join fetch pe.listProposalService lps ");
            sql.append(" join fetch lps.service serv ");
            sql.append(" join fetch serv.serviceType ");
            sql.append(" join fetch pe.financeType fte ");
            sql.append(" join fetch de.dossierVehicle dve ");
            sql.append(" join fetch dve.vehicleVersion vve ");
            sql.append(" join fetch de.structure se ");
            sql.append(" join fetch se.dealership dse ");
            sql.append(" where pe.adp is not null ");
            sql.append(" and de.id = :dossierId ");
            
            Query query = super.getEntityManager().createQuery(sql.toString());

            query.setParameter("dossierId", dossierId);

            List<ProposalEntity> ls = query.getResultList();

            ProposalEntity entity = ls.get(0);

            LOGGER.debug(" >> END findProposalCompletedToPrint ");  
            return entity;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }   
            
    }
    
    
    @SuppressWarnings("unchecked")
    public ProposalEntity findProposalByAdp(String adp) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalByAdp ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" SELECT pe FROM ProposalEntity pe                ");
            sql.append("      LEFT JOIN FETCH pe.listProposalService ps  ");
            sql.append("      LEFT JOIN FETCH ps.service se              ");
            sql.append("      LEFT JOIN FETCH se.serviceType st          ");
            sql.append("    WHERE pe.adp = :adp                          ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("adp", adp);

            List<ProposalEntity> ls = query.getResultList();
            
            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findProposalByAdp ");
            return null;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        } 
            
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<ProposalEntity> findProposalByDossierAndAdp(Long dossierId, String adp) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalByDossierAndAdp ");
            
            Session session = this.getEntityManager().unwrap(Session.class);
            
            Criteria criteria = session.createCriteria(ProposalEntity.class);
            criteria.add(Restrictions.eq("dossier.id", dossierId));
            
            if(!AppUtil.isNullOrEmpty(adp)){
                criteria.add(Restrictions.eq("adp", adp));
            }
            
            List<ProposalEntity> lista = criteria.list();
    
            LOGGER.debug(" >> END findProposalByDossierAndAdp ");
            return lista;
            
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
            
    }
    
    @SuppressWarnings({"deprecation" })
    public ProposalEntity findProposalRootFetch(Long proposalId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalRootFetch ");
            
            Session session = this.getEntityManager().unwrap(Session.class);

            Criteria criteria = session.createCriteria(ProposalEntity.class);

            criteria.add(Restrictions.eq("id", proposalId));
            criteria.setFetchMode("product", FetchMode.JOIN);
            criteria.setFetchMode("financeType", FetchMode.JOIN);
            criteria.setFetchMode("commission", FetchMode.JOIN);
            criteria.setFetchMode("user", FetchMode.JOIN);
            criteria.setFetchMode("repackage", FetchMode.JOIN);
            criteria.setFetchMode("listProposalTax", FetchMode.JOIN);
            criteria.setFetchMode("listProposalService", FetchMode.JOIN);
            criteria.setFetchMode("listInstalment", FetchMode.JOIN);

            ProposalEntity entity = (ProposalEntity) criteria.uniqueResult();

            LOGGER.debug(" >> END findProposalRootFetch ");
            return entity;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public ProposalEntity findProposalMainByDossier(Long dossierId, String adp) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalMainByDossier ");
            
            Session session = this.getEntityManager().unwrap(Session.class);

            Criteria criteria = session.createCriteria(ProposalEntity.class);
            criteria.add(Restrictions.eq("dossier.id", dossierId));
            criteria.add(Restrictions.eq("exibitionMain", Boolean.TRUE));

            List<ProposalEntity> lista = criteria.list();

            if (AppUtil.isNullOrEmpty(lista)) {
                return null;
            }

            ProposalEntity temp = findProposalRootFetch(lista.get(0).getId());
            
            LOGGER.debug(" >> END findProposalMainByDossier ");
            return temp;

        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public ProposalEntity findProposalWithAdpByDossier(Long dossierId) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProposalWithAdpByDossier ");
            
            StringBuilder sql = new StringBuilder();

            sql.append(" select pe from ProposalEntity pe ");
            sql.append(" where pe.adp is not null ");
            sql.append(" and pe.dossier.id = :dossierId ");

            Query query = super.getEntityManager().createQuery(sql.toString());

            query.setParameter("dossierId", dossierId);

            List<ProposalEntity> ls = query.getResultList();

            if (AppUtil.isNullOrEmpty(ls)) {
                return null;
            }

            ProposalEntity temp = ls.get(0);
            
            LOGGER.debug(" >> END findProposalWithAdpByDossier ");
            return temp;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }     
             
    }
    
}
