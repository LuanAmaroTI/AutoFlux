// Arquivo de build de nível superior onde você pode adicionar opções de configuração comuns a todos os subprojetos/módulos.
plugins {
    // Plugin do Aplicativo Android
    alias(libs.plugins.android.application) apply false
    // Plugin do Kotlin Android
    alias(libs.plugins.kotlin.android) apply false
    // Plugin do Kotlin Compose (embora utilizaremos XML, o projeto foi criado com suporte a Compose)
    alias(libs.plugins.kotlin.compose) apply false
    // Plugin do Google Services para Firebase
    alias(libs.plugins.google.services) apply false
}