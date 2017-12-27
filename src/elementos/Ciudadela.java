/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.CeldaOcupadaException;
import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Ciudadela extends Edificio {

    private static int[] numeroCiudadelas = new int[Civilizacion.getNumDeCivilizaciones()];

    public Ciudadela() throws CeldaOcupadaException, ParametroIncorrectoException {
        this(10);
    }
    
    public Ciudadela(int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        this(100, 50, 40, 50, 50, 25, 20, capPer);
    }
    
    public Ciudadela(int salud, int CRM, int CRP, int ataq, int def, int CCC, int capAl, int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        super(salud, CRM, CRP, ataq, def, CCC, capAl, Juego.TCIUDADELA);
        // Una ciudadela puede crear paisanos y almacednar
        setCrearPaisanos(true);
        setCapPersonajes(capPer);
        setCrearSoldados(false);
        setCapAlmacenar(false);        
    }
    
    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroCiudadelas[civil.getIdCivilizacion()]++;
        setNombre("Ciudadela-" + numeroCiudadelas[civil.getIdCivilizacion()]);
    }
}
