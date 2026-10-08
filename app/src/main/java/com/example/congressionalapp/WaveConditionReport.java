package com.example.congressionalapp;

/**
 * DATA MODEL: WaveConditionReport
 * 
 * An immutable object that holds the processed results of a wave data request.
 * It translates raw metric units into imperial units (feet) used for user display and safety logic.
 */
public class WaveConditionReport {
    private final float heightFeet;
    private final float heightMeters;
    private final float periodSeconds;
    private final float directionDegrees;
    private final String sourceName;
    private final long timestamp;

    public WaveConditionReport(float heightFeet, float heightMeters, float periodSeconds, float directionDegrees, String sourceName, long timestamp) {
        this.heightFeet = heightFeet;
        this.heightMeters = heightMeters;
        this.periodSeconds = periodSeconds;
        this.directionDegrees = directionDegrees;
        this.sourceName = sourceName;
        this.timestamp = timestamp;
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

    public String getSourceName() {
        return sourceName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
