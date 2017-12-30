/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.personaje;

import elementos.Civilizacion;
import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia y maria
 */
public class Legionario extends Soldado{
    private static int [] numeroLegionarios = new int[Civilizacion.getNumDeCivilizaciones()];
    
    public Legionario() throws ParametroIncorrectoException{
        super(Juego.TLEGIONARIO);
    }
    
    public Legionario(int salud, int armadura, int ataque) throws ParametroIncorrectoException {
        super(salud, armadura, ataque, Juego.TLEGIONARIO);
    }
    
    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroLegionarios[civil.getIdCivilizacion()]++;        
        setNombre("Legionario-"+numeroLegionarios[civil.getIdCivilizacion()]);        
    }
}
