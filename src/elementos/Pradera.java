/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.CeldaOcupadaException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Pradera extends ContRecurso{
    public Pradera(Celda c) throws CeldaOcupadaException {
        super(c, new Recurso(Juego.TPRADERA, 0));
        c.setTransitable(true);
    }
}
