package com.mmontes.model.dao;

import java.util.List;

public interface StatsDao {

    List<List<Double>> getMostFavourited(Integer maxNumOfFavs);
    List<List<Double>> getMostCommented(Integer maxNumOfComments);
    List<List<Double>> getBestRated();
}
