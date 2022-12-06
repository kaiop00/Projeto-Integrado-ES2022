package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;

import com.ufc.academiaufc.model.Treino;

import org.junit.Before;
import org.junit.Test;

public class TreinoUnitTest {
    private Treino treino;

    @Before
    public void Treino(){
        treino = new Treino(
                "123","12345","Aluno",false,
                "Peitoral","Supino Reto","3x4",
                "Drop-set","Avançado",false
        );
    }

    @Test
    public void testSetIdTreino(){
        String id = "123";
        treino.setId(id);
        assertEquals("",id, treino.getId());
    }

    @Test
    public void testSetIdUserTreino(){
        String idUser = "12345";
        assertEquals("",idUser, treino.getIdUser());
    }
    @Test
    public void testNomeUserTreino(){
        String nomeUser = "Aluno";
        assertEquals("",nomeUser, treino.getNomeUSer());
    }
    @Test
    public void testContaUserTreino(){
        boolean conta  = false;
        assertEquals("",conta, treino.isContaUser());
    }
    @Test
    public void testTituloTreino(){
        String titulo = "Peitoral";
        assertEquals("",titulo, treino.getTitulo());
    }
    @Test
    public void testNomeExTreino(){
        String nome = "Supino Reto";
        assertEquals("",nome, treino.getNome());
    }
    @Test
    public void testSerieRepeticaoTreino(){
        String serieRepeticao = "3x4";
        assertEquals("",serieRepeticao, treino.getSerieRepeticao());
    }
    @Test
    public void testTecnicaAvancadaTreino(){
        String serieRepeticao = "Drop-set";
        assertEquals("",serieRepeticao, treino.getTecnicaAvancada());
    }
    @Test
    public void testNivelTreino(){
        String nivel = "Avançado";
        assertEquals("",nivel, treino.getNivel());
    }

    @Test
    public void testStatusTreino(){
        boolean status = false;
        assertEquals("", status, treino.isStatus());
    }


}
