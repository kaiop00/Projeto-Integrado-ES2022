package com.ufc.academiaufc.activity.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.ufc.academiaufc.activity.form.FormMedidaActivity;
import com.ufc.academiaufc.adapter.AdapterMedidas;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Medida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinhasMedidasActivity extends AppCompatActivity implements AdapterMedidas.Onclick {


    private List<Medida> medidaList  = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView text_info;
    private SwipeableRecyclerView rv_medidas;
    private AdapterMedidas adapterMedidas;



    private Medida medida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_medidas);

        iniciaComponentes();
        configRvMedidas();
        configCliques();

    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaMedidas();
    }

    private void configCliques() {
        findViewById(R.id.ib_add).setOnClickListener(view -> {
            startActivity(new Intent(this, FormMedidaActivity.class));
            finish();
        });
        findViewById(R.id.ib_volar).setOnClickListener(view ->
                finish());

    }
    private void recuperaMedidas() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("medidas")
                .child(FirebaseHelper.getIdFirebase());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                medidaList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        medida = snap.getValue(Medida.class);
                        medidaList.add(medida);
                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhuma medida cadastrada.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(medidaList);
                adapterMedidas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void configRvMedidas() {
        rv_medidas.setLayoutManager(new LinearLayoutManager(this));
        rv_medidas.setHasFixedSize(true);
        adapterMedidas = new AdapterMedidas(medidaList, this);
        rv_medidas.setAdapter(adapterMedidas);

        rv_medidas.setListener(new SwipeLeftRightCallback.Listener() {
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
            adapterMedidas.notifyDataSetChanged();
        }));
        builder.setPositiveButton("Sim", ((dialogInterface, i) -> {
            medidaList.get(pos).deletar();
            adapterMedidas.notifyItemRemoved(pos);
        }));

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void iniciaComponentes() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Minhas medidas");


        text_info = findViewById(R.id.text_info);
        progressBar = findViewById(R.id.progressBar);
        rv_medidas = findViewById(R.id.rv_medidas);

    }

    @Override
    public void onClickListener(Medida medida) {
        Intent intent = new Intent(this, FormMedidaActivity.class);
        intent.putExtra("medida", medida);
        startActivity(intent);

    }
}