package com.mmontes.test.model.service;

import com.mmontes.model.entity.Metric;
import com.mmontes.model.service.RatingService;
import com.mmontes.model.service.StatsService;
import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.exception.InvalidMetricException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.mmontes.test.util.Constants.NON_EXISTING_METRIC_ID;
import static com.mmontes.test.util.Constants.SPRING_CONFIG_TEST_FILE;
import static com.mmontes.util.Constants.SPRING_CONFIG_FILE;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Autowired
    private RatingService ratingService;

    @Before
    public void createData(){

    }

    @Test
    public void getMetrics() {
        for (MetricDto metricDto : statsService.getMetrics()) {
            assertNotNull(metricDto.getId());
            assertNotNull(metricDto.getName());
        }
    }

    @Test(expected = InvalidMetricException.class)
    public void getInvalidMetricException() throws InvalidMetricException {
        statsService.getStats(NON_EXISTING_METRIC_ID);
    }

    @Test
    public void getBestRatedStats(){
        try {
            statsService.getStats(Metric.BEST_RATED.getId());
        } catch (InvalidMetricException e) {
            e.printStackTrace();
        }
    }
}
