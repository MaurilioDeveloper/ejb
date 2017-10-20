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
import com.rci.omega2.ejb.dao.ProofIncomeTypeDAO;
import com.rci.omega2.ejb.dto.ProofIncomeTypeDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProofIncomeTypeEntity;

@Stateless
public class ProofIncomeTypeBO extends BaseBO {

    private static final Logger LOGGER = LogManager.getLogger(ProofIncomeTypeBO.class);

    @EJB
    private SantanderDomainBO santanderDomainBO; 
    
    public List<ProofIncomeTypeDTO> findAllProofIncomeType() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findAllProofIncomeType ");
            ProofIncomeTypeDAO dao = daoFactory(ProofIncomeTypeDAO.class);

            List<ProofIncomeTypeEntity> proof = dao.findAll(ProofIncomeTypeEntity.class);

            List<ProofIncomeTypeDTO> lsDto = new ArrayList<>();

            for (ProofIncomeTypeEntity item : proof) {
                ProofIncomeTypeDTO dto = new ProofIncomeTypeDTO();
                dto.setId(CriptoUtilsOmega2.encrypt(item.getId()));
                dto.setDescription(item.getDescription());
                lsDto.add(dto);
            }

            LOGGER.debug(" >> END findAllProofIncomeType ");
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
     * @param importCode
     * @return
     * @throws UnexpectedException
     */
    public ProofIncomeTypeEntity findProofIncomeTypeByImportCode(String importCode) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findProofIncomeTypeByImportCode ");
            
            ProofIncomeTypeDAO dao = daoFactory(ProofIncomeTypeDAO.class);
            ProofIncomeTypeEntity proofIncomeType = dao.findProofIncomeTypeByImportCode(importCode);
            
            if(AppUtil.isNullOrEmpty(proofIncomeType)){
                //Busca domnio no Santander
                DominioDTO domain = santanderDomainBO.findSantanderDomain(
                                    SantanderDomainEnum.TIPO_COMPROVANTE_RENDA, String.valueOf(importCode).trim());
                
                if(!AppUtil.isNullOrEmpty(domain)){
                    //Salva no banco
                    proofIncomeType = new ProofIncomeTypeEntity();
                    proofIncomeType.setActive(Boolean.TRUE);
                    proofIncomeType.setImportCode(domain.getCodigo());
                    proofIncomeType.setDescription(domain.getDescricao());
                    dao.save(proofIncomeType);
                }
            }
            
            LOGGER.debug(" >> END findProofIncomeTypeByImportCode ");
            return proofIncomeType;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
