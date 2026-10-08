package com.example.congressionalapp;

/**
 * INTERFACE: WaveDataProvider
 * 
 * Defines the standard contract for any wave data source (OpenMeteo, PacIOOS, etc.).
 * Implementation allows for asynchronous fetching of wave conditions for a given BeachLocation.
 */
public interface WaveDataProvider {
    
    /**
     * Callback interface to return results to the UI or background service.
     */
    interface Callback {
        void onSuccess(WaveConditionReport data);
        void onError(String errorMessage);
    }

    /**
     * Triggers an asynchronous network request to fetch wave data.
     * @param beach The target location to fetch data for.
     * @param callback The handler for success or failure results.
     */
    void fetchWaveData(BeachLocation beach, Callback callback);
}
