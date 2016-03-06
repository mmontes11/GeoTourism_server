package com.mmontes.model.dao;

import java.util.List;

public interface StatsDao {

    List<List<Double>> getMostFavourited();
    List<List<Double>> getMostCommented();
    List<List<Double>> getBestRated();
}
