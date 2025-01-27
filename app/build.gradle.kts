plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //id("org.web3j")
}

android {
    namespace = "com.link.stinkies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.link.stinkies"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    /*packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

     */
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.runtime:runtime-livedata")

    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.compose.animation:animation-graphics-android:1.5.4")
    implementation("androidx.databinding:databinding-runtime:8.1.3")
    implementation("androidx.media3:media3-database:1.2.0")
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.5")
    implementation("androidx.compose.foundation:foundation-android:1.5.4")
    implementation("androidx.compose.foundation:foundation-layout-android:1.5.4")
    implementation("com.google.ar.sceneform:filament-android:1.17.1")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.compose.material:material:1.4.0-beta01")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.animation:animation-graphics:1.3.3")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("com.coinbase:coinbase-wallet-sdk:1.0.3")

    implementation("androidx.browser:browser:1.5.0")

    implementation("com.patrykandpatrick.vico:compose:1.12.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.12.0")
    implementation("com.patrykandpatrick.vico:core:1.12.0")

    implementation("org.web3j:core:4.9.1")

    // For the view system.
    implementation("com.patrykandpatrick.vico:views:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}