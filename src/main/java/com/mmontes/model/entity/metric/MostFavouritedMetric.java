package com.mmontes.model.entity.metric;

import com.mmontes.model.dao.FavouriteDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MostFavouritedMetric extends Metric{

    @Autowired
    private FavouriteDao favouriteDao;

    public MostFavouritedMetric() {
    }

    public MostFavouritedMetric(String id, String name) {
        super(id, name);
    }

    @Override
    public List<List<Double>> getStats() {
        Integer maxNumOfFavs = favouriteDao.getMaxNumOfFavs();
        return super.statsDao.getMostFavourited(maxNumOfFavs);
    }


}
