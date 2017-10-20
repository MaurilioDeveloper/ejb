package com.rci.omega2.ejb.bo;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dao.SaleTypeDAO;
import com.rci.omega2.ejb.dao.SimulationDAO;
import com.rci.omega2.ejb.dto.SalesTypeDTO;
import com.rci.omega2.ejb.dto.SalesmanDTO;
import com.rci.omega2.ejb.exception.BusinessException;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;

@Stateless
public class SimulationBO extends BaseBO {
    private static final Logger LOGGER = LogManager.getLogger(SimulationBO.class);

    public List<SalesmanDTO> findListSalesmanByDealership(Long delearshipId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findListSalesmanByDealership ");
            SimulationDAO dao = daoFactory(SimulationDAO.class);
            List<SalesmanDTO> list = dao.findListSalesmanByDealership(delearshipId);
            if (list.size() == 0) {
                throw new BusinessException("no-salesman-to-dealership");
            }
            LOGGER.debug(" >> END findListSalesmanByDealership ");
            return list;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
    public SalesmanDTO findSalesmanBySimulation(Long simulationId) throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSalesmanBySimulation ");
            SimulationDAO dao = daoFactory(SimulationDAO.class);
            SalesmanDTO salesman = dao.findSalesmanBySimulation(simulationId);
            LOGGER.debug(" >> END findSalesmanBySimulation ");
            return salesman;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

    public List<SalesTypeDTO> findSalesTypesActive() throws UnexpectedException {
        try {
            LOGGER.debug(" >> INIT findSalesTypesActive ");
            SaleTypeDAO dao = daoFactory(SaleTypeDAO.class);
            List<SalesTypeDTO> temp = dao.findAllActive();
            
            LOGGER.debug(" >> INIT findSalesTypesActive ");
            return temp;
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }

}
