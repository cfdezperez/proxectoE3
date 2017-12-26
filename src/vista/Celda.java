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
import elementos.Pradera;
import interfazUsuario.Juego;

/**
 * Clase que define una celda en el mapa
 *
 * @author celia
 */
public class Celda {

    private int x, y;   // Posición del elemento en la pantalla
    private Mapa mapa;
    private int tipoCelda;
    private List<Personaje> listaPersonajes = new ArrayList<Personaje>();
    private Edificio edificio = null;
    private ContRecurso contRecurso = null;
    private Civilizacion civilizacion;
    private boolean transitable = true;
    private boolean visible = false;

    /**
     * Crea una nueva celda vacía en un mapa
     * 
     * @param m El mapa donde colocamos la celda
     * @param x Posición x de la celda
     * @param y Posición y de la celda
     */
    public Celda(Mapa m, int x, int y) {
        this.x = x;
        this.y = y;
        this.mapa = m;
        setTipo();
    }

    //GETTERS Y SETTERS
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public Mapa getMapa() {
        return this.mapa;
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
    
    public Edificio getEdificio() {
        return this.edificio;
    }
    
    public ContRecurso getContRecurso() {
        return this.contRecurso;
    }
    
    public List<Personaje> getPersonajes() {
        return this.listaPersonajes;
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

    public void setCivilizacion(Civilizacion civ) {
        this.civilizacion = civ;
    }

    public void setContRecurso(ContRecurso x){
        this.contRecurso = x;
    }
    //FUNCIONES

    /**
     * Añade un personaje a la celda
     *
     * @param p Personaje a añadir
     */
    public void anhadePersonaje(Personaje p) {
        if (getTransitable()) {
            // Si es una pradera, la elimino
            if(this.contRecurso.getTipo() == Juego.TPRADERA) {
                this.contRecurso = null;
            }
            this.listaPersonajes.add(p);
            setVisible(true);
            mapa.actualizaVisibilidad(this);
            setTipo();
        } else {
            // TODO: throws new CeldaNoTransitableException
            System.out.println("No puedo añadir un elemento = "+getNumElementos());
        }
    }

    /**
     * Añade un edificio a la celda
     *
     * @param e Edificio a añadir
     */
    public void anhadeEdificio(Edificio e) {
        if (getTransitable()) {
            if (this.edificio == null) {
                this.edificio = e;
                setTipo();
            } else {
                // TODO: throws new CeldaOcupadaException
            }
        } else {
            // TODO: throws new CeldaNoTransitableException
        }
    }
    /**
     * Añade un contenedor de recursos a la celda
     *
     * @param cr Contenedor a añadir
     */
    public void anhadeCR(ContRecurso cr) {
        if ((this.listaPersonajes.isEmpty()) && (this.edificio == null)) {
            this.contRecurso = cr;
            setTipo();
        } else {
            // TODO: throws new CeldaOcupadaException
        }
    }    

    
    //Devuelve una cadena con las coordenadas de la celdas
    @Override
    public String toString() {
        String s = "(" + this.getY() + "," + this.getX() + ")";
        return s;
    }

    public void eliminarPersonaje(Personaje p) {
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

    /**
     * Reinicializa la celda
     */
    public void restartCelda(){
        this.restartPersonajes();
        this.edificio = null;
        this.contRecurso = new Pradera(this);
        this.tipoCelda = Juego.TPRADERA;
        this.civilizacion = null;
        this.transitable = true;
    }
    
    private void setTipo() {
        // Si no tiene elementos, la convierto en pradera
        if(getNumElementos() <= 0) {
            new Pradera(this);
            this.setVisible(true);
        } else if (getNumElementos() > 1) { // Queda más de un elemento
            this.setTipoCelda(Juego.TVARIOS);
        }
        else { // Si queda solo un tipo de elemento, miramos si es un CR, edificio o un personaje
            if(this.contRecurso != null) {
                this.setTipoCelda(this.contRecurso.getTipo());
            } else if (this.edificio != null) {
                this.setTipoCelda(this.edificio.getTipo());
            } else if (!this.getPersonajes().isEmpty()) {
                this.setTipoCelda(this.listaPersonajes.get(0).getTipo());
            }
        }
    }
}
