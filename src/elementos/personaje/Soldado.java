/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.personaje;

import elementos.Personaje;
import excepciones.ParametroIncorrectoException;
import excepciones.personaje.SoldadoRecException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SolRepararException;
import excepciones.personaje.SolAlmacenarException;

/**
 *
 * @author celia
 */
public abstract class Soldado extends Personaje {
    
    public Soldado(int tipo) throws ParametroIncorrectoException {
        this(100, 50, 75, tipo);
    }
    
    public Soldado(int salud, int armadura, int ataque, int tipo) throws ParametroIncorrectoException {
        super(salud, armadura, ataque, false, tipo);
    }
    
    /**
     *
     * @param direccion
     * @throws SoldadoRecException
     */
    @Override
    public void recolectar(String direccion) throws SoldadoRecException {
        throw new SoldadoRecException("Un soldado no puede recolectar");
    }
    
    @Override
    public void construirEdificio(String nedificio, String direccion) throws SolConstruirException {
        throw new SolConstruirException("Un soldado no puede construír");
    }
    
    @Override
    public void reparar(String direccion) throws SolRepararException {
        throw new SolRepararException("Un soldado no puede reparar");
    }
    
    @Override
    public void almacenar(String direccion) throws SolAlmacenarException {
        throw new SolAlmacenarException("Un soldado no puede almacenar");
    }
}
