package com.ufc.academiaufc;

import static org.junit.Assert.assertNotNull;

import com.ufc.academiaufc.model.Favorito;
import com.ufc.academiaufc.model.Usuario;

import org.junit.Before;
import org.junit.Test;

public class FavoritoUnitTest {
    Favorito favorito;

    @Before
    public void Favorito(){
        favorito = new Favorito();
    }

    @Test
    public void testFavoritoNull() {
        assertNotNull(favorito);
    }


}
