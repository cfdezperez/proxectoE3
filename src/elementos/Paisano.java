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
public class Paisano extends Personaje {
    private static int [] numeroPaisanos = new int[Civilizacion.getNumDeCivilizaciones()];
    

    public Paisano(Celda c, Civilizacion civ) {
        this(c, civ, 100, 25, 25, 150);
    }
    
    public Paisano(Celda c, Civilizacion civil, int salud, int armadura, int ataque, int capacidad) {
        super(c, civil, salud, armadura, ataque, capacidad, true, Juego.TPAISANO);
        numeroPaisanos[civil.getIdCivilizacion()]++;
        setNombre("Paisano-"+numeroPaisanos[civil.getIdCivilizacion()]);
    }
}
