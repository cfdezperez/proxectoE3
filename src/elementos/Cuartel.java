/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.CeldaOcupadaException;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Cuartel extends Edificio{
    public Cuartel(Celda c, String nombre, Civilizacion civil, int tipo) throws CeldaOcupadaException{
        super(c, nombre, civil, tipo);
    }
}
