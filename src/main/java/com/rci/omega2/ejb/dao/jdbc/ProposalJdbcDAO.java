package com.rci.omega2.ejb.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rci.omega2.ejb.dto.ProposalEmailDTO;
import com.rci.omega2.ejb.dto.SendProposalReportDTO;
import com.rci.omega2.ejb.dto.SendProposalSimulationReportDTO;
import com.rci.omega2.ejb.utils.GeneralUtils;

public class ProposalJdbcDAO extends JdbcBaseDAO {

    public ProposalEmailDTO findEmailInformationByDossier(Long dossierId) throws Exception {
        ProposalEmailDTO dto = new ProposalEmailDTO();
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();

            String sql = "select case when d.import_code_omega is null then d.dossier_id "
                    + "else d.import_code_omega end as intern_number, " + "ds.description as status, "
                    + "sa.name_person as salesman_name, " + "sa.email as salesman_email, "
                    + "s.description as dealership_name, " + "c.email as client_email " + "from tb_dossier d "
                    + "inner join tb_person sa on sa.person_id = d.person_id_salesman "
                    + "inner join tb_customer c on c.customer_id = d.customer_id "
                    + "inner join tb_structure s on s.structure_id = d.structure_id "
                    + "inner join tb_dossier_status ds on ds.dossier_status_id = d.dossier_status_id "
                    + "where d.dossier_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, dossierId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dto.setSimulationNumber(rs.getLong("intern_number"));
                dto.setSimulationStatus(rs.getString("status"));
                dto.setSalesmanName(rs.getString("salesman_name"));
                dto.setSalesmanEmail(rs.getString("salesman_email"));
                dto.setDealershipName(rs.getString("dealership_name"));
                dto.setClientEmail(rs.getString("client_email"));
            }
        } finally {
            jdbcClose();
        }
        return dto;
    }

    public SendProposalReportDTO findDataReportFromDossier(Long dossierId) throws Exception {
        SendProposalReportDTO dto = new SendProposalReportDTO();
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();
            String sql = "select s.description as dealership_name," 
                    + "sa.name_person as saleman_name, "
                    + "vm.description as model, " 
                    + "vv.description as version, " 
                    + "vv.model_year, "
                    + "vv.manufacture_year, "
                    + "case when c.person_type='PF' then 'Pessoa Física' else 'Pessoa Jurídica' end as client_type, "
                    + "'Paraná' as client_state, " 
                    + "d.vehicle_price, " 
                    + "da.amount as accessory_amount, "
                    + "do.amount as options_amount " 
                    + "from tb_dossier d "
                    + "left join tb_person sa on sa.person_id = d.person_id_salesman "
                    + "left join tb_structure s on s.structure_id = d.structure_id "
                    + "left join tb_dossier_vehicle dv on dv.dossier_vehicle_id = d.dossier_vehicle_id "
                    + "left join tb_vehicle_version vv on vv.vehicle_version_id = dv.vehicle_version_id "
                    + "left join tb_vehicle_model vm on vm.vehicle_model_id = vv.vehicle_model_id "
                    + "left join tb_customer c on c.customer_id = d.customer_id "
                    + "left join tb_dossier_vehicle_accessory da on da.dossier_id = d.dossier_id "
                    + "left join tb_dossier_vehicle_option do on do.dossier_id = d.dossier_id "
                    + "where d.dossier_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, dossierId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dto.setDealership(rs.getString("dealership_name"));
                dto.setSalesman(rs.getString("saleman_name"));
                dto.setModel(rs.getString("model"));
                dto.setVersion(rs.getString("version"));
                dto.setModelYear(rs.getInt("model_year"));
                dto.setManufactureYear(rs.getInt("manufacture_year"));
                dto.setClientType(rs.getString("client_type"));
                dto.setState(rs.getString("client_state"));
                dto.setVehicleValue(GeneralUtils.roundPrice(rs.getBigDecimal("vehicle_price")));
                dto.setAccessoriesValue(GeneralUtils.roundPrice(rs.getBigDecimal("accessory_amount")));
                dto.setOptionalValue(GeneralUtils.roundPrice(rs.getBigDecimal("options_amount")));
            }
        } finally {
            jdbcClose();
        }
        return dto;
    }

    public List<SendProposalSimulationReportDTO> findSimulationsFromDossier(Long dossierId) throws Exception {
        List<SendProposalSimulationReportDTO> listDto = new ArrayList<SendProposalSimulationReportDTO>();
        try {
            PreparedStatement ps = null;
            Connection conn = getJdbcConnection();

            String sql = "select pp.proposal_id as id_proposal, "
                    + "pp.instalment_amount as monthly_amount, " 
                    + "pr.description as finance_table, "
                    + "pp.instalment_quantity as term, " 
                    + "pp.deposit as entry_amount, " 
                    + "pp.financed_amount, "
                    + "(pp.montly_rate*100) as client_tax, " 
                    + "pp.delay_value as delay " 
                    + "from tb_dossier d "
                    + "inner join tb_proposal pp on pp.dossier_id  = d.dossier_id "
                    + "inner join tb_product pr on pr.product_id = pp.product_id " 
                    + "where d.dossier_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, dossierId);
            ResultSet rs = ps.executeQuery();

            int countSimulation = 1;
            while (rs.next()) {
                SendProposalSimulationReportDTO dto = new SendProposalSimulationReportDTO();
                String services = getServicesFromSimulation(rs.getLong("id_proposal"), conn);
                dto.setServices(services);
                dto.setSimulationCount(countSimulation);
                dto.setMonthlyAmount(GeneralUtils.roundPrice(rs.getBigDecimal("monthly_amount")));
                dto.setFinanceTable(rs.getString("finance_table"));
                dto.setTerm(rs.getInt("term"));
                dto.setEntryAmount(GeneralUtils.roundPrice(rs.getBigDecimal("entry_amount")));
                dto.setFinancedAmount(GeneralUtils.roundPrice(rs.getBigDecimal("financed_amount")));
                dto.setClientTax(GeneralUtils.roundPrice(rs.getBigDecimal("client_tax")));
                dto.setDelay(rs.getInt("delay"));
                listDto.add(dto);
                countSimulation++;
            }
        } finally {
            jdbcClose();
        }
        return listDto;
    }

    private String getServicesFromSimulation(Long proposalId, Connection conn) throws Exception {
        String result = "";
        try {
            PreparedStatement ps = null;

            String sql = "select s.description "
                        +"from tb_proposal_service ps "
                        +"inner join tb_service s on s.service_id = ps.service_id "
                        +"where ps.proposal_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, proposalId);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result += rs.getString("description")+"\n";
            }
            
        } catch (Exception e) {
            
        }
        return result;
    }
}
