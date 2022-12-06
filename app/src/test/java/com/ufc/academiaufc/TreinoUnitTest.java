package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ufc.academiaufc.model.Treino;
import com.ufc.academiaufc.model.Usuario;

import org.junit.Before;
import org.junit.Test;

public class TreinoUnitTest {
    Treino treino;
    Usuario usuario;

    @Before
    public void Treino(){
        treino = new Treino();
    }
    @Before
    public void Usuario(){
        usuario = new Usuario();
    }

    @Test
    public void testUsuarioNull() {
        assertNotNull(treino);
    }

    @Test
    public void testSetIdTreino() {
        String id = "-NGERobDoh6Uz1Wwy-gE";
        treino.setId(id);
        assertEquals("",id, treino.getId());
    }
    @Test
    public void testSetIdUserTreino() {
        String idUser = "6jNLHdXaY2ZnJZOhYCGQ9kkaxUk1";
        treino.setId(idUser);
        assertEquals("",idUser, treino.getId());
    }
    @Test
    public void testSetNomeUserTreino() {
        String nomeUser = "Carol";
        treino.setNomeUSer(nomeUser);
        assertEquals("",nomeUser, treino.getNomeUSer());
    }

    @Test
    public void testSetNivelTreino(){
        String nivel = "Avançado";
        treino.setNivel(nivel);
        assertEquals("",nivel,treino.getNivel());
    }

    @Test
    public void testSetContaUserTreino(){
        boolean contaUser = false;
        treino.setContaUser(contaUser);
        assertEquals("",contaUser,treino.isContaUser());
    }

    @Test
    public void testSetTituloTreino(){
        String titulo = "Peitoral";
        treino.setTitulo(titulo);
        assertEquals("",titulo,treino.getTitulo());
    }
    @Test
    public void testSetNomeTreino(){
        String nomeTreino = "Supino Reto";
        treino.setNome(nomeTreino);
        assertEquals("",nomeTreino,treino.getNome());
    }
    @Test
    public void testSetSerieRepeticaoTreino(){
        String serieRepeticao = "4x3";
        treino.setSerieRepeticao(serieRepeticao);
        assertEquals("",serieRepeticao,treino.getSerieRepeticao());
    }

    @Test
    public void testSetStatusTreino(){
        boolean status = false;
        treino.setStatus(status);
        assertEquals("",status,treino.isStatus());
    }
    @Test
    public void testSetTecnicaAvancadaTreino(){
        String tecnica = "Drop-set";
        treino.setTecnicaAvancada(tecnica);
        assertEquals("",tecnica,treino.getTecnicaAvancada());
    }



}
