package com.ufc.academiaufc.activity.publico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.ufc.academiaufc.activity.detalhe.DetalhesTreinoActivity;
import com.ufc.academiaufc.adapter.AdapterTreinos;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Treino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FichasTreinoActivity extends AppCompatActivity implements AdapterTreinos.Onclick {

    private List<Treino> treinoList = new ArrayList<>();

    private ImageButton ib_menu;
    private ProgressBar progressBar;
    private TextView text_info;
    private RecyclerView rv_treinos;

    private AdapterTreinos adapterTreinos;
    private Treino treino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas_treino);

        iniciaComponentes();
        configCliques();
        configRvTreinos();

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaTreinos();
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
                        showDialogoLogin();
                    }

                } else if (menuItem.getItemId() == R.id.menu_minha_conta){
                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(this, MinhaContaActivity.class));

                    } else {
                        showDialogoLogin();
                    }

                }else{
                    if (FirebaseHelper.getAutenticado()) {
                        FirebaseHelper.getAuth().signOut();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();

                    } else {
                        showDialogoLogin();
                    }

                }

                return true;
            });
            popupMenu.show();
        });
    }

    private void configRvTreinos() {
        rv_treinos.setLayoutManager(new LinearLayoutManager(this));
        rv_treinos.setHasFixedSize(true);
        adapterTreinos = new AdapterTreinos(treinoList, this);
        rv_treinos.setAdapter(adapterTreinos);

    }

    private void showDialogoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticação");
        builder.setMessage("Você não está autenticado no app, deseja fazer isso agora?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Sim", (dialog, which) -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void recuperaTreinos() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("treinos_publicos");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                treinoList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        treino = snap.getValue(Treino.class);
                        if (treino.isStatus() == true){
                            treinoList.add(treino);
                        }else{

                        }
                        
                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhum treino encontrado.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(treinoList);
                adapterTreinos.notifyDataSetChanged();
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
        rv_treinos = findViewById(R.id.rv_treinos);
    }

    @Override
    public void onClickListener(Treino treino) {
        Intent intent = new Intent(this, DetalhesTreinoActivity.class);
        intent.putExtra("treinoSelecionado",treino);
        startActivity(intent);

    }
}