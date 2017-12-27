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
public class Arbusto extends ContRecurso{
    public Arbusto(Celda c, Recurso rec) throws CeldaOcupadaException {
        super(c, rec);
        c.setTransitable(true);
        setTipo(Juego.TARBUSTO);        
    }
}
