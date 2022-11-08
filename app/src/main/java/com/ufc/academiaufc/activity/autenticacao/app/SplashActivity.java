package com.ufc.academiaufc.activity.autenticacao.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.usuario.TelaInicialActivity;
import com.ufc.academiaufc.activity.academia.TelaInicialAcademiaActivity;
import com.ufc.academiaufc.activity.autenticacao.LoginActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Login;

public class SplashActivity extends AppCompatActivity {

    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::verificaLogin, 3000);
    }

    private void verificaLogin() {
        if (FirebaseHelper.getAutenticado()) {
            verificaCadastro();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

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

    private void verificaAcesso(Login login) {
        if (login != null) {
            if (login.getTipo().equals("U")) {
                startActivity(new Intent(this, TelaInicialActivity.class));
            } else {
                startActivity(new Intent(this, TelaInicialAcademiaActivity.class));
            }

        }
    }

}