/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;
import vista.Celda;
import interfazUsuario.Juego;

/**
 *
 * @author celia
 */
public class Personaje {
    private Celda celda;
    private String nombre;
    private Civilizacion civilizacion;
    private boolean estado; // True vivo, False Muerto
    private int tipoPersonaje;
    private int salud;
    private final int saludInicial;
    private int armadura;
    private int ataque;
    private int[] capRecoleccion = new int[4]; //0 capacidad total, 1 capacidad madera, 
    //2 capacidad comida, 3 capacidad piedra
    private final int capRecoleccionInicial;
    private boolean estarGrupo;

    //CONSTRUCTORES
   
    /**
     * Crea un personaje en una celda, con un nombre, un tipo y asociado a una
     * civilización
     *
     * @param c Celda en la que se sitúa el personaje
     * @param nombre Nombre del personaje, sin número (Paisano o Soldado)
     * @param civil Civilización a la que pertenece
     * @param tipo Tipo del personaje
     */
    public Personaje(Celda c, String nombre, Civilizacion civil, int tipo) {
        this(c, nombre, civil, tipo, 50, 25, 25, 150);
    }

    /**
     *
     * @param celda
     * @param nombre
     * @param civil
     * @param tipo
     * @param salud
     * @param armadura
     * @param ataque
     * @param capacidad
     */
    public Personaje(Celda celda, String nombre, Civilizacion civil, int tipo, int salud, int armadura, int ataque, int capacidad) {
        // Si la capacidad de recolección o la salud es 0, estoy muerto TRANSFORMAR EN EXCEPCION
        if (salud <= 0) {
            this.estado = false;
            this.capRecoleccionInicial = 0;
            this.saludInicial = salud;
        } else {
            this.celda = celda;
            this.nombre = nombre;
            this.civilizacion = civil;
            this.estado = true;
            this.salud = salud;
            this.saludInicial = salud;
            this.armadura = armadura < 0 ? 0 : armadura;
            this.ataque = ataque < 0 ? 0 : ataque;
            this.tipoPersonaje = tipo;

            //Es un soldado por lo que no tiene capacidad de recolección ni edificación TRANSFORMAR EN EXCEPCION
//            if (this.tipoPersonaje == Juego.TSOLDADO) {
//                this.capRecoleccion[0] = 0;
//            } else if (this.tipoPersonaje == Juego.TPAISANO && capacidad <= 0) {
//                this.estado = false;
//            } else {
//                this.capRecoleccion[0] = capacidad;
//            }
            this.capRecoleccionInicial = this.capRecoleccion[0];
            this.estarGrupo = false;
        }
    }

    //GETTERS Y SETTERS
    /**
     * Obtiene la celda en la que se encuentra el personaje
     *
     * @return La celda en la que se encuentra el personaje
     */
    public Celda getCelda() {
        return this.celda;
    }

    /** 
     * Obtiene el nombre del personaje
     * 
     * @return String ombre personaje
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Obtiene la civilizacion a la que pertenece el personaje
     * 
     * @return Civilizacion a la que pertenece personaje
     */
    public Civilizacion getCivilizacion() {
        return this.civilizacion;
    }

//    /**
//     * Estado del personaje
//     * 
//     * @return boolean determinando su estado
//     */
//    public boolean getEstado() {
//        return this.estado;
//    }

    /**
     * Tipo de personaje: paisano, soldado o grupo
     * 
     * @return int con tipo
     */
    public int getTipo() {
        return this.tipoPersonaje;
    }

    /**
     * Salud personaje
     * 
     * @return int salud personaje
     */
    public int getSalud() {
        return this.salud;
    }

    /**
     * Armadura personaje
     * 
     * @return int armadura personaje
     */
    public int getArmadura() {
        return this.armadura;
    }

    /**
     * Ataque personaje
     * 
     * @return int ataque personaje
     */
    public int getAtaque() {
        return this.ataque;
    }

    /**
     * Capacidad recoleccion total del personaje
     * 
     * @return int capacidad recolección total
     */
    public int getCapRecoleccion() {
        return this.capRecoleccion[0];
    }

    /**
     * Capacidad de recolección para la madera, cantidad de madera que tiene
     * 
     * @return int capacidad madera
     */
    public int getMadera() {
        return this.capRecoleccion[Recurso.TRMADERA];
    }

    /**
     * Capacidad de recolección para la comida, cantidad de comida que tiene
     * 
     * @return int capacidad comida
     */
    public int getComida() {
        return this.capRecoleccion[Recurso.TRCOMIDA];
    }

    /**
     * Capacidad de recolección para la piedra, cantidad de piedra que tiene
     * 
     * @return int capacidad piedra
     */
    public int getPiedra() {
        return this.capRecoleccion[Recurso.TRPIEDRA];
    }

    public boolean getEstarGrupo() {
        return this.estarGrupo;
    }

    public int getCRInicial() {
        return this.capRecoleccionInicial;
    }

    public int getRecursoTipo(int tipo) {
        switch (tipo) {
            case Recurso.TRMADERA:
                return capRecoleccion[Recurso.TRMADERA];
            case Recurso.TRCOMIDA:
                return capRecoleccion[Recurso.TRCOMIDA];
            case Recurso.TRPIEDRA:
                return capRecoleccion[Recurso.TRPIEDRA];
            default:
                System.out.println("El recurso no existe");
                return 0;
        }
    }

    //Los set de tipo y capEdificacion no tienen sentido, 
    //pues no se pueden campiar esas caracteristicas del personaje
    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @param x_ej
     * @param y_ej
     */
    public void setCelda(int x_ej, int y_ej) {
        if (x_ej >= 0 && y_ej >= 0) {
//            this.celda = new Celda(x_ej, y_ej, this.tipoPersonaje, this.nombre, this.civilizacion);
        }
    }

    /**
     *
     * @param s
     */
    public void setSalud(int s) {
        if (s <= 0) {
            this.salud = 0;
            this.estado = false;
        } else {
            this.salud = s;
        }
    }

    /**
     *
     * @param armadura
     */
    public void setArmadura(int armadura) {
        this.armadura = armadura < 0 ? 0 : armadura;
    }

    /**
     *
     * @param ataque
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque < 0 ? 0 : ataque;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     *
     * @param cap
     */
    public void setCapRecoleccion(int cap) {
        if (cap < 0) {
            this.capRecoleccion[0] = 0;
        } else {
            this.capRecoleccion[0] = cap;
        }
    }

    public void setCapRecoleccionTipo(int cap, int tipo) {
        if (cap < 0) {
            cap = 0;
        }
        this.capRecoleccion[tipo] = cap;
    }

    public void setEstarGrupo(boolean grupo) {
        this.estarGrupo = grupo;
    }

    public void setMadera(int a) {
        this.capRecoleccion[Recurso.TRMADERA] = a;
    }

    public void setComida(int a) {
        this.capRecoleccion[Recurso.TRCOMIDA] = a;
    }

    public void setPiedra(int a) {
        this.capRecoleccion[Recurso.TRPIEDRA] = a;
    }  
}
