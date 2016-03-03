package com.mmontes.model.service;

import com.mmontes.model.dao.CommentDao;
import com.mmontes.model.dao.FavouriteDao;
import com.mmontes.model.dao.StatsDao;
import com.mmontes.model.entity.Metric;
import com.mmontes.util.dto.DtoService;
import com.mmontes.util.dto.MetricDto;
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

    @Autowired
    private FavouriteDao favouriteDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<MetricDto> getMetrics() {
        List<MetricDto> metricDtos = new ArrayList<>();
        for (Metric m : Metric.values()) {
            metricDtos.add(dtoService.Metric2MetricDto(m));
        }
        return metricDtos;
    }

    @Override
    public List<List<Double>> getStats(Metric metric) throws InvalidMetricException {
        if (metric.equals(Metric.MOST_FAVOURITED)) {
            Integer maxNumOfFavs = favouriteDao.getMaxNumOfFavs();
            return statsDao.getMostFavourited(maxNumOfFavs);
        }
        if (metric.equals(Metric.MOST_COMMENTED)) {
            Integer maxNumOfComments = commentDao.getMaxNumOfComments();
            return statsDao.getMostCommented(maxNumOfComments);
        }
        if (metric.equals(Metric.BEST_RATED)) {
            return statsDao.getBestRated();
        }
        throw new InvalidMetricException(metric.getId());
    }
}
