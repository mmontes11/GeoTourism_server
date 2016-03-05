package com.mmontes.model.entity.metric;


import java.util.List;

public class BestRatedMetric extends Metric{

    public BestRatedMetric() {
    }

    public BestRatedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public List<List<Double>> getStats() {
        return super.statsDao.getBestRated();
    }
}
