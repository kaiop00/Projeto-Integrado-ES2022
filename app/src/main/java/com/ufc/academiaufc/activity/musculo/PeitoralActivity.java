package com.ufc.academiaufc.activity.musculo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.publico.GifInfoActivity;

public class PeitoralActivity extends AppCompatActivity {

    private TextView text_voador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peitoral);

        iniciaComponentes();
        configCliques();

    }
    private void configCliques(){
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());
        findViewById(R.id.btn_ex1).setOnClickListener(view -> {
            String voador = "Voador";
            String peitoral = "Peitoral";
            Intent intent = new Intent(this, GifInfoActivity.class);
            intent.putExtra("voador",voador);
            intent.putExtra("peitoral",peitoral);
            startActivity(intent);
        });

    }


    private void iniciaComponentes(){
        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Peitoral");
    }
}