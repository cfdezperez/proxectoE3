/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones.personaje;

/**
 * Se lanza si la capacidad de movimiento es diferente de la solicitada
 * 
 * @author celia y maria
 */
public class CapMovimientoException extends MoverException {
    public CapMovimientoException(String m) {
        super(m);
    }
}
