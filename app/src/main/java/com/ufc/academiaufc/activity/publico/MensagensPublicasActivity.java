package com.ufc.academiaufc.activity.publico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.usuario.MeusTreinosActivity;
import com.ufc.academiaufc.activity.usuario.MinhaContaActivity;
import com.ufc.academiaufc.activity.autenticacao.LoginActivity;
import com.ufc.academiaufc.adapter.AdapterMensagens;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Mensagem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MensagensPublicasActivity extends AppCompatActivity implements AdapterMensagens.Onclick {


    private List<Mensagem> mensagemList = new ArrayList<>();

    private ImageButton ib_menu;
    private ProgressBar progressBar;
    private TextView text_info;
    private RecyclerView rv_mensagem;

    private AdapterMensagens adapterMensagens;
    private Mensagem mensagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens_publicas);

        iniciaComponentes();
        configCliques();
        configRvMensagens();

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaMensagens();
    }

    private void configCliques() {
        findViewById(R.id.ib_volar).setOnClickListener(view ->
                finish());
        ib_menu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, ib_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_treinamento) {
                    startActivity(new Intent(this, TreinamentosActivity.class));

                } else if (menuItem.getItemId() == R.id.menu_meu_treino) {

                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MeusTreinosActivity.class));
                    } else {

                    }

                } else if (menuItem.getItemId() == R.id.menu_minha_conta){
                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MinhaContaActivity.class));

                    } else {

                    }

                }else{
                    if (FirebaseHelper.getAutenticado()) {
                        FirebaseHelper.getAuth().signOut();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();

                    } else {

                    }

                }

                return true;
            });
            popupMenu.show();
        });
    }

    private void configRvMensagens() {
        rv_mensagem.setLayoutManager(new LinearLayoutManager(this));
        rv_mensagem.setHasFixedSize(true);
        adapterMensagens = new AdapterMensagens(mensagemList, this);
        rv_mensagem.setAdapter(adapterMensagens);

    }
    private void recuperaMensagens() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("mensagens_publicas");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mensagemList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        mensagem = snap.getValue(Mensagem.class);
                        mensagemList.add(mensagem);
                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhuma mensagem encontrado.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(mensagemList);
                adapterMensagens.notifyDataSetChanged();
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
        rv_mensagem = findViewById(R.id.rv_mensagens);
    }


    @Override
    public void onClickListener(Mensagem mensagem) {

    }
}