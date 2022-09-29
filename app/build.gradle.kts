import cn.govast.plugin.version.*

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("cn.govast.plugin.version")
}

android {
    namespace = "cn.govast.gmusic"
    compileSdk = Version.compile_sdk_version

    defaultConfig {
        applicationId = "com.gcode.gmusic"
        minSdk = Version.min_sdk_version
        targetSdk = Version.target_sdk_version
        versionCode = Version.version_code
        versionName = Version.version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),"proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    androidTestImplementation(AndroidX.espresso_core)
    androidTestImplementation(AndroidX.junit)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar","*.aar"))))
    implementation(AndroidX.activity_ktx)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.cardview)
    implementation(AndroidX.constraintlayout)
    implementation(AndroidX.core_ktx)
    implementation(AndroidX.lifecycle_livedata_ktx)
    implementation(AndroidX.lifecycle_runtime_ktx)
    implementation(AndroidX.lifecycle_viewmodel_ktx)
    implementation(AndroidX.nav_dynamic_features_fragment)
    implementation(AndroidX.nav_fragment_ktx)
    implementation(AndroidX.nav_ui_ktx)
    implementation(AndroidX.preference_ktx)
    implementation(AndroidX.recyclerview)
    implementation(AndroidX.viewpager2)
    implementation(Squareup.okhttp3)
    implementation(Squareup.retrofit2)
    implementation(Squareup.retrofit2_adapter_rxjava3)
    implementation(Squareup.retrofit2_converter_gson)
    implementation(Google.material)
    implementation(Libraries.animatedbottombar)
    implementation(Libraries.permissionx)
    implementation(Libraries.recyclerview_animators)
    implementation(Libraries.vastadapter)
    implementation(Libraries.zxing)
    testImplementation(Libraries.junit)
}