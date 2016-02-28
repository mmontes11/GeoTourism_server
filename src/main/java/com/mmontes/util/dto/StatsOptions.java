package com.mmontes.util.dto;

public class StatsOptions {
    private Double minOpacity;
    private Double maxZoom;
    private int max;
    private int blur;

    public StatsOptions() {
    }

    public Double getMinOpacity() {
        return minOpacity;
    }

    public void setMinOpacity(Double minOpacity) {
        this.minOpacity = minOpacity;
    }

    public Double getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(Double maxZoom) {
        this.maxZoom = maxZoom;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getBlur() {
        return blur;
    }

    public void setBlur(int blur) {
        this.blur = blur;
    }
}
