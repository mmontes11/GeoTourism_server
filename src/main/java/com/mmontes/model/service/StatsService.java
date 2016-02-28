package com.mmontes.model.service;

import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.dto.StatsDto;
import com.mmontes.util.exception.InvalidMetricException;

import java.util.List;

public interface StatsService {
    List<MetricDto> getMetrics();
    StatsDto getStats(int metricID) throws InvalidMetricException;
}
