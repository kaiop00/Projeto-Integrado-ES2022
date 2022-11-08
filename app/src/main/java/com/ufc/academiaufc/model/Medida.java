package com.ufc.academiaufc.model;

import com.google.firebase.database.DatabaseReference;
import com.ufc.academiaufc.helper.FirebaseHelper;

import java.io.Serializable;

public class Medida implements Serializable {

    private String id;
    private String idUser;
    private String nomeUser;
    private String medidaBracoD;
    private String medidaBracoE;
    private String objetivo;
    private String mes;
    private String ano;




    public Medida() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("medidas")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        reference.setValue(this);
    }
    public  void deletar(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("medidas")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getId());
        reference.removeValue();
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

    public String getMedidaBracoD() {
        return medidaBracoD;
    }

    public void setMedidaBracoD(String medidaBracoD) {
        this.medidaBracoD = medidaBracoD;
    }

    public String getMedidaBracoE() {
        return medidaBracoE;
    }

    public void setMedidaBracoE(String medidaBracoE) {
        this.medidaBracoE = medidaBracoE;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
