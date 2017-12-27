/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.CeldaEnemigaException;
import excepciones.CeldaOcupadaException;
import excepciones.FueraDeMapaException;
import excepciones.NoRecolectableException;
import excepciones.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.PersonajeLlenoException;
import excepciones.RecursosException;
import excepciones.SoldadoRecException;
import vista.Celda;
import interfazUsuario.Juego;
import vista.Mapa;

/**
 *
 * @author celia
 */
public abstract class Personaje {

    private Celda celda;
    private String nombre = null;
    private Civilizacion civilizacion;
    private boolean estado; // True vivo, False Muerto
    private int tipoPersonaje;
    private int salud;
    private final int saludInicial;
    private int armadura;
    private int ataque;
    private boolean capEdificacion;
    private boolean estarGrupo;

    /**
     * Crea un personaje en una celda y asociado a una civilización
     *
     * @param salud Salud inicial del personaje
     * @param armadura Capacidad de defensa inicial
     * @param ataque Capacidad de ataque inicial
     * @param capEdificacion Indica si puede edificar
     * @param tipo
     * @throws excepciones.ParametroIncorrectoException
     */
    public Personaje(int salud, int armadura, int ataque, boolean capEdificacion, int tipo) throws ParametroIncorrectoException {
        if (salud <= 0) {
            throw new ParametroIncorrectoException("La salud no puede ser negativa o nula");
        } else {
            //this.celda = celda;
            this.estado = true;
            this.salud = salud;
            this.saludInicial = salud;
            this.armadura = armadura < 0 ? 0 : armadura;
            this.ataque = ataque < 0 ? 0 : ataque;
            this.capEdificacion = capEdificacion;
            this.tipoPersonaje = tipo;
            // No estamos en ningun grupo
            this.estarGrupo = false;
            //this.actualizaVisibilidad();

            // Añadimos el personaje a la celda y cambiamos las características de la misma
//            this.celda.anhadePersonaje(this);
//            this.celda.setCivilizacion(Juego.getCivilizacionActiva());
//            this.celda.setTransitable(true);
//            this.celda.setVisible(true);
//            
//            // Añadimos el personaje a la civilizacion
//            this.civilizacion.anhadePersonaje(this);
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

    public boolean getEstarGrupo() {
        return this.estarGrupo;
    }

    public boolean getCapEdificacion() {
        return this.capEdificacion;
    }

    /**
     * Establece el nombre del personaje
     *
     * @param nombre El nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la civilización del personaje
     */
    public void setCivilizacion(Civilizacion civ) {
        this.civilizacion = civ;
    }

    /**
     * Cambia la celda en la que está situado el personaje
     *
     * @param c La nueva celda del personaje
     */
    public void setCelda(Celda c) {
        this.celda = c;
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

    public void setEstarGrupo(boolean grupo) {
        this.estarGrupo = grupo;
    }

    public void setTipo(int tipo) {
        this.tipoPersonaje = tipo;
    }

    //FUNCIONES
    /**
     *
     * @param mapa
     * @param direccion
     * @throws excepciones.NoTransitablebleException
     * @throws excepciones.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.CeldaEnemigaException
     * @throws excepciones.CeldaOcupadaException
     */
    public void mover(Mapa mapa, String direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException {
        Celda actual = this.getCelda();
        Celda vecina = mapa.obtenerCeldaVecina(actual, direccion);

        if (!vecina.getTransitable()) {
            throw new NoTransitablebleException("La celda no permite la entrada.");
        } else if (vecina.getCivilizacion() != null && vecina.getCivilizacion() != this.getCivilizacion()) {
            throw new CeldaEnemigaException("La celda está ocupada por el enemigo.");
        } else {
            // Elimina el personaje de la celda actual
            actual.eliminarPersonaje(this);
            // Añade el personaje a la celda vecina y cambia la celda del personaje 
            vecina.anhadePersonaje(this);
            this.setCelda(vecina);
            vecina.setCivilizacion(Juego.getCivilizacionActiva());
            // Actualiza la visibilidad
            this.actualizaVisibilidad();

            // Si la celda vecina tiene un edificio, indicamos que ya no está vacío
            Edificio e = vecina.getEdificio();
            if (e != null) {
                e.setEstarVacio(false);
            }

            System.out.println("El " + this.getNombre() + " se ha movido a la celda " + vecina);

        }
    }

    /**
     * Haz visibles las celdas que rodean al personaje
     *
     */
    public void actualizaVisibilidad() {
        Celda c = this.getCelda();
        Mapa mapa = c.getMapa();
        int x = c.getX();
        int y = c.getY();

        mapa.obtenerCelda(x, y).setVisible(true);
        mapa.obtenerCelda(x, y).setVisitadaPor(this.civilizacion);
        Celda vecina;
        if (x > 0) {
            vecina = mapa.obtenerCelda(x - 1, y);
            vecina.setVisible(true);
            // Si la celda vecina no tiene personajes, la marcamos como visitada
            // por la civilización actual
            if (vecina.getPersonajes().isEmpty()) {
                vecina.setVisitadaPor(this.civilizacion);
            }
        }
        if (x < (mapa.getTamX() - 1)) {
            vecina = mapa.obtenerCelda(x + 1, y);
            vecina.setVisible(true);
            // Si la celda vecina no tiene personajes, la marcamos como visitada
            // por la civilización actual
            if (vecina.getPersonajes().isEmpty()) {
                vecina.setVisitadaPor(this.civilizacion);
            }
        }
        if (y > 0) {
            vecina = mapa.obtenerCelda(x, y - 1);
            vecina.setVisible(true);
            // Si la celda vecina no tiene personajes, la marcamos como visitada
            // por la civilización actual
            if (vecina.getPersonajes().isEmpty()) {
                vecina.setVisitadaPor(this.civilizacion);
            }
        }
        if (y < (mapa.getTamY() - 1)) {
            vecina = mapa.obtenerCelda(x, y + 1);
            vecina.setVisible(true);
            // Si la celda vecina no tiene personajes, la marcamos como visitada
            // por la civilización actual
            if (vecina.getPersonajes().isEmpty()) {
                vecina.setVisitadaPor(this.civilizacion);
            }
        }
    }

    /**
     *
     * @param direccion
     * @throws RecursosException
     * @throws PersonajeLlenoException
     * @throws FueraDeMapaException
     * @throws ParametroIncorrectoException
     * @throws NoRecolectableException
     * @throws CeldaOcupadaException
     * @throws SoldadoRecException
     */
    public abstract void recolectar(String direccion) throws RecursosException, PersonajeLlenoException, FueraDeMapaException, ParametroIncorrectoException, NoRecolectableException, CeldaOcupadaException, SoldadoRecException;

    //public abstract void construirEdificio(Mapa mapa, String nedificio, int tipo, String direccion);
    /**
     * Se encarga de fijar el nombre a partir del tipo de personaje y la
     * civilización a la que pertenece
     *
     * @param civil Civilización a la que pertenece
     */
    public abstract void inicializaNombre(Civilizacion civil);

//
//
//    /**
//     *
//     * @param mapa
//     * @param direccion
//     */
//    public void almacenar(Mapa mapa, String direccion) {
//        if (this.tipoPersonaje != Mapa.TPAISANO) {
//            System.out.println("Solo los paisanos pueden almacenar.");
//        } else {
//            Celda vecina = obtenerCeldaVecina(mapa, direccion);
//            if (vecina == null) {
//                System.out.println("No se puede almacenar hacia el "
//                        + direccion + ": se sale del mapa.");
//            } else {
//                for (String s : vecina.getNombreElementos()) {
//                    if (mapa.getCivActiva().getEdCivilizacion().containsKey(s)) {  // La celda contiene un edificio
//                        Edificio e = mapa.getCivActiva().getEdCivilizacion().get(s);
//                        if (e.getTipo() == Mapa.TCASA) {
//                            System.out.println("El edificio seleccionado no puede almacenar.");
//                        } else {
//                            e.setCapAlmacenamientoTotal(e.getCapAlmacenamiento()[0] + this.capRecoleccion[0]);
//                            e.setMadera(e.getCapAlmacenamiento()[Recurso.TRMADERA] + this.capRecoleccion[Recurso.TRMADERA]);
//                            e.setComida(e.getCapAlmacenamiento()[Recurso.TRCOMIDA] + this.capRecoleccion[Recurso.TRCOMIDA]);
//                            e.setPiedra(e.getCapAlmacenamiento()[Recurso.TRPIEDRA] + this.capRecoleccion[Recurso.TRPIEDRA]);
//                            //todo lo que tiene el personaje se le pasa a la ciudadela
//                            System.out.println("Almacenado " + this.capRecoleccion[Recurso.TRMADERA]
//                                    + " madera, " + this.capRecoleccion[Recurso.TRCOMIDA]
//                                    + " comida, y " + this.capRecoleccion[Recurso.TRPIEDRA]
//                                    + " piedra en el edificio " + direccion.toUpperCase());
//                            // Restauramos las capacidades del paisano
//                            this.capRecoleccion[0] = this.capRecoleccionInicial; //capacidad recoleccion vuelve a ser la inicial
//                            this.capRecoleccion[Recurso.TRMADERA] = 0;
//                            this.capRecoleccion[Recurso.TRCOMIDA] = 0;
//                            this.capRecoleccion[Recurso.TRPIEDRA] = 0;
//                        }
//                    } else {
//                        String mensaje;
//                        switch (vecina.getTipoCelda()) {
//                            case Mapa.TCASA:
//                                mensaje = "una casa";
//                                break;
//                            case Mapa.TCUARTEL:
//                                mensaje = "un cuartel";
//                                break;
//                            case Mapa.TPAISANO:
//                                mensaje = "un paisano";
//                                break;
//                            case Mapa.TSOLDADO:
//                                mensaje = "un soldado";
//                                break;
//                            case Mapa.TPRADERA:
//                                mensaje = "una pradera";
//                                break;
//                            case Mapa.TARBUSTO:
//                                mensaje = "un arbusto";
//                                break;
//                            case Mapa.TBOSQUE:
//                                mensaje = "un bosque";
//                                break;
//                            case Mapa.TCANTERA:
//                                mensaje = "una cantera";
//                                break;
//                            default:
//                                mensaje = "esa celda";
//                        }
//                        System.out.println("El " + this.nombre
//                                + " no puede almacenar en " + mensaje + ".");
//                    }
//                }
//            }
//        }
//    }
//
//
//
//
//
//
//
//    public void defender(Mapa mapa, String direccion) {
//        Celda vecina = obtenerCeldaVecina(mapa, direccion); //igual que en mirar comprueba que la direccion sea válida
//
//        if (vecina == null) {
//            System.out.println("No se puede mover el personaje hacia el "
//                    + direccion + ": se sale del mapa.");
//        } else {
//            if (vecina.getNumElementos() != 0) {
//
//                //Una celda con un edificio solo tendría ese edificio, no más elementos
//                //Si los personajes entran en el edificio no cuentan como elementos dela celda
//                if (mapa.getCivActiva().getEdCivilizacion().containsKey(vecina.getNombreElementos().get(0))) {  // La celda contiene un edificio
//                    Edificio e = mapa.getCivActiva().getEdCivilizacion().get(vecina.getNombreElementos().get(0));
//                    if ((e.getTipo() != Mapa.TCIUDADELA) && (e.getTipo() != Mapa.TCASA) && (e.getTipo() != Mapa.TCUARTEL)) { //en c no funciona con mas de un and aqui no se
//                        System.out.println("El edificio seleccionado ni una casa, ni una ciudadela ni un cuartel.");
//                    } else {
//                        if (e.getCapPersonajes() > 0) {
//                            mover(mapa, vecina, direccion);
//                            e.setDefensa(this.getArmadura());
//                            e.setCapPersonajes(e.getCapPersonajes() - 1);
//                            this.setSalud(this.saludInicial);
//                            System.out.println("El " + this.getNombre() + " ha entrado en el " + e.getNombre() + " (capacidad restante edificio es " + e.getCapPersonajes() + ")");
//                        } else {
//                            System.out.println("El " + e.getNombre() + " ya está al maximo de su capacidad, el " + this.getNombre() + " no ha podido entrar en el edificio.");
//                        }
//
//                    }
//                } else { //la celda no contiene un edificio
//                    System.out.println("LLEGA AQUI");
//                    String mensaje;
//                    switch (vecina.getTipoCelda()) {
//                        case Mapa.TARBUSTO:
//                            mensaje = "un arbusto";
//                            break;
//                        case Mapa.TBOSQUE:
//                            mensaje = "un bosque";
//                            break;
//                        case Mapa.TCANTERA:
//                            mensaje = "una cantera";
//                            break;
//                        case Mapa.TPRADERA:
//                            mensaje = "una pradera";
//                            break;
//                        default:
//                            mensaje = "esa celda";
//                    }
//                    System.out.println("El " + this.nombre
//                            + " no puede defender " + mensaje + ".");
//                }
//            } else {
//                System.out.println("La celda es una pradera, no se puede defender");
//            }
//        }
//    }
//    
//    public void atacar(Mapa mapa, String direccion) {
//        Celda vecina = obtenerCeldaVecina(mapa, direccion); 
//
//        if (vecina == null) {
//            System.out.println("No se puede atacar hacia el "
//                    + direccion + ": se sale del mapa.");
//        } else
//        {
//            if (vecina.getNumElementos() != 0) {
//
//                Iterator it2 = obtenerCivEnemiga(mapa).getEdCivilizacion().entrySet().iterator();
//                while (it2.hasNext()) {
//                    Map.Entry e = (Map.Entry) it2.next();
//                    //Hay un edificio
//                    if (((Edificio) e.getValue()).getCelda().getX() == vecina.getX()
//                            && ((Edificio) e.getValue()).getCelda().getY() == vecina.getY()) {
//                        //De la otra civilizacion
//                        if (!((Edificio) e.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                            //Si está vacío
//                            if (((Edificio) e.getValue()).getEstarVacio() == true) {
//                                ((Edificio) e.getValue()).setSalud(((Edificio) e.getValue()).getSalud() - this.getAtaque());
//                                System.out.println("El edificio " + ((Edificio) e.getValue()).getNombre() + " ha sido atacado.");
//                                //si salud es 0
//                                if (((Edificio) e.getValue()).getSalud() <= 0) {
//                                    ((Edificio) e.getValue()).setEstado(false);
//                                    vecina.setNumeroElementos(vecina.getNumElementos() - 1);
//                                    System.out.println("El edificio " + ((Edificio) e.getValue()).getNombre() + " ha sido destruido.");
//                                }
//                                //No está vacio
//                            } else {
//                                Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
//                                while (it.hasNext()) {
//                                    Map.Entry e2 = (Map.Entry) it.next();
//                                    //Si tiene personajes, ataca estos primero
//                                    if (((Personaje) e2.getValue()).getCelda().getX() == vecina.getX()
//                                            && ((Personaje) e2.getValue()).getCelda().getY() == vecina.getY()) {
//                                        //Comprueba son civilizacion enemiga
//                                        if (!((Personaje) e2.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                            //Comprueban no en un grupo
//                                            if (((Personaje) e2.getValue()).getEstarGrupo() == false) {
//                                                ((Personaje) e2.getValue()).setSalud(((Personaje) e2.getValue()).getSalud()
//                                                        - this.getAtaque() / ((Personaje) e2.getValue()).getArmadura());
//                                                System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha sido atacado.");
//                                                //Comprueba si murieron y hacen cambios
//                                                if (((Personaje) e2.getValue()).getSalud() <= 0) {
//                                                    ((Personaje) e2.getValue()).setEstado(false);
//                                                    ((Edificio) e.getValue()).setCapPersonajes(((Edificio) e.getValue()).getCapPersonajes() + 1);
//                                                    System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha muerto.");
//                                                    vecina.setNumeroElementos(vecina.getNumElementos() - 1);
//
//                                                    ((Edificio) e.getValue()).setAtaque(((Edificio) e.getValue()).getAtaque() - ((Personaje) e2.getValue()).getAtaque());
//                                                    ((Edificio) e.getValue()).setDefensa(((Edificio) e.getValue()).getDefensa() - ((Personaje) e2.getValue()).getArmadura());
//                                                }
//                                            }
//                                        } else {
//                                            System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                        }
//                                    }
//                                }
//
//                                Iterator it3 = obtenerCivEnemiga(mapa).getGrupoCivilizacion().entrySet().iterator();
//                                while (it3.hasNext()) {
//                                    Map.Entry e3 = (Map.Entry) it3.next();
//                                    //Si tiene un grupo, va atacando a todos los personajes
//                                    if (((Grupo) e3.getValue()).getCelda().getX() == vecina.getX()
//                                            && ((Grupo) e3.getValue()).getCelda().getY() == vecina.getY()) {
//                                        if (!((Grupo) e3.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                            Iterator it4 = ((Grupo) e3.getValue()).getCivilizacion().getPerCivilizacion().entrySet().iterator();
//                                            while (it4.hasNext()) {
//                                                //Comienza a atacar a todos los personajes
//                                                Map.Entry e4 = (Map.Entry) it4.next();
//                                                Personaje atacado = ((Personaje) e4.getValue());
//                                                for (int i = 0; i < ((Grupo) e3.getValue()).getComponentes(); i++) {
//                                                    atacado.setSalud(atacado.getSalud() - this.getAtaque() / atacado.getArmadura());
//                                                    //Si muere uno
//                                                    if (atacado.getSalud() <= 0) {
//                                                        atacado.setEstado(false);
//                                                        System.out.println("El " + atacado.getNombre() + " ha muerto.");
//                                                        ((Grupo) e3.getValue()).desligar(atacado, mapa);
//                                                    }
//                                                }
//                                            }
//                                        } else {
//                                            System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            System.out.println("No puedes atacar a un edificio de tu misma civilizacion");
//                        }
//                    }
//                    else {
//                        //Si hay un personaje
//                        Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
//                        while (it.hasNext()) {
//                            Map.Entry e2 = (Map.Entry) it.next();
//                            if (((Personaje) e2.getValue()).getCelda().getX() == vecina.getX()
//                                    && ((Personaje) e2.getValue()).getCelda().getY() == vecina.getY()) {
//                                if (!((Personaje) e2.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                    if (((Personaje) e2.getValue()).getEstarGrupo() == false) {
//                                        ((Personaje) e2.getValue()).setSalud(((Personaje) e2.getValue()).getSalud()
//                                                - this.getAtaque() / ((Personaje) e2.getValue()).getArmadura());
//                                        if (((Personaje) e2.getValue()).getSalud() <= 0) {
//                                            ((Personaje) e2.getValue()).setEstado(false);
//                                            vecina.setNumeroElementos(vecina.getNumElementos() - 1);
//                                            System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha muerto.");
//                                        }
//                                    }
//                                } else {
//                                    System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                }
//
//                            }
//                        }
//                        Iterator it3 = obtenerCivEnemiga(mapa).getGrupoCivilizacion().entrySet().iterator();
//                        while (it3.hasNext()) {
//                            Map.Entry e3 = (Map.Entry) it3.next();
//                            //Si es un grupo
//                            if (((Grupo) e3.getValue()).getCelda().getX() == vecina.getX()
//                                    && ((Grupo) e3.getValue()).getCelda().getY() == vecina.getY()) {
//                                if (!((Grupo) e3.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                    Iterator it4 = ((Grupo) e3.getValue()).getCivilizacion().getPerCivilizacion().entrySet().iterator();
//                                    while (it4.hasNext()) {
//                                        Map.Entry e4 = (Map.Entry) it4.next();
//                                        Personaje atacado = ((Personaje) e4.getValue());
//                                        for (int i = 0; i < ((Grupo) e3.getValue()).getComponentes(); i++) {
//                                            atacado.setSalud(atacado.getSalud() - this.getAtaque() / atacado.getArmadura());
//                                            if (atacado.getSalud() <= 0) {
//                                                atacado.setEstado(false);
//                                                System.out.println("El " + atacado.getNombre() + " ha muerto.");
//                                                ((Grupo) e3.getValue()).desligar(atacado, mapa);
//                                            }
//                                        }
//                                    }
//                                } else {
//                                    System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                }
//                            }
//                        }
//                    }
//                }
//
//            } else {
//                System.out.println("La celda es una pradera, no se puede atacar.");
//            }
//            comprobarDestruccion(mapa);
//        }
//    }
//    
//    private Civilizacion obtenerCivEnemiga(Mapa mapa) {
//        Civilizacion civ = this.civilizacion;
//        Iterator it = mapa.getCivilizacion().entrySet().iterator();
//        while (it.hasNext()) {
//            //si el edificio tiene grupo dentro
//            Map.Entry e = (Map.Entry) it.next();
//            if (!((Civilizacion) e.getValue()).equals(mapa.getCivActiva())) {
//                civ = ((Civilizacion) e.getValue());
//            }
//        }
//        return civ;
//    }
    @Override
    public String toString() {
        return("Personaje "+this.getNombre()+" de la civilización "+this.getCivilizacion());
    }
//
//    //Borra los edificios muertos
//    private void comprobarDestruccion(Mapa mapa) {
//        Iterator it2 = obtenerCivEnemiga(mapa).getEdCivilizacion().entrySet().iterator();
//        while (it2.hasNext()) {
//            Map.Entry e = (Map.Entry) it2.next();
//            if (((Edificio) e.getValue()).getSalud() == 0) {
//                it2.remove();
//            }
//        }
//
//        Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry e = (Map.Entry) it.next();
//            if (((Personaje) e.getValue()).getSalud() == 0) {
//                it.remove();
//            }
//        }
//    }
}
