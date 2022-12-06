package com.ufc.academiaufc.model;

import com.google.firebase.database.DatabaseReference;
import com.ufc.academiaufc.helper.FirebaseHelper;

import java.io.Serializable;

public class Treino implements Serializable {

    private String id;
    private String idUser;
    private String nomeUSer;
    private boolean contaUser;
    private String titulo;
    private String nome;
    private String nome2;
    private String nome3;
    private String nome4;
    private String nome5;
    private String nome6;
    private String serieRepeticao;
    private String tempo1;
    private String tecnicaAvancada;
    private String serieRepeticao2;
    private String tempo2;
    private String tecnicaAvancada2;
    private String serieRepeticao3;
    private String tempo3;
    private String tecnicaAvancada3;
    private String serieRepeticao4;
    private String tempo4;
    private String tecnicaAvancada4;
    private String serieRepeticao5;
    private String tempo5;
    private String tecnicaAvancada5;
    private String serieRepeticao6;
    private String tempo6;
    private String tecnicaAvancada6;
    private String nivel;
    private boolean status;



    public Treino() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("treinos")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        reference.setValue(this);

        DatabaseReference treinoPublico = FirebaseHelper.getDatabaseReference()
                .child("treinos_publicos")
                .child(this.getId());
        treinoPublico.setValue(this);
    }

    public void deletar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("treinos")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        reference.removeValue();

        DatabaseReference treinoPublico = FirebaseHelper.getDatabaseReference()
                .child("treinos_publicos")
                .child(this.getId());
        treinoPublico.removeValue();


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNomeUSer() {
        return nomeUSer;
    }

    public void setNomeUSer(String nomeUSer) {
        this.nomeUSer = nomeUSer;
    }

    public boolean isContaUser() {
        return contaUser;
    }

    public void setContaUser(boolean contaUser) {
        this.contaUser = contaUser;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome2() {
        return nome2;
    }

    public void setNome2(String nome2) {
        this.nome2 = nome2;
    }

    public String getNome3() {
        return nome3;
    }

    public void setNome3(String nome3) {
        this.nome3 = nome3;
    }

    public String getNome4() {
        return nome4;
    }

    public void setNome4(String nome4) {
        this.nome4 = nome4;
    }

    public String getNome5() {
        return nome5;
    }

    public void setNome5(String nome5) {
        this.nome5 = nome5;
    }

    public String getNome6() {
        return nome6;
    }

    public void setNome6(String nome6) {
        this.nome6 = nome6;
    }

    public String getSerieRepeticao() {
        return serieRepeticao;
    }

    public void setSerieRepeticao(String serieRepeticao) {
        this.serieRepeticao = serieRepeticao;
    }

    public String getTempo1() {
        return tempo1;
    }

    public void setTempo1(String tempo1) {
        this.tempo1 = tempo1;
    }

    public String getTecnicaAvancada() {
        return tecnicaAvancada;
    }

    public void setTecnicaAvancada(String tecnicaAvancada) {
        this.tecnicaAvancada = tecnicaAvancada;
    }

    public String getSerieRepeticao2() {
        return serieRepeticao2;
    }

    public void setSerieRepeticao2(String serieRepeticao2) {
        this.serieRepeticao2 = serieRepeticao2;
    }

    public String getTempo2() {
        return tempo2;
    }

    public void setTempo2(String tempo2) {
        this.tempo2 = tempo2;
    }

    public String getTecnicaAvancada2() {
        return tecnicaAvancada2;
    }

    public void setTecnicaAvancada2(String tecnicaAvancada2) {
        this.tecnicaAvancada2 = tecnicaAvancada2;
    }

    public String getSerieRepeticao3() {
        return serieRepeticao3;
    }

    public void setSerieRepeticao3(String serieRepeticao3) {
        this.serieRepeticao3 = serieRepeticao3;
    }

    public String getTempo3() {
        return tempo3;
    }

    public void setTempo3(String tempo3) {
        this.tempo3 = tempo3;
    }

    public String getTecnicaAvancada3() {
        return tecnicaAvancada3;
    }

    public void setTecnicaAvancada3(String tecnicaAvancada3) {
        this.tecnicaAvancada3 = tecnicaAvancada3;
    }

    public String getSerieRepeticao4() {
        return serieRepeticao4;
    }

    public void setSerieRepeticao4(String serieRepeticao4) {
        this.serieRepeticao4 = serieRepeticao4;
    }

    public String getTempo4() {
        return tempo4;
    }

    public void setTempo4(String tempo4) {
        this.tempo4 = tempo4;
    }

    public String getTecnicaAvancada4() {
        return tecnicaAvancada4;
    }

    public void setTecnicaAvancada4(String tecnicaAvancada4) {
        this.tecnicaAvancada4 = tecnicaAvancada4;
    }

    public String getSerieRepeticao5() {
        return serieRepeticao5;
    }

    public void setSerieRepeticao5(String serieRepeticao5) {
        this.serieRepeticao5 = serieRepeticao5;
    }

    public String getTempo5() {
        return tempo5;
    }

    public void setTempo5(String tempo5) {
        this.tempo5 = tempo5;
    }

    public String getTecnicaAvancada5() {
        return tecnicaAvancada5;
    }

    public void setTecnicaAvancada5(String tecnicaAvancada5) {
        this.tecnicaAvancada5 = tecnicaAvancada5;
    }

    public String getSerieRepeticao6() {
        return serieRepeticao6;
    }

    public void setSerieRepeticao6(String serieRepeticao6) {
        this.serieRepeticao6 = serieRepeticao6;
    }

    public String getTempo6() {
        return tempo6;
    }

    public void setTempo6(String tempo6) {
        this.tempo6 = tempo6;
    }

    public String getTecnicaAvancada6() {
        return tecnicaAvancada6;
    }

    public void setTecnicaAvancada6(String tecnicaAvancada6) {
        this.tecnicaAvancada6 = tecnicaAvancada6;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
