package com.udlive.flinktest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MetaData {
    @JsonProperty("dist_mm")
    private List<Double> distanceToWater;

    public List<Double> getDistanceToWater() {
        return distanceToWater;
    }

    public void setDistanceToWater(List<Double> distanceToWater) {
        this.distanceToWater = distanceToWater;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "distanceToWater=" + distanceToWater +
                '}';
    }
}
