package com.mmontes.model.entity.metric;

import com.mmontes.model.dao.FavouriteDao;
import com.mmontes.util.dto.LatLngWeight;
import com.mmontes.util.dto.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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
    public StatsDto getStats(List<Long> TIPs, Date fromDate, Date toDate) {
        Integer max = favouriteDao.getMaxNumOfFavs();
        List<LatLngWeight> data = super.statsDao.getMostFavourited(TIPs,fromDate,toDate);
        return new StatsDto(max,data);
    }
}
