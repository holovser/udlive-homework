package com.udlive.flinktest.statistics;

import com.udlive.flinktest.model.Telemetry;
import org.apache.flink.api.common.functions.AggregateFunction;

import java.time.Instant;
import java.time.ZoneId;


public class SummaryAggregator implements AggregateFunction<Telemetry, SummaryAccumulator, SummaryAccumulator> {
    @Override
    public SummaryAccumulator createAccumulator() {
        return new SummaryAccumulator();
    }

    @Override
    public SummaryAccumulator add(Telemetry telemetry, SummaryAccumulator summaryAccumulator) {
        summaryAccumulator.allCount++;

        if (telemetry.getDistanceToWater() != null) {
            Double distanceToWater = telemetry.getDistanceToWater();

            summaryAccumulator.distanceCount++;
            summaryAccumulator.sumDistance += distanceToWater;
            summaryAccumulator.minDistance = getMin(summaryAccumulator.minDistance, distanceToWater);
            summaryAccumulator.maxDistance = getMax(summaryAccumulator.minDistance, distanceToWater);
        }

        Integer batteryVoltage = telemetry.getBatteryVoltage();
        summaryAccumulator.sumBatteryVoltage += batteryVoltage;
        summaryAccumulator.minBatteryVoltage = getMin(summaryAccumulator.minBatteryVoltage, batteryVoltage);
        summaryAccumulator.maxBatteryVoltage = getMax(summaryAccumulator.maxBatteryVoltage, batteryVoltage);


        Integer signalStrength = telemetry.getSignalStrength();
        summaryAccumulator.sumSignalStrength += signalStrength;
        summaryAccumulator.minSignalStrength = getMin(summaryAccumulator.minSignalStrength, signalStrength);
        summaryAccumulator.maxSignalStrength = getMax(summaryAccumulator.maxSignalStrength, signalStrength);

        if (telemetry.getUptime() == 0 ) {
            summaryAccumulator.rebootCounts++;
        }

        summaryAccumulator.date = Instant.ofEpochSecond(telemetry.getEpochTimestamp())
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();

        return summaryAccumulator;
    }

    @Override
    public SummaryAccumulator getResult(SummaryAccumulator summaryAccumulator) {
        summaryAccumulator.averageBatteryVoltage = Double.valueOf(summaryAccumulator.sumBatteryVoltage) / summaryAccumulator.allCount;
        summaryAccumulator.averageSignalStrength = Double.valueOf(summaryAccumulator.sumSignalStrength) / summaryAccumulator.allCount;

        summaryAccumulator.averageDistance = summaryAccumulator.sumDistance / summaryAccumulator.distanceCount;

        return summaryAccumulator;
    }

    @Override
    public SummaryAccumulator merge(SummaryAccumulator acc1, SummaryAccumulator acc2) {
        SummaryAccumulator res = new SummaryAccumulator();

        res.distanceCount = acc1.distanceCount + acc2.distanceCount;
        res.allCount = acc1.allCount + acc2.allCount;
        res.rebootCounts = acc1.rebootCounts + acc2.rebootCounts;

        res.sumSignalStrength = acc1.sumSignalStrength + acc2.sumSignalStrength;
        res.minSignalStrength = getMin(acc1.minSignalStrength, acc2.minSignalStrength);
        res.maxSignalStrength = getMax(acc1.maxSignalStrength, acc2.maxSignalStrength);
        res.averageSignalStrength = Double.valueOf(res.sumSignalStrength) / res.allCount;

        res.sumBatteryVoltage = acc1.sumBatteryVoltage + acc2.sumBatteryVoltage;
        res.averageBatteryVoltage = Double.valueOf(res.sumBatteryVoltage) / res.allCount;
        res.minBatteryVoltage = getMin(acc1.minBatteryVoltage, acc2.minBatteryVoltage);
        res.maxBatteryVoltage = getMax(acc1.maxBatteryVoltage, acc2.maxBatteryVoltage);

        res.sumDistance = acc1.sumDistance + acc2.sumDistance;
        res.averageDistance = res.sumDistance / res.distanceCount;
        res.minDistance = getMin(acc1.minDistance, acc2.minDistance);
        res.maxDistance = getMax(acc1.maxDistance, acc2.maxDistance);

        return res;
    }

    private <T extends Comparable> T getMin(T value1, T value2) {
        return value1.compareTo(value2) < 0 ? value1 : value2;
    }

    private <T extends Comparable> T getMax(T value1, T value2) {
        return value1.compareTo(value2) > 0 ? value1 : value2;
    }
}
