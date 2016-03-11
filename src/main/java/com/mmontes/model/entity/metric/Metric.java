package com.mmontes.model.entity.metric;

import com.mmontes.model.dao.StatsDao;
import com.mmontes.util.dto.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class Metric {

    @Autowired
    protected StatsDao statsDao;
    private String id;
    private String name;

    public Metric() {
    }

    public Metric(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract StatsDto getStats(List<Long> TIPs);
}
