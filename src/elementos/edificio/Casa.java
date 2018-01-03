/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.edificio;

import elementos.Civilizacion;
import elementos.Edificio;
import elementos.Personaje;
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
public class Casa extends Edificio{
    private static int[] numeroCasas = new int[Civilizacion.getNumDeCivilizaciones()];
    
    /**
     * Crea una casa con parámetros por defecto.
     * 
     * @throws CeldaOcupadaException
     * @throws ParametroIncorrectoException 
     */
    public Casa() throws CeldaOcupadaException, ParametroIncorrectoException {
        this(10);
    }
    
    /**
     * Crea una casa especificando la capacidad de alojamiento
     * 
     * @param capAlojar Número de personajes que puede alojar
     * @throws CeldaOcupadaException
     * @throws ParametroIncorrectoException 
     */
    public Casa(int capAlojar) throws CeldaOcupadaException, ParametroIncorrectoException {
        this(10, 50, 40, 25, 20, capAlojar);
    }
    
    /**
     * Crea una casa especificando todos los parámetros
     * 
     * @param salud
     * @param CRM
     * @param CRP
     * @param CCC
     * @param capAl
     * @param capAlojar
     * @throws CeldaOcupadaException
     * @throws ParametroIncorrectoException 
     */
    public Casa(int salud, int CRM, int CRP, int CCC, int capAl, int capAlojar) throws CeldaOcupadaException, ParametroIncorrectoException {
        super(salud, CRM, CRP, CCC, capAl, Juego.TCASA);
        // Una casa puede alojar personajes
        this.setCapAlojar(true);
        this.setCapAlojamiento(capAlojar);
        setCapPersonajes(capAlojar);
        Edificio.addCapAlojamientoTotal(capAlojar);
        // Una casa no puede crear paisanos ni soldados ni almacednar
        setCrearPaisanos(false);
        setCrearSoldados(false);
        setCapAlmacenar(false);        
    }
    

    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroCasas[civil.getIdCivilizacion()]++;
        setNombre("Casa-" + numeroCasas[civil.getIdCivilizacion()]);
    }    

    @Override
    public Personaje creaPersonaje(Celda vecina, String tipoPersonaje) throws EdificioException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
        throw new EdificioException("Las casas no crean personajes");
    }
}
