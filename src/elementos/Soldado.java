/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import vista.Celda;

/**
 *
 * @author celia
 */
public class Soldado extends Personaje{
    
    public Soldado(Celda c, Civilizacion civ, int tipo) {
        this(c, civ, 100, 50, 75, tipo);
    }
    
    public Soldado(Celda c, Civilizacion civil, int salud, int armadura, int ataque, int tipo) {
        // Los soldados no pueden recolectar ni edificar
        super(c, civil, salud, armadura, ataque, false, tipo);
    }
}
