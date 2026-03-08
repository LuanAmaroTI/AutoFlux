plugins {
    // Plugin do Aplicativo Android
    alias(libs.plugins.android.application)
    // Plugin do Kotlin Android
    alias(libs.plugins.kotlin.android)
    // Plugin do Google Services
    alias(libs.plugins.google.services)
}

android {
    // Namespace do pacote do aplicativo
    namespace = "com.autoeletricapenha.autoflux"
    // Versão do SDK para compilação
    compileSdk = 35

    defaultConfig {
        // ID único do aplicativo
        applicationId = "com.autoeletricapenha.autoflux"
        // Versão mínima do Android suportada (API 26 conforme PRD)
        minSdk = 26
        // Versão do SDK alvo
        targetSdk = 35
        // Código da versão do app
        versionCode = 1
        // Nome da versão do app
        versionName = "1.0"

        // Runner de testes de instrumentação
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Desativa a minificação para simplificar o projeto acadêmico
            isMinifyEnabled = false
            // Arquivos de regras do Proguard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Compatibilidade com Java 11
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        // Opções do compilador Kotlin
        jvmTarget = "11"
    }
    buildFeatures {
        // Ativa o ViewBinding para facilitar o acesso aos elementos do layout
        viewBinding = true
    }
}

dependencies {
    // Bibliotecas principais do Android e UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // Plataforma Firebase (BOM) para gerenciar versões
    implementation(platform(libs.firebase.bom))
    // Autenticação Firebase
    implementation(libs.firebase.auth.ktx)
    // Firestore (Banco de Dados em tempo real)
    implementation(libs.firebase.firestore.ktx)

    // Testes Unitários
    testImplementation(libs.junit)
    // Testes de Instrumentação
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}