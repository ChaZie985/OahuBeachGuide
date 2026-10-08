package com.example.congressionalapp;

public class WaveConditionReport {
    private final float heightFeet;
    private final float heightMeters;
    private final float periodSeconds;
    private final float directionDegrees;
    private final float windSpeedMph;
    private final float windDirectionDeg;
    private final float uvIndex;
    private final float tideFeet;
    private final String sourceName;
    private final long timestamp;

    public WaveConditionReport(float heightFeet, float heightMeters, float periodSeconds, float directionDegrees,
                               float windSpeedMph, float windDirectionDeg, float uvIndex, float tideFeet,
                               String sourceName, long timestamp) {
        this.heightFeet = heightFeet;
        this.heightMeters = heightMeters;
        this.periodSeconds = periodSeconds;
        this.directionDegrees = directionDegrees;
        this.windSpeedMph = windSpeedMph;
        this.windDirectionDeg = windDirectionDeg;
        this.uvIndex = uvIndex;
        this.tideFeet = tideFeet;
        this.sourceName = sourceName;
        this.timestamp = timestamp;
    }

    public WaveConditionReport(float heightFeet, float heightMeters, float periodSeconds, float directionDegrees, String sourceName, long timestamp) {
        this(heightFeet, heightMeters, periodSeconds, directionDegrees, 15f, 70f, 11f, 1.6f, sourceName, timestamp);
    }

    public float getHeightFeet() {
        return heightFeet;
    }

    public float getHeightMeters() {
        return heightMeters;
    }

    public float getPeriodSeconds() {
        return periodSeconds;
    }

    public float getDirectionDegrees() {
        return directionDegrees;
    }

    public float getWindSpeedMph() {
        return windSpeedMph;
    }

    public float getWindDirectionDeg() {
        return windDirectionDeg;
    }

    public float getUvIndex() {
        return uvIndex;
    }

    public float getTideFeet() {
        return tideFeet;
    }

    public String getSourceName() {
        return sourceName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
