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
public class Arquero extends Soldado{
   private static int [] numeroArqueros = new int[Civilizacion.getNumDeCivilizaciones()];
   
    public Arquero(Celda c, Civilizacion civil){
        // TODO: Poner bien las características de los arqueros
        super(c, civil, Juego.TARQUERO);
        numeroArqueros[civil.getIdCivilizacion()]++;
        setNombre("Arquero-"+numeroArqueros[civil.getIdCivilizacion()]);
    }
}
