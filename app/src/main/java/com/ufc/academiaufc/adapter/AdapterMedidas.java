package com.ufc.academiaufc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.model.Medida;
import com.ufc.academiaufc.model.Treino;

import java.util.List;

public class AdapterMedidas extends RecyclerView.Adapter<AdapterMedidas.MyViewHolder> {

    private List<Medida> medidaList;
    private Onclick onclick;


    public AdapterMedidas(List<Medida> medidaList, Onclick onclick) {
        this.medidaList = medidaList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medida, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Medida medida = medidaList.get(position);
        holder.text_mes.setText(medida.getMes());
        holder.text_ano.setText(medida.getAno());
        holder.text_objetivo.setText(medida.getObjetivo());
        holder.text_nome_user.setText(medida.getNomeUser());

        holder.itemView.setOnClickListener(view -> onclick.onClickListener(medida));

    }

    @Override
    public int getItemCount() {
        return medidaList.size();
    }

    public interface Onclick {
        public void onClickListener(Medida medida);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_mes, text_ano, text_nome_user, text_objetivo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_mes = itemView.findViewById(R.id.text_mes);
            text_objetivo = itemView.findViewById(R.id.text_objetivo);
            text_ano = itemView.findViewById(R.id.text_ano);
            text_nome_user = itemView.findViewById(R.id.text_nome_user);



        }
    }
}
