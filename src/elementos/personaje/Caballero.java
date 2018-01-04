/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.personaje;

import elementos.Civilizacion;
import elementos.Personaje;
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
        // Pone la capacidad de movimiento a 2
        setCapMovimiento(2);
    }

    @Override
    public int danhoAtaque(Personaje enemigo) {
        int danhoCausado = this.getAtaque() - enemigo.getArmadura();

        // Los caballeros hacen el doble de daño a los legionarios y arqueros
        if ((enemigo instanceof Legionario) || (enemigo instanceof Arquero)) {
            danhoCausado *= 2;
        }
        return (danhoCausado);
    }
}
