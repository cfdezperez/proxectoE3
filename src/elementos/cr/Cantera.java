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
public class Cantera extends ContRecurso{
    public Cantera(Recurso rec) {
        super(rec);       
    }
    
    @Override
    public String toString() {
        return("\n\tContenedor de recursos de tipo Cantera\n\tCantidad de piedra "+this.getRecurso().getCapacidad());
    }    

    @Override
    public void procesar() throws NoProcesableException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
}
