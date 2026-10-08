package com.example.congressionalapp;

/**
 * COORDINATOR: WaveDataCoordinator
 * 
 * Acting as a central router, this class determines which data provider to use
 * for fetching wave data. Currently, it defaults to OpenMeteo due to server 
 * reliability, but it is structured to support multiple sources.
 */
public class WaveDataCoordinator {
    private final WaveDataProvider openMeteoSource;

    public WaveDataCoordinator() {
        this.openMeteoSource = new OpenMeteoWaveProvider();
    }

    /**
     * Fetches wave data using the prioritized data provider.
     * @param beach The location details.
     * @param callback Result handler.
     */
    public void fetchWaveData(BeachLocation beach, WaveDataProvider.Callback callback) {
        // Utilizing OpenMeteo for consistent worldwide/nearshore coverage
        openMeteoSource.fetchWaveData(beach, callback);
    }
}
