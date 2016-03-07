package com.mmontes.model.service;

import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.dto.StatsDto;
import com.mmontes.util.exception.InstanceNotFoundException;

import java.util.List;

public interface StatsService {
    List<MetricDto> getAllMetrics();
    StatsDto getStats(String metricID) throws InstanceNotFoundException;
}
