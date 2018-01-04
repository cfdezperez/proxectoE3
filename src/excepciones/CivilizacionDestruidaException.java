/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Se lanza cuando se destruyen todas las ciudadelas de una civilizacion
 * 
 * @author celia y maria
 */
public class CivilizacionDestruidaException extends Exception {
    public CivilizacionDestruidaException(String m) {
        super(m);
    }
}
