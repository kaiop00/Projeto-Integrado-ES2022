package com.ufc.academiaufc.model;

import com.google.firebase.database.DatabaseReference;
import com.ufc.academiaufc.helper.FirebaseHelper;

public class Login {
    private String id;
    private String tipo;


    public Login(){

    }

    public Login(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public void salvar(){
        DatabaseReference longinRef = FirebaseHelper.getDatabaseReference()
                .child("login")
                .child(getId());
        longinRef.setValue(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
