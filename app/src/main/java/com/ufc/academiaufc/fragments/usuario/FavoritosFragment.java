package com.ufc.academiaufc.fragments.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.detalhe.DetalhesTreinoActivity;
import com.ufc.academiaufc.adapter.AdapterTreinos;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Treino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FavoritosFragment extends Fragment implements AdapterTreinos.Onclick {

    private AdapterTreinos adapterTreinos;
    private final List<Treino> treinoList = new ArrayList<>();

    private RecyclerView rv_treinos;
    private ProgressBar progressBar;
    private TextView text_info;

    private final List<String> favoritosList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        iniciaComponentes(view);

        configRV();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperaFavoritos();
    }

    private void recuperaFavoritos() {
        if (FirebaseHelper.getAutenticado()) {
            DatabaseReference favoritosRef = FirebaseHelper.getDatabaseReference()
                    .child("favoritos")
                    .child(FirebaseHelper.getIdFirebase());
            favoritosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    favoritosList.clear();
                    treinoList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        favoritosList.add(ds.getValue(String.class));
                    }

                    if (favoritosList.size() > 0) {
                        recuperaTreinos();
                    } else {
                        text_info.setText("Nenhum treino favoritado.");
                        adapterTreinos.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            text_info.setText("");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void recuperaTreinos() {
        for (int i = 0; i < favoritosList.size(); i++) {
            DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                    .child("treinos_publicos")
                    .child(favoritosList.get(i));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Treino treino = snapshot.getValue(Treino.class);
                    treinoList.add(treino);

                    if (treinoList.size() == favoritosList.size()) {
                        text_info.setText("");
                        progressBar.setVisibility(View.GONE);
                        Collections.reverse(treinoList);
                        adapterTreinos.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    private void configRV() {
        rv_treinos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_treinos.setHasFixedSize(true);
        adapterTreinos = new AdapterTreinos(treinoList, this);
        rv_treinos.setAdapter(adapterTreinos);
    }

    private void iniciaComponentes(View view) {
        rv_treinos = view.findViewById(R.id.rv_treinos);
        progressBar = view.findViewById(R.id.progressBar);
        text_info = view.findViewById(R.id.text_info);
    }

    @Override
    public void onClickListener(Treino treino) {
        Intent intent = new Intent(requireContext(), DetalhesTreinoActivity.class);
        intent.putExtra("treinoSelecionado", treino);
        startActivity(intent);
    }
}