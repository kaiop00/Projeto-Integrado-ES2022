package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ufc.academiaufc.model.Favorito;
import com.ufc.academiaufc.model.Mensagem;

import org.junit.Before;
import org.junit.Test;

public class MensagemUnitTest {
    Mensagem mensagem;

    @Before
    public void Mensagem(){
        mensagem = new Mensagem(
                "frfrf","test1","Denilson","feriado","hoje é feriado","12","fechado","",""
        );
    }

    @Test
    public void testIdMensagem() {
        assertNotNull(mensagem.getId());
    }
    @Test
    public void testIdUserMensagem() {
        assertNotNull(mensagem.getIdUser());
    }
    @Test
    public void testNomeUserMensagem() {
        String nome = "Denilson";
        assertEquals("",nome, mensagem.getNomeUser());
    }
    @Test
    public void testTituloMensagem() {
        assertEquals("","feriado", mensagem.getTitulo());
    }
    @Test
    public void testMsgMensagem() {
        assertEquals("","hoje é feriado", mensagem.getMsg());
    }
    @Test
    public void testDiaMensagem() {
        String Dia = "12";
        assertEquals("",Dia, mensagem.getDia());
    }
    @Test
    public void testSituacaoMensagem() {
        String Situacao = "fechado";
        assertEquals("",Situacao, mensagem.getSituacao());
    }
    @Test
    public void testHorarioInicalMensagem() {
        assertEquals("","", mensagem.getHoraInicio());
    }
    @Test
    public void testHorarioFinalMensagem() {
    assertEquals("","", mensagem.getHoraFinal());
    }



}
