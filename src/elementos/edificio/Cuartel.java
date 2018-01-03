/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.edificio;

import elementos.Civilizacion;
import elementos.Edificio;
import elementos.Personaje;
import elementos.personaje.Arquero;
import elementos.personaje.Caballero;
import elementos.personaje.Legionario;
import excepciones.celda.CeldaOcupadaException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.edificio.EdificioException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia y maria
 */
public class Cuartel extends Edificio{
    private static int[] numeroCuarteles = new int[Civilizacion.getNumDeCivilizaciones()];

    public Cuartel() throws CeldaOcupadaException, ParametroIncorrectoException {
        this(10);
    }
    
    public Cuartel(int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        this(100, 50, 40, 50, 2000, capPer);
    }
    
    public Cuartel(int salud, int CRM, int CRP, int CCC, int capAl, int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        super(salud, CRM, CRP, CCC, capAl, Juego.TCUARTEL);
        // Una ciudadela puede crear paisanos y almacednar
        setCrearPaisanos(false);
        setCapPersonajes(capPer);
        setCrearSoldados(true);
        setCapAlmacenar(true);        
    }
    
    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroCuarteles[civil.getIdCivilizacion()]++;
        setNombre("Cuartel-" + numeroCuarteles[civil.getIdCivilizacion()]);
    }

    /**
     * Crea un nuevo personaje (paisano) a la celda
     * @param c La celda
     * @param tipoPersonaje
     * @return
     * @throws ParametroIncorrectoException
     * @throws CeldaEnemigaException
     * @throws NoTransitablebleException
     * @throws FueraDeMapaException
     * @throws excepciones.edificio.EdificioException
     */
    @Override
    public Personaje creaPersonaje(Celda c, String tipoPersonaje) throws ParametroIncorrectoException, CeldaEnemigaException,
            NoTransitablebleException, FueraDeMapaException, EdificioException {
        Personaje s;
        switch(tipoPersonaje.toLowerCase()) {
            case "arquero":
                s = new Arquero();
                break;
            case "caballero":
                s = new Caballero();
                break;
            case "legionario":
                s = new Legionario();
                break;
            default:
                throw new ParametroIncorrectoException("Tipo de soldado desconocido");  
        }
        s.inicializaNombre(Juego.getCivilizacionActiva());
        Juego.getCivilizacionActiva().anhadePersonaje(s);
        c.anhadePersonaje(s);
        return s;
    }

}
