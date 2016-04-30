package com.mmontes.rest.controller;

import com.mmontes.model.service.StatsService;
import com.mmontes.util.Constants;
import com.mmontes.util.dto.MetricDto;
import com.mmontes.util.dto.StatsDto;
import com.mmontes.util.exception.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @RequestMapping(value = "/social/stats/metric/{metricID}", method = RequestMethod.POST)
    public ResponseEntity<StatsDto>
    getMetricStats(@PathVariable String metricID,
                   @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date fromDate,
                   @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = Constants.DATE_FORMAT) Date toDate,
                   @RequestBody List<Long> TIPs){
        try {
            StatsDto statsDto = statsService.getStats(metricID,TIPs,fromDate,toDate);
            return new ResponseEntity<>(statsDto, HttpStatus.OK);
        } catch (InstanceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
