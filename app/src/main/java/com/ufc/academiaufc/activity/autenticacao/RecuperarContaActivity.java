package com.ufc.academiaufc.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.helper.FirebaseHelper;

public class RecuperarContaActivity extends AppCompatActivity {

    private EditText edit_email;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);
        configCliques();
        iniciaComponentes();


    }


    private void configCliques() {
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());
    }

    public void validaDados(View view) {
        String email = edit_email.getText().toString();

        if (!email.isEmpty()) {

            progressBar.setVisibility(View.VISIBLE);

            recuperarSenha(email);

        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu email.");
        }
    }

    private void recuperarSenha(String email) {

        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "E-mail enviado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                String error = task.getException().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private void iniciaComponentes() {
        edit_email = findViewById(R.id.edit_email);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Recupere sua conta");
    }
}