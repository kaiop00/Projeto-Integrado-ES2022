package com.ufc.academiaufc.activity.form;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.academiaufc.R;
import com.ufc.academiaufc.activity.usuario.MeusTreinosActivity;
import com.ufc.academiaufc.helper.FirebaseHelper;
import com.ufc.academiaufc.model.Academia;
import com.ufc.academiaufc.model.Login;
import com.ufc.academiaufc.model.Treino;
import com.ufc.academiaufc.model.Usuario;

public class FormTreinoAlunoActivity extends AppCompatActivity {

    private EditText edit_titulo;
    private EditText edit_nome;
    private EditText edit_series_repeticoes;
    private EditText edit_Tecnica_Avancada;
    private EditText edit_nivel;
    private EditText edit_nome2;
    private EditText edit_series_repeticoes2;
    private EditText edit_Tecnica_Avancada2;
    private EditText edit_nome3;
    private EditText edit_series_repeticoes3;
    private EditText edit_Tecnica_Avancada3;


    private ProgressBar progressBar;

    private Login login;
    private Treino treino;
    private Usuario usuario;
    private Academia academia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_treino_aluno);

        iniciaComponentes();

        configGetIntent();

        configCliques();


    }


    private void configGetIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            treino = (Treino) bundle.getSerializable("treinoAluno");
            configDados();

        } else {

        }

    }

    private void configDados() {
        edit_titulo.setText(treino.getTitulo());
        edit_nome.setText(treino.getNome());
        edit_series_repeticoes.setText(treino.getSerieRepeticao());
        edit_Tecnica_Avancada.setText(treino.getTecnicaAvancada());
        edit_nome2.setText(treino.getNome2());
        edit_series_repeticoes2.setText(treino.getSerieRepeticao2());
        edit_Tecnica_Avancada2.setText(treino.getTecnicaAvancada2());
        edit_nome3.setText(treino.getNome3());
        edit_series_repeticoes3.setText(treino.getSerieRepeticao3());
        edit_Tecnica_Avancada3.setText(treino.getTecnicaAvancada3());
        edit_nivel.setText(treino.getNivel());

    }

    private void configCliques() {
        findViewById(R.id.ib_salvar).setOnClickListener(view -> recuperaDadosUsuario());
        findViewById(R.id.ib_volar).setOnClickListener(view -> finish());
    }

    private void recuperaDadosUsuario() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario = snapshot.getValue(Usuario.class);

                validaDados();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void validaDados() {

        String titulo = edit_titulo.getText().toString();
        String nome = edit_nome.getText().toString();
        String seriesRepeticoes = edit_series_repeticoes.getText().toString();
        String tecnicaAvancada = edit_Tecnica_Avancada.getText().toString();
        String nome2 = edit_nome2.getText().toString();
        String seriesRepeticoes2 = edit_series_repeticoes2.getText().toString();
        String tecnicaAvancada2 = edit_Tecnica_Avancada2.getText().toString();
        String nome3 = edit_nome3.getText().toString();
        String seriesRepeticoes3 = edit_series_repeticoes3.getText().toString();
        String tecnicaAvancada3 = edit_Tecnica_Avancada3.getText().toString();
        String nivel = edit_nivel.getText().toString();

        if (!titulo.isEmpty()) {
            if (!nome.isEmpty()) {
                if (!seriesRepeticoes.isEmpty()) {
                    if (!tecnicaAvancada.isEmpty()) {
                        if (!nome2.isEmpty()) {
                            if (!seriesRepeticoes2.isEmpty()) {
                                if (!tecnicaAvancada2.isEmpty()) {
                                    if (!nome3.isEmpty()) {
                                        if (!seriesRepeticoes3.isEmpty()) {
                                            if (!tecnicaAvancada3.isEmpty()) {
                                                if (!nivel.isEmpty()) {
                                                    if (nivel.equals("Avançado") || nivel.equals("Intermediário") || nivel.equals("Iniciante")) {
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        if (treino == null) treino = new Treino();
                                                        treino.setIdUser(FirebaseHelper.getIdFirebase());
                                                        treino.setNomeUSer(usuario.getNome());
                                                        treino.setContaUser(usuario.isTipoConta());
                                                        treino.setTitulo(titulo);
                                                        treino.setNome(nome);
                                                        treino.setSerieRepeticao(seriesRepeticoes);
                                                        treino.setTecnicaAvancada(tecnicaAvancada);
                                                        treino.setNome2(nome2);
                                                        treino.setSerieRepeticao2(seriesRepeticoes2);
                                                        treino.setTecnicaAvancada2(tecnicaAvancada2);
                                                        treino.setNome3(nome3);
                                                        treino.setSerieRepeticao3(seriesRepeticoes3);
                                                        treino.setTecnicaAvancada3(tecnicaAvancada3);
                                                        treino.setNivel(nivel);
                                                        treino.salvar();
                                                        finish();
                                                        startActivity(new Intent(this, MeusTreinosActivity.class));


                                                    } else {
                                                        edit_nivel.requestFocus();
                                                        edit_nivel.setError("Informe o nivel como Avançado, Intermediário ou Iniciante.");
                                                    }

                                                } else {
                                                    edit_nivel.requestFocus();
                                                    edit_nivel.setError("Informação obrigatoria.");
                                                }

                                            } else {
                                                edit_Tecnica_Avancada3.requestFocus();
                                                edit_Tecnica_Avancada3.setError("Informação obrigatoria.");
                                            }

                                        } else {
                                            edit_series_repeticoes3.requestFocus();
                                            edit_series_repeticoes3.setError("Informação obrigatoria.");
                                        }

                                    } else {
                                        edit_nome3.requestFocus();
                                        edit_nome3.setError("Informação obrigatoria.");
                                    }

                                } else {
                                    edit_Tecnica_Avancada2.requestFocus();
                                    edit_Tecnica_Avancada2.setError("Informação obrigatoria.");
                                }

                            } else {
                                edit_series_repeticoes2.requestFocus();
                                edit_series_repeticoes2.setError("Informação obrigatoria.");
                            }

                        } else {
                            edit_nome2.requestFocus();
                            edit_nome2.setError("Informação obrigatoria.");
                        }


                    } else {
                        edit_Tecnica_Avancada.requestFocus();
                        edit_Tecnica_Avancada.setError("Informação obrigatoria.");
                    }

                } else {
                    edit_series_repeticoes.requestFocus();
                    edit_series_repeticoes.setError("Informação obrigatoria.");
                }

            } else {
                edit_nome.requestFocus();
                edit_nome.setError("Informação obrigatoria.");
            }
        } else {
            edit_titulo.requestFocus();
            edit_titulo.setError("Informação obrigatoria.");
        }
    }

    private void iniciaComponentes() {
        edit_titulo = findViewById(R.id.edit_titulo);
        edit_nome = findViewById(R.id.edit_nome);
        edit_series_repeticoes = findViewById(R.id.edit_series_repeticoes);
        edit_Tecnica_Avancada = findViewById(R.id.edit_tecnica_avancada);

        edit_nome2 = findViewById(R.id.edit_nome2);
        edit_series_repeticoes2 = findViewById(R.id.edit_series_repeticoes2);
        edit_Tecnica_Avancada2 = findViewById(R.id.edit_tecnica_avancada2);

        edit_nome3 = findViewById(R.id.edit_nome3);
        edit_series_repeticoes3 = findViewById(R.id.edit_series_repeticoes3);
        edit_Tecnica_Avancada3 = findViewById(R.id.edit_tecnica_avancada3);
        edit_nivel = findViewById(R.id.edit_nivel);

        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_titulo);
        text_titulo.setText("Form treino");
    }
}