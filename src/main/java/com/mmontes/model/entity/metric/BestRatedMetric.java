package com.mmontes.model.entity.metric;


import com.mmontes.util.Constants;
import com.mmontes.util.dto.LatLngWeight;
import com.mmontes.util.dto.StatsDto;

import java.util.List;

public class BestRatedMetric extends Metric {

    public BestRatedMetric() {
    }

    public BestRatedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public StatsDto getStats(List<Long> TIPs) {
        Integer max = Constants.MAX_RATING_VALUE;
        List<LatLngWeight> data = super.statsDao.getBestRated(TIPs);
        return new StatsDto(max, data);
    }
}
