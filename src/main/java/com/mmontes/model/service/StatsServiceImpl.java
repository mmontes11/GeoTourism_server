package com.mmontes.model.service;

import com.mmontes.model.dao.StatsDao;
import com.mmontes.model.entity.Metric;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.dto.StatsDto;
import com.mmontes.util.exception.InvalidMetricException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("StatsService")
@Transactional
public class StatsServiceImpl implements StatsService {

    @Autowired
    private DtoService dtoService;

    @Autowired
    private StatsDao statsDao;

    @Override
    public List<MetricDto> getMetrics() {
        List<MetricDto> metricDtos = new ArrayList<>();
        for (Metric m : Metric.values()){
            metricDtos.add(dtoService.Metric2MetricDto(m));
        }
        return metricDtos;
    }

    @Override
    public StatsDto getStats(int metricID) throws InvalidMetricException {
        if (metricID == Metric.MOST_FAVOURITED.getId()){
            return null;
        }
        if (metricID == Metric.MOST_COMMENTED.getId()){
            return null;
        }
        if (metricID == Metric.BEST_RATED.getId()){
            return null;
        }
        throw new InvalidMetricException(metricID);
    }
}
