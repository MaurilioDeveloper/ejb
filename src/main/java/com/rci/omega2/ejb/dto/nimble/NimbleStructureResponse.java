package com.rci.omega2.ejb.dto.nimble;

public class NimbleStructureResponse extends NimbleBaseResponse{
    private NimbleStructureDataDTO data;

    public NimbleStructureDataDTO getData() {
        return data;
    }

    public void setData(NimbleStructureDataDTO data) {
        this.data = data;
    }

}
