
package com.rci.omega2.ejb.dto.nimble;

public class NimbleUserResponseDTO extends NimbleBaseResponse {

    private NimbleUserDataDTO data;

    public NimbleUserDataDTO getData() {
        return data;
    }

    public void setData(NimbleUserDataDTO data) {
        this.data = data;
    }

}