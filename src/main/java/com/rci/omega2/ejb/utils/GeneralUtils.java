package com.rci.omega2.ejb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.entity.DossierStatusEntity;
import com.rci.omega2.entity.SaleTypeEntity;

public class GeneralUtils {
    
    private static final Logger LOGGER = LogManager.getLogger(GeneralUtils.class);

    public static boolean isAnEmail(String value) {
        if (value != null && !value.trim().isEmpty()) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    public static Date addDayToDate(int quantity, Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.add(Calendar.DAY_OF_YEAR, quantity);
        return cal.getTime();
    }

    public static Date removeDayToDate(int quantity, Date baseDate) {
        return addDayToDate(quantity * -1, baseDate);
    }

    public static Boolean convertBoolean(BigDecimal value) {
        if (BigDecimal.ONE.equals(value)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static String convertClobToString(Clob data) throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        Reader reader = data.getCharacterStream();
        BufferedReader br = new BufferedReader(reader);

        String line;
        while (null != (line = br.readLine())) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
    
    public static PhoneDetail splitPhone(String value){
        PhoneDetail phone = new PhoneDetail();
        String ddd = (value.substring(0,2));
        String number = value.substring(2,value.length());
        phone.setDdd(ddd);
        phone.setNumber(number);
        return phone;
    }
    
    public static Integer calculateAge(Date birthDate){
        Calendar calBirthDate = Calendar.getInstance();
        calBirthDate.setTime(birthDate);
        int yearBd = calBirthDate.get(Calendar.YEAR);
        int monthBd = calBirthDate.get(Calendar.MONTH);
        int dayBd = calBirthDate.get(Calendar.DAY_OF_MONTH);
        Calendar calCurrentDate = Calendar.getInstance();
        calCurrentDate.setTime(new Date());
        int yearTd = calCurrentDate.get(Calendar.YEAR);
        int monthTd = calCurrentDate.get(Calendar.MONTH);
        int dayTd = calCurrentDate.get(Calendar.DAY_OF_MONTH);
        
        Integer result = yearTd-yearBd;
        if(monthTd<monthBd){
            result--;
            return result;
        }
        if(monthTd == monthBd){
            if(dayTd<dayBd){
                result--;
            }
        }
        return result;
    }

    public static String stringNotNull(String str){
        if(str == null){
            return "";
        }
        return str.trim();
    }
    
    public static BigDecimal bigDecimalNotNull(BigDecimal vl){
        if(vl == null){
            return BigDecimal.ZERO;
        }
        return vl;
    }
    
    public static List<Date> calculateInstalmentDate(Calendar firstDate, Integer term){
        List<Date> lista = new ArrayList<>();
        for (int i = 0; i < term; i++) {
            lista.add(firstDate.getTime());
            firstDate.add(Calendar.MONTH, 1);
        }
        return lista;
    }

    public static BigDecimal sum(BigDecimal vl01, BigDecimal vl02){
        vl01 = bigDecimalNotNull(vl01);
        vl02 = bigDecimalNotNull(vl02);
        return vl01.add(vl02);
    }

    public static BigDecimal percentualCalculate(BigDecimal entrada, BigDecimal total) throws UnexpectedException {
        entrada = bigDecimalNotNull(entrada);
        total = bigDecimalNotNull(total);
        BigDecimal percentual = entrada.movePointRight(2).divide(total, AppConstants.DECIMAL_2, getRoundBigdecimal());
        return percentual;
    }
    
    /*
     * Arredondamento padrão para simulação e condição de chamada Caso 0.145
     * arredonda para 0.14 Caso 0.146 arredonda para 0.15
     */
    public static BigDecimal roundPrice(BigDecimal value) {
        if (value != null) {
            return value.setScale(AppConstants.DECIMAL_2, getRoundBigdecimal());
        } else {
            return null;
        }
    }
    
    private static int getRoundBigdecimal(){
        return BigDecimal.ROUND_HALF_EVEN;
    }
    
    public static boolean isSaleTypeNewVehicle(SaleTypeEntity saleType){
        return saleType.getId().equals(AppConstants.SALE_TYPE_SHOW_ROOM) || saleType.getId().equals(AppConstants.SALE_TYPE_DIRECT);
    }
    
    public static boolean showDocumentList(DossierStatusEntity status){
        List<Long> statusValidos = new ArrayList<>();
        statusValidos.add(AppConstants.DOSSIER_STATUS_EM_ANALISE);
        statusValidos.add(AppConstants.DOSSIER_STATUS_NEGADA);
        statusValidos.add(AppConstants.DOSSIER_STATUS_APROVADA);
        statusValidos.add(AppConstants.DOSSIER_STATUS_EFETIVADA);
        statusValidos.add(AppConstants.DOSSIER_STATUS_DESISTENCIA);
        
        return statusValidos.contains(status.getId());
    }
    
    public static String cpfCnpjFormatter(String cpfCnpj){
        String cpfCnpjNumbers = AppUtil.onlyNumbers(cpfCnpj);
        if(AppUtil.isNullOrEmpty(cpfCnpjNumbers)){
            return cpfCnpjNumbers;
        }
        
        if(cpfCnpjNumbers.length() > 11){
            return cpfCnpjNumbers.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})", "$1.$2.$3/$4-$5");
        } else {
            return cpfCnpjNumbers.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})", "$1.$2.$3-$4");
        }
        
                
    }
    
    public static String cepFormatter(String cep) throws UnexpectedException {
        try {
            String cepNumbers = AppUtil.onlyNumbers(cep);
            if(AppUtil.isNullOrEmpty(cepNumbers)){
                return cepNumbers;
            }
            
            return cepNumbers.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})", "$1.$2-$3");
        } catch (Exception e) {
            LOGGER.error(AppConstants.ERROR, e);
            throw new UnexpectedException(e);
        }
    }
    
}
