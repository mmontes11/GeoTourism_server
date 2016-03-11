package com.mmontes.model.dao;

import com.mmontes.util.dto.LatLngWeight;

import java.util.List;

public interface StatsDao {
    List<LatLngWeight> getMostFavourited(List<Long> TIPs);
    List<LatLngWeight> getMostCommented(List<Long> TIPs);
    List<LatLngWeight> getBestRated(List<Long> TIPs);
}
