package com.ufc.academiaufc.activity.publico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.academiaufc.R;

import pl.droidsonroids.gif.GifImageView;

public class GifInfoActivity extends AppCompatActivity {

    private GifImageView gv_treino;
    private TextView text_titulo;
    private TextView text_nome_treino;
    private TextView text_descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_info);

        iniciaComponente();
        configCliques();
        configGetExtras();


    }

    private void configGetExtras() {
        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("voador");
        String nomeMusculo = bundle.getString("peitoral");
        configDados(nome, nomeMusculo);
    }

    private void configDados(String nome, String nomeMusculo) {
        text_nome_treino.setText(nome);
        text_titulo.setText(nomeMusculo);

        configDescricao();

    }

    private void configDescricao() {
        if (text_nome_treino.getText().toString().equals("Voador") ){
            text_descricao.setText("O Voador trabalha toda a área do peitoral, desde o músculo peitoral maior até os menores " +
                    "dando uma ênfase em definição, alongamento e ganho de força. O exercício também trabalha" +
                    " grande parte dos músculos dos membros superiores: desde deltóides e músculo trapézio até os tríceps.");
        }

    }

    private void configCliques(){
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());

    }
    private void iniciaComponente(){
        text_titulo = findViewById(R.id.text_titulo);
        text_nome_treino = findViewById(R.id.text_nome_treino);
        text_descricao = findViewById(R.id.text_descricao);
        gv_treino = findViewById(R.id.gv_treino);


    }
}