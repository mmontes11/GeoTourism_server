package com.mmontes.model.entity.metric;

import com.mmontes.model.dao.FavouriteDao;
import com.mmontes.util.dto.LatLngWeight;
import com.mmontes.util.dto.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MostFavouritedMetric extends Metric {

    @Autowired
    private FavouriteDao favouriteDao;

    public MostFavouritedMetric() {
    }

    public MostFavouritedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public StatsDto getStats() {
        Integer max = favouriteDao.getMaxNumOfFavs();
        List<LatLngWeight> data = super.statsDao.getMostFavourited();
        return new StatsDto(max,data);
    }
}
