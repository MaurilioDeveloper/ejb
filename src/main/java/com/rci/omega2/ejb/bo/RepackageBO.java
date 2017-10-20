package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.RepackageDAO;
import com.rci.omega2.ejb.dto.RepackageDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;

@Stateless
public class RepackageBO extends BaseBO{
    
    private static final Logger LOGGER = LogManager.getLogger(RepackageBO.class);
    
    public List<RepackageDTO> findByProduct(Long productId, Integer duration) throws UnexpectedException {
        
        LOGGER.debug(" >> INIT findByProduct ");
        
        RepackageDAO dao = daoFactory(RepackageDAO.class);

        List<RepackageDTO> lista = dao.findByProduct(productId, duration);

        LOGGER.debug(" >> END findByProduct ");
        return lista;
    }
    
}
