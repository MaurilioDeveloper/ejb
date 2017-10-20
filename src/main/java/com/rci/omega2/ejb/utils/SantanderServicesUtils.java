package com.rci.omega2.ejb.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.exception.UnexpectedException;

/**
 * 
 * @author Ricardo Zandonai rzandonai@stefanini.com
 *
 */
public class SantanderServicesUtils {

    public static String dateToStringSantanderFormat(Date date) {
        if(AppUtil.isNullOrEmpty(date)){
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.format(date);
    }

    /**
     * 
     * @param strDate
     * @param mask
     * @return
     * @throws ParseException
     */
    public static Date santanderFormatToDate(String strDate, String mask) throws ParseException {

        //Returna null caso esteja zerado
        if(AppUtil.isNullOrEmpty(strDate) || 0 == Integer.valueOf(strDate)){
            return null;
        }
        
        
        SimpleDateFormat fmt = new SimpleDateFormat(mask);
        Date returnDate = fmt.parse(strDate);

        /*
         * Verifica se a data enviada pelo Santander é uma data valida, caso não
         * será retornada como null (ex. 01/15/2000)
         */
        if (fmt.format(returnDate).equals(strDate)) {
            return returnDate;
        }
        return null;
    }
    

    public static String boolToSOrN(Boolean handicapped) {
        return (handicapped ? "S" : "N");
    }

    public static Boolean sOrNtoBoolToBoolean(String value) {
        // case the value is not S or N it will return null
        if ("S".equals(value.trim())) {
            return Boolean.TRUE;
        } else if ("N".equals(value.trim())) {
            return Boolean.FALSE;
        } else {
            return null;
        }
    }

    public static String formatDDD(String ddd) {
        return formatNumber(ddd, 3);
    }
    
    public static String formatPhone(String tel) {
        return formatNumber(tel, 10);
    }

    public static String formatPostalCode(String postCode) {
        return formatNumber(postCode, 8);
    }
    
    public static String formatRamal(String ramal) {
        return formatNumber(ramal, 5);
    }
    
    public static String formatNumber(Integer number, int qta) {
        if(AppUtil.isNullOrEmpty(number)){
            return null;
        }
        return formatNumber(String.valueOf(number), qta);
    }
    
    public static String formatNumber(String number, int qta) {
        if(AppUtil.isNullOrEmpty(number)){
            return null;
        }
        number = AppUtil.onlyNumbers(number);
        
        if (number.length() >= qta) {
            return number.substring(number.length() - qta);
        }
        
        return StringUtils.leftPad(number, qta, "0");
    }

    public static String boolToNorI(Boolean nationalCar) {
        return (nationalCar ? "N" : "I");
    }

    public static String personTypeToForJ(String name) {
        return name.equalsIgnoreCase("PF") ? "F" : "J";
    }

    public static String valueOrDefault(String description, String def) {
        if (description != null) {
            return description;
        }
        return def;
    }
    /**
     * 
     * @param value
     * @return
     */
    public static BigDecimal stringToBigDecimal(String value){
        if(AppUtil.isNullOrEmpty(value)){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value).divide(BigDecimal.valueOf(100));
    } 
    
    /**
     * 
     * @param insertedDate
     * @return
     */
    public static Date add20DaysOnExpireDate(Date insertedDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(insertedDate);
        cal.add(Calendar.DATE, 20);
        
        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SATURDAY:{
                cal.add(Calendar.DATE, 2);
                break;
            }
            case Calendar.SUNDAY:{
                cal.add(Calendar.DATE, 1);
                break;
            }
        }
        
        return cal.getTime();
    }
    
    public static boolean isPessoaJuridica(String personType) throws UnexpectedException {
               
        if("F".equalsIgnoreCase(personType)){
            return false;
        }
        
        if("J".equalsIgnoreCase(personType)){
            return true;
        }
        
        throw new UnexpectedException("Tipo pessoa não identificada [" + personType + "]");
    }
    
    
    public static String extractCPF(String documentNumber){
        documentNumber = AppUtil.onlyNumberNullableZero(documentNumber);
        if(documentNumber.length() > 11){
            return documentNumber.substring(documentNumber.length() - 11);
        }
        
        return documentNumber;
    }
    
    public static String extractCNPJ(String documentNumber){
        documentNumber = AppUtil.onlyNumberNullableZero(documentNumber);
        if(documentNumber.length() > 11){
            return documentNumber.substring(documentNumber.length() - 14);
        }
        
        return documentNumber;
    }
    
    public static String extractPhone(String phone){
        if(!AppUtil.isNullOrEmpty(phone))
            return Long.valueOf(phone.replaceAll("[^\\d]", "")).toString();
        return "";
    }
    
    public static String extractFillZeroToLeft(String value, Integer qt){
        if(value != null && !"".equals(value))
             return StringUtils.leftPad(Long.valueOf(value).toString(), qt, "0");
        return "";
    }
    
    
    public static Date convertDateSinceOf(String month, String year) throws ParseException{
        if(AppUtil.isNullOrEmpty(month) || AppUtil.isNullOrEmpty(year)){
            return null;
        }
        return new SimpleDateFormat("MM/yyyy").parse(month + "/" + year);
    }
    
    
    public static String extractImportCodeBank(String bankName){
        Pattern p = Pattern.compile("\\d\\d\\d");
        Matcher m = p.matcher(bankName);
        if(m.find()){
               return m.group(0);
        }
        return null;
    }
    
    
}
