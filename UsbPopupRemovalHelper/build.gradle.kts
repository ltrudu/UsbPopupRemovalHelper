plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.zebra.usbpopupremovalhelper"
    compileSdk = 33

    defaultConfig {
        minSdk = 30

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

//publishing {
//    publications {
//        // Creates a Maven publication called "release".
//        create<MavenPublication>("release") {
//            // Applies the component for the release build variant.
//            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
//            // You can then customize attributes of the publication as shown below.
//            groupId = "com.zebra.usbpopupremovalhelper"
//            artifactId = "usbpopupremovalhelper"
//            version = "0.1.1"
//        }
//    }
//    repositories {
//        mavenLocal()
//    }
//}
//

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    compileOnly ("com.symbol:emdk:9.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.github.jitpack:gradle-simple:1.0")

}