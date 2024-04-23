package com.udlive.flinktest.statistics;

public class Accumulator {

    public Integer distanceCount = 0;
    public Integer allCount = 0;
    public Integer rebootCounts = 0;


    public Double sumDistance = 0.0;
    public Double minDistance = Double.MAX_VALUE;
    public Double maxDistance = Double.MIN_VALUE;
    public Double averageDistance = 0.0;

    public Integer sumBatteryVoltage = 0;
    public Integer minBatteryVoltage = Integer.MAX_VALUE;
    public Integer maxBatteryVoltage = Integer.MIN_VALUE;
    public Double averageBatteryVoltage = 0.0;

    public Integer sumSignalStrength = 0;
    public Integer minSignalStrength = Integer.MAX_VALUE;
    public Integer maxSignalStrength = Integer.MIN_VALUE;
    public Double averageSignalStrength = 0.0;

    public Accumulator() {
    }
}
