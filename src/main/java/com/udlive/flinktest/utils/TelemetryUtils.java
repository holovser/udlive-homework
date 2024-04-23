package com.udlive.flinktest.utils;

import com.udlive.flinktest.model.Telemetry;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TelemetryUtils {
    public static Integer getTimeStampFromTelemetry(Telemetry telemetry) {
        Long timestamp = telemetry.getEpochEpochTimestampSeconds();

        LocalDate localDate = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();

        return localDate.getDayOfYear();
    }
}
