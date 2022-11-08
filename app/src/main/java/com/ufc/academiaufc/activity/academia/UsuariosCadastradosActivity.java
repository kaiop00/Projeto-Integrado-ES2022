package com.ufc.academiaufc.activity.academia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.adapter.AdapterUsuarios;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuariosCadastradosActivity extends AppCompatActivity implements AdapterUsuarios.Onclick {

    private List<Usuario> usuarioList = new ArrayList<>();

    private ImageButton ib_menu;
    private ProgressBar progressBar;
    private TextView text_info;
    private RecyclerView rv_usuarios;

    private AdapterUsuarios adapterUsuarios;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_cadastrados);
        iniciaComponentes();
        configCliques();
        configRvUsuarios();

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaUsuarios();
    }

    private void configCliques() {
        findViewById(R.id.ib_volar).setOnClickListener(view ->
                finish());
    }

    private void configRvUsuarios() {
        rv_usuarios.setLayoutManager(new LinearLayoutManager(this));
        rv_usuarios.setHasFixedSize(true);
        adapterUsuarios = new AdapterUsuarios(usuarioList, this);
        rv_usuarios.setAdapter(adapterUsuarios);

    }


    private void recuperaUsuarios() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("usuarios");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarioList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        usuario = snap.getValue(Usuario.class);
                        usuarioList.add(usuario);
                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhum usuário encontrado.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(usuarioList);
                adapterUsuarios.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void iniciaComponentes() {
        ib_menu = findViewById(R.id.ib_menu);
        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
        rv_usuarios = findViewById(R.id.rv_usuarios);
    }


    @Override
    public void onClickListener(Usuario usuario) {

    }
}