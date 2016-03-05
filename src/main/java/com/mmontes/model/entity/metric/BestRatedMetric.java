package com.mmontes.model.entity.metric;


import com.mmontes.model.dao.StatsDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BestRatedMetric extends Metric{

    @Autowired
    private StatsDao statsDao;

    public BestRatedMetric() {
    }

    public BestRatedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public List<List<Double>> getStats() {
        return statsDao.getBestRated();
    }
}
