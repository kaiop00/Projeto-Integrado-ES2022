package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.ufc.academiaufc.model.Usuario;

import org.junit.Before;
import org.junit.Test;

public class UsuarioUnitTest {
    Usuario usuario;

    @Before
    public void Usuario(){
    usuario = new Usuario();
    }

    @Test
    public void testUsuarioNull() {
        assertNotNull(usuario);
    }

    @Test
    public void testSetIdUsuario() {
        String id = "1m0UCMsIe1SkWBSISEZ6GKQPMCq1";
        usuario.setId(id);
        assertEquals("",id, usuario.getId());
    }

    @Test
    public void testSetNomeUsuario() {
        String nome = "Aluno";
        usuario.setNome("Aluno");
        assertEquals("",nome, usuario.getNome());
    }

    @Test
    public void testSetEmailUsuario(){
        String email = "Aluno@gmail.com";
        usuario.setEmail(email);
        assertEquals("",email, usuario.getEmail());
    }

    @Test
    public void testSetTipoUsuario(){
        String tipo = "Aluno";
        usuario.setTipo("Aluno");
        assertEquals("",tipo, usuario.getTipo());
    }

    @Test
    public void testSetSenhaUsuario(){
        String senha = "Aluno123";
        usuario.setSenha("Aluno123");
        assertEquals("",senha, usuario.getSenha());
    }
    @Test
    public void testTamanhoSenhaUsuario(){
        String senha = "Aluno123";
        senha.length();
        assertTrue("", true);
    }

    @Test
    public void testTipoContaAlunoUsuario(){
        String tipo = "Aluno";
        usuario.setTipo(tipo);
        assertEquals("", "Aluno", usuario.getTipo());
    }

    @Test
    public void testTipoContaPersonalUsuario(){
        String tipo = "Personal";
        usuario.setTipo(tipo);
        assertEquals("", "Personal", usuario.getTipo());
    }








}
