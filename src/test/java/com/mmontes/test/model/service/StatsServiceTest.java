package com.mmontes.test.model.service;

import com.mmontes.model.entity.Metric;
import com.mmontes.model.service.RatingService;
import com.mmontes.model.service.StatsService;
import com.mmontes.model.service.TIPService;
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
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class StatsServiceTest {

    @Autowired
    private StatsService statsService;

    @Autowired
    private TIPService tipService;

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
        Metric.getMetricFromID(NON_EXISTING_METRIC_ID);
    }

    @Test
    public void getMetricFromID(){
        try {
            assertEquals(Metric.MOST_FAVOURITED,Metric.getMetricFromID(0));
            assertEquals(Metric.MOST_COMMENTED,Metric.getMetricFromID(1));
            assertEquals(Metric.BEST_RATED,Metric.getMetricFromID(2));
        } catch (InvalidMetricException e) {
            fail();
        }
    }

    @Test
    public void getBestRatedStats(){
        try {
            statsService.getStats(Metric.BEST_RATED);
        } catch (InvalidMetricException e) {
            fail();
        }
    }
}
