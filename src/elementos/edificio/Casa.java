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
        this(100, 50, 40, 50, 50, 25, 20, capAlojar);
    }
    
    /**
     * Crea una casa especificando todos los parámetros
     * 
     * @param salud
     * @param CRM
     * @param CRP
     * @param ataq
     * @param def
     * @param CCC
     * @param capAl
     * @param capAlojar
     * @throws CeldaOcupadaException
     * @throws ParametroIncorrectoException 
     */
    public Casa(int salud, int CRM, int CRP, int ataq, int def, int CCC, int capAl, int capAlojar) throws CeldaOcupadaException, ParametroIncorrectoException {
        super(salud, CRM, CRP, ataq, def, CCC, capAl, Juego.TCASA);
        // Una casa puede alojar personajes
        this.setCapAlojar(true);
        this.setCapAlojamiento(capAlojar);
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
}
