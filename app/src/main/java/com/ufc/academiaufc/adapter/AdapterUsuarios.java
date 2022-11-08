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
import com.ufc.academiaufc.model.Usuario;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.MyViewHolder> {

    private List<Usuario> usuarioList;
    private Onclick onclick;


    public AdapterUsuarios(List<Usuario> usuarioList, Onclick onclick) {
        this.usuarioList = usuarioList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Usuario usuario = usuarioList.get(position);
        holder.text_nome.setText(usuario.getNome());
        holder.text_email.setText(usuario.getEmail());
        holder.text_tipo.setText(usuario.getTipo());


        holder.itemView.setOnClickListener(view -> onclick.onClickListener(usuario));

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public interface Onclick {
        public void onClickListener(Usuario usuario);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text_nome, text_email, text_tipo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_nome = itemView.findViewById(R.id.text_nome);
            text_email = itemView.findViewById(R.id.text_email);
            text_tipo = itemView.findViewById(R.id.text_tipo);




        }
    }
}
