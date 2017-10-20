package com.rci.omega2.ejb.dto.simulation;

import com.rci.omega2.ejb.dto.BaseDTO;
import com.rci.omega2.ejb.dto.ColorDTO;
import com.rci.omega2.ejb.dto.ProvinceDTO;

public class CarDetailsDTO extends BaseDTO{

    /**serial version*/
    private static final long serialVersionUID = 1L;
    
    private String chassiNumber;
    private String registerNumber;
    private ProvinceDTO registrationProvince;
    private ProvinceDTO licensingProvince;
    private ColorDTO vehicleColor;
    private String renavam;
    private String vehicleOrigin;

    /* ano fabricacao veiculo usado */
    private Integer manufactureYear;
    
    public String getChassiNumber() {
        return chassiNumber;
    }

    public void setChassiNumber(String chassiNumber) {
        this.chassiNumber = chassiNumber;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public ProvinceDTO getRegistrationProvince() {
        return registrationProvince;
    }

    public void setRegistrationProvince(ProvinceDTO registrationProvince) {
        this.registrationProvince = registrationProvince;
    }

    public ProvinceDTO getLicensingProvince() {
        return licensingProvince;
    }

    public void setLicensingProvince(ProvinceDTO licensingProvince) {
        this.licensingProvince = licensingProvince;
    }

    public ColorDTO getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(ColorDTO vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getVehicleOrigin() {
        return vehicleOrigin;
    }

    public void setVehicleOrigin(String vehicleOrigin) {
        this.vehicleOrigin = vehicleOrigin;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

}
