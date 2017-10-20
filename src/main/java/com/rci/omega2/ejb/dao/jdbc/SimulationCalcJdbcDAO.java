package com.rci.omega2.ejb.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.rci.omega2.ejb.utils.JdbcUtils;
import com.rci.omega2.entity.ServiceEntity;
import com.rci.omega2.entity.ServiceTypeEntity;

public class SimulationCalcJdbcDAO extends JdbcBaseDAO {

    public BigDecimal getTotalOptions(List<Long> idsSearch) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();

            String ids = JdbcUtils.convertListLongToStringParameter(idsSearch);

            ps = conn.prepareStatement("select o.amount from tb_vehicle_version_options o "
                    + "where o.vehicle_version_options_id in (" + ids + ")");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = total.add(rs.getBigDecimal("amount"));
            }
        } finally {
            jdbcClose();
        }
        return total;
    }

    public BigDecimal getTotalTaxes(List<Long> idsSearch) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();

            String ids = JdbcUtils.convertListLongToStringParameter(idsSearch);

            ps = conn.prepareStatement("select t.amount from tb_tax t " + "where t.tax_id in (" + ids + ")");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = total.add(rs.getBigDecimal("amount"));
            }
        } finally {
            jdbcClose();
        }
        return total;
    }
    
    public BigDecimal getServiceValue(Long idSearch) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();
            ps = conn.prepareStatement("select s.amount from tb_service s where s.service_id = ?");
            ps.setLong(1, idSearch);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = total.add(rs.getBigDecimal("amount"));
            }
        } finally {
            jdbcClose();
        }
        return total;
    }
    
    public ServiceEntity findServiceById(Long idSearch)throws Exception{
        try{
            ServiceEntity service = new ServiceEntity();
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();
            ps = conn.prepareStatement("select s.amount, s.percentage, st.service_type_id "                   
                    + "from tb_service s "
                    + "inner join tb_service_type st "
                    + "on s.service_type_id = st.service_type_id "
                    + "where s.service_id = ?");
            ps.setLong(1, idSearch);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ServiceTypeEntity type = new ServiceTypeEntity();
                service.setServiceType(type);
                service.setAmount(rs.getBigDecimal("amount"));
                service.setPercentage(rs.getBigDecimal("percentage"));
                service.getServiceType().setId(rs.getLong("service_type_id"));
            }
            return service;
        }finally{
            jdbcClose();
        }
    }

    /*public BigDecimal getSpfPercentage() throws Exception {
        BigDecimal result = BigDecimal.ZERO;
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();
            ps = conn.prepareStatement("select s.percentage from tb_service s where s.tax_type = ? ");
            ps.setString(1, TaxTypeEnum.SPF.name());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getBigDecimal("percentage");
            }
        } finally {
            jdbcClose();
        }
        return result;
    }*/

}
