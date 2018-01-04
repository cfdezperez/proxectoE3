/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.personaje;

import elementos.Civilizacion;
import elementos.Edificio;
import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia y maria
 */
public class Arquero extends Soldado {

    private static int[] numeroArqueros = new int[Civilizacion.getNumDeCivilizaciones()];

    public Arquero() throws ParametroIncorrectoException {
        super(Juego.TARQUERO);
    }

    public Arquero(int salud, int armadura, int ataque) throws ParametroIncorrectoException {
        super(salud, armadura, ataque, Juego.TARQUERO);
    }

    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroArqueros[civil.getIdCivilizacion()]++;
        setNombre("Arquero-" + numeroArqueros[civil.getIdCivilizacion()]);
    }

    @Override
    public int getAtaque(Edificio enemigo) {
        // Si el que ataca a un edificio es un arquero, el daño se reduce a la mitad
        return ((this.getAtaque() - enemigo.getDefensa())/2);
    }

}
