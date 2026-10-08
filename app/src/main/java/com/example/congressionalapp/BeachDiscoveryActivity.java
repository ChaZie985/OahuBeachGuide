package com.example.congressionalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

/**
 * ACTIVITY: BeachDiscoveryActivity
 * 
 * Provides a searchable list of all beach locations with ultra light gray background,
 * black lettering, and alternating blue and green borders.
 */
public class BeachDiscoveryActivity extends AppCompatActivity {

    private List<BeachLocation> beachList;
    private List<BeachLocation> filteredList;
    private BeachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        beachList = BeachRepository.getBeaches();
        filteredList = new ArrayList<>(beachList);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        ListView listViewBeaches = findViewById(R.id.listViewBeaches);
        adapter = new BeachAdapter(this, filteredList);
        listViewBeaches.setAdapter(adapter);

        listViewBeaches.setOnItemClickListener((parent, view, position, id) -> {
            BeachLocation selectedBeach = filteredList.get(position);
            Intent intent = new Intent(BeachDiscoveryActivity.this, BeachInfoDisplayActivity.class);
            intent.putExtra("BEACH", selectedBeach);
            startActivity(intent);
        });

        setupSearchView();
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performFiltering(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performFiltering(newText);
                return true;
            }
        });
    }

    private void performFiltering(String text) {
        filteredList.clear();
        String query = text.toLowerCase().trim();
        if (query.isEmpty()) {
            filteredList.addAll(beachList);
        } else {
            for (BeachLocation beach : beachList) {
                boolean matchesName = beach.getName().toLowerCase().contains(query);
                boolean matchesProtected = (query.equals("protected") || query.equals("flat")) && beach.isReefProtected();
                boolean matchesUnprotected = (query.equals("unprotected") || query.equals("shorebreak")) && (!beach.isReefProtected() && !beach.isBreaksFarOut());
                
                if (matchesName || matchesProtected || matchesUnprotected) {
                    filteredList.add(beach);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private static class BeachAdapter extends ArrayAdapter<BeachLocation> {
        BeachAdapter(AppCompatActivity context, List<BeachLocation> items) {
            super(context, R.layout.row_beach_item, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_beach_item, parent, false);
            }

            MaterialCardView card = (MaterialCardView) convertView;
            TextView tvName = convertView.findViewById(R.id.tvBeachItemName);
            BeachLocation beach = getItem(position);

            if (beach != null) {
                tvName.setText(beach.getName() + " (" + beach.getHawaiianName() + ")");
                tvName.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                card.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.darker_gray));

                // Alternate between blue and green borders
                if (position % 2 == 0) {
                    card.setStrokeColor(ContextCompat.getColor(getContext(), R.color.primary_blue));
                } else {
                    card.setStrokeColor(ContextCompat.getColor(getContext(), R.color.light_green));
                }
            }

            return convertView;
        }
    }
}
