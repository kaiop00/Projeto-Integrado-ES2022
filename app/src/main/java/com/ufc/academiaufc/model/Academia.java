package com.ufc.academiaufc.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.ufc.academiaufc.helper.FirebaseHelper;

public class Academia {

    private String id;
    private String nome;
    private String codigo;
    private String email;
    private String senha;
    private boolean tipoConta;



    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("academia")
                .child(this.getId());
        reference.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(boolean tipoConta) {
        this.tipoConta = tipoConta;
    }
}
