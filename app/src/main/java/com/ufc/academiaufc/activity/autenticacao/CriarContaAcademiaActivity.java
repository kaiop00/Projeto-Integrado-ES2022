package com.ufc.academiaufc.activity.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.academia.TelaInicialAcademiaActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Academia;
import com.ufc.academiaufc.model.Login;

public class CriarContaAcademiaActivity extends AppCompatActivity {

    private EditText edit_nome;
    private EditText edit_email;
    private EditText edit_senha;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_academia);

        iniciaComponentes();
        configCliques();
    }
    public void validaDados(View view) {

        String nome = edit_nome.getText().toString();
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {


                    progressBar.setVisibility(View.VISIBLE);
                    Academia academia = new Academia();
                    academia.setNome(nome);
                    academia.setEmail(email);
                    academia.setSenha(senha);
                    academia.setTipoConta(true);

                    cadastrarAcademia(academia);



                } else {
                    edit_senha.requestFocus();
                    edit_senha.setError("Informe sua senha.");
                }

            } else {
                edit_email.requestFocus();
                edit_email.setError("Informe seu email.");
            }

        } else {
            edit_nome.requestFocus();
            edit_nome.setError("Informe seu nome.");
        }
    }

    private void cadastrarAcademia(Academia academia) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                academia.getEmail(), academia.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                String idUser = task.getResult().getUser().getUid();
                academia.setId(idUser);
                academia.salvar();
                Login login = new Login(idUser,"A");
                login.salvar();


                Intent intent = new Intent(this, TelaInicialAcademiaActivity.class);
                startActivity(intent);
                finish();

            } else {
                String error = FirebaseHelper.validaErros(task.getException().getMessage());
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void configCliques() {
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes() {

        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Crie a conta da sua academia");


    }
}