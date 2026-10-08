package com.example.congressionalapp;

import androidx.annotation.NonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/**
 * IMPLEMENTATION: OpenMeteoWaveProvider
 * 
 * Fetches live offshore wave data from the Open-Meteo Marine API. 
 * This class handles the networking via OkHttp and JSON parsing of wave heights,
 * periods, and directions based on the beach's GPS coordinates.
 */
public class OpenMeteoWaveProvider implements WaveDataProvider {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void fetchWaveData(BeachLocation beach, Callback callback) {
        String url = "https://marine-api.open-meteo.com/v1/marine?latitude=" + beach.getLatitude() + 
                     "&longitude=" + beach.getLongitude() + "&hourly=wave_height,wave_period,wave_direction";

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonData = response.body().string();
                        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                        JsonObject hourly = jsonObject.getAsJsonObject("hourly");
                        
                        float heightMeters = hourly.getAsJsonArray("wave_height").get(0).getAsFloat();
                        float period = hourly.getAsJsonArray("wave_period").get(0).getAsFloat();
                        float direction = hourly.getAsJsonArray("wave_direction").get(0).getAsFloat();
                        float heightFeet = heightMeters * 3.28084f;

                        WaveConditionReport data = new WaveConditionReport(heightFeet, heightMeters, period, direction, "OpenMeteo", System.currentTimeMillis());
                        callback.onSuccess(data);
                    } catch (Exception e) {
                        callback.onError("Error parsing Open-Meteo data");
                    }
                } else {
                    callback.onError("Open-Meteo server error");
                }
            }
        });
    }
}
