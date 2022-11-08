package com.ufc.academiaufc.activity.autenticacao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.usuario.TelaInicialActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Login;
import com.ufc.academiaufc.model.Usuario;

public class CriarContaActivity extends AppCompatActivity {

    private EditText edit_nome;
    private EditText edit_email;
    private EditText edit_senha;
    private ProgressBar progressBar;

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

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
                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    usuario.setTipo("Aluno");
                    usuario.setTipoConta(false);

                    cadastrarUsuario(usuario);



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

    private void cadastrarUsuario(Usuario usuario) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                String idUser = task.getResult().getUser().getUid();
                usuario.setId(idUser);
                usuario.salvar();
                Login login = new Login(idUser,"U");
                login.salvar();


                Intent intent = new Intent(this, TelaInicialActivity.class);
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
        findViewById(R.id.ib_cadastrar_academia).setOnClickListener(view -> showDialogoLogin());
    }
    private void showDialogoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Código");
        builder.setMessage("Insira o código para cadastrar sua academia.");

        //Inflate
        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        EditText edit_codigo = view.findViewById(R.id.edit_codigo);
        Button btn_confirma = view.findViewById(R.id.btn_confirma);
        btn_confirma.setOnClickListener(view1 -> {
            if (edit_codigo.getText().toString().equals("123") ){
                startActivity(new Intent(this,CriarContaAcademiaActivity.class));
            }else {
                Toast.makeText(this, "Código incorreto", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();


    }

    private void iniciaComponentes() {

        edit_nome = findViewById(R.id.edit_nome);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Crie sua conta");


    }
}
