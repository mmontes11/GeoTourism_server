package com.mmontes.util.dto;

public class TIPSyncDto {

    private Long osm_id;
    private String name;
    private Long tip_type_id;
    private Long city_id;
    private String lat;
    private String lon;
    private String info_url;

    public TIPSyncDto() {
    }

    public TIPSyncDto(Long osm_id, String name, Long tip_type_id, Long city_id, String lat, String lon, String info_url) {
        this.osm_id = osm_id;
        this.name = name;
        this.tip_type_id = tip_type_id;
        this.city_id = city_id;
        this.lat = lat;
        this.lon = lon;
        this.info_url = info_url;
    }

    public Long getOsm_id() {
        return osm_id;
    }

    public void setOsm_id(Long osm_id) {
        this.osm_id = osm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTip_type_id() {
        return tip_type_id;
    }

    public void setTip_type_id(Long tip_type_id) {
        this.tip_type_id = tip_type_id;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getInfo_url() {
        return info_url;
    }

    public void setInfo_url(String info_url) {
        this.info_url = info_url;
    }
}
