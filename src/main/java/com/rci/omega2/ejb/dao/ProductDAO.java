package com.rci.omega2.ejb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.ejb.dto.ProductDTO;
import com.rci.omega2.ejb.dto.ProductFilterDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.ejb.utils.AppConstants;
import com.rci.omega2.ejb.utils.CriptoUtilsOmega2;
import com.rci.omega2.entity.ProductEntity;
import com.rci.omega2.entity.enumeration.StructureTypeEnum;

public class ProductDAO extends BaseDAO{
    
    private static final Logger LOGGER = LogManager.getLogger(ProductDAO.class);
    
    /*
     *  Regras de negocio do UC1008 - MS -Consultar tabela de financiamento
     *  RN01    Para cada campo obrigatório não preenchido o mesmo deve ser informado no objeto de retorno do erro.
     *  RN03    A tabela de FinanciamentodeveestarcomoAtiva (RQ02)
     *  RN04    Caso o campo [Condição de chamada ] estejacomo “True”, a tabelaobrigatoriamentedeveterumacondição de chamada (RQ01)
     *  RN05    Se a tabelaestivervinculada à um grupodinâmico, a Concessionáriadeveestarparticipandodestegrupo. (RQ03)
     *  RN06    A tabela de financiamentodevepossuir a versão do veículovinculadaounãopossuirnenhumaversão (RQ04)
     *  RN07    O ano de fabricação e anomodelo do veículodevemestardentro do limite de ano de fabricação e anomodelo da tabela de financiamento (RQ05)
     *  RN09    A data atual (hoje) deveestardentro do range de inicio e fim de vigência da tabela de financiamento (RQ07)
     *  RN10    A tabela de financiamentodeveestarvinculadaaomesmotipo de financiamento (RQ08)
     *  RN11    A tabela de financiamentodeveestarvinculadaaomesmotipo de pessoa / cliente (RQ09)
     *  RN12    A tabela de financiamentodeveestarvinculadoao(s)mesmo(s)tipo(s)especial(is) de veículoounãopossuirnenhumtipocadastrado. (RQ10)
     *  RN13    A tabela de financiamentodeveestarvinculadoaomesmotipode veículoounãopossuirnenhumtipocadastrado (RQ11)
     *  RN14    A tabela de financiamentodevepossuir a mesmamarcafinanceiraque a Concessionária (RQ12)
     *  
     */
    @SuppressWarnings("unchecked")
    public List<ProductDTO> findProduct(ProductFilterDTO productFilter) throws UnexpectedException {
        
        try {
            LOGGER.debug(" >> INIT findProduct ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT ");
            sql.append(" P1.PRODUCT_ID, ");
            sql.append(" P1.DESCRIPTION, ");
            sql.append(" CASE WHEN EXISTS ");
            sql.append(" (SELECT 1 FROM TB_PRODUCT_PROMOTIONAL PP WHERE P1.PRODUCT_ID = PP.PRODUCT_ID AND (0 = :idCalculation or PP.AVAILABLE =1)) THEN 'true' ");
            sql.append(" ELSE 'false' END AS Promotional, ");
            sql.append(" P1.PRODUCT_INFORMATION ");
            sql.append(" FROM ( SELECT P.* ");
            sql.append("        FROM TB_PRODUCT P ");
            sql.append("        INNER JOIN TB_FINANCE_TYPE FT ON FT.FINANCE_TYPE_ID = P.FINANCE_TYPE_ID ");
            sql.append("        INNER JOIN TB_PRODUCT_SALE_TYPE PST ON P.PRODUCT_ID = PST.PRODUCT_ID ");
            sql.append("        WHERE P.ACTIVE = 1 ");//RQ02 –Detalhamento RN03
            sql.append("        AND FT.ACTIVE = 1 ");//RQ08–Detalhamento RN10
            sql.append("        AND P.FINANCE_TYPE_ID = :financeType ");//RQ08–Detalhamento RN10 Value:3
            sql.append("        AND PST.SALE_TYPE_ID = :saleType ");//RQ11 –Detalhamento RN13 Value:1
            if(productFilter.getManufactureYear() == null) {
                sql.append("        AND (:manufactureYear >= P.MIN_MANUFACTURE_YEAR) ");//RQ05–Detalhamento RN07 Value:2017
                sql.append("        AND (:manufactureYear <= P.MAX_MANUFACTURE_YEAR) ");//RQ05–Detalhamento RN07 Value:2017
            }
            sql.append("        AND (:modelYear >= P.MIN_MODEL_YEAR OR P.MIN_MODEL_YEAR IS NULL) ");//RQ05–Detalhamento RN07 Value:2017
            sql.append("        AND (:modelYear <= P.MAX_MODEL_YEAR OR P.MAX_MODEL_YEAR IS NULL) ");//RQ05–Detalhamento RN07 Value:2017
            sql.append("        AND :currentDate BETWEEN P.INITIAL_PERIOD AND P.FINAL_PERIOD ");//RQ07–Detalhamento RN09 Value:Data Atual
            sql.append("        AND P.PERSON_TYPE <> :personType ) P1 ");//RQ09–Detalhamento RN11 Value:PJ  
            sql.append("        WHERE  (EXISTS(SELECT 1 FROM TB_PRODUCT_FINANCIAL_BRAND PFB ");//RQ03–Detalhamento RN05
            sql.append("                    INNER JOIN TB_FINANCIAL_BRAND FB ON PFB.FINANCIAL_BRAND_ID = FB.FINANCIAL_BRAND_ID ");//RQ03–Detalhamento RN05
            sql.append("                    INNER JOIN TB_STRUCTURE S ON FB.FINANCIAL_BRAND_ID = S.FINANCIAL_BRAND_ID ");//RQ03–Detalhamento RN05
            sql.append("                    WHERE P1.PRODUCT_ID = PFB.PRODUCT_ID ");//RQ03–Detalhamento RN05
            sql.append("                    AND S.STRUCTURE_TYPE = :concessionaria ");//RQ03–Detalhamento RN05 Value: CONCESSIONARIA
            sql.append("                    AND S.STRUCTURE_ID = :structureId ");//RQ03–Detalhamento RN05 Value:14 
            sql.append("                    AND NOT EXISTS( SELECT 1 FROM TB_FINANCING_GROUP_PRODUCT FGP ");//RQ03–Detalhamento RN05
            sql.append("                                    INNER JOIN TB_FINANCING_GROUP FG ON FGP.FINANCING_GROUP_ID = FG.FINANCING_GROUP_ID ");//RQ03–Detalhamento RN05
            sql.append("                                    INNER JOIN TB_FINANCING_GROUP_STRUCTURE FGS ON FG.FINANCING_GROUP_ID = FGS.FINANCING_GROUP_ID ");//RQ03–Detalhamento RN05
            sql.append("                                    WHERE FGP.PRODUCT_ID = P1.PRODUCT_ID))");//RQ03–Detalhamento RN05
            sql.append("             OR EXISTS(SELECT 1 FROM TB_FINANCING_GROUP_PRODUCT FGP ");//RQ03–Detalhamento RN05
            sql.append("                       INNER JOIN TB_FINANCING_GROUP FG ON FGP.FINANCING_GROUP_ID = FG.FINANCING_GROUP_ID ");//RQ03–Detalhamento RN05
            sql.append("                       INNER JOIN TB_FINANCING_GROUP_STRUCTURE FGS ON FG.FINANCING_GROUP_ID = FGS.FINANCING_GROUP_ID ");//RQ03–Detalhamento RN05
            sql.append("                       WHERE P1.PRODUCT_ID = FGP.PRODUCT_ID ");//RQ03–Detalhamento RN05
            sql.append("                       AND FGS.STRUCTURE_ID = :structureId))");//RQ03–Detalhamento RN05 Value:14 
            sql.append("        AND ( EXISTS (SELECT 1 FROM TB_GROUP_VEHICLE_VERS_PRODUCT GVVP ");//RQ04–Detalhamento RN06
            sql.append("                      INNER JOIN TB_GROUP_VEHICLE_VERSION GVV ON GVVP.GROUP_VEHICLE_VERSION_ID = GVV.GROUP_VEHICLE_VERSION_ID ");//RQ04–Detalhamento RN06
            sql.append("                      INNER JOIN TB_GRP_VEHICLE_VER_VEHICLE_VER GVVV ON GVV.GROUP_VEHICLE_VERSION_ID = GVVV.GROUP_VEHICLE_VERSION_ID ");//RQ04–Detalhamento RN06
            sql.append("                      WHERE P1.PRODUCT_ID = GVVP.PRODUCT_ID ");//RQ04–Detalhamento RN06
            sql.append("                      AND GVVV.VEHICLE_VERSION_ID = :vehicleVersion) ");//RQ04–Detalhamento RN06 Value:1
            sql.append("              OR NOT EXISTS (SELECT 1 FROM TB_GROUP_VEHICLE_VERS_PRODUCT GVVP ");//RQ04–Detalhamento RN06
            sql.append("                             WHERE GVVP.PRODUCT_ID = P1.PRODUCT_ID)) ");//RQ04–Detalhamento RN06
            sql.append("        AND EXISTS (SELECT 1 FROM TB_PRODUCT_FINANCIAL_BRAND PFB ");//RQ12 – Detalhamento RN14 
            sql.append("                    INNER JOIN TB_STRUCTURE TS ON PFB.FINANCIAL_BRAND_ID = TS.FINANCIAL_BRAND_ID ");//RQ12 – Detalhamento RN14
            sql.append("                    WHERE P1.PRODUCT_ID = PFB.PRODUCT_ID ");//RQ12 – Detalhamento RN14
            sql.append("                    AND TS.STRUCTURE_ID = :structureId) ");//RQ12 – Detalhamento RN14 Value:14 
            sql.append("        AND ( EXISTS(SELECT 1 FROM TB_PRODUCT_SPECIAL_VEHICLE PSV ");//RQ11 –Detalhamento RN13
            sql.append("                     WHERE PSV.PRODUCT_ID = P1.PRODUCT_ID AND PSV.SPECIAL_VEHICLE_TYPE_ID IN (:vehicleSpecial) ) ");//RQ11 –Detalhamento RN13 Value:1
            sql.append("              OR NOT EXISTS(SELECT 1 FROM TB_PRODUCT_SPECIAL_VEHICLE PSV WHERE PSV.PRODUCT_ID = P1.PRODUCT_ID)) ");//RQ11 –Detalhamento RN13
            
            if(AppConstants.TYPE_FINANCING_CDC_FLEX.equals(productFilter.getFinanceType())){
                sql.append(" AND EXISTS ( SELECT 1 FROM TB_REPACKAGE_PRODUCT RP INNER JOIN TB_REPACKAGE R ON R.REPACKAGE_ID = RP.REPACKAGE_ID WHERE R.ACTIVE = 1 AND P1.PRODUCT_ID = RP.PRODUCT_ID ) ");
            }
            sql.append(" ORDER BY P1.IMPORT_CODE ASC ");
            
            if(productFilter.getVehicleSpecials().isEmpty()){
                productFilter.getVehicleSpecials().add(0l) ;
            }
            
            Query query =  super.getEntityManager().createNativeQuery(sql.toString());
      
            query.setParameter("financeType", productFilter.getFinanceType());
            query.setParameter("saleType", productFilter.getSaleType());
            if(productFilter.getManufactureYear() == null) {
                query.setParameter("manufactureYear", productFilter.getManufactureYear());
            }
            query.setParameter("modelYear", productFilter.getModelYear());
            query.setParameter("currentDate", productFilter.getCurrentDate());
            query.setParameter("personType", productFilter.getPersonType());
            query.setParameter("idCalculation", productFilter.getIdCalculation());
            query.setParameter("concessionaria", StructureTypeEnum.CONCESSIONARIA.name());
            query.setParameter("structureId", productFilter.getStructureId());
            query.setParameter("vehicleVersion", productFilter.getVehicleVersion());
            query.setParameter("vehicleSpecial", productFilter.getVehicleSpecials());
    
            List<Object[]> products = query.getResultList(); 
            List<ProductDTO> listProducts = new ArrayList<ProductDTO>(); 
      
            for(Object[] row : products){
                ProductDTO dto = new ProductDTO();
                dto.setProductId(CriptoUtilsOmega2.encrypt(Long.valueOf((row[0]).toString())));
                dto.setDescription((String) row[1]);
                dto.setPromotional(Boolean.parseBoolean((String) row[2]));
                dto.setProductInformation((String) row[3]);
          
                listProducts.add(dto);
            }
            
            LOGGER.debug(" >> END findProduct ");
            return listProducts;
            
        } catch (UnexpectedException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }    
        
    }
    
    @SuppressWarnings("unchecked")
    public ProductEntity findProductByImportCodeAndPeriod(String importCode, Date createdDate)
            throws UnexpectedException {

        try {
            LOGGER.debug(" >> INIT findProductByImportCodeAndPeriod ");
            
            StringBuilder sql = new StringBuilder();
            
            sql.append(" SELECT pd FROM ProductEntity pd                            ");
            sql.append("    WHERE pd.importCode = :importCode                       ");
            sql.append("    AND :createDate BETWEEN pd.initialPeriod                ");
            sql.append("                    AND  pd.finalPeriod                     ");

            Query query = super.getEntityManager().createQuery(sql.toString());
            query.setParameter("importCode", importCode);
            query.setParameter("createDate", createdDate);

            List<ProductEntity> ls = query.getResultList();
            if (!ls.isEmpty()) {
                return ls.get(0);
            }

            LOGGER.debug(" >> END findProductByImportCodeAndPeriod ");
            return null;

        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }

    }
    
}
