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
public class Grupo extends Personaje {

    private static int[] numeroGrupos = new int[Civilizacion.getNumDeCivilizaciones()];

    public Grupo(Celda c, Civilizacion civil, int salud, int armadura, int ataque, int capacidad) {
        super(c, civil, salud, armadura, ataque, capacidad, true, Juego.TGRUPO);
        numeroGrupos[civil.getIdCivilizacion()]++;
        setNombre("Grupo-" + numeroGrupos[civil.getIdCivilizacion()]);
    }
}
