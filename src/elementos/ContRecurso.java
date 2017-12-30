/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.recursos.NoProcesableException;
import excepciones.recursos.RecursosException;
import interfazUsuario.Juego;
import vista.*;

/**
 *
 * @author celia y maria
 */
public abstract class ContRecurso {

    private Celda celda;
    private Recurso recurso;
    private String nombre;
    private int tipoContenedor;
    private boolean transitable;

    //CONSTRUCTORES
    public ContRecurso(Recurso rec) {
        //this.celda = c;
        this.recurso = new Recurso(rec);
        this.tipoContenedor = this.recurso.getTipo();
        this.transitable=false;
        //c.setCivilizacion(null);
        //c.anhadeCR(this);
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

    public boolean esTransitable(){
        return this.transitable;
    }

    //Solo se puede cambiar el recurso o tipo cuando llega a 0 y se transforma en pradera
    public void setRecurso(int tipo) throws RecursosException {
        if (tipo == Juego.TPRADERA) {
            this.recurso = new Recurso();
        } else {
            throw new RecursosException("No se puede modificar el recurso del contenedor\n");
        }
    }

    public void setTipo(int tipo) throws RecursosException {
        if (tipo == Juego.TPRADERA) {
            this.tipoContenedor = tipo;
        } else {
            throw new RecursosException("No se puede modificar el recurso del contenedor\n");
        }
    }
    
    public void setCelda(Celda c) {
        this.celda = c;
    }
    
    public void setTransitable(boolean a){
        this.transitable=a;
    }
    
    public abstract void procesar() throws NoProcesableException;
}
