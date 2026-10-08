package com.example.congressionalapp;

public class WaveDataCoordinator {
    private final WaveDataProvider openMeteoSource;

    public WaveDataCoordinator() {
        this.openMeteoSource = new OpenMeteoWaveProvider();
    }

    public void fetchWaveData(BeachLocation beach, WaveDataProvider.Callback callback) {
        openMeteoSource.fetchWaveData(beach, callback);
    }
}
