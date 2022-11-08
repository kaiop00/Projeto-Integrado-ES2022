package com.ufc.academiaufc.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.model.Treino;

import java.util.List;

public class AdapterTreinos extends RecyclerView.Adapter<AdapterTreinos.MyViewHolder> {

    private List<Treino> treinoList;
    private Onclick onclick;


    public AdapterTreinos(List<Treino> treinoList, Onclick onclick) {
        this.treinoList = treinoList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treino, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Treino treino = treinoList.get(position);
        holder.text_titulo.setText(treino.getTitulo());
        holder.text_nivel.setText(treino.getNivel());
        holder.text_nome_user.setText(treino.getNomeUSer());

        if(treino.getNivel().equals("Avançado") || treino.getNivel().equals("avançado")){
            holder.card.setCardBackgroundColor(Color.parseColor("#0066FF"));
        }else if(treino.getNivel().equals("Intermediário") || treino.getNivel().equals("intermediário")){
            holder.card.setCardBackgroundColor(Color.parseColor("#8000FF"));
        }else{
            holder.card.setCardBackgroundColor(Color.parseColor("#00A3FF"));
        }

        holder.itemView.setOnClickListener(view -> onclick.onClickListener(treino));



    }

    @Override
    public int getItemCount() {
        return treinoList.size();
    }

    public interface Onclick {
        public void onClickListener(Treino treino);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_titulo, text_nivel, text_nome_user;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_titulo = itemView.findViewById(R.id.text_titulo_treino);
            text_nivel = itemView.findViewById(R.id.text_nivel);
            text_nome_user = itemView.findViewById(R.id.text_nome_user);
            card = itemView.findViewById(R.id.card_treino);

        }
    }
}
