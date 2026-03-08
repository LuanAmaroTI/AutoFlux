package com.autoeletricapenha.autoflux

// Importa bibliotecas do Android e Firebase
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.autoeletricapenha.autoflux.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Declara a classe para a tela principal
class MainActivity : AppCompatActivity() {

    // Declaração do ViewBinding
    private lateinit var binding: ActivityMainBinding
    // Declaração do Firebase Auth e Firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // Método chamado ao criar a tela
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa a classe pai
        super.onCreate(savedInstanceState)
        
        // Inicializa o ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Define o layout XML para esta tela
        setContentView(binding.root)

        // Define o título do app na barra superior (ActionBar)
        supportActionBar?.title = "AutoFlux"

        // Inicializa o Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Botão para ir para a tela de Nova Movimentação
        binding.btnNovaMovimentacao.setOnClickListener {
            // Cria a intenção para abrir a NovaMovimentacaoActivity
            startActivity(Intent(this, NovaMovimentacaoActivity::class.java))
        }

        // Botão para ir para a tela de Histórico
        binding.btnHistorico.setOnClickListener {
            // Cria a intenção para abrir a HistoricoActivity
            startActivity(Intent(this, HistoricoActivity::class.java))
        }

        // Botão para sair do aplicativo (Logout)
        binding.btnLogout.setOnClickListener {
            // Realiza o logout no Firebase Auth
            auth.signOut()
            // Interação ao sair: Toast de feedback
            Toast.makeText(this, "Logout realizado com sucesso!", Toast.LENGTH_SHORT).show()
            // Volta para a tela de login
            startActivity(Intent(this, LoginActivity::class.java))
            // Fecha a tela atual
            finish()
        }
    }

    // Método chamado quando a tela volta a ser exibida (foco volta para cá)
    override fun onResume() {
        // Chama implementação da super classe
        super.onResume()
        // Atualiza o saldo total sempre que voltar para esta tela
        atualizarSaldo()
    }

    // Função para calcular e exibir o saldo atualizado
    private fun atualizarSaldo() {
        // Obtém o ID do usuário logado
        val usuarioId = auth.currentUser?.uid ?: return

        // Consulta as movimentações do usuário no Firestore
        db.collection("movimentacoes")
            .whereEqualTo("usuarioId", usuarioId)
            .get()
            .addOnSuccessListener { result ->
                // Variável para armazenar o saldo total
                var saldoTotal = 0.0
                
                // Percorre todos os documentos retornados
                for (document in result) {
                    // Obtém o valor e o tipo de cada movimentação
                    val valor = document.getDouble("valor") ?: 0.0
                    val tipo = document.getString("tipo") ?: ""

                    // Soma se for entrada, subtrai se for despesa
                    if (tipo == "entrada") {
                        saldoTotal += valor
                    } else {
                        saldoTotal -= valor
                    }
                }
                // Exibe o saldo formatado no TextView
                binding.textSaldo.text = "R$ %.2f".format(saldoTotal)
            }
    }
}