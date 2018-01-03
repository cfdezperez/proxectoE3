/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.cr;

import elementos.ContRecurso;
import elementos.Recurso;
import excepciones.recursos.NoProcesableException;
import interfazUsuario.Juego;

/**
 *
 * @author celia y maria
 */
public class Pradera extends ContRecurso{
    public Pradera() {
        super(new Recurso(Juego.TPRADERA, 0));
        setTransitable(true);
    }
    
    @Override
    public String toString() {
        return("\tContenedor de recursos de tipo Pradera, no productiva");
    }
    
    @Override
    public Recurso procesar() throws NoProcesableException {
        throw new NoProcesableException("Las praderas no se procesan");
    }    
}
