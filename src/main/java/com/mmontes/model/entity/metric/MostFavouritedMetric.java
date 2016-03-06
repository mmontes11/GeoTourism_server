package com.mmontes.model.entity.metric;

import java.util.List;

public class MostFavouritedMetric extends Metric {

    public MostFavouritedMetric() {
    }

    public MostFavouritedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public List<List<Double>> getStats() {
        return super.statsDao.getMostFavourited();
    }
}
