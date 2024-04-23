package com.udlive.flinktest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class Telemetry {

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("uptime_s")
    private Integer uptime;

    @JsonProperty("csq")
    private Integer signalStrength;

    @JsonProperty("batV")
    private Integer batteryVoltage;

    @JsonProperty("timestamp_utc")
    private Integer epochTimestamp;

    private Double distanceToWater;

    @JsonProperty("rx0")
    private void unpackNameFromNestedObject(Map<String, Object> metadata) {
        if (metadata.get("dist_mm") instanceof List) {
            List<Double> distanceToWaterList = (List<Double>) metadata.get("dist_mm");

            if (distanceToWaterList != null && !distanceToWaterList.isEmpty()) {
                distanceToWater = distanceToWaterList.get(0);
            }
        }
    }


    public String getDeviceId() {
        return deviceId;
    }

    public Integer getUptime() {
        return uptime;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public Integer getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setUptime(Integer uptime) {
        this.uptime = uptime;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public void setBatteryVoltage(Integer batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Double getDistanceToWater() {
        return distanceToWater;
    }


    public Integer getEpochTimestamp() {
        return epochTimestamp;
    }

    public void setEpochTimestamp(Integer epochTimestamp) {
        this.epochTimestamp = epochTimestamp;
    }

    @Override
    public String toString() {
        return "Telemetry{" +
                "deviceId='" + deviceId + '\'' +
                ", uptime=" + uptime +
                ", signalStrength=" + signalStrength +
                ", batteryVoltage=" + batteryVoltage +
                ", distanceToWater=" + distanceToWater +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
