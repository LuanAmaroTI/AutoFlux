package com.autoeletricapenha.autoflux.model

// Importa a anotação para ignorar campos desconhecidos no Firestore
import com.google.firebase.firestore.IgnoreExtraProperties

// Define que esta classe representa um documento no Firestore
@IgnoreExtraProperties
data class Movimentacao(
    // ID do usuário que criou a movimentação
    var usuarioId: String = "",
    // Tipo da movimentação: "entrada" ou "despesa"
    var tipo: String = "",
    // Descrição do que foi gasto ou recebido
    var descricao: String = "",
    // Valor monetário da movimentação
    var valor: Double = 0.0,
    // Data da movimentação no formato AAAA-MM-DD
    var data: String = ""
) {
    // Construtor vazio necessário para o Firebase Firestore converter o documento em objeto
    constructor() : this("", "", "", 0.0, "")
}