plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.20"
}
    android {
        namespace = "com.example.zeptoapp"
        compileSdk = 35

        defaultConfig {
            applicationId = "com.example.zeptoapp"
            minSdk = 24
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

        // For Media 3
        implementation("androidx.media3:media3-exoplayer:1.6.1")
        //implementation("androidx.media3:media3-ui-compose:1.6.1")
        implementation("androidx.media3:media3-ui:1.6.1")

        //For google font
        implementation("androidx.compose.ui:ui-text-google-fonts:1.8.2")

        //Coil For Image Loading
        implementation("io.coil-kt.coil3:coil-compose:3.2.0")
        implementation("io.coil-kt.coil3:coil-gif:3.2.0")

        //For Navigation
        implementation("androidx.navigation:navigation-compose:2.9.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

         //dagger hilt
        implementation("com.google.dagger:hilt-android:2.56.1")
        ksp("com.google.dagger:hilt-android-compiler:2.56.1")
        implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

        //Lifecycle components
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")

        //For Icons
        implementation("androidx.compose.material:material-icons-extended-android:1.7.8")

        //
        implementation ("com.google.android.gms:play-services-location:21.3.0")
        implementation ("com.google.accompanist:accompanist-permissions:0.37.3")

        implementation("com.squareup.retrofit2:retrofit:3.0.0")
        implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    }