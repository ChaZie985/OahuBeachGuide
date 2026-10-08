package com.example.congressionalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Locale;

public class BeachInfoDisplayActivity extends AppCompatActivity {

    private BeachLocation currentBeach;
    private LinearLayout alertStrip;
    private TextView tvBeachName;
    private TextView tvHawaiianName;
    private TextView tvVerdictBadge;
    private TextView tvVerdictReason;
    private TextView tvFreshness;
    private View cardSaferOptions;
    private TextView tvWaveHeight;
    private TextView tvWindCondition;
    private TextView tvTideInfo;
    private TextView tvLifeguardStatus;
    private TextView tvWaterQuality;
    private TextView tvUvAndJellyfish;
    private TextView tvPermanentHazards;
    private TextView tvLocalKnowledge;
    private TextView tvGoodFor;
    private TextView tvAmenities;
    private LinearLayout emergencySheet;
    private TextView tvEmergencyLocation;
    private TextView tvBeachDescription;
    private View cardAddressBox;
    private TextView tvBeachAddress;
    private TextView tvBeachCoordinates;

    private final WaveDataCoordinator waveCoordinator = new WaveDataCoordinator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_detail);

        initViews();

        BeachLocation beach = (BeachLocation) getIntent().getSerializableExtra("BEACH");
        if (beach == null) {
            beach = BeachRepository.getSampleBeaches().get(0);
        }
        currentBeach = beach;

        populateBeachData(beach);
        fetchLiveConditions(beach);
        setupListeners();
    }

    private void initViews() {
        alertStrip = findViewById(R.id.alertStrip);
        tvBeachName = findViewById(R.id.tvBeachName);
        tvHawaiianName = findViewById(R.id.tvHawaiianName);
        tvVerdictBadge = findViewById(R.id.tvVerdictBadge);
        tvVerdictReason = findViewById(R.id.tvVerdictReason);
        tvFreshness = findViewById(R.id.tvFreshness);
        cardSaferOptions = findViewById(R.id.cardSaferOptions);
        tvWaveHeight = findViewById(R.id.tvWaveHeight);
        tvWindCondition = findViewById(R.id.tvWindCondition);
        tvTideInfo = findViewById(R.id.tvTideInfo);
        tvLifeguardStatus = findViewById(R.id.tvLifeguardStatus);
        tvWaterQuality = findViewById(R.id.tvWaterQuality);
        tvUvAndJellyfish = findViewById(R.id.tvUvAndJellyfish);
        tvPermanentHazards = findViewById(R.id.tvPermanentHazards);
        tvLocalKnowledge = findViewById(R.id.tvLocalKnowledge);
        tvGoodFor = findViewById(R.id.tvGoodFor);
        tvAmenities = findViewById(R.id.tvAmenities);
        emergencySheet = findViewById(R.id.emergencySheet);
        tvEmergencyLocation = findViewById(R.id.tvEmergencyLocation);
        tvBeachDescription = findViewById(R.id.tvBeachDescription);
        cardAddressBox = findViewById(R.id.cardAddressBox);
        tvBeachAddress = findViewById(R.id.tvBeachAddress);
        tvBeachCoordinates = findViewById(R.id.tvBeachCoordinates);
    }

    private void populateBeachData(BeachLocation beach) {
        tvBeachName.setText(beach.getName());
        tvHawaiianName.setText(beach.getHawaiianName() + " · " + beach.getShoreline());
        
        if (tvBeachDescription != null) {
            tvBeachDescription.setText(AppLocalization.getLocalizedDescription(this, beach));
        }

        if (tvBeachAddress != null) {
            tvBeachAddress.setText(beach.getName() + " (" + beach.getHawaiianName() + "), " + beach.getShoreline() + " Coast, Oʻahu, HI");
        }
        if (tvBeachCoordinates != null) {
            tvBeachCoordinates.setText(String.format(Locale.US, "GPS: %.4f, %.4f (Tap for directions)", beach.getLatitude(), beach.getLongitude()));
        }
        
        tvPermanentHazards.setText(AppLocalization.getLocalizedHazard(this, beach.getId()));
        tvLocalKnowledge.setText("“" + AppLocalization.getLocalizedLocalKnowledge(this, beach.getId()) + "”");
        tvGoodFor.setText(AppLocalization.getLocalizedGoodFor(this, beach));
        tvAmenities.setText(AppLocalization.getLocalizedAmenities(this, beach));

        TextView lblAboutThisBeach = findViewById(R.id.lblAboutThisBeach);
        if (lblAboutThisBeach != null) lblAboutThisBeach.setText(AppLocalization.get(this, "about_this_beach"));

        TextView lblBeachAddressNav = findViewById(R.id.lblBeachAddressNav);
        if (lblBeachAddressNav != null) lblBeachAddressNav.setText(AppLocalization.get(this, "beach_address_navigation"));

        TextView lblSaferOptions = findViewById(R.id.lblSaferOptions);
        if (lblSaferOptions != null) lblSaferOptions.setText(AppLocalization.get(this, "safer_options"));

        TextView lblTodaysConditions = findViewById(R.id.lblTodaysConditions);
        if (lblTodaysConditions != null) lblTodaysConditions.setText(AppLocalization.get(this, "todays_conditions"));

        TextView lblHazards = findViewById(R.id.lblHazards);
        if (lblHazards != null) lblHazards.setText(AppLocalization.get(this, "hazards"));

        TextView lblLocalsKnow = findViewById(R.id.lblLocalsKnow);
        if (lblLocalsKnow != null) lblLocalsKnow.setText(AppLocalization.get(this, "locals_know"));

        TextView lblGoodFor = findViewById(R.id.lblGoodFor);
        if (lblGoodFor != null) lblGoodFor.setText(AppLocalization.get(this, "good_for"));

        TextView lblAmenitiesWildlife = findViewById(R.id.lblAmenitiesWildlife);
        if (lblAmenitiesWildlife != null) lblAmenitiesWildlife.setText(AppLocalization.get(this, "amenities_wildlife"));

        Button btnShowOnMap = findViewById(R.id.btnShowOnMap);
        if (btnShowOnMap != null) {
            btnShowOnMap.setText(AppLocalization.get(this, "show_me_on_map"));
        }

        tvFreshness.setText(AppLocalization.get(this, "freshness_text"));

        if ("sandy-beach".equals(beach.getId()) || "sunset-beach".equals(beach.getId())) {
            alertStrip.setVisibility(View.VISIBLE);
        } else {
            alertStrip.setVisibility(View.GONE);
        }
    }

    private void fetchLiveConditions(BeachLocation beach) {
        waveCoordinator.fetchWaveData(beach, new WaveDataProvider.Callback() {
            @Override
            public void onSuccess(WaveConditionReport data) {
                runOnUiThread(() -> {
                    float offshoreHeight = data.getHeightFeet();
                    float actualShoreHeight = offshoreHeight;

                    if (beach.isReefProtected()) {
                        actualShoreHeight = offshoreHeight * 0.1f;
                        if (actualShoreHeight > 1.0f) actualShoreHeight = 1.0f;
                    } else if (beach.isBreaksFarOut()) {
                        actualShoreHeight = offshoreHeight * 0.35f;
                    }

                    float windMph = data.getWindSpeedMph();
                    String windDir = getCardinalDirection(data.getWindDirectionDeg());
                    float uv = data.getUvIndex();
                    String uvCat = getUvCategory(uv);
                    float tide = data.getTideFeet();

                    tvWaveHeight.setText(String.format(Locale.US, "Wave height: %.1f–%.1f ft", actualShoreHeight, actualShoreHeight + 1.5f));
                    tvWindCondition.setText(String.format(Locale.US, "Wind: %.1f mph %s (Live onshore wind)", windMph, windDir));
                    tvTideInfo.setText(String.format(Locale.US, "Tide: %+.1f ft (Live tidal level)", tide));
                    tvLifeguardStatus.setText(AppLocalization.get(BeachInfoDisplayActivity.this, "lifeguard_text"));
                    tvWaterQuality.setText(AppLocalization.get(BeachInfoDisplayActivity.this, "water_quality_text"));
                    tvUvAndJellyfish.setText(String.format(Locale.US, "UV Index: %.1f (%s) · Reef-safe sunscreen recommended", uv, uvCat));

                    evaluateGeneralVerdict(beach);
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    tvWaveHeight.setText("Conditions unknown: Unable to fetch live surf data.");
                    tvVerdictBadge.setText(AppLocalization.get(BeachInfoDisplayActivity.this, "status_caution") + " (Offline)");
                    tvVerdictBadge.setBackgroundColor(ContextCompat.getColor(BeachInfoDisplayActivity.this, android.R.color.darker_gray));
                    tvVerdictReason.setText("Surf data unavailable. Rating limited until network returns.");
                });
            }
        });
    }

    private static String getCardinalDirection(float degrees) {
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int index = Math.round(degrees / 22.5f) % 16;
        if (index < 0) index += 16;
        return directions[index];
    }

    private static String getUvCategory(float uv) {
        if (uv <= 2) return "Low";
        if (uv <= 5) return "Moderate";
        if (uv <= 7) return "High";
        if (uv <= 10) return "Very High";
        return "Extreme";
    }

    private void evaluateGeneralVerdict(BeachLocation beach) {
        String verdict;
        String reason;
        int badgeColor;

        boolean isHighRisk = "sandy-beach".equals(beach.getId()) || 
                             "sunset-beach".equals(beach.getId()) || 
                             "makapuu-beach".equals(beach.getId());

        if (isHighRisk) {
            verdict = AppLocalization.get(this, "verdict_dangerous");
            reason = AppLocalization.get(this, "reason_dangerous");
            badgeColor = ContextCompat.getColor(this, R.color.danger_red);
            cardSaferOptions.setVisibility(View.VISIBLE);
        } else if (beach.isReefProtected() || beach.isBreaksFarOut()) {
            verdict = AppLocalization.get(this, "verdict_safe");
            reason = AppLocalization.get(this, "reason_safe");
            badgeColor = ContextCompat.getColor(this, R.color.safe_green);
            cardSaferOptions.setVisibility(View.GONE);
        } else {
            verdict = AppLocalization.get(this, "verdict_caution");
            reason = AppLocalization.get(this, "reason_caution");
            badgeColor = ContextCompat.getColor(this, R.color.caution_yellow);
            cardSaferOptions.setVisibility(View.VISIBLE);
        }

        tvVerdictBadge.setText(verdict);
        tvVerdictBadge.setBackgroundColor(badgeColor);
        tvVerdictReason.setText(reason);
        tvFreshness.setText(AppLocalization.get(this, "freshness_text"));
    }

    private void setupListeners() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        Button btnShowOnMap = findViewById(R.id.btnShowOnMap);
        if (btnShowOnMap != null) {
            btnShowOnMap.setOnClickListener(v -> {
                Intent intent = new Intent(BeachInfoDisplayActivity.this, InteractiveMapActivity.class);
                intent.putExtra("TARGET_BEACH", currentBeach);
                startActivity(intent);
            });
        }

        Button btnEmergency = findViewById(R.id.btnEmergency);
        btnEmergency.setOnClickListener(v -> {
            tvEmergencyLocation.setText("Location: " + currentBeach.getName() + " (" + currentBeach.getHawaiianName() + ")\nCoordinates: " + currentBeach.getLatitude() + ", " + currentBeach.getLongitude());
            emergencySheet.setVisibility(View.VISIBLE);
        });

        findViewById(R.id.btnCloseEmergency).setOnClickListener(v -> emergencySheet.setVisibility(View.GONE));

        findViewById(R.id.btnCall911).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:911"));
            startActivity(intent);
        });

        View cardAddressBox = findViewById(R.id.cardAddressBox);
        if (cardAddressBox != null) {
            cardAddressBox.setOnClickListener(v -> {
                String uri = String.format(Locale.US, "geo:%f,%f?q=%f,%f(%s)", 
                        currentBeach.getLatitude(), currentBeach.getLongitude(), 
                        currentBeach.getLatitude(), currentBeach.getLongitude(), 
                        Uri.encode(currentBeach.getName()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                }
            });
        }
    }
}
