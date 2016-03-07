package com.mmontes.util.dto;

import java.util.List;

public class StatsDto {

    private Integer max;
    private List<LatLngWeight> data;

    public StatsDto(Integer max, List<LatLngWeight> data) {
        this.max = max;
        this.data = data;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public List<LatLngWeight> getData() {
        return data;
    }

    public void setData(List<LatLngWeight> data) {
        this.data = data;
    }
}
