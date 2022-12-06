package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ufc.academiaufc.model.Login;
import com.ufc.academiaufc.model.Mensagem;

import org.junit.Before;
import org.junit.Test;

public class LoginUnitTest {
    Login login;

    @Before
    public void Login(){
        login = new Login(

        );
    }

    @Test
    public void testIdLogin() {
        String id = "12312312";
        login.setId(id);
        assertEquals("",id, login.getId());
    }
    @Test
    public void testTipoLogin() {
        String tipo = "academia";
        login.setTipo(tipo);
        assertEquals("",tipo, login.getTipo());
    }

}
