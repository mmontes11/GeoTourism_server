package com.mmontes.rest.response;

import java.util.List;

public class StatsResponse {
    private List<List<Double>> stats;

    public StatsResponse(List<List<Double>> stats) {
        this.stats = stats;
    }

    public List<List<Double>> getStats() {
        return stats;
    }

    public void setStats(List<List<Double>> stats) {
        this.stats = stats;
    }
}
