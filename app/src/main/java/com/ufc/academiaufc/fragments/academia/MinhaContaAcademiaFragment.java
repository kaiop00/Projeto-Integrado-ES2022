package com.ufc.academiaufc.fragments.academia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.autenticacao.LoginActivity;
import com.ufc.academiaufc.activity.form.FormMedidaActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Academia;
import com.ufc.academiaufc.model.Usuario;


public class MinhaContaAcademiaFragment extends Fragment {
    
    private EditText edit_nome;
    private EditText edit_email;
    private TextView text_tipo_user, text_codigo;
    private ProgressBar progressBar;
    private AlertDialog dialog;
    private Academia academia;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_minha_conta_academia, container, false);

        iniciaComponentes(view);

        configCliques(view);
        
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        recuperaDados();
    }


    private void configCliques(View view) {
        view.findViewById(R.id.ib_sair).setOnClickListener(v -> {
            if (FirebaseHelper.getAutenticado()){
                FirebaseHelper.getAuth().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        view.findViewById(R.id.ib_salvar).setOnClickListener(v -> validaDados());
        view.findViewById(R.id.text_codigo_verificacao).setOnClickListener(v -> {
            showDialogoLogin();
        });
    }

    private void recuperaDados() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("academia")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                academia = snapshot.getValue(Academia.class);
                configDados();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void validaDados() {
        String nome = edit_nome.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        if (!nome.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            academia.setNome(nome);
            academia.salvar();
            progressBar.setVisibility(View.GONE);

            Toast.makeText(requireActivity(), "Alteração salva com sucesso!", Toast.LENGTH_SHORT).show();

        } else {
            edit_nome.requestFocus();
            edit_nome.setError("Informe seu nome");
        }
    }

    private void showDialogoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Código de verificação");
        builder.setMessage("Insira o código para verificação do personal.  ");

        //Inflate
        View view = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        EditText edit_codigo = view.findViewById(R.id.edit_codigo);
        Button btn_confirma = view.findViewById(R.id.btn_confirma);
        btn_confirma.setOnClickListener(view1 -> {
            academia.setCodigo(edit_codigo.getText().toString());
            text_codigo.setText("Código de verificação: "+academia.getCodigo());
            academia.salvar();


            dialog.dismiss();
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();


    }




    private void configDados() {
        edit_nome.setText(academia.getNome());
        edit_email.setText(academia.getEmail());
        text_codigo.setText("Código de verificação: "+academia.getCodigo());
        progressBar.setVisibility(View.GONE);
    }

    private void iniciaComponentes(View view) {
        edit_nome = view.findViewById(R.id.edit_nome);
        edit_email = view.findViewById(R.id.edit_email);
        text_tipo_user = view.findViewById(R.id.text_tipo_user);
        progressBar = view.findViewById(R.id.progressBar);
        text_codigo = view.findViewById(R.id.text_codigo);

        TextView text_titulo = view.findViewById(R.id.text_titulo);
        text_titulo.setText("Perfil");

    }
}