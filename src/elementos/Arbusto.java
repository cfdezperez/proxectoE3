/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

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
        return("Arbusto");
    }
}