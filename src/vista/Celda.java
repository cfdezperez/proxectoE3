/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import elementos.Personaje;
import java.util.ArrayList;
import java.util.List;

import elementos.Civilizacion;
import elementos.ContRecurso;
import elementos.Edificio;
import interfazUsuario.Juego;

/**
 * Clase que define una celda en el mapa
 *
 * @author celia
 */
public class Celda {

    private int x, y;   // Posición del elemento en la pantalla
    private int tipoCelda;
    private List<Personaje> listaPersonajes = new ArrayList<Personaje>();
    private Edificio edificio = null;
    private ContRecurso contRecurso = null;
    private Civilizacion civilizacion;
    private int numElementos;
    private boolean transitable;
    private boolean visible;

    /**
     * Crea una nueva celda con un personaje dentro
     *
     * @param x_ej Posición x de la celda
     * @param y_ej Posición y de la celda
     * @param personaje Personaje en la celda
     * @param transitable Indica si se puede entrar en la celda
     * @param visible Indica si la celda se va a visualizar
     */
    public Celda(int x_ej, int y_ej, Personaje personaje, boolean transitable, boolean visible) {
        this.x = x_ej;
        this.y = y_ej;
        this.listaPersonajes.add(personaje);
        this.civilizacion = personaje.getCivilizacion();
        this.transitable = transitable;
        this.visible = visible;
        setTipo();
    }

    /**
     * Crea una nueva celda con un edificio dentro
     *
     * @param x_ej Posición x de la celda
     * @param y_ej Posición y de la celda
     * @param edificio Edificio en la celda
     * @param transitable Indica si se puede entrar en la celda
     * @param visible Indica si la celda se va a visualizar
     */
    public Celda(int x_ej, int y_ej, Edificio edificio, boolean transitable, boolean visible) {
        this.x = x_ej;
        this.y = y_ej;
        this.edificio = edificio;
        this.civilizacion = edificio.getCivilizacion();
        this.transitable = transitable;
        this.visible = visible;
        setTipo();        
    }

    /**
     * Crea una nueva celda con un contenedor de recursos dentro
     *
     * @param x_ej Posición x de la celda
     * @param y_ej Posición y de la celda
     * @param contenedor Contenedor de recursos en la celda
     * @param transitable Indica si se puede entrar en la celda
     * @param visible Indica si la celda se va a visualizar
     */
    public Celda(int x_ej, int y_ej, ContRecurso contenedor, boolean transitable, boolean visible) {
        this.x = x_ej;
        this.y = y_ej;
        this.contRecurso = contenedor;
        this.civilizacion = null;
        this.transitable = transitable;
        this.visible = visible;
        setTipo();
    }

    //GETTERS Y SETTERS
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getTipoCelda() {
        return this.tipoCelda;
    }

    public Civilizacion getCivilizacion() {
        return this.civilizacion;
    }

    public int getNumElementos() {
        int count = 0;
        if (contRecurso != null) {
            return 1;
        } else if (edificio != null) {
            count = 1;
        }
        return count + this.listaPersonajes.size();
    }

    public boolean getTransitable() {
        return this.transitable;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setX(int x_ej) {
        if (x_ej > 0) {
            this.x = x_ej;
        } else {
            this.x = 0;
        }
    }

    public void setY(int y_ej) {
        if (y_ej > 0) {
            this.y = y_ej;
        } else {
            this.y = 0;
        }
    }

    public void setTipoCelda(int tipo) {
        this.tipoCelda = tipo;
    }

    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setNumeroElementos(int n) {
        this.numElementos = n;
    }

    public void setCivilizacion(Civilizacion civ) {
        this.civilizacion = civ;
    }


    //FUNCIONES
    //Devuelve una cadena con las coordenadas de la celdas
    @Override
    public String toString() {
        String s = "(" + this.getX() + "," + this.getY() + ")";
        return s;
    }

    public void elimina(Personaje p) {
        this.listaPersonajes.remove(p);
        // Fijo el tipo después de eliminar el personaje
        this.setTipo();
    }

    /**
     * Reinicializa la lista de personajes de la celda
     */
    public void restartPersonajes() {
        this.listaPersonajes = new ArrayList<Personaje>();
    }

    private void setTipo() {
        // Si se queda sin elementos, la convierto en pradera
        if (this.getNumElementos() <= 0) {
            this.setTipoCelda(Juego.TPRADERA);
            this.setTransitable(true);
            this.setVisible(true);
            this.setCivilizacion(null);
        } else if (getNumElementos() > 1) { // Queda más de un elemento
            this.setTipoCelda(Juego.TVARIOS);

        } else { // Si queda solo un tipo de elemento, miramos si es un edificio o un personaje
            if(this.edificio != null) {
                this.setTipoCelda(edificio.getTipo());
            } else {
                this.setTipoCelda(listaPersonajes.get(0).getTipo());
            }
        }
    }
}
