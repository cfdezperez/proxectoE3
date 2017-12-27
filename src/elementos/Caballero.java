/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Caballero extends Soldado{
    private static int [] numeroCaballeros = new int[Civilizacion.getNumDeCivilizaciones()];
    
    public Caballero(Celda c, Civilizacion civil) throws ParametroIncorrectoException{
        // TODO: Poner bien las características de los arqueros
        super(c, civil, Juego.TCABALLERO);
        numeroCaballeros[civil.getIdCivilizacion()]++;
        setNombre("Caballero-"+numeroCaballeros[civil.getIdCivilizacion()]);
    }
}
