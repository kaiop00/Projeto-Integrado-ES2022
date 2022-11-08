package com.ufc.academiaufc.fragments.academia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class TreinosAcademiaFragment extends Fragment implements AdapterTreinos.Onclick {


    private List<Treino> treinoList = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView text_info;
    private RecyclerView rv_treinos;

    private AdapterTreinos adapterTreinos;
    private Treino treino;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_treinos_academia, container, false);
        iniciaComponentes(view);
        configRvTreinos();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperaTreinos();
    }




    private void configRvTreinos() {
        rv_treinos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_treinos.setHasFixedSize(true);
        adapterTreinos = new AdapterTreinos(treinoList, this);
        rv_treinos.setAdapter(adapterTreinos);

    }

    private void recuperaTreinos() {
        DatabaseReference databaseReference = FirebaseHelper.getDatabaseReference()
                .child("treinos_publicos");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                treinoList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        treino = snap.getValue(Treino.class);
                        if (treino.isStatus() == true){
                            treinoList.add(treino);
                        }else{

                        }

                    }
                    text_info.setText("");
                } else {
                    text_info.setText("Nenhum treino encontrado.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(treinoList);
                adapterTreinos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void iniciaComponentes(View view) {
        text_info = view.findViewById(R.id.text_info);
        progressBar = view.findViewById(R.id.progressBar);
        rv_treinos = view.findViewById(R.id.rv_treinos);
    }

    @Override
    public void onClickListener(Treino treino) {
        Intent intent = new Intent(requireContext(), DetalhesTreinoActivity.class);
        intent.putExtra("treinoSelecionado", treino);
        startActivity(intent);

    }
}