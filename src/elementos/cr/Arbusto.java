/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.cr;

import elementos.ContRecurso;
import elementos.Recurso;
import excepciones.recursos.NoProcesableException;

/**
 *
 * @author celia
 */
public class Arbusto extends ContRecurso{
    public Arbusto(Recurso rec) {
        super(rec);       
    }


    @Override
    public String toString() {
        return("\n\tContenedor de recursos de tipo Arbusto\n\tCantidad de comida "+this.getRecurso().getCapacidad());
    }

    @Override
    public void procesar() throws NoProcesableException {
        throw new NoProcesableException("Los arbustos no se procesan");
    }
}