import cn.govast.plugin.version.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("cn.govast.plugin.version")
}

android {
    namespace = "cn.govast.music"
    compileSdk = Version.compile_sdk_version

    defaultConfig {
        minSdk = Version.min_sdk_version
        targetSdk = Version.target_sdk_version

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    implementation(AndroidX.activity_ktx)
    implementation(AndroidX.appcompat)
    implementation(Google.material)
    implementation(Squareup.okhttp3)
    implementation(Squareup.retrofit2)
    implementation(Squareup.retrofit2_adapter_rxjava3)
    implementation(Squareup.retrofit2_converter_gson)
    testImplementation(Libraries.junit)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
}