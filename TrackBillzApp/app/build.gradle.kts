plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.trackbillz"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.trackbillz"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // View Model Library
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Material Icons Extended Lib
    implementation(libs.androidx.material.icons.extended)

    // Dagger Hilt (DI)
    implementation (libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.androidx.hilt.compiler)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // KSP dependency for Room compiler
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Image Loading
    implementation(libs.coil.compose)

    // Android Material2
    implementation(libs.androidx.material)

    // KOIN
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.coroutines)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.annotation)
    ksp(libs.koin.ksp.compiler)

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")

    //Work Manager
    implementation(libs.androidx.work.runtime.ktx)
    implementation("io.insert-koin:koin-androidx-workmanager:3.5.0")


    // Local Unit Tests
    testImplementation (libs.hamcrest.all)
    testImplementation (libs.core.testing)
    testImplementation (libs.robolectric)
    testImplementation (libs.jetbrains.kotlinx.coroutines.test)
    testImplementation (libs.truth)
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockk.mockk)

    // Instrumented Unit Tests (Android Tests)
    androidTestImplementation (libs.androidx.core)
    androidTestImplementation (libs.jetbrains.kotlinx.coroutines.test)
    androidTestImplementation (libs.core.testing)
    androidTestImplementation (libs.truth)
    androidTestImplementation (libs.mockito.core)
    androidTestImplementation(libs.mockk.mockk)

    //Excel
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    // Biometric
    implementation("androidx.biometric:biometric:1.1.0")

    // Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

}