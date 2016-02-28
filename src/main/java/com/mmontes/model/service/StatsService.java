package com.mmontes.model.service;

import com.mmontes.model.entity.Metric;
import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.exception.InvalidMetricException;

import java.util.List;

public interface StatsService {
    List<MetricDto> getMetrics();
    List<List<Double>> getStats(Metric metric) throws InvalidMetricException;
}
