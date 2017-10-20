package com.rci.omega2.ejb.utils;

import java.util.ArrayList;
import java.util.List;

import com.rci.omega2.common.util.AppUtil;
import com.rci.omega2.ejb.dto.FinanceTypeDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;
import com.rci.omega2.ejb.exception.UnexpectedException;
import com.rci.omega2.entity.FinanceTypeEntity;
import com.rci.omega2.entity.ProvinceEntity;

public class AppDtoUtils {

    public static ProvinceDTO populateProvinceDTO(ProvinceEntity entity) throws UnexpectedException {
        if (AppUtil.isNullOrEmpty(entity)) {
            return null;
        }
        ProvinceDTO dto = new ProvinceDTO();

        dto.setId(CriptoUtilsOmega2.encrypt(entity.getId()));
        dto.setDescription(entity.getDescription());

        return dto;
    }

    public static List<FinanceTypeDTO> populateFinanceTypeDTO(List<FinanceTypeEntity> financeType, Boolean crypto)
            throws UnexpectedException {
        List<FinanceTypeDTO> listDto = new ArrayList<FinanceTypeDTO>();

        for (FinanceTypeEntity entity : financeType) {
            FinanceTypeDTO dto = new FinanceTypeDTO();
            dto.setFinanceTypeId(String.valueOf(entity.getId()));
            if (crypto) {
                dto.setFinanceTypeId(CriptoUtilsOmega2.encrypt(entity.getId()));
            }
            dto.setDescription(entity.getDescription());
            listDto.add(dto);
        }

        return listDto;
    }

    public static boolean isNotNullAndNotEmpty(String string) {
        if (string != null && !string.isEmpty()) {
            return true;
        }
        return false;
    }
}
