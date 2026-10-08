package com.example.congressionalapp;

public interface WaveDataProvider {
    interface Callback {
        void onSuccess(WaveConditionReport data);
        void onError(String errorMessage);
    }

    void fetchWaveData(BeachLocation beach, Callback callback);
}
