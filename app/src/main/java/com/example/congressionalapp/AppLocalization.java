package com.example.congressionalapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * MANAGER: AppLocalization
 * 
 * Manages app-wide language state (English, Spanish, Japanese, Korean, Chinese) and provides
 * comprehensive localized translations for all UI text, card headings, safety verdicts, beach hazards, and conditions.
 */
public class AppLocalization {
    private static final String PREF_NAME = "AppLangPref";
    private static final String KEY_LANG = "selected_language"; // "en", "es", "ja", "ko", "zh"

    public static void setLanguage(Context context, String langCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANG, langCode).apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANG, "en");
    }

    public static String get(Context context, String key) {
        String lang = getLanguage(context);
        switch (lang) {
            case "es":
                return getSpanish(key);
            case "fr":
                return getFrench(key);
            case "ja":
                return getJapanese(key);
            case "ko":
                return getKorean(key);
            case "zh":
                return getChinese(key);
            default:
                return getEnglish(key);
        }
    }

    private static String getEnglish(String key) {
        switch (key) {
            case "explore_map": return "Explore Map";
            case "show_me_on_map": return "Show me on map";
            case "search_beaches": return "Search Beaches";
            case "safe_recommendations": return "Safe Recommendations";
            case "search_amenities": return "Search by Amenities";
            case "welcome_title": return "Welcome to the\nOahu Beach Guide";
            case "back": return "Back";
            case "categorized_by_proximity": return "Categorized by closest to you";
            case "finding_best_spots": return "Finding the best spots for you...";
            case "shore_label": return "Shore: ";
            case "miles_away_label": return " miles away";
            case "amenities_title": return "Search by Amenities";
            case "amenities_subtitle": return "Select amenities at the top, then click submit to see your top 4 matching safe beaches below.";
            case "amenity_restrooms": return "Restrooms";
            case "amenity_showers": return "Showers";
            case "amenity_parking": return "Parking";
            case "amenity_lifeguard": return "Lifeguard Tower";
            case "amenity_picnic": return "Picnic Areas";
            case "find_matching_beaches": return "Find Matching Safe Beaches";
            case "top_four_safe_beaches": return "Top 4 Safe Matching Beaches:";
            case "amenities_disclaimer": return "Note: Only safe beaches are shown. These are matched directly based on your selected amenities.";
            case "safer_options": return "Safer options nearby";
            case "todays_conditions": return "Today's conditions";
            case "hazards": return "Hazards at this beach";
            case "locals_know": return "What locals know";
            case "good_for": return "Who it's good for";
            case "amenities_wildlife": return "Amenities & Wildlife";
            case "emergency_assistance": return "EMERGENCY ASSISTANCE";
            case "call_911": return "CALL 911 NOW";
            case "close_emergency": return "Close Emergency View";
            case "sorted_by_proximity": return "Sorted by closest to you (Proximity)";
            case "status_safe": return "RECOMMENDED (SAFE)";
            case "status_dangerous": return "DANGEROUS (HEAVY BREAK)";
            case "status_caution": return "CAUTION (MODERATE)";
            case "verdict_dangerous": return "▲ Dangerous today";
            case "verdict_safe": return "● Lower risk today";
            case "verdict_caution": return "▲ Use caution";
            case "reason_dangerous": return "High hazard risk. Use extreme caution or stay on the sand.";
            case "reason_safe": return "Calm or protected conditions. Generally safe for swimming and families.";
            case "reason_caution": return "Moderate conditions. Check local surf and wind before entering the water.";
            case "wind_text": return "Wind: 15 mph ENE (Strong onshore wind, choppy water)";
            case "tide_text": return "Tide: +1.6 ft (High at 2:15 PM, Low at 8:30 PM)";
            case "lifeguard_text": return "Lifeguard: On duty now (Tower #1)";
            case "water_quality_text": return "Water quality: No advisories";
            case "uv_text": return "UV Index: 11 (Extreme) · Reef-safe sunscreen recommended";
            case "malama_text": return "Mālama ʻāina: Stay 10 ft away from sea turtles and 50 ft from Hawaiian monk seals. Pack out your trash and protect the reef.";
            case "freshness_text": return "Updated 14 minutes ago.";
            case "location_label": return "Location: ";
            case "coordinates_label": return "Coordinates: ";
            case "first_aid_steps": return "Quick First Aid Steps:\n• Rip current: Swim parallel to shore, never against the current.\n• Neck/Back injury: Do not move the person; keep spine aligned.\n• Sting / Jellyfish: Rinse with vinegar, scrape tentacles.";
            case "about_this_beach": return "About this beach";
            case "beach_address_navigation": return "📍 Beach Address & Navigation";
            default: return key;
        }
    }

    private static String getSpanish(String key) {
        switch (key) {
            case "explore_map": return "Explorar Mapa";
            case "show_me_on_map": return "Mostrar en el mapa";
            case "search_beaches": return "Buscar Playas";
            case "safe_recommendations": return "Recomendaciones de Playas Seguras";
            case "search_amenities": return "Buscar por Servicios";
            case "welcome_title": return "Bienvenido a la\nGuía de Playas de Oahu";
            case "back": return "Volver";
            case "categorized_by_proximity": return "Categorizado por el más cercano a ti";
            case "finding_best_spots": return "Buscando los mejores lugares para ti...";
            case "shore_label": return "Costa: ";
            case "miles_away_label": return " millas de distancia";
            case "amenities_title": return "Buscar por Servicios";
            case "amenities_subtitle": return "Seleccione los servicios arriba y haga clic en enviar para ver las 4 mejores playas seguras.";
            case "amenity_restrooms": return "Baños";
            case "amenity_showers": return "Duchas";
            case "amenity_parking": return "Estacionamiento";
            case "amenity_lifeguard": return "Torre de salvavidas";
            case "amenity_picnic": return "Áreas de picnic";
            case "find_matching_beaches": return "Buscar playas seguras";
            case "top_four_safe_beaches": return "Top 4 playas seguras coinciden:";
            case "amenities_disclaimer": return "Nota: Solo se muestran playas seguras. Coinciden directamente según los servicios seleccionados.";
            case "safer_options": return "Opciones más seguras cerca";
            case "todays_conditions": return "Condiciones de hoy";
            case "hazards": return "Peligros en esta playa";
            case "locals_know": return "Lo que saben los locales";
            case "good_for": return "Para quién es bueno";
            case "amenities_wildlife": return "Servicios y vida silvestre";
            case "emergency_assistance": return "ASISTENCIA DE EMERGENCIA";
            case "call_911": return "LLAMAR AL 911 AHORA";
            case "close_emergency": return "Cerrar Vista de Emergencia";
            case "sorted_by_proximity": return "Ordenado por más cercano a ti (Proximidad)";
            case "status_safe": return "RECOMENDADO (SEGURO)";
            case "status_dangerous": return "PELIGROSO (OLEAJE FUERTE)";
            case "status_caution": return "PRECAUCIÓN (MODERADO)";
            case "verdict_dangerous": return "▲ Peligroso hoy";
            case "verdict_safe": return "● Menor riesgo hoy";
            case "verdict_caution": return "▲ Use precaución";
            case "reason_dangerous": return "Alto riesgo de peligro. Extreme la precaución o quédese en la arena.";
            case "reason_safe": return "Condiciones tranquilas o protegidas. Generalmente seguro para nadar y familias.";
            case "reason_caution": return "Condiciones moderadas. Verifique el oleaje y el viento local antes de entrar al agua.";
            case "wind_text": return "Viento: 15 mph ENE (Viento fuerte, agua agitada)";
            case "tide_text": return "Marea: +1.6 pies (Pleamar 2:15 PM, Baja 8:30 PM)";
            case "lifeguard_text": return "Salvavidas: En servicio ahora (Torre #1)";
            case "water_quality_text": return "Calidad del agua: Sin avisos";
            case "uv_text": return "Índice UV: 11 (Extremo) · Protector solar seguro para arrecifes";
            case "malama_text": return "Mālama ʻāina: Manténgase a 10 pies de las tortugas marinas y 50 pies de las focas monje. Recoja su basura y proteja el arrecife.";
            case "freshness_text": return "Actualizado hace 14 minutos.";
            case "location_label": return "Ubicación: ";
            case "coordinates_label": return "Coordenadas: ";
            case "first_aid_steps": return "Pasos de primeros auxilios:\n• Corriente de resaca: Nade paralelo a la orilla.\n• Lesión de cuello/espalda: No mueva a la persona.\n• Picadura de medusa: Enjuague con vinagre.";
            case "about_this_beach": return "Acerca de esta playa";
            case "beach_address_navigation": return "📍 Dirección y Navegación de la Playa";
            default: return key;
        }
    }

    private static String getFrench(String key) {
        switch (key) {
            case "explore_map": return "Explorer la Carte";
            case "show_me_on_map": return "Afficher sur la carte";
            case "search_beaches": return "Rechercher des Plages";
            case "safe_recommendations": return "Recommandations de Plages Sûres";
            case "search_amenities": return "Rechercher par Équipements";
            case "welcome_title": return "Bienvenue sur le\nGuide des Plages d'Oahu";
            case "back": return "Retour";
            case "categorized_by_proximity": return "Classé par proximité";
            case "finding_best_spots": return "Recherche des meilleurs endroits...";
            case "shore_label": return "Côte : ";
            case "miles_away_label": return " miles";
            case "amenities_title": return "Rechercher par Équipements";
            case "amenities_subtitle": return "Sélectionnez les équipements ci-dessus, puis cliquez sur valider pour voir les 4 meilleures plages sûres correspondant à vos critères.";
            case "amenity_restrooms": return "Toilettes";
            case "amenity_showers": return "Douches";
            case "amenity_parking": return "Parking";
            case "amenity_lifeguard": return "Poste de secours";
            case "amenity_picnic": return "Zones de pique-nique";
            case "find_matching_beaches": return "Trouver des Plages Sûres";
            case "top_four_safe_beaches": return "Top 4 des plages sûres correspondantes :";
            case "amenities_disclaimer": return "Remarque : Seules les plages sûres sont affichées, filtrées selon vos équipements sélectionnés.";
            case "safer_options": return "Options plus sûres à proximité";
            case "todays_conditions": return "Conditions du jour";
            case "hazards": return "Dangers sur cette plage";
            case "locals_know": return "Ce que savent les locaux";
            case "good_for": return "Recommandé pour";
            case "amenities_wildlife": return "Équipements & Faune";
            case "emergency_assistance": return "ASSISTANCE D'URGENCE";
            case "call_911": return "APPELER LE 911 MAINTENANT";
            case "close_emergency": return "Fermer la vue d'urgence";
            case "sorted_by_proximity": return "Trié par proximité";
            case "status_safe": return "RECOMMANDÉ (SÛR)";
            case "status_dangerous": return "DANGEREUX (VAGUES FORTES)";
            case "status_caution": return "ATTENTION (MODÉRÉ)";
            case "verdict_dangerous": return "▲ Dangereux aujourd'hui";
            case "verdict_safe": return "● Risque faible aujourd'hui";
            case "verdict_caution": return "▲ Faire attention";
            case "reason_dangerous": return "Risque élevé. Soyez extrêmement prudent ou restez sur le sable.";
            case "reason_safe": return "Conditions calmes ou protégées. Généralement sûr pour la baignade et les familles.";
            case "reason_caution": return "Conditions modérées. Vérifiez les vagues et le vent avant d'entrer dans l'eau.";
            case "wind_text": return "Vent : 15 mph ENE (Vent de mer fort, eau agitée)";
            case "tide_text": return "Marée : +1.6 ft (Marée haute à 14h15, Basse à 20h30)";
            case "lifeguard_text": return "Maître-nageur : En service (Tour #1)";
            case "water_quality_text": return "Qualité de l'eau : Aucun avertissement";
            case "uv_text": return "Indice UV : 11 (Extrême) · Crème solaire respectueuse des récifs recommandée";
            case "malama_text": return "Mālama ʻāina : Restez à 10 pieds des tortues marines et 50 pieds des phoques moines. Emportez vos déchets et protégez le récif.";
            case "freshness_text": return "Mis à jour il y a 14 minutes.";
            case "location_label": return "Emplacement : ";
            case "coordinates_label": return "Coordonnées : ";
            case "first_aid_steps": return "Premiers secours rapides :\n• Courant d'arrachement : Nagez parallèlement à la côte.\n• Blessure cou/dos : Ne déplacez pas la personne.\n• Piqûre de méduse : Rincez au vinaigre.";
            case "about_this_beach": return "À propos de cette plage";
            case "beach_address_navigation": return "📍 Adresse et Navigation de la Plage";
            default: return key;
        }
    }

    private static String getJapanese(String key) {
        switch (key) {
            case "explore_map": return "マップを見る";
            case "show_me_on_map": return "マップで見る";
            case "search_beaches": return "ビーチを検索";
            case "safe_recommendations": return "安全なビーチのおすすめ";
            case "search_amenities": return "設備・アメニティで検索";
            case "welcome_title": return "オアフ・ビーチガイドへ\nようこそ";
            case "back": return "戻る";
            case "categorized_by_proximity": return "現在地から近い順に分類";
            case "finding_best_spots": return "おすすめのスポットを探しています...";
            case "shore_label": return "岸: ";
            case "miles_away_label": return " マイル離れています";
            case "amenities_title": return "設備・アメニティで検索";
            case "amenities_subtitle": return "上部で設備を選択し、送信をクリックして安全な上位4つのビーチを表示します。";
            case "amenity_restrooms": return "トイレ";
            case "amenity_showers": return "シャワー";
            case "amenity_parking": return "駐車場";
            case "amenity_lifeguard": return "ライフガードタワー";
            case "amenity_picnic": return "ピクニックエリア";
            case "find_matching_beaches": return "安全なビーチを検索";
            case "top_four_safe_beaches": return "上位4つの安全な一致ビーチ:";
            case "amenities_disclaimer": return "注意: 安全なビーチのみが表示されます。選択された設備に基づいて直接マッチングされます。";
            case "safer_options": return "近くの安全なビーチ";
            case "todays_conditions": return "本日のコンディション";
            case "hazards": return "このビーチの危険性";
            case "locals_know": return "地元の情報";
            case "good_for": return "こんな人におすすめ";
            case "amenities_wildlife": return "設備と野生生物";
            case "emergency_assistance": return "緊急アシスタンス";
            case "call_911": return "今すぐ911に電話";
            case "close_emergency": return "緊急画面を閉じる";
            case "sorted_by_proximity": return "現在地から近い順に並べ替え";
            case "status_safe": return "おすすめ（安全）";
            case "status_dangerous": return "危険（強い波）";
            case "status_caution": return "注意（中程度）";
            case "verdict_dangerous": return "▲ 本日は危険";
            case "verdict_safe": return "● 本日は低リスク";
            case "verdict_caution": return "▲ 要注意";
            case "reason_dangerous": return "危険度が高いため、十分注意するか砂浜でお過ごしください。";
            case "reason_safe": return "穏やかで保護された状態です。海水浴やご家族連れに最適です。";
            case "reason_caution": return "中程度のコンディションです。入水前に現地の波や風をご確認ください。";
            case "wind_text": return "風: 東北東 15 mph（強い風、波が荒れています）";
            case "tide_text": return "潮位: +1.6フィート（満潮 14:15、干潮 20:30）";
            case "lifeguard_text": return "ライフガード: 勤務中（タワー #1）";
            case "water_quality_text": return "水質: 警告なし";
            case "uv_text": return "紫外線指数: 11（極端）· リーフセーフ日焼け止め推奨";
            case "malama_text": return "マラマ・アイナ: ウミガメから10フィート、ハワイモンクアザラシから50フィート離れてください。ゴミを持ち帰り、サンゴ礁を保護しましょう。";
            case "freshness_text": return "14分前に更新されました。";
            case "location_label": return "場所: ";
            case "coordinates_label": return "座標: ";
            case "first_aid_steps": return "応急処置の手順:\n• 離岸流: 海岸と平行に泳ぐ。\n• 首/背中の負傷: 動かさない。\n• クラゲ刺傷: お酢で洗い流す。";
            case "about_this_beach": return "このビーチについて";
            case "beach_address_navigation": return "📍 ビーチの住所とナビゲーション";
            default: return key;
        }
    }

    private static String getKorean(String key) {
        switch (key) {
            case "explore_map": return "지도 탐색";
            case "show_me_on_map": return "지도에서 보기";
            case "search_beaches": return "해변 검색";
            case "safe_recommendations": return "안전한 해변 추천";
            case "search_amenities": return "편의시설로 검색";
            case "welcome_title": return "오아후 해변 가이드에\n오신 것을 환영합니다";
            case "back": return "뒤로";
            case "categorized_by_proximity": return "가까운 순으로 분류";
            case "finding_best_spots": return "최적의 장소를 찾는 중...";
            case "shore_label": return "해변: ";
            case "miles_away_label": return " 마일 거리";
            case "amenities_title": return "편의시설로 검색";
            case "amenities_subtitle": return "상단에서 편의시설을 선택하고 제출을 클릭하여 아래에서 추천 안전 해변 4곳을 확인하세요.";
            case "amenity_restrooms": return "화장실";
            case "amenity_showers": return "샤워실";
            case "amenity_parking": return "주차장";
            case "amenity_lifeguard": return "라이프가드 타워";
            case "amenity_picnic": return "피크닉 구역";
            case "find_matching_beaches": return "안전한 해변 찾기";
            case "top_four_safe_beaches": return "추천 안전 해변 상위 4곳:";
            case "amenities_disclaimer": return "참고: 안전한 해변만 표시됩니다. 선택하신 편의시설을 바탕으로 직접 매칭되었습니다.";
            case "safer_options": return "주변 안전한 해변";
            case "todays_conditions": return "오늘의 해양 상태";
            case "hazards": return "이 해변의 위험 요소";
            case "locals_know": return "현지인들의 정보";
            case "good_for": return "추천 대상";
            case "amenities_wildlife": return "편의시설 및 야생동물";
            case "emergency_assistance": return "긴급 구조 요청";
            case "call_911": return "지금 911에 전화하기";
            case "close_emergency": return "긴급 화면 닫기";
            case "sorted_by_proximity": return "가까운 순으로 정렬 (거리순)";
            case "status_safe": return "추천 (안전)";
            case "status_dangerous": return "위험 (강한 파도)";
            case "status_caution": return "주의 (보통)";
            case "verdict_dangerous": return "▲ 오늘 위험함";
            case "verdict_safe": return "● 오늘 위험 낮음";
            case "verdict_caution": return "▲ 주의 필요";
            case "reason_dangerous": return "위험 요소가 높습니다. 각별히 주의하시거나 모래사장에 머물러 주세요.";
            case "reason_safe": return "파도가 잔잔하고 안전합니다. 수영과 가족 단위 방문에 적합합니다.";
            case "reason_caution": return "보통 수준의 파도입니다. 입수 전 현지 파도와 바람을 확인하세요.";
            case "wind_text": return "바람: 동북동 15 mph (강한 순풍, 거친 파도)";
            case "tide_text": return "조수: +1.6 피트 (만조 오후 2:15, 간조 오후 8:30)";
            case "lifeguard_text": return "라이프가드: 근무 중 (Tower #1)";
            case "water_quality_text": return "수질: 경보 없음";
            case "uv_text": return "자외선 지수: 11 (매우 높음) · 산호 친화적 자외선 차단제 권장";
            case "malama_text": return "말라마 ʻ아이나: 바다거북에서 3미터, 하와이몽크바다표범에서 15미터 이상 거리를 유지하세요. 쓰레기를 되가져가고 산호초를 보호하세요.";
            case "freshness_text": return "14분 전에 업데이트됨.";
            case "location_label": return "위치: ";
            case "coordinates_label": return "좌표: ";
            case "first_aid_steps": return "응급 처치 단계:\n• 이안류: 해안과 평행하게 수영.\n• 목/등 부상: 환자를 이동시키지 않음.\n• 해파리 쏘임: 식초로 씻어내기.";
            case "about_this_beach": return "이 해변 소개";
            case "beach_address_navigation": return "📍 해변 주소 및 길찾기";
            default: return key;
        }
    }

    private static String getChinese(String key) {
        switch (key) {
            case "explore_map": return "探索地图";
            case "show_me_on_map": return "在地图上显示";
            case "search_beaches": return "搜索海滩";
            case "safe_recommendations": return "安全海滩推荐";
            case "search_amenities": return "按设施搜索";
            case "welcome_title": return "欢迎来到\n瓦胡岛海滩指南";
            case "back": return "返回";
            case "categorized_by_proximity": return "按离您最近分类";
            case "finding_best_spots": return "正在为您寻找最佳地点...";
            case "shore_label": return "岸边：";
            case "miles_away_label": return " 英里外";
            case "amenities_title": return "按设施搜索";
            case "amenities_subtitle": return "在上方选择设施，然后点击提交查看下方排名前4的安全匹配海滩。";
            case "amenity_restrooms": return "洗手间";
            case "amenity_showers": return "淋浴";
            case "amenity_parking": return "停车场";
            case "amenity_lifeguard": return "救生员塔";
            case "amenity_picnic": return "野餐区";
            case "find_matching_beaches": return "寻找匹配的安全海滩";
            case "top_four_safe_beaches": return "前 4 名安全匹配海滩：";
            case "amenities_disclaimer": return "注意：仅显示安全海滩。这些是根据您选择的设施直接匹配的。";
            case "safer_options": return "附近的安全海滩";
            case "todays_conditions": return "今日海况";
            case "hazards": return "此海滩的危险因素";
            case "locals_know": return "当地人须知";
            case "good_for": return "适合人群";
            case "amenities_wildlife": return "设施与野生动物";
            case "emergency_assistance": return "紧急救援";
            case "call_911": return "立即拨打 911";
            case "close_emergency": return "Close Emergency View";
            case "sorted_by_proximity": return "按离您最近排序（距离）";
            case "status_safe": return "推荐（安全）";
            case "status_dangerous": return "危险（强浪）";
            case "status_caution": return "注意（中等）";
            case "verdict_dangerous": return "▲ 今日危险";
            case "verdict_safe": return "● 今日风险较低";
            case "verdict_caution": return "▲ 请注意安全";
            case "reason_dangerous": return "存在高度危险风险。请极度小心或留在沙滩上。";
            case "reason_safe": return "海况平静或受保护。通常适合游泳和家庭游玩。";
            case "reason_caution": return "海况适中。入水前请检查当地浪况和风向。";
            case "wind_text": return "风向风速：东北东 15 英里/小时（顺风较强，海面 choppy）";
            case "tide_text": return "潮汐：+1.6 英尺（满潮下午 2:15，干潮晚上 8:30）";
            case "lifeguard_text": return "救生员：值班中（1号塔）";
            case "water_quality_text": return "水质：无预警";
            case "uv_text": return "紫外线指数：11（极高）· 建议使用环保防晒霜";
            case "malama_text": return "Mālama ʻāina：与海龟保持 10 英尺距离，与夏威夷僧海豹保持 50 英尺距离。请带走垃圾并保护珊瑚礁。";
            case "freshness_text": return "14分钟前更新。";
            case "location_label": return "位置：";
            case "coordinates_label": return "坐标：";
            case "first_aid_steps": return "急救步骤：\n• 离岸流：沿海岸平行游动。\n• 颈部/背部受伤：切勿移动伤者。\n• 水母蛰伤：用醋冲洗并刮除触须。";
            case "about_this_beach": return "关于此海滩";
            case "beach_address_navigation": return "📍 海滩地址与导航";
            default: return key;
        }
    }

    public static String getLocalizedHazard(Context context, String beachId) {
        String lang = getLanguage(context);
        if ("es".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "• Rompiente: Las olas rompen directamente en la arena; lesiones graves de cuello y espalda ocurren incluso en días pequeños.\n• Corrientes de resaca: La fuerte resaca arrastra a los nadadores mar adentro.";
            if ("kailua-beach".equals(beachId)) return "• Vientos fuertes: Los vientos alisios fuertes pueden empujar inflables mar adentro.\n• Picaduras: Ocasionales picaduras de carabela portuguesa.";
            if ("waimea-bay".equals(beachId)) return "• Rompiente invernal masiva y potentes corrientes de resaca en invierno.";
            if ("sunset-beach".equals(beachId)) return "• Rompiente invernal peligrosa, fuertes corrientes de resaca y canales de arrecife cambiantes.";
            if ("hanauma-bay".equals(beachId)) return "• Rocas de arrecife resbaladizas y fuerte exposición solar.";
            if ("makapuu-beach".equals(beachId)) return "• Rompiente severa, potente resaca y fuertes corrientes.";
        } else if ("fr".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "• Rouleau de bord : Les vagues déferlent directement sur le sable ; de graves blessures au cou et au dos s'y produisent même par petites vagues.\n• Courants de retour : Le puissant reflux entraîne les nageurs vers le large.";
            if ("kailua-beach".equals(beachId)) return "• Vents forts : Les alizés soutenus peuvent pousser les embarcations gonflables au large.\n• Piqûres : Piqûres occasionnelles de galères portugaises.";
            if ("waimea-bay".equals(beachId)) return "• Rouleaux hivernaux massifs et puissants courants de retour en hiver. Calme en été.";
            if ("sunset-beach".equals(beachId)) return "• Rouleaux hivernaux dangereux, puissants courants de retour et passes de récif chaotiques.";
            if ("hanauma-bay".equals(beachId)) return "• Rochers coralliens glissants et forte exposition au soleil.";
            if ("makapuu-beach".equals(beachId)) return "• Rouleau de bord sévère, puissant reflux et forts courants de retour.";
        } else if ("ja".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "• ショアブレイク: 波が砂浜に直接打ち付けます。小さな波の日でも首や背中の大怪我が発生します。\n• 離岸流: 強い引き波が泳ぎ手を沖へ引っ張ります。";
            if ("kailua-beach".equals(beachId)) return "• 強風: 強い貿易風により浮き輪が沖へ流されることがあります。\n• クラゲ: ポルトガルマンボウ（カツオノエボシ）に注意。";
            if ("waimea-bay".equals(beachId)) return "• 冬季の巨大なショアブレイクと強力な離岸流。夏期は穏やかです。";
            if ("sunset-beach".equals(beachId)) return "• 危険な冬季のショアブレイク、強い離岸流、複雑なリーフチャネル。";
            if ("hanauma-bay".equals(beachId)) return "• 滑りやすいサンゴ礁の岩場と強い日差し。";
            if ("makapuu-beach".equals(beachId)) return "• 激しいショアブレイク、強力なバックウォッシュ、強い離岸流。";
        } else if ("ko".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "• 쇼어브레이크: 파도가 모래사장에 직접 부딪쳐 작은 파도에서도 심각한 목/등 부상이 발생합니다.\n• 이안류: 강력한 역류가 수영객을 바다로 끌고 갑니다.";
            if ("kailua-beach".equals(beachId)) return "• 강풍: 강한 무역풍이 튜브 등을 바다 멀리 밀어낼 수 있습니다.\n• 해파리: 간헐적인 해파리 쏘임 주의.";
            if ("waimea-bay".equals(beachId)) return "• 겨울철 거대한 쇼어브레이크와 강력한 이안류. 여름철은 수영하기 안전합니다.";
            if ("sunset-beach".equals(beachId)) return "• 위험한 겨울철 쇼어브레이크, 강한 이안류, 수중 암초 채널.";
            if ("hanauma-bay".equals(beachId)) return "• 미끄러운 산호초 바위와 강한 햇빛.";
            if ("makapuu-beach".equals(beachId)) return "• 심각한 쇼어브레이크, 강력한 백워시, 강한 이안류.";
        } else if ("zh".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "• 岸边碎浪：波浪直接在沙滩上破碎；即使在浪小的时候也会发生严重的颈部和背部受伤。\n• 离岸流：强烈的回流会将游泳者拉向海中。";
            if ("kailua-beach".equals(beachId)) return "• 强风：强劲的信风可能会将充气玩具吹向外海。\n• 水母：偶有僧帽水母蛰伤。";
            if ("waimea-bay".equals(beachId)) return "• 冬季巨浪岸边碎浪和强烈的离岸流。夏季风平浪静，适合游泳。";
            if ("sunset-beach".equals(beachId)) return "• 危险的冬季岸边碎浪、强烈的离岸流以及多变的珊瑚礁通道。";
            if ("hanauma-bay".equals(beachId)) return "• 湿滑的珊瑚礁岩石以及强烈的阳光照射。";
            if ("makapuu-beach".equals(beachId)) return "• 剧烈的岸边碎浪、强劲的回流和强大的离岸流。";
        }
        return getEnglishHazard(beachId);
    }

    private static String getEnglishHazard(String beachId) {
        switch (beachId) {
            case "sandy-beach":
                return "• Shorebreak: Waves break directly on the sand; serious neck and back injuries happen here even on small days.\n• Rip currents: Strong backwash pulls swimmers out.";
            case "kailua-beach":
                return "• Strong trade winds can push inflatables offshore. Occasional Portuguese man-o-war stings.";
            case "waimea-bay":
                return "• Massive winter shorebreak and powerful rip currents. Summer is calm and safe for swimming.";
            case "sunset-beach":
                return "• Dangerous winter shorebreak, strong rip currents, and shifting underwater reef channels.";
            case "hanauma-bay":
                return "• Slippery reef rocks, strong sun exposure, and mandatory conservation rules.";
            case "makapuu-beach":
                return "• Severe shorebreak, powerful backwash, and strong rip currents.";
            default:
                return "• Standard ocean hazards apply. Check local conditions.";
        }
    }

    public static String getLocalizedLocalKnowledge(Context context, String beachId) {
        String lang = getLanguage(context);
        if ("es".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "Parece atractivo desde el estacionamiento. Obsérvelo desde la arena a menos que sea un bodysurfer experimentado.";
            if ("kailua-beach".equals(beachId)) return "Generalmente más tranquilo dentro del arrecife, pero verifique la dirección del viento antes de salir en kayak.";
            if ("waimea-bay".equals(beachId)) return "Respete el oleaje de invierno. En verano, la bahía actúa como una piscina plácida.";
        } else if ("fr".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "Semble attrayant depuis le parking. Observez depuis le sable à moins d'être un bodysurfeur expérimenté.";
            if ("kailua-beach".equals(beachId)) return "Généralement plus calme à l'intérieur du récif, mais vérifiez le vent avant de sortir en kayak.";
            if ("waimea-bay".equals(beachId)) return "Respectez les vagues hivernales. En été, la baie ressemble à une piscine paisible.";
        } else if ("ja".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "駐車場からは魅力的に見えます。経験豊富なボディサーファーでなければ、砂浜から眺めるだけにしてください。";
            if ("kailua-beach".equals(beachId)) return "リーフ内は比較的穏やかですが、カヤックで出る前に風向きを確認してください。";
            if ("waimea-bay".equals(beachId)) return "冬季のサーフには敬意を払ってください。夏期は穏やかなプールのようになります。";
        } else if ("ko".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "주차장에서 보면 매력적이지만, 숙련된 바디서퍼가 아니라면 모래사장에서 구경만 하세요.";
            if ("kailua-beach".equals(beachId)) return "산호초 안쪽은 대체로 잔잔하지만, 카약 탑승 전 풍향을 확인하세요.";
            if ("waimea-bay".equals(beachId)) return "겨울철 파도를 존중하세요. 여름에는 평온한 수영장 같습니다.";
        } else if ("zh".equals(lang)) {
            if ("sandy-beach".equals(beachId)) return "从停车场看很诱人。除非您是经验丰富的冲浪高手，否则请在沙滩上观赏。";
            if ("kailua-beach".equals(beachId)) return "礁石内部通常较为平静，但在划皮划艇前请检查风向。";
            if ("waimea-bay".equals(beachId)) return "请敬畏冬季浪况。夏季时，海湾宛如平静的游泳池。";
        }
        return "Looks inviting from the parking lot. Watch from the sand unless you are an experienced bodysurfer.";
    }

    public static String getLocalizedGoodFor(Context context, BeachLocation beach) {
        String lang = getLanguage(context);
        String goodStr = "Good for";
        String notGoodStr = "Not good for";
        if ("es".equals(lang)) {
            goodStr = "Bueno para";
            notGoodStr = "No recomendado para";
        } else if ("fr".equals(lang)) {
            goodStr = "Recommandé pour";
            notGoodStr = "Non recommandé pour";
        } else if ("ja".equals(lang)) {
            goodStr = "おすすめ";
            notGoodStr = "おすすめしません";
        } else if ("ko".equals(lang)) {
            goodStr = "추천 활동";
            notGoodStr = "비추천 활동";
        } else if ("zh".equals(lang)) {
            goodStr = "适合";
            notGoodStr = "不适合";
        }
        return "• " + goodStr + ": " + String.join(", ", beach.getGoodFor()) +
                "\n• " + notGoodStr + ": " + String.join(", ", beach.getNotGoodFor());
    }

    public static String getLocalizedAmenities(Context context, BeachLocation beach) {
        String lang = getLanguage(context);
        String amStr = "Amenities";
        if ("es".equals(lang)) amStr = "Servicios";
        else if ("fr".equals(lang)) amStr = "Équipements";
        else if ("ja".equals(lang)) amStr = "設備";
        else if ("ko".equals(lang)) amStr = "편의시설";
        else if ("zh".equals(lang)) amStr = "设施";

        return "• " + amStr + ": " + String.join(", ", beach.getAmenities());
    }

    public static String getLocalizedDescription(Context context, BeachLocation beach) {
        String lang = getLanguage(context);
        String beachId = beach.getId();

        if ("es".equals(lang)) {
            String desc = getSpanishDescription(beachId);
            if (desc != null) return desc;
            return "Una hermosa playa situada a lo largo de la " + getSpanishShore(beach.getShoreline()) + " de Oʻahu. Las condiciones de natación varían según el oleaje y se recomienda precaución. Es popular para la relajación y la recreación local.";
        } else if ("fr".equals(lang)) {
            String desc = getFrenchDescription(beachId);
            if (desc != null) return desc;
            return "Une belle plage située le long de la " + getFrenchShore(beach.getShoreline()) + " d'Oahu. Les conditions de baignade varient selon les vagues et la prudence est recommandée. Elle est populaire pour la détente et les loisirs locaux.";
        } else if ("ja".equals(lang)) {
            String desc = getJapaneseDescription(beachId);
            if (desc != null) return desc;
            return "オアフ島の" + getJapaneseShore(beach.getShoreline()) + "に位置する美しいビーチです。遊泳コンディションは波の状況により異なるため注意が必要です。地元のレジャーやリラクゼーションに人気があります。";
        } else if ("ko".equals(lang)) {
            String desc = getKoreanDescription(beachId);
            if (desc != null) return desc;
            return "오아후섬의 " + getKoreanShore(beach.getShoreline()) + "에 위치한 아름다운 해변입니다. 파도 상태에 따라 수영 환경이 다르므로 주의가 필요합니다. 현지 휴양 및 여가 활동으로 인기가 높습니다.";
        } else if ("zh".equals(lang)) {
            String desc = getChineseDescription(beachId);
            if (desc != null) return desc;
            return "位于瓦胡岛" + getChineseShore(beach.getShoreline()) + "的美丽海滩。游泳条件根据海浪状况而异，建议注意安全。这里是当地休闲放松的热门地点。";
        }
        return beach.getDescription();
    }

    private static String getSpanishDescription(String beachId) {
        if ("royal-hawaiian-beach".equals(beachId)) return "Una icónica playa en el centro de Waikīkī frente al histórico Hotel Royal Hawaiian con vistas panorámicas hacia Diamond Head. En general es segura para nadar y aprender a surfear bajo la supervisión de salvavidas a pesar de las multitudes. Es famosa mundialmente como la cuna del surf moderno y la cultura de canoas de doble casco.";
        if ("waimea-bay".equals(beachId)) return "Una bahía de fama mundial en forma de anfiteatro con arena dorada y aguas cristalinas. Se transforma de una piscina tranquila y segura para nadar en verano a un sitio de olas gigantes y peligrosas en invierno. Es internacionalmente legendaria por albergar campeonatos de surf de grandes olas.";
        if ("sandy-beach".equals(beachId)) return "Una playa de arena dorada expuesta a fuertes vientos alisios del sureste y potentes olas entrantes. Es notoriamente peligrosa para nadar debido a violentas rompientes de orilla que causan frecuentes lesiones espinales. Es mundialmente reconocida como la playa de bodysurf más agresiva de Oʻahu.";
        if ("pokai-bay".equals(beachId)) return "Una bahía tranquila y naturalmente protegida por un rompeolas en alta mar cerca del puerto de Waiʻanae. Se considera ampliamente como la playa para nadar más segura de toda la costa de sotavento. Es famosa como un refugio familiar para practicar paddleboard, kayak y natación segura.";
        if ("sunset-beach".equals(beachId)) return "Una amplia costa de arena en la costa norte conocida por sus enormes olas de invierno y canales submarinos cambiantes. Nadar es seguro en verano, pero extremadamente peligroso en invierno. Es internacionalmente célebre como una parada vital en la prestigiosa Vans Triple Crown of Surfing.";
        return null;
    }

    private static String getFrenchDescription(String beachId) {
        if ("royal-hawaiian-beach".equals(beachId)) return "Une plage emblématique du centre de Waikīkī devant l'historique Hôtel Royal Hawaiian avec des vues panoramiques sur Diamond Head. Elle est généralement sûre pour la baignade et l'initiation au surf sous surveillance malgré la foule. Elle est mondialement connue comme le berceau du surf moderne.";
        if ("waimea-bay".equals(beachId)) return "Une baie en amphithéâtre de renommée mondiale avec du sable doré et des eaux cristallines. Elle se transforme d'une piscine calme et sûre en été en un site de vagues géantes et dangereuses en hiver. Elle est mondialement légendaire pour ses championnats de surf.";
        if ("sandy-beach".equals(beachId)) return "Une plage de sable doré exposée aux alizés du sud-est et à de puissantes houles. Elle est notoirement dangereuse pour la baignade en raison de vagues de bord violentes causant de fréquentes blessures. Elle est mondialement reconnue comme le site de bodysurf le plus agressive d'Oahu.";
        if ("pokai-bay".equals(beachId)) return "Une baie calme et naturellement abritée par une digue au large près du port de Waiʻanae. Elle est largement considérée comme la plus sûre de toute la côte sous le vent. Elle est célèbre comme havre familial pour le paddle et la baignade.";
        if ("sunset-beach".equals(beachId)) return "Une large côte de sable sur la côte nord connue pour ses vagues massives en hiver et ses chenaux sous-marins changeants. La baignade est sûre en été mais extrêmement dangereuse en hiver. Elle est internationalement célèbre pour la Vans Triple Crown of Surfing.";
        return null;
    }

    private static String getJapaneseDescription(String beachId) {
        if ("royal-hawaiian-beach".equals(beachId)) return "歴史的なロイヤル・ハワイアン・ホテルの前方に広がる、ダイヤモンドヘッドの絶景を望むワイキキ中心部の象徴的なビーチです。多くの観光客でにぎわいますが、ライフガードの監視下で遊泳やサーフィンを楽しむことができます。近代サーフィンとアウトリガーカヌー文化の発祥地として世界的に有名です。";
        if ("waimea-bay".equals(beachId)) return "黄金色の砂浜と透き通った海が広がる、円形劇場のような世界的に有名な湾です。夏には穏やかで安全なプールのようになりますが、冬には巨大で危険な大波が押し寄せます。「ザ・エディ」などのビッグウェーブ・サーフィン大会の開催地として伝説的な場所です。";
        if ("sandy-beach".equals(beachId)) return "南東からの強い貿易風と強力なうねりにさらされる、黄金色の砂浜が広がるビーチです。強烈なショアブレイクのため遊泳には非常に危険で、脊髄損傷事故が多発しています。オアフ島で最も激しいボディサーフィンができるビーチとして世界的によく知られています。";
        if ("pokai-bay".equals(beachId)) return "ワイアナエ港近くの沖合の防波堤によって自然に守られた、穏やかで静かな湾です。リーワード海岸全体で最も安全な遊泳ビーチとして広く知られています。パドルボードやカヤック、安全な海水浴を楽しむ家族連れの楽園として有名です。";
        if ("sunset-beach".equals(beachId)) return "冬の巨大な波と複雑な水中チャネルで知られる、ノースショアの広大な砂浜の海岸です。夏には安全に泳げますが、冬には非常に危険です。名高い「ヴァンズ・トリプル・クラウン・オブ・サーフィン」の重要な会場として世界的に有名です。";
        return null;
    }

    private static String getKoreanDescription(String beachId) {
        if ("royal-hawaiian-beach".equals(beachId)) return "역사적인 로얄 하와이안 호텔 앞에 펼쳐진 다이아몬드 헤드 전망의 상징적인 와이키키 중심부 해변입니다. 관광객이 많지만 라이프가드의 감독 아래 수영과 서핑을 안전하게 즐길 수 있습니다. 현대 서핑과 아웃리거 카누 문화의 발상지로 세계적으로 유명합니다.";
        if ("waimea-bay".equals(beachId)) return "황금빛 모래사장과 수정처럼 맑은 물이 어우러진 세계적으로 유명한 원형 만입니다. 여름에는 안전하고 평온한 수영장 같지만 겨울에는 거대하고 위험한 파도가 몰아칩니다. 빅웨이브 서핑 대회의 전설적인 개최지로 유명합니다.";
        if ("sandy-beach".equals(beachId)) return "남동쪽 무역풍과 강력한 파도에 노출된 황금빛 모래 해변입니다. 강력한 쇼어브레이크로 인해 수영하기에 매우 위험하며 척추 부상이 자주 발생합니다. 오아후에서 가장 격렬한 바디서핑 해변으로 세계적으로 유명합니다.";
        if ("pokai-bay".equals(beachId)) return "와이아나에 항구 근처의 해상 방파제로 자연 보호를 받는 차분하고 아늑한 만입니다. 리워드 해안 전체에서 가장 안전한 수영 해변으로 널리 알려져 있습니다. 패들보드, 카약, 안전한 해수욕을 즐기는 가족 단위 피서지로 유명합니다.";
        if ("sunset-beach".equals(beachId)) return "겨울철 거대한 파도와 수중 채널로 유명한 노스쇼어의 넓은 모래 해안입니다. 여름에는 수영하기 안전하지만 겨울에는 매우 위험합니다. 저명한 밴스 트리플 크라운 서핑 대회의 중요한 개최지로 국제적으로 유명합니다.";
        return null;
    }

    private static String getChineseDescription(String beachId) {
        if ("royal-hawaiian-beach".equals(beachId)) return "位于历史悠久的皇家夏威夷酒店前方、可眺望钻石山美景的威基基中心标志性海滩。尽管游客较多，但在救生员监督下通常适合游泳和冲浪。这里作为现代冲浪和独木舟文化的发源地而闻名于世。";
        if ("waimea-bay".equals(beachId)) return "拥有金色沙滩和清澈海水的世界著名圆形海湾。夏季风平浪静、是安全的游泳池，而冬季则会涌起巨大而危险的巨浪。作为举办顶级大浪冲浪比赛的传奇场地而闻名于世。";
        if ("sandy-beach".equals(beachId)) return "暴露在强烈的东南信风和强劲涌浪中的金色沙滩。由于强烈的岸边碎浪会导致严重的颈椎受伤，此处游泳极其危险。被公认为瓦胡岛最具挑战性的冲浪和身体冲浪海滩之一。";
        if ("pokai-bay".equals(beachId)) return "位于怀阿纳海港附近、由离岸防波堤天然庇护的平静海湾。被广泛认为是整个背风海岸最安全的游泳海滩。作为适合家庭进行桨板、皮划艇和安全游泳的避风港而闻名。";
        if ("sunset-beach".equals(beachId)) return "以冬季巨浪和多变水下通道闻名的北海岸宽阔沙滩。夏季游泳安全，但冬季极其危险。作为著名的冲浪三冠王赛事的关键举办地而享誉国际。";
        return null;
    }

    private static String getSpanishShore(String shoreline) {
        if (shoreline.contains("South")) return "costa sur";
        if (shoreline.contains("Southeast")) return "costa sureste";
        if (shoreline.contains("Windward")) return "costa de barlovento";
        if (shoreline.contains("North")) return "costa norte";
        if (shoreline.contains("Leeward")) return "costa de sotavento";
        return "costa";
    }

    private static String getFrenchShore(String shoreline) {
        if (shoreline.contains("South")) return "côte sud";
        if (shoreline.contains("Southeast")) return "côte sud-est";
        if (shoreline.contains("Windward")) return "côte au vent";
        if (shoreline.contains("North")) return "côte nord";
        if (shoreline.contains("Leeward")) return "côte sous le vent";
        return "côte";
    }

    private static String getJapaneseShore(String shoreline) {
        if (shoreline.contains("South")) return "南海岸";
        if (shoreline.contains("Southeast")) return "南東海岸";
        if (shoreline.contains("Windward")) return "風上海岸";
        if (shoreline.contains("North")) return "北海岸";
        if (shoreline.contains("Leeward")) return "西海岸";
        return "海岸";
    }

    private static String getKoreanShore(String shoreline) {
        if (shoreline.contains("South")) return "남쪽 해안";
        if (shoreline.contains("Southeast")) return "남동쪽 해안";
        if (shoreline.contains("Windward")) return "윈드워드 해안";
        if (shoreline.contains("North")) return "노스쇼어 북쪽 해안";
        if (shoreline.contains("Leeward")) return "리워드 서쪽 해안";
        return "해안";
    }

    private static String getChineseShore(String shoreline) {
        if (shoreline.contains("South")) return "南部海岸";
        if (shoreline.contains("Southeast")) return "东南部海岸";
        if (shoreline.contains("Windward")) return "迎风海岸";
        if (shoreline.contains("North")) return "北部海岸";
        if (shoreline.contains("Leeward")) return "背风海岸";
        return "海岸";
    }
}
