/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import elementos.personaje.Arquero;
import elementos.cr.Bosque;
import elementos.cr.Cantera;
import elementos.edificio.Ciudadela;
import elementos.Civilizacion;
import elementos.recursos.Madera;
import elementos.personaje.Paisano;
import elementos.recursos.Piedra;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.recursos.NoRecolectableException;
import excepciones.celda.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.personaje.PersonajeException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.recursos.RecursosException;
import excepciones.personaje.SoldadoRecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.Celda;
import vista.Mapa;

/**
 *
 * @author celia
 */
public class Proyecto_final {

    public static void main(String[] args) {

        try {
            Juego j = Menu.cargarFicheros("/home/tomas/Personal/Celia/POO");
            j.getMapa().imprimir();
            System.out.println(j.getMapa().obtenerCelda(0, 1).mirar());
        } catch (ParametroIncorrectoException ex) {
            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FueraDeMapaException ex) {
            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CeldaOcupadaException ex) {
            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CeldaEnemigaException ex) {
            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String[] civs = {"Romana", "Griega"};
//        Juego j = new Juego(5, 10, civs);
//        Mapa mapa = j.getMapa();
//        mapa.imprimir();
//
//        Celda c = mapa.obtenerCelda(2, 1);
//        // Creo un Paisano, le pongo nombre automático, lo añado a la celda y a la civilizacion
//        Paisano p = null;
//        try {
//            p = new Paisano();
//            p.inicializaNombre(Juego.getCivilizacionActiva());
//            Juego.getCivilizacionActiva().anhadePersonaje(p);
//            c.anhadePersonaje(p);
//        } catch (CeldaEnemigaException | ParametroIncorrectoException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        // Creo un arquero, le pongo nombre automático, lo añado a la celda y a la civilizacion
//        c = mapa.obtenerCelda(3, 1);
//        Arquero ar = null;
//        try {
//            ar = new Arquero();
//            ar.inicializaNombre(Juego.getCivilizacionActiva());
//            Juego.getCivilizacionActiva().anhadePersonaje(ar);
//            c.anhadePersonaje(ar);
//        } catch (CeldaEnemigaException | ParametroIncorrectoException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        // Creo una cantera
//        try {
//            c = mapa.obtenerCelda(2, 2);
//            Cantera cant = new Cantera(new Piedra(150));
//            c.anhadeCR(cant);
//        } catch (CeldaOcupadaException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        // Creo una bosque
//        c = mapa.obtenerCelda(4, 1);
//        Bosque bos = new Bosque(new Madera(15));
//        try {
//            c.anhadeCR(bos);
//        } catch (CeldaOcupadaException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        mapa.imprimir();
//        try {
//            p.mover(mapa, "sur");
//        } catch (NoTransitablebleException | FueraDeMapaException ex) {
//            System.out.println("No me puedo mover al sur: " + ex.getMessage());
//        } catch (ParametroIncorrectoException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (CeldaEnemigaException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (CeldaOcupadaException ex) {
//            Logger.getLogger(Proyecto_final.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            p.recolectar("norte");
//        } catch (NoRecolectableException ex) {
//            System.out.println("No recolecto al norte: " + ex.getMessage());
//        } catch (RecursosException | PersonajeLlenoException | FueraDeMapaException | ParametroIncorrectoException ex) {
//            System.out.println("No recolecto al norte: " + ex.getMessage());
//        }
//        try {
//            p.recolectar("sur");
//        } catch (RecursosException | PersonajeLlenoException | FueraDeMapaException | ParametroIncorrectoException ex) {
//            System.out.println("No recolecto al sur: " + ex.getMessage());
//        }
//        try {
//            p.recolectar("este");
//        } catch (RecursosException | CeldaException | PersonajeException | ParametroIncorrectoException ex) {
//            System.out.println("No recolecto al este: " + ex.getMessage());
//        }
//        try {
//            p.mover(mapa, "sur");
//        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException | CeldaOcupadaException ex) {
//            System.out.println("El personaje " + p.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
//        }
//        mapa.imprimir();
//
//        try {
//            ar.recolectar("este");
//        } catch (SoldadoRecException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        try {
//            p.mover(mapa, "norte");
//            p.mover(mapa, "este");
//        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException | CeldaOcupadaException ex) {
//            System.out.println("El personaje " + p.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
//        }
//        try {
//            p.recolectar("este");
//        } catch (RecursosException | CeldaException | PersonajeException | ParametroIncorrectoException ex) {
//            System.out.println("No recolecto al este: " + ex.getMessage());
//        }
//        try {
//            ar.mover(mapa, "este");
//        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException | CeldaOcupadaException ex) {
//            System.out.println("El personaje " + ar.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
//        }
//
//        mapa.imprimir();
//
//        try {
//            ar.mover(mapa, "sur");
//        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException | CeldaOcupadaException ex) {
//            System.out.println("El personaje " + ar.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
//        }
//
//        mapa.imprimir();
//
//        try {
//            ar.mover(mapa, "norte");
//            mapa.imprimir();
//            ar.mover(mapa, "norte");
//            mapa.imprimir();
//            ar.mover(mapa, "norte");
//            mapa.imprimir();
//        } catch (NoTransitablebleException | FueraDeMapaException | ParametroIncorrectoException | CeldaEnemigaException | CeldaOcupadaException ex) {
//            System.out.println("El personaje " + ar.getNombre() + " no se puede mover en la dirección indicada: " + ex.getMessage());
//        }
//
//        try {
//            // Creo una Ciudadela
//            Ciudadela ciud = new Ciudadela();
//            Civilizacion gr = j.getCivilizacion("Griega");
//            ciud.inicializaNombre(gr);
//            gr.anhadeEdificio(ciud);
//            mapa.obtenerCelda(2, 3).anhadeEdificio(ciud);
//            mapa.imprimirVisitadasCivilizacion(j.getCivilizacion("Griega"));
//
//        } catch (ParametroIncorrectoException | CeldaOcupadaException | CeldaEnemigaException ex) {
//            System.out.println("Error creando ciudadela: " + ex.getMessage());
//        }
//
//    }

    }
}
