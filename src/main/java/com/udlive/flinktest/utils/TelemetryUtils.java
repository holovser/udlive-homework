package com.udlive.flinktest.utils;

import com.udlive.flinktest.model.Telemetry;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TelemetryUtils {
    public static Integer getKeyFromDate(Telemetry telemetry) {
        Integer timestamp = telemetry.getEpochTimestamp();

        LocalDate localDate = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();

        return localDate.getDayOfYear();
    }
}
