package com.mmontes.model.entity;

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

    public String toString() {
        return this.getName();
    }
}
