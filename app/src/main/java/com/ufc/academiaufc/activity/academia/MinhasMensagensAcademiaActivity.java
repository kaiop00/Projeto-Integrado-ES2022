package com.ufc.academiaufc.activity.academia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.form.FormMensagemActivity;
import com.ufc.academiaufc.activity.form.FormTreinoPersonalActivity;
import com.ufc.academiaufc.adapter.AdapterMensagens;
import com.ufc.academiaufc.adapter.AdapterTreinos;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Login;
import com.ufc.academiaufc.model.Mensagem;
import com.ufc.academiaufc.model.Treino;
import com.ufc.academiaufc.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinhasMensagensAcademiaActivity extends AppCompatActivity implements AdapterMensagens.Onclick {

    private List<Mensagem> mensagemList = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView text_info;
    private SwipeableRecyclerView rv_mensagens;
    private AdapterMensagens adapterMensagens;
    private Mensagem mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_mensagens_academia);

        iniciaComponentes();
        configRvMensagens();
        configCliques();

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaMensagens();

    }

    private void configCliques() {
        findViewById(R.id.ib_add).setOnClickListener(view -> startActivity(new Intent(this, FormMensagemActivity.class)));
        findViewById(R.id.ib_volar).setOnClickListener(view -> startActivity(new Intent(this, TelaInicialAcademiaActivity.class)));
    }


    private void configRvMensagens() {
        rv_mensagens.setLayoutManager(new LinearLayoutManager(this));
        rv_mensagens.setHasFixedSize(true);
        adapterMensagens = new AdapterMensagens(mensagemList, this);
        rv_mensagens.setAdapter(adapterMensagens);

        rv_mensagens.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {

            }

            @Override
            public void onSwipedRight(int position) {
                showDialogoDelete(position);
            }
        });
    }
    private void recuperaMensagens() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("mensagens")
                .child(FirebaseHelper.getIdFirebase());
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
                    text_info.setText("Nenhuma mensagem cadastrado.");
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

    private void showDialogoDelete(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar Mensagem");
        builder.setMessage("Aperte em sim para confirmar ou em não para cancelar");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", ((dialogInterface, i) -> {
            dialogInterface.dismiss();
            adapterMensagens.notifyDataSetChanged();
        }));
        builder.setPositiveButton("Sim", ((dialogInterface, i) -> {
            mensagemList.get(pos).deletar();
            adapterMensagens.notifyItemRemoved(pos);
        }));

        AlertDialog dialog = builder.create();
        dialog.show();


    }
    public void iniciaComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Minhas mensagens");


        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
        rv_mensagens = findViewById(R.id.rv_mensagens);

    }


    @Override
    public void onClickListener(Mensagem mensagem) {
        Intent intent = new Intent(this, FormMensagemActivity.class);
        intent.putExtra("mensagem", mensagem);
        startActivity(intent);
        finish();
    }
}