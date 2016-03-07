package com.mmontes.model.dao;

import com.mmontes.util.dto.LatLngWeight;

import java.util.List;

public interface StatsDao {
    List<LatLngWeight> getMostFavourited();
    List<LatLngWeight> getMostCommented();
    List<LatLngWeight> getBestRated();
}
