package com.ufc.academiaufc.model;

import com.google.firebase.database.DatabaseReference;
import com.ufc.academiaufc.helper.FirebaseHelper;

import java.io.Serializable;

public class Mensagem implements Serializable {

    private String id;
    private String idUser;
    private String nomeUser;
    private String titulo;
    private String msg;
    private String dia;
    private String situacao;
    private String horaInicio;
    private String horaFinal;



    public Mensagem() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("mensagens")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        reference.setValue(this);

        DatabaseReference treinoPublico = FirebaseHelper.getDatabaseReference()
                .child("mensagens_publicas")
                .child(this.getId());
        treinoPublico.setValue(this);
    }

    public void deletar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("mensagens")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        reference.removeValue();

        DatabaseReference treinoPublico = FirebaseHelper.getDatabaseReference()
                .child("mensagens_publicas")
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

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }
}
