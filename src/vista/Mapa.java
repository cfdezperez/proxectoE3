/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import elementos.Civilizacion;
import elementos.Personaje;
import elementos.Pradera;
import excepciones.FueraDeMapaException;
import excepciones.ParametroIncorrectoException;
import java.util.ArrayList;
import java.util.List;

import interfazUsuario.Juego;

/**
 *
 * @author tomas
 */
public class Mapa {

    private List<List<Celda>> Celdas;
    private int tamX, tamY;

    public Mapa(int tamX, int tamY) {
        this.tamX = tamX;
        this.tamY = tamY;
        this.Celdas = new ArrayList<List<Celda>>(tamY);

        for (int y = 0; y < tamY; y++) { //Creamos as celdas
            List<Celda> celdasFila = new ArrayList<>(tamX);
            for (int x = 0; x < tamX; x++) {//Imolas recorrendo
                // Creamos una celda vacía
                Celda c = new Celda(this, x, y);
                celdasFila.add(c); //Se inicializan todas las celdas como praderas
            }
            this.Celdas.add(celdasFila);
        }
    }

    /**
     * Obtiene una celda en el mapa
     *
     * @param x Posición x de la celda
     * @param y Posición y de la celda
     * @return La celda en (x,y)
     */
    public Celda obtenerCelda(int x, int y) {
        if (x >= 0 && x < tamX && y >= 0 && y < tamY) {
            return this.Celdas.get(y).get(x);
        } else {
            // TODO: throws new FueraDeMapaException
            return null;
        }
    }

    /**
     * Obtiene la celda inmediatamente vecina a una dada en una cierta dirección
     *
     * @param actual La celda actual
     * @param direccion La dirección a buscar
     * @return La celda vecina
     * @throws excepciones.FueraDeMapaException
     */
    public Celda obtenerCeldaVecina(Celda actual, String direccion) throws FueraDeMapaException, ParametroIncorrectoException {
        return obtenerCeldaVecina(actual, direccion, 1);
    }

    /**
     * Obtiene la celda a una diatancia dada de una celda concreta en una cierta
     * dirección
     *
     * @param actual La celda actual
     * @param direccion La dirección a buscar
     * @distancia La distancia a la que se encuentra la vecina
     * @return La celda vecina
     * @throws excepciones.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     */
    public Celda obtenerCeldaVecina(Celda actual, String direccion, int distancia) throws FueraDeMapaException, ParametroIncorrectoException {
        Celda vecina = null;
        int actualX = actual.getX();
        int actualY = actual.getY();
        switch (direccion.toLowerCase()) {
            case "norte":
                if (actualY - distancia >= 0) {
                    vecina = obtenerCelda(actualX, actualY - distancia);
                } else {
                    throw new FueraDeMapaException("La celda está fuera del mapa");
                }
                break;
            case "sur":
                if (actualY + distancia < this.tamY) {
                    vecina = obtenerCelda(actualX, actualY + distancia);
                } else {
                    throw new FueraDeMapaException("La celda está fuera del mapa");
                }
                break;
            case "este":
                if (actualX + distancia < this.tamX) {
                    vecina = obtenerCelda(actualX + distancia, actualY);
                } else {
                    throw new FueraDeMapaException("La celda está fuera del mapa");
                }
                break;
            case "oeste":
                if (actualX - distancia >= 0) {
                    vecina = obtenerCelda(actualX - distancia, actualY);
                } else {
                    throw new FueraDeMapaException("La celda está fuera del mapa");
                }
                break;
            default:
                throw new ParametroIncorrectoException("Dirección incorrecta");
        }
        return vecina;
    }
    
    /**
     * Mete praderas en todas las celdas vacías
     */
    public void inicializaMapa() {
        for(List<Celda> fila: this.Celdas) {
            for(Celda c: fila) {
                    new Pradera(c);
                    c.setTransitable(true);
                    c.setVisible(false);
            }
        }       
    }

    /**
     * Haz visibles las celdas que rodean a una dada
     *
     * @param c La celda de la que cambiamos su visibilidad
     */
    public void actualizaVisibilidad(Celda c) {
        int x = c.getX();
        int y = c.getY();
        if(x==1 && y==1) {
            System.out.println("Estoy en la celda "+c);
        }
        if (x > 0) {
            this.obtenerCelda(x - 1, y).setVisible(true);
        }
        if (x < (this.tamX - 1)) {
            this.obtenerCelda(x + 1, y).setVisible(true);
        }
        if (y > 0) {
            this.obtenerCelda(x, y - 1).setVisible(true);
        }
        if (y < (this.tamY - 1)) {
            this.obtenerCelda(x, y + 1).setVisible(true);
        }
    }

    /**
     * Imprime el mapa desde el punto de vista de la civilización activa
     */
    public void imprimir() {
        imprimirCivilizacion(Juego.getCivilizacionActiva());
    }
    
    /**
     * Imprime el mapa desde el punto de vista de una civilización
     * @param civil La civilización que queremos ver
     */
    public void imprimirCivilizacion(Civilizacion civil) {
        String raya = " ";
        for (int i = 0; i < 3 * tamX; i++) {
            raya += "-";
        }
        System.out.println(raya);
        // Recorremos las celdas del mapa
        for (List<Celda> c : this.Celdas) {
            System.out.print("|");
            //Vamos recorriendo cada uno de los List, que es cada fila
            for (Celda c1 : c) {

                if (c1.getVisible()) {
                    Civilizacion civcelda = c1.getCivilizacion();
                    if (civcelda == civil || civcelda == null) {
                        if (civcelda != null) {
                            System.out.print(Juego.SIMBOLOS[civcelda.getIdCivilizacion() % Civilizacion.getNumDeCivilizaciones()][c1.getTipoCelda()]);
                        } else {
                            System.out.print(Juego.SIMBOLOS[0][c1.getTipoCelda()]);
                        }
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println("|");
        }

        System.out.println(raya);
    }
}
