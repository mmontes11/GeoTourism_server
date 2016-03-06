package com.mmontes.model.service;

import com.mmontes.model.entity.metric.Metric;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("StatsService")
@Transactional
public class StatsServiceImpl implements StatsService {

    @Autowired
    private DtoService dtoService;

    @Autowired
    private List<Metric> metrics;

    @Override
    public List<MetricDto> getAllMetrics() {
        return dtoService.ListMetric2ListMetricDto(metrics);
    }

    private Metric getMetricByID(String id) throws InstanceNotFoundException {
        Metric metricFound = null;
        for (Metric metric : metrics) {
            if (metric.getId().equals(id)) {
                metricFound = metric;
                break;
            }
        }
        if (metricFound == null) {
            throw new InstanceNotFoundException(id, Metric.class.getName());
        }
        return metricFound;
    }

    @Override
    public List<List<Double>> getStats(String metricID) throws InstanceNotFoundException {
        Metric metric = getMetricByID(metricID);
        return metric.getStats();
    }
}
