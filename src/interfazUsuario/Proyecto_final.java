/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import elementos.Cantera;
import elementos.Paisano;
import elementos.Piedra;
import excepciones.CeldaEnemigaException;
import excepciones.CeldaOcupadaException;
import excepciones.FueraDeMapaException;
import excepciones.NoRecolectableException;
import excepciones.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.PersonajeLlenoException;
import excepciones.RecursosException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.Celda;
import vista.Mapa;

/**
 *
 * @author tomas
 */
public class Proyecto_final {

    public static void main(String[] args) throws CeldaOcupadaException {
        String[] civs = {"Romana", "Griega"};
        Juego j = new Juego(5, 10, civs);
        Mapa mapa = j.getMapa();
        mapa.imprimir();

        Celda c = mapa.obtenerCelda(2, 1);
        Paisano p;
        try {
            p = new Paisano(c, Juego.getCivilizacionActiva());
        
        Celda c1 = mapa.obtenerCelda(2,2);
        Cantera cant = new Cantera(c1, new Piedra(150));

        mapa.imprimir();
            try {
                p.recolectar("norte");
            }catch(NoRecolectableException ex){
                System.out.println(ex.getMessage());
            } catch (RecursosException | PersonajeLlenoException | FueraDeMapaException ex) {
                System.out.println("No se puede recolectar esa celda");
            }
            try {
                p.recolectar("sur");
            } catch (RecursosException | PersonajeLlenoException | FueraDeMapaException ex) {
                System.out.println("No pudo recolectar el personaje");
            }
            try {
                p.recolectar("este");
            } catch (RecursosException | PersonajeLlenoException | FueraDeMapaException ex) {
                System.out.println("personaje lleno, no pudo recolectar");
            }
        try {
            p.mover(mapa, "sur");
        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException ex) {
            System.out.println("El personaje " + p.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
        }
        mapa.imprimir();

        try {
            p.mover(mapa, "norte");
        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException ex) {
            System.out.println("El personaje " + p.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
        }
        mapa.imprimir();
    }  catch (ParametroIncorrectoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
