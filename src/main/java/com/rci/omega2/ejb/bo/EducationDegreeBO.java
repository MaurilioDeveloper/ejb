package com.rci.omega2.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altec.bsbr.app.afc.webservice.impl.DominioDTO;
import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.common.util.SantanderDomainEnum;
import com.rci.omega2.ejb.dao.EducationDegreeDAO;
import com.rci.omega2.ejb.dto.EducationDegreeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.EducationDegreeEntity;

@Stateless
public class EducationDegreeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(EducationDegreeBO.class);
    
    @EJB
    private SantanderDomainBO santanderDomainBO; 

    public List<EducationDegreeDTO> findAllEducationDegree() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllEducationDegree ");
            EducationDegreeDAO dao = daoFactory(EducationDegreeDAO.class);

            List<EducationDegreeEntity> education = dao.findAll(EducationDegreeEntity.class);

            List<EducationDegreeDTO> lsDto = new ArrayList<>();

            for (EducationDegreeEntity item : education) {
                EducationDegreeDTO dto = new EducationDegreeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }

            LOGGER.debug(" >> END findAllEducationDegree ");
            return lsDto;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    /**
     * Add Dominio caso não tenha
     * @param id
     * @return
     * @throws UnexpectedException
     */
   public EducationDegreeEntity findEducationDegreeById(Long id) throws UnexpectedException {
       try {
           LOGGER.debug(" >> INIT findEducationDegreeById ");
           
           EducationDegreeDAO dao = daoFactory(EducationDegreeDAO.class);
           EducationDegreeEntity educationDegree = dao.find(EducationDegreeEntity.class, id);
           
           
           if(AppUtil.isNullOrEmpty(educationDegree)){
               //Busca domnio no Santander
               DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                   SantanderDomainEnum.GRAU_INSTRUCAO, String.valueOf(id).trim());
               
               if(!AppUtil.isNullOrEmpty(domain)){
                   //Salva no banco
                   educationDegree = new EducationDegreeEntity();
                   educationDegree.setActive(Boolean.TRUE);
                   educationDegree.setId(Long.valueOf(domain.getCodigo()));
                   educationDegree.setDescription(domain.getDescricao());
                   dao.save(educationDegree);
               }
           }
           
           LOGGER.debug(" >> END findEducationDegreeById ");
           return educationDegree;
           
       } catch (UnexpectedException e) {
           throw e;
       } catch (Exception e) {
           LOGGER.error(AppConstants.ERROR, e);
           throw new UnexpectedException(e);
       }
   }

}
