package com.udlive.flinktest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telemetry {

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("uptime_s")
    private Integer uptime;

    @JsonProperty("csq")
    private Integer signalStrength;

    @JsonProperty("batV")
    private Integer batteryVoltage;

    @JsonProperty("rx0")
    private MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
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

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public String toString() {
        return "Telemetry{" +
                "deviceId='" + deviceId + '\'' +
                ", uptime=" + uptime +
                ", signalStrength=" + signalStrength +
                ", batteryVoltage=" + batteryVoltage +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
