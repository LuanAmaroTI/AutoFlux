package com.autoeletricapenha.autoflux

// Importa bibliotecas do Android e Firebase
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.autoeletricapenha.autoflux.adapter.MovimentacaoAdapter
import com.autoeletricapenha.autoflux.databinding.ActivityHistoricoBinding
import com.autoeletricapenha.autoflux.model.Movimentacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// Declara a classe para a tela de histórico de movimentações
class HistoricoActivity : AppCompatActivity() {

    // Declaração do ViewBinding
    private lateinit var binding: ActivityHistoricoBinding
    // Declaração do Firebase Auth e Firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    // Lista para armazenar as movimentações carregadas do banco
    private val listaMovimentacoes = mutableListOf<Movimentacao>()
    // Adapter para gerenciar a exibição da lista no RecyclerView
    private lateinit var adapter: MovimentacaoAdapter

    // Método chamado ao criar a tela
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa a classe pai
        super.onCreate(savedInstanceState)
        
        // Inicializa o ViewBinding
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        // Define o layout XML para esta tela
        setContentView(binding.root)

        // Habilita o ícone de voltar na Toolbar conforme solicitado
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Histórico"

        // Inicializa as instâncias do Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inicializa o Adapter com a lista vazia
        adapter = MovimentacaoAdapter(listaMovimentacoes)
        
        // Configura o RecyclerView com LayoutManager e o Adapter
        binding.recyclerViewHistorico.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewHistorico.adapter = adapter

        // Chama a função para carregar os dados do Firestore
        carregarHistorico()
    }

    // Trata o clique no botão de voltar da Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Fecha a tela e volta para a anterior
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Função para buscar os dados no Firebase Firestore
    private fun carregarHistorico() {
        val usuarioId = auth.currentUser?.uid ?: return

        db.collection("movimentacoes")
            .whereEqualTo("usuarioId", usuarioId)
            .orderBy("data", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                listaMovimentacoes.clear()
                for (document in result) {
                    val movimentacao = document.toObject(Movimentacao::class.java)
                    listaMovimentacoes.add(movimentacao)
                }
                adapter.notifyDataSetChanged()
            }
    }
}