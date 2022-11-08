package com.ufc.academiaufc.activity.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.academia.TelaInicialAcademiaActivity;
import com.ufc.academiaufc.activity.form.FormTreinoAlunoActivity;
import com.ufc.academiaufc.activity.form.FormTreinoPersonalActivity;
import com.ufc.academiaufc.adapter.AdapterTreinos;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Login;
import com.ufc.academiaufc.model.Treino;
import com.ufc.academiaufc.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusTreinosActivity extends AppCompatActivity implements AdapterTreinos.Onclick {

    private List<Treino> treinoList = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView text_info;
    private SwipeableRecyclerView rv_treinos;
    private AdapterTreinos adapterTreinos;
    private Treino treino;
    private Usuario usuario;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_treinos);

        iniciaComponentes();
        configRvTreinos();
        configCliques();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaTreinos();

    }

    private void configCliques() {
        findViewById(R.id.ib_add).setOnClickListener(view -> verificaCadastro());
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());
    }

    private void configRvTreinos() {
        rv_treinos.setLayoutManager(new LinearLayoutManager(this));
        rv_treinos.setHasFixedSize(true);
        adapterTreinos = new AdapterTreinos(treinoList, this);
        rv_treinos.setAdapter(adapterTreinos);

        rv_treinos.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                showDialogoDelete(position);
            }
        });
    }

    private void showDialogoDelete(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar treino");
        builder.setMessage("Aperte em sim para confirmar ou em não para cancelar");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", ((dialogInterface, i) -> {
            dialogInterface.dismiss();
            adapterTreinos.notifyDataSetChanged();
        }));
        builder.setPositiveButton("Sim", ((dialogInterface, i) -> {
            treinoList.get(pos).deletar();
            adapterTreinos.notifyItemRemoved(pos);
        }));

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void recuperaTreinos() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("treinos")
                .child(FirebaseHelper.getIdFirebase());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                treinoList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        treino = snap.getValue(Treino.class);
                        treinoList.add(treino);
                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhum treino cadastrado.");
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

    private void verificaCadastro() {
        DatabaseReference loginRef = FirebaseHelper.getDatabaseReference()
                .child("login")
                .child(FirebaseHelper.getIdFirebase());
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                login = snapshot.getValue(Login.class);
                verificaAcesso(login);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void recuperaDados() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                configDestino(usuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void configDestino(Usuario usuario) {
        if (usuario != null){
            if (usuario.isTipoConta() == true){
                startActivity(new Intent(this,FormTreinoPersonalActivity.class));
            }else{
                startActivity(new Intent(this, FormTreinoAlunoActivity.class));
            }
        }
    }

    private void verificaAcesso(Login login) {
        if (login != null) {
            if (login.getTipo().equals("U")) {
                recuperaDados();
            } else {
                startActivity(new Intent(this, FormTreinoPersonalActivity.class));
            }

        } else {

        }
    }

    public void iniciaComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Meus treino");


        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
        rv_treinos = findViewById(R.id.rv_treinos);

    }

    @Override
    public void onClickListener(Treino treino) {
        if (treino.isContaUser() == false) {
            Intent intent = new Intent(this, FormTreinoAlunoActivity.class);
            intent.putExtra("treinoAluno", treino);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, FormTreinoPersonalActivity.class);
            intent.putExtra("treinoPersonal", treino);
            startActivity(intent);
            finish();
        }


    }
}