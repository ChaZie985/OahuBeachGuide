package com.example.congressionalapp;

import java.io.Serializable;
import java.util.List;

public class BeachLocation implements Serializable {
    private final String id;
    private final String name;
    private final String hawaiianName;
    private final String shoreline;
    private final double latitude;
    private final double longitude;
    private final boolean reefProtected;
    private final boolean breaksFarOut;
    private final String pacIoosDataset;
    private final String permanentHazards;
    private final String localKnowledge;
    private final List<String> goodFor;
    private final List<String> notGoodFor;
    private final List<String> amenities;
    private final List<String> sources;
    private final String description;

    public BeachLocation(String id, String name, String hawaiianName, String shoreline, 
                         double latitude, double longitude, boolean reefProtected, 
                         boolean breaksFarOut, String pacIoosDataset, 
                         String permanentHazards, String localKnowledge,
                         List<String> goodFor, List<String> notGoodFor, 
                         List<String> amenities, List<String> sources,
                         String description) {
        this.id = id;
        this.name = name;
        this.hawaiianName = hawaiianName;
        this.shoreline = shoreline;
        this.latitude = latitude;
        this.longitude = longitude;
        this.reefProtected = reefProtected;
        this.breaksFarOut = breaksFarOut;
        this.pacIoosDataset = pacIoosDataset;
        this.permanentHazards = permanentHazards;
        this.localKnowledge = localKnowledge;
        this.goodFor = goodFor;
        this.notGoodFor = notGoodFor;
        this.amenities = amenities;
        this.sources = sources;
        this.description = description;
    }

    public BeachLocation(String name, double latitude, double longitude) {
        this(name.toLowerCase().replace(" ", "-"), name, name, "South shore", 
             latitude, longitude, false, false, null, 
             "None reported", "Check conditions before entering.", 
             List.of("swimming"), List.of("none"), 
             List.of("parking"), List.of("Hawaii Beach Safety"),
             "A scenic coastal beach area surrounded by natural terrain. Conditions vary by season and visitors should check ocean safety reports before entering. It is popular for local recreation and coastal enjoyment.");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getHawaiianName() { return hawaiianName; }
    public String getShoreline() { return shoreline; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public boolean isReefProtected() { return reefProtected; }
    public boolean isBreaksFarOut() { return breaksFarOut; }
    public String getPacIoosDataset() { return pacIoosDataset; }
    public String getPermanentHazards() { return permanentHazards; }
    public String getLocalKnowledge() { return localKnowledge; }
    public List<String> getGoodFor() { return goodFor; }
    public List<String> getNotGoodFor() { return notGoodFor; }
    public List<String> getAmenities() { return amenities; }
    public List<String> getSources() { return sources; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return name;
    }
}
