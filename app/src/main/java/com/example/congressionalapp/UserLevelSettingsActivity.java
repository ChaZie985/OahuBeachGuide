package com.example.congressionalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserLevelSettingsActivity extends AppCompatActivity {

    private int experienceLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        SeekBar seekBar = findViewById(R.id.seekBarExperience);
        TextView tvValue = findViewById(R.id.tvExperienceValue);
        Button btnContinue = findViewById(R.id.btnContinue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                experienceLevel = progress + 1;
                String label = experienceLevel + "";
                if (experienceLevel == 1) label += " (Tourist)";
                else if (experienceLevel == 10) label += " (Pro Surfer)";
                
                tvValue.setText(String.format("Level: %s", label));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnContinue.setOnClickListener(v -> {
            persistUserLevel();
            navigateToHome();
        });
    }

    private void persistUserLevel() {
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("SKILL_LEVEL", experienceLevel);
        editor.apply();
    }

    private void navigateToHome() {
        Intent intent = new Intent(UserLevelSettingsActivity.this, AppHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
