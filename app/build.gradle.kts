/*
 * Copyright 2022 Vast Gui guihy2019@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    implementation(AndroidX.palette)
    implementation(AndroidX.preference_ktx)
    implementation(AndroidX.recyclerview)
    implementation(AndroidX.room_ktx)
    implementation(AndroidX.room_runtime)
    implementation(AndroidX.viewpager2)
    implementation(Google.material)
    implementation(Libraries.animatedbottombar)
    implementation(Libraries.event_bus)
    implementation(Libraries.glide)
    implementation(Libraries.glide_transformations)
    implementation(Libraries.mmkv)
    implementation(Libraries.permissionx)
    implementation(Libraries.progressmanager)
    implementation(Libraries.recyclerview_animators)
    implementation(Libraries.zxing)
    implementation(Squareup.okhttp3)
    implementation(Squareup.retrofit2)
    implementation(Squareup.retrofit2_adapter_rxjava3)
    implementation(Squareup.retrofit2_converter_gson)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar","*.aar"))))
    implementation(project(":city"))
    implementation(project(":music"))
    kapt(AndroidX.room_compiler)
    testImplementation(Libraries.junit)
}