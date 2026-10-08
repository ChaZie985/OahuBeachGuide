package com.example.congressionalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * ACTIVITY: SwimmerAbilityActivity
 * 
 * Step 2 of Amenity & Skill Search: Users select their swimmer ability level (Beginner, Intermediate, Advanced),
 * and the app computes and displays the top 3 best matching Oʻahu beaches tailored to that skill level and amenities.
 */
public class SwimmerAbilityActivity extends AppCompatActivity {

    private ArrayList<String> selectedAmenities;
    private TextView tvResultsTitle;
    private List<BeachScoreItem> topThreeList;
    private ArrayAdapter<BeachScoreItem> adapter;

    private static class BeachScoreItem {
        BeachLocation beach;
        int score;
        String displayString;

        BeachScoreItem(BeachLocation beach, int score, int rank) {
            this.beach = beach;
            this.score = score;
            this.displayString = "#" + rank + " " + beach.getName() + " (" + beach.getHawaiianName() + ")\nShoreline: " + beach.getShoreline() + " · Match Score: " + score;
        }

        @Override
        public String toString() {
            return displayString;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimmer_ability);

        selectedAmenities = getIntent().getStringArrayListExtra("SELECTED_AMENITIES");
        if (selectedAmenities == null) {
            selectedAmenities = new ArrayList<>();
        }

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        tvResultsTitle = findViewById(R.id.tvResultsTitle);
        ListView listViewTopThree = findViewById(R.id.listViewTopThree);
        topThreeList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topThreeList);
        listViewTopThree.setAdapter(adapter);

        listViewTopThree.setOnItemClickListener((parent, view, position, id) -> {
            BeachScoreItem item = topThreeList.get(position);
            Intent intent = new Intent(SwimmerAbilityActivity.this, BeachInfoDisplayActivity.class);
            intent.putExtra("BEACH", item.beach);
            startActivity(intent);
        });

        findViewById(R.id.btnLevelBeginner).setOnClickListener(v -> rankAndDisplayBeaches("Beginner"));
        findViewById(R.id.btnLevelIntermediate).setOnClickListener(v -> rankAndDisplayBeaches("Intermediate"));
        findViewById(R.id.btnLevelAdvanced).setOnClickListener(v -> rankAndDisplayBeaches("Advanced"));
    }

    private void rankAndDisplayBeaches(String skillLevel) {
        topThreeList.clear();
        List<BeachLocation> allBeaches = BeachRepository.getBeaches();
        List<BeachScoreItem> scoredBeaches = new ArrayList<>();

        for (BeachLocation beach : allBeaches) {
            String beachId = beach.getId();
            boolean isHighRisk = "sandy-beach".equals(beachId) || "sunset-beach".equals(beachId) || "makapuu-beach".equals(beachId) || "waimea-bay".equals(beachId);
            boolean isProtectedOrCalm = beach.isReefProtected() || beach.isBreaksFarOut() || 
                                       "ala-moana-beach".equals(beachId) || "ko-olina-lagoons".equals(beachId) || 
                                       "kailua-beach".equals(beachId) || "lanikai-beach".equals(beachId) || 
                                       "bellows-field".equals(beachId);

            int skillScore = 0;
            if (skillLevel.equals("Beginner")) {
                if (isHighRisk) {
                    continue; // Beginners should never be recommended high-risk shores like Sandy or Sunset
                }
                if (isProtectedOrCalm) {
                    skillScore += 10;
                } else {
                    skillScore += 3;
                }
            } else if (skillLevel.equals("Intermediate")) {
                if ("sandy-beach".equals(beachId) || "sunset-beach".equals(beachId)) {
                    continue; // Skip extreme expert shores for intermediates
                }
                if (!isHighRisk) {
                    skillScore += 8;
                } else {
                    skillScore += 2;
                }
            } else if (skillLevel.equals("Advanced")) {
                if (isHighRisk || beach.getPermanentHazards().toLowerCase().contains("shorebreak") || beach.getPermanentHazards().toLowerCase().contains("surf")) {
                    skillScore += 10; // Heavily reward advanced surf & shorebreak spots
                } else {
                    skillScore += 4;
                }
            }

            // Amenity score calculation
            int amenityScore = 0;
            List<String> beachAmenities = beach.getAmenities();
            for (String req : selectedAmenities) {
                for (String am : beachAmenities) {
                    if (am.toLowerCase().contains(req)) {
                        amenityScore++;
                        break;
                    }
                }
            }

            int totalScore = (skillScore * 5) + amenityScore;
            if (totalScore > 0) {
                scoredBeaches.add(new BeachScoreItem(beach, totalScore, 0));
            }
        }

        // Sort by total composite score descending
        scoredBeaches.sort((b1, b2) -> Integer.compare(b2.score, b1.score));

        // Take top 3
        int count = Math.min(3, scoredBeaches.size());
        for (int i = 0; i < count; i++) {
            BeachScoreItem item = scoredBeaches.get(i);
            topThreeList.add(new BeachScoreItem(item.beach, item.score, i + 1));
        }

        adapter.notifyDataSetChanged();
        if (topThreeList.isEmpty()) {
            tvResultsTitle.setVisibility(View.VISIBLE);
            tvResultsTitle.setText("No beaches match this combination. Try different criteria.");
            Toast.makeText(this, "No matching beaches found.", Toast.LENGTH_SHORT).show();
        } else {
            tvResultsTitle.setVisibility(View.VISIBLE);
            tvResultsTitle.setText("Top 3 Ranked Beaches for " + skillLevel + " Swimmers:");
            Toast.makeText(this, "Ranked top 3 beaches for " + skillLevel + "s!", Toast.LENGTH_SHORT).show();
        }
    }
}
