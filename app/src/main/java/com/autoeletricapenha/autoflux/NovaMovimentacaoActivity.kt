package com.autoeletricapenha.autoflux

// Importa bibliotecas do Android e Firebase
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.autoeletricapenha.autoflux.databinding.ActivityNovaMovimentacaoBinding
import com.autoeletricapenha.autoflux.model.Movimentacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

// Declara a classe para a tela de registro de nova movimentação
class NovaMovimentacaoActivity : AppCompatActivity() {

    // Declaração do ViewBinding
    private lateinit var binding: ActivityNovaMovimentacaoBinding
    // Declaração do Firebase Auth e Firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // Método chamado ao criar a tela
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa a classe pai
        super.onCreate(savedInstanceState)
        
        // Inicializa o ViewBinding
        binding = ActivityNovaMovimentacaoBinding.inflate(layoutInflater)
        // Define o layout XML para esta tela
        setContentView(binding.root)

        // Habilita o ícone de voltar na Toolbar conforme solicitado
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Nova Movimentação"

        // Inicializa o Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Configura o clique no campo de data para abrir o calendário
        binding.editData.setOnClickListener {
            val calendario = Calendar.getInstance()
            val ano = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val mesFormatado = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                val diaFormatado = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                binding.editData.setText("$year-$mesFormatado-$diaFormatado")
            }, ano, mes, dia)

            datePicker.show()
        }

        // Configura o clique no botão de salvar
        binding.btnSalvar.setOnClickListener {
            salvarDados()
        }
    }

    // Trata o clique no botão de voltar da Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Fecha a tela e volta para a anterior
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Função para salvar as informações no Firebase Firestore
    private fun salvarDados() {
        val usuarioId = auth.currentUser?.uid ?: return
        val descricao = binding.editDescricao.text.toString()
        val valorStr = binding.editValor.text.toString()
        val data = binding.editData.text.toString()
        val tipo = if (binding.radioEntrada.isChecked) "entrada" else "despesa"

        if (descricao.isNotEmpty() && valorStr.isNotEmpty() && data.isNotEmpty()) {
            val valor = valorStr.toDouble()

            val movimentacao = Movimentacao(
                usuarioId = usuarioId,
                tipo = tipo,
                descricao = descricao,
                valor = valor,
                data = data
            )

            // Interação: Desativa o botão para evitar cliques duplos
            binding.btnSalvar.isEnabled = false

            db.collection("movimentacoes")
                .add(movimentacao)
                .addOnSuccessListener {
                    // Interação ao adicionar: Mensagem de sucesso
                    Toast.makeText(this, "Movimentação salva!", Toast.LENGTH_SHORT).show()
                    // Volta automaticamente para a tela principal (finish fecha esta Activity)
                    finish()
                }
                .addOnFailureListener { e ->
                    binding.btnSalvar.isEnabled = true
                    Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }
}