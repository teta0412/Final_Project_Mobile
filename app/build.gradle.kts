plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "nguyenanhduc.mobileapp.final_project_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "nguyenanhduc.mobileapp.final_project_mobile"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources{
            excludes.add("META-INF/native-image/native-image.properties")
            excludes.add("META-INF/native-image/reflect-config.json")
        }
    }
}

dependencies {
    implementation("com.google.android.material:material:1.11.0")
    implementation ("org.mongodb:mongodb-driver-sync:5.2.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}