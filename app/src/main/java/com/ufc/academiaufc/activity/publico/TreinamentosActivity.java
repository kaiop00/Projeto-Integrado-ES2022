package com.ufc.academiaufc.activity.publico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.musculo.PeitoralActivity;

public class TreinamentosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinamentos);

        iniciaComponente();
        configCliques();
    }

    private void configCliques() {
        findViewById(R.id.ib_volar).setOnClickListener(view -> {
            finish();
        });
        findViewById(R.id.btn_peitoral).setOnClickListener(view -> {
            startActivity(new Intent(this,PeitoralActivity.class));
        });

    }

    private void iniciaComponente() {
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Treinamentos");



    }
}