package com.rci.omega2.ejb.integrations.santander;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.rci.omega2.ejb.utils.LinkTypeEnum;

public class Utils {

    public static String bigToString(BigDecimal value, Integer decimal) {

        if (value != null) {
            value = value.setScale(2, BigDecimal.ROUND_DOWN);

            DecimalFormat df = new DecimalFormat();

            df.setMaximumFractionDigits(decimal);

            df.setMinimumFractionDigits(decimal);

            df.setGroupingUsed(false);

            return df.format(value);
        }
        return null;
    }

    public static boolean isSpouse(String linkTypeDescritpion) {
        boolean result = linkTypeDescritpion.equals(LinkTypeEnum.CONJUGE_PROP.getValue())
                || linkTypeDescritpion.equals(LinkTypeEnum.CONJUGE_AVAL1.getValue())
                || linkTypeDescritpion.equals(LinkTypeEnum.CONJUGE_AVAL2.getValue());
        return result;
    }

    public static boolean isRetiredOrAutonomous(String ocupation) {
        boolean result = ocupation.equals("APOSENTADO / PENSIONISTA") || ocupation.equals("AUTONOMO");
        return result;
    }
}
