package com.example.congressionalapp;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class AppHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initializeBackgroundSafetyMonitor();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }

        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        TextView titleText = findViewById(R.id.titleText);
        if (titleText != null) {
            titleText.setText(AppLocalization.get(this, "welcome_title"));
        }

        Button btnSwitchLanguage = findViewById(R.id.btnSwitchLanguage);
        if (btnSwitchLanguage != null) {
            btnSwitchLanguage.setText("🌐 " + AppLocalization.getLanguage(this).toUpperCase());
            btnSwitchLanguage.setOnClickListener(v -> {
                Intent intent = new Intent(AppHomeActivity.this, LanguageSelectActivity.class);
                startActivity(intent);
                finish();
            });
        }

        Button btnViewMap = findViewById(R.id.btnViewMap);
        btnViewMap.setText(AppLocalization.get(this, "explore_map"));
        btnViewMap.setOnClickListener(v -> {
            Intent intent = new Intent(AppHomeActivity.this, InteractiveMapActivity.class);
            startActivity(intent);
        });

        Button btnSearchBeaches = findViewById(R.id.btnSearchBeaches);
        btnSearchBeaches.setText(AppLocalization.get(this, "search_beaches"));
        btnSearchBeaches.setOnClickListener(v -> {
            Intent intent = new Intent(AppHomeActivity.this, BeachDiscoveryActivity.class);
            startActivity(intent);
        });

        Button btnRecommendations = findViewById(R.id.btnRecommendations);
        btnRecommendations.setText(AppLocalization.get(this, "safe_recommendations"));
        btnRecommendations.setOnClickListener(v -> {
            Intent intent = new Intent(AppHomeActivity.this, SafetyRecommendationActivity.class);
            startActivity(intent);
        });

        Button btnAmenitySearch = findViewById(R.id.btnAmenitySearch);
        btnAmenitySearch.setText(AppLocalization.get(this, "search_amenities"));
        btnAmenitySearch.setOnClickListener(v -> {
            Intent intent = new Intent(AppHomeActivity.this, AmenitySearchActivity.class);
            startActivity(intent);
        });
    }

    private void initializeBackgroundSafetyMonitor() {
        PeriodicWorkRequest safetyRequest =
                new PeriodicWorkRequest.Builder(ProximitySafetyWorker.class, 1, TimeUnit.HOURS)
                        .build();

        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                "BeachSafetyWork",
                ExistingPeriodicWorkPolicy.KEEP,
                safetyRequest);
    }
}
