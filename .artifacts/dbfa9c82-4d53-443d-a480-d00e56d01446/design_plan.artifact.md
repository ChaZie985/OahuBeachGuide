# Design Plan: Oʻahu Beach Information Page (Native Android App)

## Overview
A public-safety first beach information screen for Oʻahu beaches, designed for local families, visitors, non-native speakers, and emergency situations. It prioritizes clarity, official warnings, data freshness, and place respect (Hawaiian diacritics like ʻokina and kahakō).

## Core Principles
1. **Never show false safety**: Use "Lower risk" or "Generally safe", never plain "Safe". Stale/missing data shows "Conditions unknown" with conservative ratings.
2. **Plain explanation**: Every rating includes a plain sentence explaining why.
3. **Official information wins**: Closures and High Surf Warnings override everything.
4. **Show freshness**: Every live value displays its update timestamp and state (`ok`, `stale`, `unavailable`).
5. **Place Respect**: Authentic Hawaiian names (e.g., *Wāwāmalu* for Sandy Beach) with correct diacritics.

## Palette & Color Tokens (Android Resource Mappings)
- `--deep-ocean` (`#0A2342`): Main text, header text
- `--reef` (`#12406B`): Secondary headings
- `--pacific` (`#1767B0`): Buttons and active states
- `--lagoon` (`#2A9BD6`): Charts and highlights
- `--seafoam` (`#EAF6FC`): Light background
- `--foam` (`#FFFFFF`): Panel surfaces

### Safety Colors (Ratings & Alerts Only)
- **Lower Risk / Safe**: Green (`#1B9E5A`, text white)
- **Caution / Use caution**: Yellow (`#F4B400`, text dark)
- **Dangerous / High risk**: Red (`#D3263A`, text white)
- **Closed**: Striped charcoal (`#1E1E1E`, barrier style)

## Typography
- **Display**: Bold rounded sans-serif (Outfit / Bricolage Grotesque style via system sans-serif bold)
- **Body**: Highly readable sans-serif (at least 17px equivalent on mobile)
- **Diacritics Support**: Full support for ʻokina (`ʻ`) and kahakō (`ā`, `ē`, `ī`, `ō`, `ū`).

## Page Sections (Mobile Native Layout)
1. **Alert Strip**: Full-width official closures or High Surf / flood warnings.
2. **Header**: Beach name in large display type, Hawaiian name, shoreline label, Save/Share buttons, and fixed Emergency [SOS] button.
3. **Today's Verdict**: Main rating badge, plain-language reason sentence, activity selector (Wade, Swim, Snorkel, Surf), personalization line, and freshness indicator.
4. **Safer Options Nearby**: Shown when rating is Caution, Dangerous, or Closed (with distance/drive time and reason).
5. **Today's Conditions**: Wave height, wind, tide curve, lifeguard status, water quality, UV index, jellyfish window.
6. **Hazards & Local Knowledge**: Permanent hazards (shorebreak, rip currents) with plain explanations and local knowledge quotes.
7. **Who It's Good For**: Good/not good tags and seasonal patterns.
8. **Beach Details & Amenities**: Restrooms, showers, parking, directions button.
9. **Wildlife & Mālama ʻāina**: Seal/turtle distance rules and pack-out trash notes.
10. **Emergency Sheet**: Full-screen emergency modal with 911 call button, GPS coordinates, and first-aid steps.
