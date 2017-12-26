/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import excepciones.CeldaEnemigaException;
import excepciones.FueraDeMapaException;
import excepciones.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;

/**
 *
 * @author tomas
 */
public interface Comando {
    public void mover(String nombre, int direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException;
    
}
