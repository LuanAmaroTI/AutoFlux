package com.autoeletricapenha.autoflux.adapter

// Importa classes básicas do Android e UI
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.autoeletricapenha.autoflux.R
import com.autoeletricapenha.autoflux.databinding.ItemMovimentacaoBinding
import com.autoeletricapenha.autoflux.model.Movimentacao

// Define o Adapter para gerenciar a lista de movimentações no RecyclerView
class MovimentacaoAdapter(
    // Recebe a lista de movimentações para exibição
    private val lista: List<Movimentacao>
) : RecyclerView.Adapter<MovimentacaoAdapter.MyViewHolder>() {

    // Classe ViewHolder para conter as views de cada item da lista
    class MyViewHolder(val binding: ItemMovimentacaoBinding) : RecyclerView.ViewHolder(binding.root)

    // Cria uma nova instância do ViewHolder (novo item no layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Infla o layout item_movimentacao utilizando ViewBinding
        val binding = ItemMovimentacaoBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        // Retorna o ViewHolder criado
        return MyViewHolder(binding)
    }

    // Vincula os dados do objeto Movimentacao aos elementos visuais (TextViews)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Obtém o objeto da movimentação na posição atual
        val movimentacao = lista[position]
        
        // Formata o valor com o sinal de acordo com o tipo
        if (movimentacao.tipo == "entrada") {
            // Define o texto como positivo (+) e cor verde
            holder.binding.textItemValor.text = "+ R$ ${movimentacao.valor}"
            holder.binding.textItemValor.setTextColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_dark)
            )
        } else {
            // Define o texto como negativo (-) e cor vermelha
            holder.binding.textItemValor.text = "- R$ ${movimentacao.valor}"
            holder.binding.textItemValor.setTextColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark)
            )
        }

        // Define a descrição no campo correspondente
        holder.binding.textItemDescricao.text = movimentacao.descricao
        // Define a data no campo correspondente
        holder.binding.textItemData.text = movimentacao.data
    }

    // Retorna a quantidade total de itens na lista
    override fun getItemCount(): Int = lista.size
}