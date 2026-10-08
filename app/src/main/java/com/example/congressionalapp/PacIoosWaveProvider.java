package com.example.congressionalapp;

import androidx.annotation.NonNull;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/**
 * IMPLEMENTATION: PacIoosWaveProvider
 * 
 * Fetches high-resolution nearshore wave model data from PacIOOS (University of Hawaii).
 * It uses the ERDDAP protocol to retrieve the latest modeled wave face heights
 * specifically for the Hawaiian islands.
 */
public class PacIoosWaveProvider implements WaveDataProvider {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void fetchWaveData(BeachLocation beach, Callback callback) {
        String dataset = beach.getPacIoosDataset();
        if (dataset == null || dataset.isEmpty()) {
            callback.onError("No PacIOOS dataset specified for this beach");
            return;
        }

        double lat = beach.getLatitude();
        double lon = beach.getLongitude();

        // ERDDAP URL Pattern for latest grid data
        String url = String.format(java.util.Locale.US, "https://pae-paha.pacioos.hawaii.edu/erddap/griddap/%s.json?shgt[(latest)][(%f):1:(%f)][(%f):1:(%f)],mper[(latest)][(%f):1:(%f)][(%f):1:(%f)],mdir[(latest)][(%f):1:(%f)][(%f):1:(%f)]",
                dataset, lat, lat, lon, lon, lat, lat, lon, lon, lat, lat, lon, lon);

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
                        JsonObject table = jsonObject.getAsJsonObject("table");
                        JsonArray rows = table.getAsJsonArray("rows");
                        
                        if (rows.size() > 0) {
                            JsonArray firstRow = rows.get(0).getAsJsonArray();
                            float heightMeters = firstRow.get(3).getAsFloat();
                            float period = firstRow.get(4).getAsFloat();
                            float direction = firstRow.get(5).getAsFloat();
                            float heightFeet = heightMeters * 3.28084f;

                            WaveConditionReport data = new WaveConditionReport(heightFeet, heightMeters, period, direction, "PacIOOS", System.currentTimeMillis());
                            callback.onSuccess(data);
                        } else {
                            callback.onError("No data returned from PacIOOS");
                        }
                    } catch (Exception e) {
                        callback.onError("Error parsing PacIOOS data");
                    }
                } else {
                    callback.onError("PacIOOS server error: " + response.code());
                }
            }
        });
    }
}
