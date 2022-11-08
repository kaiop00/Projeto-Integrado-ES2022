package com.ufc.academiaufc.activity.autenticacao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.usuario.TelaInicialActivity;
import com.ufc.academiaufc.activity.academia.TelaInicialAcademiaActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Login;

public class LoginActivity extends AppCompatActivity {


    private EditText edit_email;
    private EditText edit_senha;
    private ProgressBar progressBar;

    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciaComponentes();
        configCliques();

    }

    private void configCliques() {

        findViewById(R.id.text_criar_conta).setOnClickListener(view ->
                startActivity(new Intent(this, CriarContaActivity.class)));
        findViewById(R.id.text_recuperar_conta).setOnClickListener(view ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));
    }

    public void validaDados(View view) {

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);

            } else {
                edit_senha.requestFocus();
                edit_senha.setError("Informe sua senha.");
            }

        } else {
            edit_email.requestFocus();
            edit_email.setError("Informe seu email.");
        }

    }

    private void logar(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                verificaCadastro(task.getResult().getUser().getUid());

            } else {
                String error = FirebaseHelper.validaErros(task.getException().getMessage()) ;
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }


        });
    }

    private void verificaCadastro(String idUser) {
        DatabaseReference loginRef = FirebaseHelper.getDatabaseReference()
                .child("login")
                .child(idUser);
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

    private void verificaAcesso(Login login) {
        if (login != null) {
            progressBar.setVisibility(View.VISIBLE);
            if (login.getTipo().equals("U")) {
                startActivity(new Intent(this, TelaInicialActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, TelaInicialAcademiaActivity.class));
                finish();
            }
        }
    }

    private void iniciaComponentes() {
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progressBar);
    }
}