package com.mmontes.util.dto;


import com.mmontes.model.entity.Stats;

import java.util.List;

public class StatsDto {
    private StatsOptions options;
    private List<Stats> data;

    public StatsDto() {
    }

    public StatsDto(StatsOptions options, List<Stats> data) {
        this.options = options;
        this.data = data;
    }

    public StatsOptions getOptions() {
        return options;
    }

    public void setOptions(StatsOptions options) {
        this.options = options;
    }

    public List<Stats> getData() {
        return data;
    }

    public void setData(List<Stats> data) {
        this.data = data;
    }
}
