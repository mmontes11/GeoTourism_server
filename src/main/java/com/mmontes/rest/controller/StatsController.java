package com.mmontes.rest.controller;

import com.mmontes.model.service.StatsService;
import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.dto.StatsDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @RequestMapping(value = "/social/stats/metrics", method = RequestMethod.GET)
    public ResponseEntity<List<MetricDto>>
    getMetrics() {
        return new ResponseEntity<>(statsService.getAllMetrics(), HttpStatus.OK);
    }

    @RequestMapping(value = "/social/stats/metric/{metricID}", method = RequestMethod.GET)
    public ResponseEntity<StatsDto>
    getMetricStats(@PathVariable String metricID,
                   @RequestParam(value = "tips", required = true) List<Long> TIPs) {
        try {
            StatsDto statsDto = statsService.getStats(metricID,TIPs);
            return new ResponseEntity<>(statsDto, HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}