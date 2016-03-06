package com.mmontes.model.entity.metric;


import java.util.List;

public class MostCommentedMetric extends Metric {

    public MostCommentedMetric() {
    }

    public MostCommentedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public List<List<Double>> getStats() {
        return super.statsDao.getMostCommented();
    }
}
