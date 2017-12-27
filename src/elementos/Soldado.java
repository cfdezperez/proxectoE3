/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.ParametroIncorrectoException;
import excepciones.SoldadoRecException;
import excepciones.SolConstruirException;
import excepciones.SolRepararException;
import excepciones.SolAlmacenarException;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Soldado extends Personaje{
    
    public Soldado(Celda c, Civilizacion civ, int tipo) throws ParametroIncorrectoException {
        this(c, civ, 100, 50, 75, tipo);
    }
    
    public Soldado(Celda c, Civilizacion civil, int salud, int armadura, int ataque, int tipo) throws ParametroIncorrectoException {
        // Los soldados no pueden recolectar ni edificar
        super(c, civil, salud, armadura, ataque, false, tipo);
    }
    
    public void recolectar(String direccion) throws SoldadoRecException {
        throw new SoldadoRecException("Un soldado no puede recolectar");
    }
    
    public void construir(String direccion) throws SolConstruirException {
        throw new SolConstruirException("Un soldado no puede construír");
    }
    
    public void reparar(String direccion) throws SolRepararException {
        throw new SolRepararException("Un soldado no puede reparar");
    }
    
    public void almacenar(String direccion) throws SolAlmacenarException {
        throw new SolAlmacenarException("Un soldado no puede almacenar");
    }
}
