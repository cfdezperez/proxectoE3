/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.personaje;

import elementos.Civilizacion;
import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;

/**
 *
 * @author celia y maria
 */
public class Caballero extends Soldado {

    private static int[] numeroCaballeros = new int[Civilizacion.getNumDeCivilizaciones()];

    public Caballero() throws ParametroIncorrectoException {
        super(Juego.TCABALLERO);
    }

    public Caballero(int salud, int armadura, int ataque) throws ParametroIncorrectoException {
        super(salud, armadura, ataque, Juego.TCABALLERO);
    }

    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroCaballeros[civil.getIdCivilizacion()]++;
        setNombre("Caballero-" + numeroCaballeros[civil.getIdCivilizacion()]);
    }
   
}
