plugins {
    id("org.jetbrains.kotlin.js") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
}

version = "1.0-SNAPSHOT"

repositories {
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains:kotlin-react:17.0.1-pre.148-kotlin-1.4.21")
    implementation("org.jetbrains:kotlin-react-dom:17.0.1-pre.148-kotlin-1.4.21")

    implementation("org.jetbrains:kotlin-styled:5.2.1-pre.148-kotlin-1.4.21")

    val ktorVersion= "1.5.3"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-js:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
}

kotlin {
    js{
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
}