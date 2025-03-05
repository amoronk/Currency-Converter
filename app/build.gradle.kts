import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.amoronk.currencyconverter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.amoronk.currencyconverter"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "FIXER_API_KEY", "\"${getApiKey()}\"")
        buildConfigField("String", "FIXER_BASE_URL", "\"https://data.fixer.io/api/\"")
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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.compose.hilt.navigation)


    implementation(libs.compose.material.icons.extended)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)

    // Timber
    implementation(libs.timber)

    // Viewmodel
    implementation(libs.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Dagger - Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Charts
    implementation(libs.mpandroidchart)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.kotlin.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}


fun getApiKey(): String {
    val propertiesFile = project.rootProject.file("apikey.properties")
    if (propertiesFile.exists()) {
        val properties = Properties()
        properties.load(propertiesFile.inputStream())
        return properties.getProperty("FIXER_API_KEY", "")
    } else {
        return ""
    }
}