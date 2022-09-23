import com.gcode.plugin.version.AndroidX
import com.gcode.plugin.version.Libraries
import com.gcode.plugin.version.Version

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.gcode.plugin.version")
}

android {
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
    }

    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar","*.aar"))))
    implementation(AndroidX.lifecycle_viewmodel_ktx)
    implementation(AndroidX.lifecycle_livedata_ktx)
    implementation(AndroidX.lifecycle_runtime_ktx)
    implementation(AndroidX.viewpager2)
    implementation(AndroidX.appcompat)
    implementation(AndroidX.constraintlayout)
    implementation(AndroidX.activity_ktx)
    implementation(AndroidX.preference_ktx)
    testImplementation(Libraries.junit)
    androidTestImplementation(AndroidX.junit)
    androidTestImplementation(AndroidX.espresso_core)
    implementation(AndroidX.recyclerview)
    implementation(AndroidX.cardview)
    implementation(AndroidX.core_ktx)
    implementation(Libraries.animatedbottombar)
    implementation(Libraries.permissionx)
    implementation(Libraries.recyclerview_animators)
    implementation(Libraries.vastadapter)
}