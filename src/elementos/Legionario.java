/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Legionario extends Soldado{
    private static int [] numeroLegionarios = new int[Civilizacion.getNumDeCivilizaciones()];
    public Legionario(Celda c, String nombre, Civilizacion civil, int tipo){
        super(c, civil, Juego.TLEGIONARIO);
        numeroLegionarios[civil.getIdCivilizacion()]++;        
        setNombre("Legionario-"+numeroLegionarios[civil.getIdCivilizacion()]);
    }
}
