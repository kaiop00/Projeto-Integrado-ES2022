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
import com.ufc.academiaufc.model.Medida;
import com.ufc.academiaufc.model.Mensagem;

import java.util.List;

public class AdapterMensagens extends RecyclerView.Adapter<AdapterMensagens.MyViewHolder> {

    private List<Mensagem> mensagemList;
    private Onclick onclick;


    public AdapterMensagens(List<Mensagem> mensagemList, Onclick onclick) {
        this.mensagemList = mensagemList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Mensagem mensagem = mensagemList.get(position);
        holder.text_titulo_msg.setText(mensagem.getTitulo());
        holder.text_situacao.setText(mensagem.getSituacao());
        holder.text_dia.setText(mensagem.getDia());
        holder.text_hora_inicio.setText("Abre ás "+ mensagem.getHoraInicio());
        holder.text_hora_final.setText("Fecha ás " + mensagem.getHoraFinal());


        if(mensagem.getSituacao().equals("Aberto")){
            holder.card_mensagem.setCardBackgroundColor(Color.parseColor("#0066FF"));
        }else{
            holder.text_hora_inicio.setText("");
            holder.text_hora_final.setText("");
            holder.card_mensagem.setCardBackgroundColor(Color.parseColor("#FF005C"));
        }

        holder.itemView.setOnClickListener(view -> onclick.onClickListener(mensagem));

    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    public interface Onclick {
        public void onClickListener(Mensagem mensagem);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_titulo_msg, text_situacao, text_dia, text_hora_inicio, text_hora_final;
        CardView card_mensagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_titulo_msg = itemView.findViewById(R.id.text_titulo_msg);
            text_situacao = itemView.findViewById(R.id.text_situacao);
            text_dia = itemView.findViewById(R.id.text_dia);
            text_hora_inicio = itemView.findViewById(R.id.text_hora_inicio);
            text_hora_final = itemView.findViewById(R.id.text_hora_final);
            card_mensagem = itemView.findViewById(R.id.card_mensagem);


        }
    }
}
