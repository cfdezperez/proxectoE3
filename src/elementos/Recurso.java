/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.recursos.RecursosException;
import interfazUsuario.Juego;

/**
 *
 * @author celia y maria
 */
public class Recurso {

    public static final int TRMADERA = Juego.TBOSQUE;
    public static final int TRCOMIDA = Juego.TARBUSTO;
    public static final int TRPIEDRA = Juego.TCANTERA;

    private int tipo; //1 Madera, 2 comida, 3 piedra
    private int capacidad;
    private String nombre;

    //CONSTRUCTORES
    public Recurso() {
        this.tipo = TRMADERA;
        this.capacidad = 50;
        this.nombre = "madera";
    }

    public Recurso(int t1, int cap) {
        this.tipo = t1;
        this.capacidad = cap;
        switch (this.tipo) {
            case TRMADERA:
                this.nombre = "madera";
                break;
            case TRCOMIDA:
                this.nombre = "comida";
                break;
            case TRPIEDRA:
                this.nombre = "piedra";
                break;
            default:
                break;
        }

    }

    public Recurso(Recurso rec) {
        this.tipo = rec.getTipo();
        this.capacidad = rec.getCapacidad();
        this.nombre = rec.getNombre();
    }

    //GETTERS
    public int getTipo() {
        return this.tipo;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setCapacidad(int cap) throws RecursosException {
        if (cap <= 0) {
            throw new RecursosException("La capacidad debe ser mayor que 0.");
        } else {
            this.capacidad = cap;
        }
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
}
