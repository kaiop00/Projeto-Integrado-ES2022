package com.ufc.academiaufc.activity.form;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskara.widget.MaskEditText;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.academia.MinhasMensagensAcademiaActivity;
import com.ufc.academiaufc.activity.usuario.MeusTreinosActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Academia;
import com.ufc.academiaufc.model.Mensagem;

public class FormMensagemActivity extends AppCompatActivity {

    private Academia academia;
    private EditText edit_titulo;
    private EditText edit_mensagem;
    private MaskEditText edit_data;
    private MaskEditText edit_hora_inicio;
    private MaskEditText edit_hora_final;
    private RadioGroup radio;
    private RadioButton aberto, fechado;

    private Mensagem mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mensagem);


        iniciaComponentes();
        configCliques();
        configGetIntent();

    }

    private void configGetIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mensagem = (Mensagem) bundle.getSerializable("mensagem");
            configDados();

        } else {

        }

    }

    private void configDados() {
        edit_titulo.setText(mensagem.getTitulo());
        edit_mensagem.setText(mensagem.getMsg());
        edit_data.setText(mensagem.getDia());
        edit_hora_inicio.setText(mensagem.getHoraInicio());
        edit_hora_final.setText(mensagem.getHoraFinal());
        if (mensagem.getSituacao().equals("Aberto")) {
            radio.check(R.id.aberto);
        } else {
            radio.check(R.id.fechado);
        }


    }

    private void configCliques() {
        findViewById(R.id.ib_salvar).setOnClickListener(view -> recuperaDadosAcademia());
        findViewById(R.id.ib_volar).setOnClickListener(view ->
                startActivity(new Intent(this, MinhasMensagensAcademiaActivity.class)));

    }

    private void recuperaDadosAcademia() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("academia")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                academia = snapshot.getValue(Academia.class);

                validaDadosAcademia();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void validaDadosAcademia() {
        String titulo = edit_titulo.getText().toString();
        String msg = edit_mensagem.getText().toString();
        String data = edit_data.getText().toString();
        String inicio = edit_hora_inicio.getText().toString();
        String fim = edit_hora_final.getText().toString();


        if (!titulo.isEmpty()) {
            if (!msg.isEmpty()) {
                if (!data.isEmpty()) {
                    if (mensagem == null) mensagem = new Mensagem();
                    mensagem.setIdUser(FirebaseHelper.getIdFirebase());
                    mensagem.setNomeUser(academia.getNome());
                    mensagem.setTitulo(titulo);
                    mensagem.setMsg(msg);
                    mensagem.setDia(data);
                    mensagem.setSituacao(getsituacao());
                    mensagem.setHoraInicio(inicio);
                    mensagem.setHoraFinal(fim);
                    mensagem.salvar();

                    finish();
                    startActivity(new Intent(this, MinhasMensagensAcademiaActivity.class));


                } else {
                    edit_data.requestFocus();
                    edit_data.setError("Informação obrigatoria.");
                }
            } else {
                edit_mensagem.requestFocus();
                edit_mensagem.setError("Informação obrigatoria.");
            }
        } else {
            edit_titulo.requestFocus();
            edit_titulo.setError("Informação obrigatoria.");
        }

    }

    private String getsituacao() {
        String situacao;
        if (aberto.isChecked()) {
            situacao = "Aberto";
        } else {
            situacao = "Fechado";
        }
        return situacao;
    }

    private void iniciaComponentes() {
        edit_titulo = findViewById(R.id.edit_titulo_msg);
        edit_mensagem = findViewById(R.id.edit_msg);
        edit_data = findViewById(R.id.edit_data);
        edit_hora_inicio = findViewById(R.id.edit_inicio);
        edit_hora_final = findViewById(R.id.edit_fim);
        radio = findViewById(R.id.radio);
        aberto = findViewById(R.id.aberto);
        fechado = findViewById(R.id.fechado);

    }


}