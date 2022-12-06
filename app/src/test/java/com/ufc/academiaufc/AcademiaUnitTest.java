package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;

import com.ufc.academiaufc.model.Academia;

import org.junit.Before;
import org.junit.Test;

public class AcademiaUnitTest {
    private Academia academia;

    @Before
    public void Academia(){
        academia = new Academia();
    }

    @Test
    public void testSetIdAcademia(){
        String id = "1m0UCMsIe1SkWBSISEZ6GKQPMCq1";
        academia.setId(id);
        assertEquals("",id, academia.getId());
    }
    @Test
    public void  testSetNomeAcademia(){
        String nome = "Academia+";
        academia.setNome(nome);
        assertEquals("",nome, academia.getNome());
    }
    @Test
    public void testSetCodigoAcademia(){
        String codigo = "123456";
        academia.setCodigo(codigo);
        assertEquals("",codigo, academia.getCodigo());
    }
    @Test
    public void testSetEmailAcademia(){
        String email = "academia@gmail.com";
        academia.setEmail(email);
        assertEquals("",email, academia.getEmail());
    }
    @Test
    public void testSetSenhaAcademia(){
        String senha = "123456";
        academia.setSenha(senha);
        assertEquals("",senha, academia.getSenha());
    }
    @Test
    public void testSetTipoConta(){
        boolean tipo = true;
        academia.setTipoConta(tipo);
        assertEquals("",tipo, academia.isTipoConta());
    }


}
