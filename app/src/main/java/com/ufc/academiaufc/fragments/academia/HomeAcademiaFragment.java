package com.ufc.academiaufc.fragments.academia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.publico.FichasTreinoActivity;
import com.ufc.academiaufc.activity.publico.TreinamentosActivity;
import com.ufc.academiaufc.activity.academia.MeusTreinosAcademiaActivity;
import com.ufc.academiaufc.activity.academia.MinhasMensagensAcademiaActivity;
import com.ufc.academiaufc.activity.academia.UsuariosCadastradosActivity;
import com.ufc.academiaufc.activity.autenticacao.LoginActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Academia;


public class HomeAcademiaFragment extends Fragment {

    private TextView text_nome_user;
    private Academia academia;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_academia, container, false);
        iniciaComponentes(view);
        configCliques(view);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        recuperaDadosAcademia();
    }
    private void configCliques(View view) {
        view.findViewById(R.id.btn_treinamento).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), TreinamentosActivity.class)));
        view.findViewById(R.id.btn_ficha_treino).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), FichasTreinoActivity.class)));
        view.findViewById(R.id.btn_minha_ficha).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), MeusTreinosAcademiaActivity.class)));
        view.findViewById(R.id.btn_usuarios).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), UsuariosCadastradosActivity.class)));

        view.findViewById(R.id.ib_forum).setOnClickListener(view1 -> {
            startActivity(new Intent(requireActivity(), MinhasMensagensAcademiaActivity.class));
        });


        view.findViewById(R.id.ib_sair).setOnClickListener(view1 -> {
            if (FirebaseHelper.getAutenticado()){
                FirebaseHelper.getAuth().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        });

    }


    private void recuperaDadosAcademia() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("academia")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                academia = snapshot.getValue(Academia.class);
                text_nome_user.setText("Olá " + academia.getNome());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void iniciaComponentes(View view) {
        text_nome_user = view.findViewById(R.id.text_nome_user);
        progressBar = view.findViewById(R.id.progressBar);
    }
}