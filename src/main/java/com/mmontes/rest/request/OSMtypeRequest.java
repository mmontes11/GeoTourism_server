package com.mmontes.rest.request;

public class OSMtypeRequest {

    private Long osmValueId;
    private Long tipTypeId;

    public OSMtypeRequest() {
    }

    public OSMtypeRequest(Long osmValudeId, Long tipTypeId) {
        this.osmValueId = osmValudeId;
        this.tipTypeId = tipTypeId;
    }

    public Long getOsmValueId() {
        return osmValueId;
    }

    public void setOsmValueId(Long osmValueId) {
        this.osmValueId = osmValueId;
    }

    public Long getTipTypeId() {
        return tipTypeId;
    }

    public void setTipTypeId(Long tipTypeId) {
        this.tipTypeId = tipTypeId;
    }
}
