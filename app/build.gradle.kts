import cn.govast.plugin.version.*

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("cn.govast.plugin.version")
}

android {
    namespace = "cn.govast.vmusic"
    compileSdk = Version.compile_sdk_version

    defaultConfig {
        applicationId = "cn.govast.vmusic"
        minSdk = Version.min_sdk_version
        targetSdk = Version.target_sdk_version
        versionCode = 1
        versionName = "1.0.22.20221030"

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
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    androidTestImplementation(AndroidX.espresso_core)
    androidTestImplementation(AndroidX.junit)
    annotationProcessor(AndroidX.room_compiler)
    annotationProcessor(Libraries.glide_compiler)
    implementation(project(":Music"))
    implementation(AndroidX.activity_ktx)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.cardview)
    implementation(AndroidX.constraintlayout)
    implementation(AndroidX.core_ktx)
    implementation(AndroidX.core_splashscreen)
    implementation(AndroidX.lifecycle_livedata_ktx)
    implementation(AndroidX.lifecycle_runtime_ktx)
    implementation(AndroidX.lifecycle_viewmodel_ktx)
    implementation(AndroidX.nav_dynamic_features_fragment)
    implementation(AndroidX.nav_fragment_ktx)
    implementation(AndroidX.nav_ui_ktx)
    implementation(AndroidX.preference_ktx)
    implementation(AndroidX.recyclerview)
    implementation(AndroidX.room_ktx)
    implementation(AndroidX.room_runtime)
    implementation(AndroidX.viewpager2)
    implementation(Google.material)
    implementation(Libraries.animatedbottombar)
    implementation(Libraries.event_bus)
    implementation(Libraries.glide)
    implementation(Libraries.mmkv)
    implementation(Libraries.permissionx)
    implementation(Libraries.recyclerview_animators)
    implementation(Libraries.zxing)
    implementation(Squareup.okhttp3)
    implementation(Squareup.retrofit2)
    implementation(Squareup.retrofit2_adapter_rxjava3)
    implementation(Squareup.retrofit2_converter_gson)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar","*.aar"))))
    kapt(AndroidX.room_compiler)
    testImplementation(Libraries.junit)
}