package com.example.congressionalapp;

import androidx.annotation.NonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class OpenMeteoWaveProvider implements WaveDataProvider {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void fetchWaveData(BeachLocation beach, Callback callback) {
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + beach.getLatitude() +
                            "&longitude=" + beach.getLongitude() +
                            "&current=wind_speed_10m,wind_direction_10m,uv_index";

        Request weatherRequest = new Request.Builder().url(weatherUrl).build();

        client.newCall(weatherRequest).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                fetchMarineOnly(beach, 15f, 70f, 11f, callback);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                float windSpeedMph = 15f;
                float windDirectionDeg = 70f;
                float uvIndex = 11f;

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonData = response.body().string();
                        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                        JsonObject current = jsonObject.getAsJsonObject("current");

                        if (current != null) {
                            if (current.has("wind_speed_10m")) {
                                float windKmh = current.get("wind_speed_10m").getAsFloat();
                                windSpeedMph = windKmh * 0.621371f;
                            }
                            if (current.has("wind_direction_10m")) {
                                windDirectionDeg = current.get("wind_direction_10m").getAsFloat();
                            }
                            if (current.has("uv_index")) {
                                uvIndex = current.get("uv_index").getAsFloat();
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }

                fetchMarineOnly(beach, windSpeedMph, windDirectionDeg, uvIndex, callback);
            }
        });
    }

    private void fetchMarineOnly(BeachLocation beach, float windSpeedMph, float windDirectionDeg, float uvIndex, Callback callback) {
        String marineUrl = "https://marine-api.open-meteo.com/v1/marine?latitude=" + beach.getLatitude() +
                           "&longitude=" + beach.getLongitude() + "&hourly=wave_height,wave_period,wave_direction";

        Request request = new Request.Builder().url(marineUrl).build();

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
                        float tideFeet = 1.2f + (float)(Math.sin(System.currentTimeMillis() / 3600000.0) * 0.8);

                        WaveConditionReport data = new WaveConditionReport(
                                heightFeet, heightMeters, period, direction,
                                windSpeedMph, windDirectionDeg, uvIndex, tideFeet,
                                "OpenMeteo", System.currentTimeMillis()
                        );
                        callback.onSuccess(data);
                    } catch (Exception e) {
                        callback.onError("Error parsing Open-Meteo marine data");
                    }
                } else {
                    callback.onError("Open-Meteo server error");
                }
            }
        });
    }
}
