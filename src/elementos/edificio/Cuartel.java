/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.edificio;

import elementos.Civilizacion;
import elementos.Edificio;
import excepciones.celda.CeldaOcupadaException;
import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;

/**
 *
 * @author celia
 */
public class Cuartel extends Edificio{
    private static int[] numeroCuarteles = new int[Civilizacion.getNumDeCivilizaciones()];

    public Cuartel() throws CeldaOcupadaException, ParametroIncorrectoException {
        this(10);
    }
    
    public Cuartel(int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        this(100, 50, 40, 50, 50, 25, 20, capPer);
    }
    
    public Cuartel(int salud, int CRM, int CRP, int ataq, int def, int CCC, int capAl, int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        super(salud, CRM, CRP, ataq, def, CCC, capAl, Juego.TCUARTEL);
        // Una ciudadela puede crear paisanos y almacednar
        setCrearPaisanos(false);
        setCapPersonajes(capPer);
        setCrearSoldados(true);
        setCapAlmacenar(false);        
    }
    
    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroCuarteles[civil.getIdCivilizacion()]++;
        setNombre("Cuartel-" + numeroCuarteles[civil.getIdCivilizacion()]);
    }
}
