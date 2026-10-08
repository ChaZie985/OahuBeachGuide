plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.congressionalapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.congressionalapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["MAPS_API_KEY"] = "AIzaSyDor3sK3M9hgSqIaeNwm_YNkzWPLcMUIhg"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("androidx.work:work-runtime:2.9.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    implementation("com.google.android.gms:play-services-maps:18.2.0")
}