/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import vista.*;

/**
 *
 * @author celia
 */
public class ContRecurso {

    private Celda celda;
    private Recurso recurso;
    private String nombre;
    private int tipoContenedor;

    //CONSTRUCTORES
    public ContRecurso(Celda c, Recurso rec) {
        this.celda = c;
        this.recurso = new Recurso(rec);
        this.tipoContenedor = this.recurso.getTipo();
        c.setCivilizacion(null);
        c.anhadeCR(this);
    }

    //GETTERS Y SETTERS    
    public Celda getCelda() {
        return this.celda;
    }

    public Recurso getRecurso() {
        return this.recurso;
    }

    public String getNombre() {
        return this.recurso.getNombre();
    }

    public int getTipo() {
        return this.tipoContenedor;
    }


    //Solo se puede cambiar el recurso o tipo cuando llega a 0 y se transforma en pradera
    public void setRecurso(int tipo) {
        if (tipo == 0) {
            this.recurso = new Recurso();
        } else {
            System.out.println("No se puede modificar el recurso del contenedor\n");
        }
    }

    public void setTipo(int tipo) {
        if (tipo == 0) {
            this.tipoContenedor = tipo;
        } else {
            System.out.println("No se puede modificar el recurso del contenedor\n");
        }
    }

}
