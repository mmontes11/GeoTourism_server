package com.mmontes.model.entity;

import com.mmontes.util.exception.InvalidMetricException;

public enum Metric {

    MOST_FAVOURITED(0),
    MOST_COMMENTED(1),
    BEST_RATED(2);

    private int id;

    Metric(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        switch (this){
            case MOST_FAVOURITED:
                return "Most Favourited";
            case MOST_COMMENTED:
                return "Most Commented";
            case BEST_RATED:
                return "Best Rated";
            default:
                return null;
        }
    }

    public static Metric getMetricFromID(int metricID) throws InvalidMetricException {
        for(Metric metric : values()){
            if (metric.getId() == metricID){
                return metric;
            }
        }
        throw new InvalidMetricException(metricID);
    }

    public String toString() {
        return this.getName();
    }
}
