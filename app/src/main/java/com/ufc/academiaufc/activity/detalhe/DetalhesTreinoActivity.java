package com.ufc.academiaufc.activity.detalhe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.autenticacao.LoginActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Favorito;
import com.ufc.academiaufc.model.Treino;
import com.ufc.academiaufc.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DetalhesTreinoActivity extends AppCompatActivity {

    private LikeButton like_button;

    private TextView text_titulo_treino;

    private TextView text_exercicio1;
    private TextView text_sr1;
    private TextView text_ta1;

    private TextView text_exercicio2;
    private TextView text_sr2;
    private TextView text_ta2;

    private TextView text_exercicio3;
    private TextView text_sr3;
    private TextView text_ta3;

    private Treino treino;
    private Usuario usuario;


    private final List<String> favoritosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_treino);

        iniciaComponentes();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            treino = (Treino) bundle.getSerializable("treinoSelecionado");

            configDados();

            recuperaUsuario();
        }


        configLikeButton();

        recuperaFavoritos();

        configCliques();
    }

    private void configDados() {
        text_titulo_treino.setText(treino.getTitulo());
        text_exercicio1.setText(treino.getNome());
        text_sr1.setText(treino.getSerieRepeticao());
        text_ta1.setText(treino.getTecnicaAvancada());
        text_exercicio2.setText(treino.getNome2());
        text_sr2.setText(treino.getSerieRepeticao2());
        text_ta2.setText(treino.getTecnicaAvancada2());
        text_exercicio3.setText(treino.getNome3());
        text_sr3.setText(treino.getSerieRepeticao3());
        text_ta3.setText(treino.getTecnicaAvancada3());
    }


    private void configLikeButton() {
        like_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (FirebaseHelper.getAutenticado()) {
                    configSnackBar("", "Treino favoritado.", R.drawable.ic_hear_on, true);
                } else {
                    likeButton.setLiked(false);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                configSnackBar("DESFAZER", "Treino removido.", R.drawable.ic_hear_off, false);
            }
        });


    }

    private void configSnackBar(String actionMsg, String msg, int icon, Boolean like) {
        configFavoritos(like);

        Snackbar snackbar = Snackbar.make(like_button, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction(actionMsg, v -> {
            if (!like) {
                configFavoritos(true);
            }

        });

        TextView text_snack_bar = snackbar.getView().findViewById(R.id.snackbar_text);
        text_snack_bar.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        text_snack_bar.setCompoundDrawablePadding(24);
        snackbar.setActionTextColor(Color.parseColor("#F78323"))
                .setTextColor(Color.parseColor("#FFFFFF"))
                .show();
    }

    private void recuperaFavoritos() {
        if (FirebaseHelper.getAutenticado()) {
            DatabaseReference favoritosRef = FirebaseHelper.getDatabaseReference()
                    .child("favoritos")
                    .child(FirebaseHelper.getIdFirebase());
            favoritosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        favoritosList.add(ds.getValue(String.class));
                    }

                    if (favoritosList.contains(treino.getId())) {
                        like_button.setLiked(true);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void configFavoritos(Boolean like) {
        if (like) {
            like_button.setLiked(true);
            favoritosList.add(treino.getId());
        } else {
            like_button.setLiked(false);
            favoritosList.remove(treino.getId());
        }

        Favorito favorito = new Favorito();
        favorito.setFavoritos(favoritosList);
        favorito.salvar();
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    private void recuperaUsuario() {
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(treino.getIdUser());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniciaComponentes() {
        like_button = findViewById(R.id.like_button);

        text_titulo_treino = findViewById(R.id.text_titulo_treino);

        text_exercicio1 = findViewById(R.id.text_exercicio1);
        text_sr1 = findViewById(R.id.text_sr1);
        text_ta1 = findViewById(R.id.text_ta1);

        text_exercicio2 = findViewById(R.id.text_exercicio2);
        text_sr2 = findViewById(R.id.text_sr2);
        text_ta2 = findViewById(R.id.text_ta2);

        text_exercicio3 = findViewById(R.id.text_exercicio3);
        text_sr3 = findViewById(R.id.text_sr3);
        text_ta3 = findViewById(R.id.text_ta3);

    }
}