package com.ufc.academiaufc.fragments.usuario;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.publico.FichasTreinoActivity;
import com.ufc.academiaufc.activity.publico.MensagensPublicasActivity;
import com.ufc.academiaufc.activity.usuario.MeusTreinosActivity;
import com.ufc.academiaufc.activity.usuario.MinhaContaActivity;
import com.ufc.academiaufc.activity.usuario.MinhasMedidasActivity;
import com.ufc.academiaufc.activity.publico.TreinamentosActivity;
import com.ufc.academiaufc.activity.autenticacao.LoginActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Usuario;


public class HomeFragment extends Fragment {

    private ImageButton ib_menu;
    private TextView text_nome_user;
    private Usuario usuario;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        iniciaComponentes(view);
        configCliques(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperaDadosUsuario();
    }

    private void configCliques(View view) {
        view.findViewById(R.id.ib_forum).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MensagensPublicasActivity.class));
        });
        view.findViewById(R.id.btn_treinamento).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), TreinamentosActivity.class)));
        view.findViewById(R.id.btn_ficha_treino).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), FichasTreinoActivity.class)));
        view.findViewById(R.id.btn_minha_ficha).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), MeusTreinosActivity.class)));
        view.findViewById(R.id.btn_minha_medida).setOnClickListener(v ->
                startActivity(new Intent(getActivity(), MinhasMedidasActivity.class)));

        ib_menu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getActivity(), ib_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_home, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_treinamento) {
                    startActivity(new Intent(getActivity(), TreinamentosActivity.class));

                } else if (menuItem.getItemId() == R.id.menu_meu_treino) {

                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(getActivity(), MeusTreinosActivity.class));
                    } else {
                        showDialogoLogin();
                    }

                } else if (menuItem.getItemId() == R.id.menu_minha_conta) {
                    if (FirebaseHelper.getAutenticado()) {
                        startActivity(new Intent(getActivity(), MinhaContaActivity.class));

                    } else {
                        showDialogoLogin();
                    }

                } else {
                    if (FirebaseHelper.getAutenticado()) {
                        FirebaseHelper.getAuth().signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    } else {
                        showDialogoLogin();
                    }

                }

                return true;
            });
            popupMenu.show();
        });
    }
    private void showDialogoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Autenticação");
        builder.setMessage("Você não está autenticado no app, deseja fazer isso agora?");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton("Sim", (dialog, which) -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void recuperaDadosUsuario() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
                text_nome_user.setText("Olá " + usuario.getNome());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciaComponentes(View view) {
        ib_menu = view.findViewById(R.id.ib_menu);
        text_nome_user = view.findViewById(R.id.text_nome_user);
        progressBar = view.findViewById(R.id.progressBar);
    }

}