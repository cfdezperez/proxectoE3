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
public class Cantera extends ContRecurso{
    public Cantera(Celda c, Recurso rec){
        super(c, rec);
        c.setTransitable(false);
        setTipo(Juego.TCANTERA);        
    }
}
