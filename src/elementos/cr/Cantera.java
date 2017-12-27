/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.cr;

import elementos.ContRecurso;
import elementos.Recurso;

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
        return("Cantera");
    }    
}
