package com.ufc.academiaufc.activity.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.form.FormMedidaActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Academia;
import com.ufc.academiaufc.model.Usuario;

public class MinhaContaActivity extends AppCompatActivity {


    private EditText edit_nome;
    private EditText edit_email;
    private TextView text_tipo_user;
    private ProgressBar progressBar;
    private Usuario usuario;
    private AlertDialog dialog;
    private Academia academia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_conta);


        iniciaComponentes();
        configCliques();


    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperaDados();



    }


    private void recuperaDados() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                configDados();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void recuperaDadosAcademia() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("academia");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        academia = snap.getValue(Academia.class);
                        showDialogoLogin(academia.getCodigo());

                    }
                }else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void configDados() {
        edit_nome.setText(usuario.getNome());
        edit_email.setText(usuario.getEmail());
        text_tipo_user.setText(usuario.getTipo());
        progressBar.setVisibility(View.GONE);


    }

    private void configCliques(){
        findViewById(R.id.ib_medida).setOnClickListener(view ->
                startActivity(new Intent(this, FormMedidaActivity.class)));
        findViewById(R.id.ib_salvar).setOnClickListener(view -> validaDados());
        findViewById(R.id.ib_volar).setOnClickListener(view ->
                startActivity(new Intent(this, TelaInicialActivity.class)));
        findViewById(R.id.text_personal).setOnClickListener(view -> {
            recuperaDadosAcademia();
        });
    }

    private void validaDados() {
        String nome = edit_nome.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        if (!nome.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            usuario.setNome(nome);
            usuario.salvar();
            progressBar.setVisibility(View.GONE);

            Toast.makeText(this, "Alteração salva com sucesso!", Toast.LENGTH_SHORT).show();

        } else {
            edit_nome.requestFocus();
            edit_nome.setError("Informe seu nome");
        }
    }

    private void showDialogoLogin(String codigo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Personal");
        builder.setMessage("Insira o código para mudar sua conta.   ");

        //Inflate
        View view = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        EditText edit_codigo = view.findViewById(R.id.edit_codigo);
        Button btn_confirma = view.findViewById(R.id.btn_confirma);
        btn_confirma.setOnClickListener(view1 -> {

            if (edit_codigo.getText().toString().equals(codigo) ){
                String tipo = "Personal";
                usuario.setTipo(tipo);
                text_tipo_user.setText(usuario.getTipo());
                usuario.setTipoConta(true);
                usuario.salvar();
            }else {

                Toast.makeText(this, "Não foi dessa vez", Toast.LENGTH_SHORT).show();
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
        text_tipo_user = findViewById(R.id.text_tipo_user);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Perfil");
    }


}