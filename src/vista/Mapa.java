/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import elementos.Personaje;
import java.util.ArrayList;
import java.util.List;

import interfazUsuario.Juego;

/**
 *
 * @author tomas
 */
public class Mapa {
   private List<List<Celda>> Celdas;
   
   public Mapa(int tamX, int tamY) {
        this.Celdas = new ArrayList<List<Celda>>(tamY);
        
        // Inicializamos las celdas a praderas transitables no visibles, no entrables y sin nombre
        for (int y = 0; y < tamY; y++) { //Creamos as celdas
            List<Celda> celdasFila = new ArrayList<>(tamX);
            for (int x = 0; x < tamX; x++) {//Imolas recorrendo
                // Creamos una pradera
                Celda c = new Celda(x, y, new Pradera(), true, false);
                celdasFila.add(c); //Se inicializan todas las celdas como praderas
            }
            this.Celdas.add(celdasFila);
        }
   }
   
   /**
    * Añade un edificio a una celda en una posición determinada
    * @param x Posición x de la celda
    * @param y Posición y de la celda
    * @param p Personaje a añadir
    */
   public void anhadePersonaje(int x, int y, Personaje p) {
       Celda c = new Celda(x, y, p, true, true);
       this.Celdas.get(y).add(x, c);
   }

}
