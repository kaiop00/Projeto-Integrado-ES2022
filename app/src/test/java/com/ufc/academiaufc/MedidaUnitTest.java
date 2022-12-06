package com.ufc.academiaufc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.ufc.academiaufc.model.Login;
import com.ufc.academiaufc.model.Medida;

import org.junit.Before;
import org.junit.Test;

public class MedidaUnitTest {
    Medida medida;

    @Before
    public void Medida(){
        medida = new Medida(
                "123","12312","deni","43","42","ganhar peso","6","2022"
        );
    }

    @Test
    public void testIdMedida() {
        assertNotNull(medida.getId());
    }
    @Test
    public void testIdUserMedida() {
        assertNotNull(medida.getIdUser());
    }
    @Test
    public void testNomeUserMedida() {
        assertEquals("","deni",medida.getNomeUser());
    }
    @Test
    public void testMedidaBracoEMedida() {
        assertEquals("","42",medida.getMedidaBracoE());
    }
    @Test
    public void testMedidaBracoDMedida() {
        assertEquals("","43",medida.getMedidaBracoD());
    }
    @Test
    public void testObjetivoMedida() {
        assertEquals("","ganhar peso",medida.getObjetivo());
    }
    @Test
    public void testMesMedida() {
        assertEquals("","6",medida.getMes());
    }
     @Test
    public void testAnoMedida() {
        assertEquals("","2022",medida.getAno());
    }

}
