package com.mmontes.model.dao;

import com.mmontes.util.dto.LatLngWeight;

import java.util.Date;
import java.util.List;

public interface StatsDao {
    List<LatLngWeight> getMostFavourited(List<Long> TIPs, Date fromDate, Date toDate);
    List<LatLngWeight> getMostCommented(List<Long> TIPs,Date fromDate,Date toDate);
    List<LatLngWeight> getBestRated(List<Long> TIPs,Date fromDate,Date toDate);
}
