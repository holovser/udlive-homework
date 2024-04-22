package com.udlive.flinktest.statistics.average;

import com.udlive.flinktest.model.Telemetry;
import org.apache.flink.api.common.functions.AggregateFunction;

public class AvgDistanceToWaterAggregator implements AggregateFunction<Telemetry, Double, Double> {
    @Override
    public Double createAccumulator() {
        return 0.0;
    }

    @Override
    public Double add(Telemetry value, Double accumulator) {
//        if (value.getMetaData().getDistanceToWater().size() > 0 ) {
//            accumulator = (accumulator + value.getMetaData().getDistanceToWater().get(0))/2;
//        }

        return accumulator;
    }

    @Override
    public Double getResult(Double accumulator) {
        return accumulator;
    }

    @Override
    public Double merge(Double a, Double b) {
        return (a+b)/2;
    }
}
