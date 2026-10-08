package com.example.congressionalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

/**
 * ACTIVITY: AmenitySearchActivity
 * 
 * Allows users to select desired beach amenities via checkboxes at the top,
 * and upon clicking submit, displays the top 4 safe matching beaches at the bottom
 * in styled card items with darker gray background, black text, and alternating blue/green borders.
 * Fully localized for English, Spanish, Japanese, Korean, and Chinese.
 */
public class AmenitySearchActivity extends AppCompatActivity {

    private CheckBox chkRestrooms, chkShowers, chkParking, chkLifeguard, chkPicnic;
    private TextView tvResultsTitle;
    private LinearLayout containerTopFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amenity_search);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setText(AppLocalization.get(this, "back"));
        btnBack.setOnClickListener(v -> finish());

        TextView tvAmenitiesTitle = findViewById(R.id.tvAmenitiesTitle);
        tvAmenitiesTitle.setText(AppLocalization.get(this, "amenities_title"));

        TextView tvAmenitiesSubtitle = findViewById(R.id.tvAmenitiesSubtitle);
        tvAmenitiesSubtitle.setText(AppLocalization.get(this, "amenities_subtitle"));

        chkRestrooms = findViewById(R.id.chkRestrooms);
        chkRestrooms.setText(AppLocalization.get(this, "amenity_restrooms"));

        chkShowers = findViewById(R.id.chkShowers);
        chkShowers.setText(AppLocalization.get(this, "amenity_showers"));

        chkParking = findViewById(R.id.chkParking);
        chkParking.setText(AppLocalization.get(this, "amenity_parking"));

        chkLifeguard = findViewById(R.id.chkLifeguard);
        chkLifeguard.setText(AppLocalization.get(this, "amenity_lifeguard"));

        chkPicnic = findViewById(R.id.chkPicnic);
        chkPicnic.setText(AppLocalization.get(this, "amenity_picnic"));

        MaterialButton btnSubmit = findViewById(R.id.btnSubmitAmenities);
        btnSubmit.setText(AppLocalization.get(this, "find_matching_beaches"));

        tvResultsTitle = findViewById(R.id.tvResultsTitle);
        containerTopFour = findViewById(R.id.containerTopFour);

        TextView tvAmenitiesDisclaimer = findViewById(R.id.tvAmenitiesDisclaimer);
        tvAmenitiesDisclaimer.setText(AppLocalization.get(this, "amenities_disclaimer"));

        btnSubmit.setOnClickListener(v -> matchAmenitiesToTopFour());
    }

    private void matchAmenitiesToTopFour() {
        List<String> requiredAmenities = new ArrayList<>();
        if (chkRestrooms.isChecked()) requiredAmenities.add("restroom");
        if (chkShowers.isChecked()) requiredAmenities.add("shower");
        if (chkParking.isChecked()) requiredAmenities.add("parking");
        if (chkLifeguard.isChecked()) requiredAmenities.add("lifeguard");
        if (chkPicnic.isChecked()) requiredAmenities.add("picnic");

        if (requiredAmenities.isEmpty()) {
            Toast.makeText(this, "Please select at least one amenity.", Toast.LENGTH_SHORT).show();
            return;
        }

        containerTopFour.removeAllViews();
        List<BeachLocation> allBeaches = BeachRepository.getBeaches();
        List<BeachScoreItem> scoredBeaches = new ArrayList<>();

        for (BeachLocation beach : allBeaches) {
            String beachId = beach.getId();
            boolean isSafeBeach = !("sandy-beach".equals(beachId) || 
                                     "sunset-beach".equals(beachId) || 
                                     "makapuu-beach".equals(beachId) || 
                                     "waimea-bay".equals(beachId));
            if (!isSafeBeach) {
                continue;
            }

            int score = 0;
            List<String> beachAmenities = beach.getAmenities();
            for (String req : requiredAmenities) {
                for (String am : beachAmenities) {
                    if (am.toLowerCase().contains(req)) {
                        score++;
                        break;
                    }
                }
            }

            if (score > 0) {
                scoredBeaches.add(new BeachScoreItem(beach, score));
            }
        }

        scoredBeaches.sort((b1, b2) -> Integer.compare(b2.score, b1.score));

        int count = Math.min(4, scoredBeaches.size());
        if (count == 0) {
            tvResultsTitle.setVisibility(View.VISIBLE);
            tvResultsTitle.setText("No safe beaches match this amenity combination.");
            Toast.makeText(this, "No matching safe beaches found.", Toast.LENGTH_SHORT).show();
            return;
        }

        tvResultsTitle.setVisibility(View.VISIBLE);
        tvResultsTitle.setText(AppLocalization.get(this, "top_four_safe_beaches"));

        for (int i = 0; i < count; i++) {
            BeachScoreItem item = scoredBeaches.get(i);
            
            View beachBoxView = getLayoutInflater().inflate(R.layout.row_beach_item, containerTopFour, false);
            MaterialCardView card = (MaterialCardView) beachBoxView;
            TextView tvName = beachBoxView.findViewById(R.id.tvBeachItemName);
            
            tvName.setText(item.beach.getName() + " (" + item.beach.getHawaiianName() + ")");
            tvName.setTextColor(ContextCompat.getColor(this, R.color.black));
            tvName.setGravity(Gravity.CENTER);
            
            card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.darker_gray));
            
            // Alternate between blue and green borders
            if (i % 2 == 0) {
                card.setStrokeColor(ContextCompat.getColor(this, R.color.primary_blue));
            } else {
                card.setStrokeColor(ContextCompat.getColor(this, R.color.light_green));
            }
            
            final BeachLocation targetBeach = item.beach;
            card.setOnClickListener(v -> {
                Intent intent = new Intent(AmenitySearchActivity.this, BeachInfoDisplayActivity.class);
                intent.putExtra("BEACH", targetBeach);
                startActivity(intent);
            });

            containerTopFour.addView(card);
        }

        Toast.makeText(this, "Showing top " + count + " safe matching beaches below!", Toast.LENGTH_SHORT).show();
    }

    private static class BeachScoreItem {
        BeachLocation beach;
        int score;

        BeachScoreItem(BeachLocation beach, int score) {
            this.beach = beach;
            this.score = score;
        }
    }
}
