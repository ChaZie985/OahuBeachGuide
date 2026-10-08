package com.example.congressionalapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class LanguageSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        Button btnEnglish = findViewById(R.id.btnEnglish);
        Button btnSpanish = findViewById(R.id.btnSpanish);
        Button btnFrench = findViewById(R.id.btnFrench);
        Button btnJapanese = findViewById(R.id.btnJapanese);
        Button btnKorean = findViewById(R.id.btnKorean);
        Button btnChinese = findViewById(R.id.btnChinese);

        btnEnglish.setOnClickListener(v -> setAppLanguageAndProceed("en"));
        btnSpanish.setOnClickListener(v -> setAppLanguageAndProceed("es"));
        btnFrench.setOnClickListener(v -> setAppLanguageAndProceed("fr"));
        btnJapanese.setOnClickListener(v -> setAppLanguageAndProceed("ja"));
        btnKorean.setOnClickListener(v -> setAppLanguageAndProceed("ko"));
        btnChinese.setOnClickListener(v -> setAppLanguageAndProceed("zh"));
    }

    private void setAppLanguageAndProceed(String languageCode) {
        AppLocalization.setLanguage(this, languageCode);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode));

        Intent intent = new Intent(LanguageSelectActivity.this, AppHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
