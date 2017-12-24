/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import interfazUsuario.Juego;
/**
 *
 * @author celia
 */
public class Recurso {
    public static final int TRMADERA=Juego.TBOSQUE;
    public static final int TRCOMIDA=Juego.TARBUSTO;
    public static final int TRPIEDRA=Juego.TCANTERA;
    
    private int tipo; //1 Madera, 2 comida, 3 piedra
    private int capacidad;
    private String nombre;
  
    //CONSTRUCTORES
    
    public Recurso(){
        this.tipo = TRMADERA;
        this.capacidad = 50;
        this.nombre = "madera";
    }
    
    public Recurso(int t1, int cap){
        this.tipo = t1;
        this.capacidad = cap;
        if(this.tipo == TRMADERA)
                    this.nombre = "madera";
        else if(this.tipo == TRCOMIDA)
                    this.nombre = "comida";
        else if(this.tipo == TRPIEDRA)
            this.nombre = "piedra";

    }
    
    public Recurso(Recurso rec){
        this.tipo = rec.getTipo();
        this.capacidad= rec.getCapacidad();
        this.nombre = rec.getNombre();
    }
    
    //GETTERS
    
    public int getTipo(){
        return this.tipo;
    }
    
    public int getCapacidad() {
        return this.capacidad;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    public void setCapacidad(int cap) {
        if (cap <= 0) {
            this.capacidad = 0;
            System.out.println("El contenedor de recursos se transforma en pradera\n");
            this.tipo = 0;
        } else {
            this.capacidad = cap;
        }
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
}
