package com.ufc.academiaufc.activity.form;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.usuario.MinhasMedidasActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Medida;
import com.ufc.academiaufc.model.Usuario;

public class FormMedidaActivity extends AppCompatActivity {

    private EditText edit_braco_esquerdo;
    private EditText edit_braco_direito;
    private EditText edit_ano;
    private EditText edit_mes;
    private EditText edit_objetivo;
    private ProgressBar progressBar;

    private Medida medida;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_medida);


        iniciaComponentes();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            medida = (Medida) bundle.getSerializable("medida");
            configDados();
        } else {

        }

        configCliques();
    }

    private void configDados() {

        edit_objetivo.setText(medida.getObjetivo());
        edit_ano.setText(medida.getAno());
        edit_mes.setText(medida.getMes());
        edit_braco_direito.setText(medida.getMedidaBracoD());
        edit_braco_esquerdo.setText(medida.getMedidaBracoE());


    }



    private void configCliques() {
        findViewById(R.id.ib_salvar).setOnClickListener(view -> recuperaDadosUsuario());
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());
    }
    private void recuperaDadosUsuario() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                validaDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void validaDados() {
        String bracoE = edit_braco_esquerdo.getText().toString();
        String bracoD = edit_braco_direito.getText().toString();
        String ano = edit_ano.getText().toString();
        String mes = edit_mes.getText().toString();
        String objetivo = edit_objetivo.getText().toString();

        if (!bracoE.isEmpty()) {
            if (!bracoD.isEmpty()) {
                if (!ano.isEmpty()) {
                    if (!mes.isEmpty()) {
                        if (!objetivo.isEmpty()) {

                            progressBar.setVisibility(View.VISIBLE);
                            if (medida == null) medida = new Medida();
                            medida.setIdUser(FirebaseHelper.getIdFirebase());
                            medida.setNomeUser(usuario.getNome());
                            medida.setMedidaBracoD(bracoD);
                            medida.setMedidaBracoE(bracoE);
                            medida.setObjetivo(objetivo);
                            medida.setAno(ano);
                            medida.setMes(mes);
                            medida.salvar();

                            startActivity(new Intent(this, MinhasMedidasActivity.class));
                            finish();

                        } else {
                            edit_objetivo.requestFocus();
                            edit_objetivo.setError("Informação obrigatoria.");
                        }
                    } else {
                        edit_mes.requestFocus();
                        edit_mes.setError("Informação obrigatoria.");
                    }
                } else {
                    edit_ano.requestFocus();
                    edit_ano.setError("Informação obrigatoria.");
                }


            } else {
                edit_braco_direito.requestFocus();
                edit_braco_direito.setError("Informação obrigatoria.");
            }
        } else {
            edit_braco_esquerdo.requestFocus();
            edit_braco_esquerdo.setError("Informação obrigatoria.");
        }


    }

    private void iniciaComponentes() {
        edit_braco_esquerdo = findViewById(R.id.edit_braco_esquerdo);
        edit_braco_direito = findViewById(R.id.edit_braco_direito);
        edit_ano = findViewById(R.id.edit_ano);
        edit_mes = findViewById(R.id.edit_mes);
        edit_objetivo = findViewById(R.id.edit_objetivo);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Form medidas");
    }
}