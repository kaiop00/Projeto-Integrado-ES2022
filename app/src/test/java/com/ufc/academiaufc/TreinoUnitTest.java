package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ufc.academiaufc.model.Treino;


import org.junit.Before;
import org.junit.Test;

public class TreinoUnitTest {
    Treino treino;


    @Before
    public void Treino(){
        treino = new Treino();
        treino.setId("123");

    }

    @Test
    public void testTreinoNull() {
        assertNotNull(treino);
    }

    @Test
    public void testSetIdTreino() {
        String id = "123";
        treino.setId(id);
        assertEquals("",id, treino.getId());
    }




}
