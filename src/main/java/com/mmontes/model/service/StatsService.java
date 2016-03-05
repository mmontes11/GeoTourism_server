package com.mmontes.model.service;

import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface StatsService {
    List<MetricDto> getAllMetrics();
    List<List<Double>> getStats(String metricID) throws InstanceNotFoundException;
}
