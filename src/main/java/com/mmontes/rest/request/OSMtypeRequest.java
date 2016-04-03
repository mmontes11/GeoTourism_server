package com.mmontes.rest.request;

public class OSMtypeRequest {

    private Long osmTypeId;
    private Long tipTypeId;

    public OSMtypeRequest() {
    }

    public OSMtypeRequest(Long osmTypeId, Long tipTypeId) {
        this.osmTypeId = osmTypeId;
        this.tipTypeId = tipTypeId;
    }

    public Long getOsmTypeId() {
        return osmTypeId;
    }

    public void setOsmTypeId(Long osmTypeId) {
        this.osmTypeId = osmTypeId;
    }

    public Long getTipTypeId() {
        return tipTypeId;
    }

    public void setTipTypeId(Long tipTypeId) {
        this.tipTypeId = tipTypeId;
    }
}
