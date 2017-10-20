package com.rci.omega2.ejb.utils;

import java.math.BigDecimal;
import java.util.List;

import com.rci.omega2.common.util.AppUtil;

public class JdbcUtils {
    
    public static String convertListLongToStringParameter(List<Long> list){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i < list.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }

    public static BigDecimal adjustValue(Object obj){
        if(AppUtil.isNullOrEmpty(obj)){
            return null;
        }
        
        BigDecimal temp = ((BigDecimal) obj).movePointRight(AppConstants.DECIMAL_2);
        return temp;
    }
}
