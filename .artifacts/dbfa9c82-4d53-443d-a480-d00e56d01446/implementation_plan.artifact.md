# Implementation Plan: Multi-Language App Support (Startup Screen & Top Corner Switcher)

Add startup language selection (English, Spanish, Japanese) occurring on every app launch, plus a top corner language switcher on main screens.

## Proposed Changes

### [Language Management & Startup Activity]
#### [NEW] [LanguageSelectActivity.java](file:///C:/Users/selra/AndroidStudioProjects/CongressionalAPP/app/src/main/java/com/example/congressionalapp/LanguageSelectActivity.java)
- Startup screen shown on every app launch with buttons for English, Spanish, and Japanese. Sets locale via `AppCompatDelegate.setApplicationLocales()` and starts `AppHomeActivity`.

#### [NEW] [activity_language_select.xml](file:///C:/Users/selra/AndroidStudioProjects/CongressionalAPP/app/src/main/res/layout/activity_language_select.xml)
- Clean, branded layout with language selection buttons matching app design tokens.

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/selra/AndroidStudioProjects/CongressionalAPP/app/src/main/AndroidManifest.xml)
- Register `LanguageSelectActivity` as the `MAIN` / `LAUNCHER` activity.

### [Top Corner Language Switcher]
#### [MODIFY] [activity_welcome.xml](file:///C:/Users/selra/AndroidStudioProjects/CongressionalAPP/app/src/main/res/layout/activity_welcome.xml)
- Add a top-corner language switch button.

#### [MODIFY] [AppHomeActivity.java](file:///C:/Users/selra/AndroidStudioProjects/CongressionalAPP/app/src/main/java/com/example/congressionalapp/AppHomeActivity.java)
- Wire up the top-corner language button to let users switch languages anytime and restart/refresh the language selection.

## Verification Plan
- Build app (`app:assembleDebug`) and verify locale switching works correctly.
