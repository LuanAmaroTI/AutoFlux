package com.autoeletricapenha.autoflux

// Importa bibliotecas do Android e Firebase
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.autoeletricapenha.autoflux.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

// Declara a classe para a tela de login
class LoginActivity : AppCompatActivity() {

    // Declaração do ViewBinding para acessar os elementos do layout
    private lateinit var binding: ActivityLoginBinding
    // Declaração do FirebaseAuth para gerenciar autenticação
    private lateinit var auth: FirebaseAuth

    // Método chamado quando a tela é carregada
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa a classe pai
        super.onCreate(savedInstanceState)
        
        // Inicializa o ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // Define o layout XML para esta tela
        setContentView(binding.root)

        // Define o título na barra superior
        supportActionBar?.title = "AutoFlux - Login"

        // Inicializa a instância do Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Verifica se o usuário já está logado
        if (auth.currentUser != null) {
            // Se estiver logado, vai direto para a tela principal
            irParaMain()
        }

        // Configura o clique no botão de login
        binding.btnLogin.setOnClickListener {
            // Obtém os valores digitados pelo usuário
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            // Valida se os campos não estão vazios
            if (email.isNotEmpty() && senha.isNotEmpty()) {
                // Tenta realizar o login no Firebase
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { task ->
                        // Verifica se o login foi bem sucedido
                        if (task.isSuccessful) {
                            // Interação ao logar: Mensagem de boas-vindas
                            Toast.makeText(this, "Bem-vindo ao AutoFlux!", Toast.LENGTH_SHORT).show()
                            // Vai para a tela principal
                            irParaMain()
                        } else {
                            // Melhora a mensagem de erro de login
                            val erro = when (task.exception) {
                                is FirebaseAuthInvalidUserException -> "Usuário não cadastrado."
                                is FirebaseAuthInvalidCredentialsException -> "Senha ou e-mail incorretos."
                                else -> "Erro ao realizar login. Tente novamente."
                            }
                            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Mensagem caso falte preencher algo
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o clique no botão de cadastrar
        binding.btnCadastrar.setOnClickListener {
            // Obtém os valores digitados
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            // Valida se os campos não estão vazios
            if (email.isNotEmpty() && senha.isNotEmpty()) {
                // Tenta criar um novo usuário no Firebase
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { task ->
                        // Verifica se o cadastro foi realizado
                        if (task.isSuccessful) {
                            // Interação ao cadastrar: Mensagem de sucesso
                            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                            irParaMain()
                        } else {
                            // Exibe erro amigável caso o cadastro falhe
                            Toast.makeText(this, "Erro ao criar conta. Verifique os dados.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Mensagem de aviso
                Toast.makeText(this, "Preencha email e senha para cadastrar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função auxiliar para mudar de tela
    private fun irParaMain() {
        // Cria a intenção para abrir a MainActivity
        val intent = Intent(this, MainActivity::class.java)
        // Inicia a nova tela
        startActivity(intent)
        // Fecha a tela de login para não voltar ao apertar o botão "voltar"
        finish()
    }
}