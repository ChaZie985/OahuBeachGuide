package com.example.congressionalapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * ACTIVITY: SafetyRecommendationActivity
 * 
 * Generates a ranked list of safe beaches tailored to tourists and families.
 * Fully localized for English, Spanish, Japanese, Korean, and Chinese.
 */
public class SafetyRecommendationActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvStatus;
    private List<BeachRanking> rankingList;
    private RecommendationAdapter adapter;
    private final WaveDataCoordinator waveCoordinator = new WaveDataCoordinator();
    private int completedRequests = 0;
    private List<BeachLocation> beaches;
    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;
    private LocationCallback locationCallback;

    private static class BeachRanking {
        BeachLocation beach;
        String recommendationMessage;
        int statusColor;
        float distanceMiles;

        BeachRanking(BeachLocation beach, String msg, int color, float dist) {
            this.beach = beach;
            this.recommendationMessage = msg;
            this.statusColor = color;
            this.distanceMiles = dist;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null) tvTitle.setText(AppLocalization.get(this, "safe_recommendations"));

        TextView tvSubtitle = findViewById(R.id.tvSubtitle);
        if (tvSubtitle != null) tvSubtitle.setText(AppLocalization.get(this, "categorized_by_proximity"));

        tvStatus = findViewById(R.id.tvStatus);
        tvStatus.setText(AppLocalization.get(this, "finding_best_spots"));

        listView = findViewById(R.id.listViewRecommendations);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setText(AppLocalization.get(this, "back"));
        btnBack.setOnClickListener(v -> finish());

        beaches = BeachRepository.getBeaches();
        rankingList = new ArrayList<>();

        adapter = new RecommendationAdapter(this, rankingList);
        listView.setAdapter(adapter);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < rankingList.size()) {
                BeachLocation selectedBeach = rankingList.get(position).beach;
                Intent intent = new Intent(SafetyRecommendationActivity.this, BeachInfoDisplayActivity.class);
                intent.putExtra("BEACH", selectedBeach);
                startActivity(intent);
            }
        });

        requestLocationAndTrack();
    }

    private void requestLocationAndTrack() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            startContinuousLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startContinuousLocationUpdates();
        } else {
            Toast.makeText(this, "Location permission denied. Using Oʻahu default center.", Toast.LENGTH_SHORT).show();
            refreshWaveConditions();
        }
    }

    private void startContinuousLocationUpdates() {
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    userLocation = location;
                }
                refreshWaveConditions();
            });

            LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                    .setMinUpdateIntervalMillis(5000)
                    .build();

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    if (locationResult != null && locationResult.getLastLocation() != null) {
                        userLocation = locationResult.getLastLocation();
                        refreshWaveConditions();
                    }
                }
            };

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

        } catch (SecurityException e) {
            refreshWaveConditions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void refreshWaveConditions() {
        completedRequests = 0;
        rankingList.clear();
        for (BeachLocation beach : beaches) {
            analyzeSafetyForBeach(beach);
        }
    }

    private Location getEffectiveUserLocation() {
        if (userLocation != null) {
            double lat = userLocation.getLatitude();
            double lng = userLocation.getLongitude();
            if (lat >= 21.2 && lat <= 21.8 && lng >= -158.3 && lng <= -157.6) {
                return userLocation;
            }
        }
        Location honolulu = new Location("");
        honolulu.setLatitude(21.3069);
        honolulu.setLongitude(-157.8583);
        return honolulu;
    }

    private void analyzeSafetyForBeach(BeachLocation beach) {
        waveCoordinator.fetchWaveData(beach, new WaveDataProvider.Callback() {
            @Override
            public void onSuccess(WaveConditionReport data) {
                float heightFeet = data.getHeightFeet();
                float actualShoreHeight = heightFeet;
                
                if (beach.isReefProtected()) {
                    actualShoreHeight = heightFeet * 0.1f;
                    if (actualShoreHeight > 1.0f) actualShoreHeight = 1.0f;
                } else if (beach.isBreaksFarOut()) {
                    actualShoreHeight = heightFeet * 0.35f;
                }

                String status = getSafetyStatusLabel(beach, actualShoreHeight);
                int color = getStatusColorCode(beach, actualShoreHeight);
                
                Location effectiveLoc = getEffectiveUserLocation();
                float[] results = new float[1];
                Location.distanceBetween(effectiveLoc.getLatitude(), effectiveLoc.getLongitude(), 
                        beach.getLatitude(), beach.getLongitude(), results);
                float dist = results[0] / 1609.34f;

                String shoreLbl = AppLocalization.get(SafetyRecommendationActivity.this, "shore_label");
                String milesLbl = AppLocalization.get(SafetyRecommendationActivity.this, "miles_away_label");

                String msg = String.format(Locale.US, "%s\n%s (%s%.1f ft)\n%.1f%s", 
                        beach.getName(), status, shoreLbl, actualShoreHeight, dist, milesLbl);
                
                boolean isSafeBeach = status.contains("RECOMMENDED") || status.contains("おすすめ") || status.contains("推荐") || status.contains("RECOMENDADO") &&
                                      !"sandy-beach".equals(beach.getId()) && 
                                      !"sunset-beach".equals(beach.getId()) && 
                                      !"makapuu-beach".equals(beach.getId());

                if (isSafeBeach) {
                    publishRankingUpdate(new BeachRanking(beach, msg, color, dist));
                } else {
                    publishFilteredOut();
                }
            }

            @Override
            public void onError(String message) {
                publishFilteredOut();
            }
        });
    }

    private synchronized void publishFilteredOut() {
        runOnUiThread(() -> {
            completedRequests++;
            if (completedRequests == beaches.size()) {
                finalizeSortingAndDisplay();
            }
        });
    }

    private synchronized void publishRankingUpdate(BeachRanking rank) {
        runOnUiThread(() -> {
            rankingList.add(rank);
            completedRequests++;
            
            if (completedRequests == beaches.size()) {
                finalizeSortingAndDisplay();
            }
        });
    }

    private void finalizeSortingAndDisplay() {
        Collections.sort(rankingList, (r1, r2) -> Float.compare(r1.distanceMiles, r2.distanceMiles));
        
        adapter.notifyDataSetChanged();
        if (rankingList.isEmpty()) {
            tvStatus.setText("No safe beaches currently available.");
        } else {
            tvStatus.setText(AppLocalization.get(this, "sorted_by_proximity"));
        }
    }

    private String getSafetyStatusLabel(BeachLocation beach, float heightFeet) {
        if (beach.isReefProtected() || (beach.isBreaksFarOut() && heightFeet <= 3.0f)) {
            return AppLocalization.get(this, "status_safe");
        } else if (heightFeet >= 2.5f) {
            return AppLocalization.get(this, "status_dangerous");
        } else if (heightFeet <= 1.5f) {
            return AppLocalization.get(this, "status_safe");
        } else {
            return AppLocalization.get(this, "status_caution");
        }
    }

    private int getStatusColorCode(BeachLocation beach, float heightFeet) {
        if (beach.isReefProtected() || (beach.isBreaksFarOut() && heightFeet <= 3.0f)) {
            return Color.rgb(0, 150, 0); // Green
        } else if (heightFeet >= 2.5f) {
            return Color.rgb(200, 0, 0); // Red
        } else if (heightFeet <= 1.5f) {
            return Color.rgb(0, 150, 0); // Green
        } else {
            return Color.rgb(255, 140, 0); // Orange
        }
    }

    private static class RecommendationAdapter extends ArrayAdapter<BeachRanking> {
        RecommendationAdapter(AppCompatActivity context, List<BeachRanking> items) {
            super(context, R.layout.row_recommendation_item, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_recommendation_item, parent, false);
            }

            MaterialCardView card = (MaterialCardView) convertView;
            TextView text = convertView.findViewById(R.id.tvRecommendationText);
            BeachRanking item = getItem(position);

            if (item != null) {
                text.setText(item.recommendationMessage);
                text.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                card.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.darker_gray));

                if (position % 2 == 0) {
                    card.setStrokeColor(ContextCompat.getColor(getContext(), R.color.primary_blue));
                } else {
                    card.setStrokeColor(ContextCompat.getColor(getContext(), R.color.light_green));
                }

                final BeachLocation targetBeach = item.beach;
                card.setOnClickListener(v -> {
                    Context context = getContext();
                    Intent intent = new Intent(context, BeachInfoDisplayActivity.class);
                    intent.putExtra("BEACH", targetBeach);
                    context.startActivity(intent);
                });
            }

            return convertView;
        }
    }
}
