/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;
import vista.*;
import interfazUsuario.Juego;
import java.util.Iterator;
import java.util.Map;

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
    

    //FUNCIONES
    /**
     *
     * @param mapa
     * @param direccion
     */
    public void mover(Mapa mapa, String direccion) {
        Celda vecina = obtenerCeldaVecina(mapa, direccion);
        if (vecina == null) {
            System.out.println("No se puede mover el personaje hacia el "
                    + direccion + ": se sale del mapa.");
        } else if (vecina.getCivilizacion() != null && vecina.getCivilizacion() != this.getCivilizacion()) {
            System.out.println("No se puede mover el personaje hacia el "
                    + direccion + ": celda ocupada por el enemigo.");
        }
        else {
            mover(mapa, vecina, direccion);
            vecina.setCivilizacion(mapa.getCivActiva());
            Iterator it2 = this.getCivilizacion().getEdCivilizacion().entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry e = (Map.Entry) it2.next();
                if (((Edificio) e.getValue()).getCelda().getX() == this.getCelda().getX()
                        && ((Edificio) e.getValue()).getCelda().getY() == this.getCelda().getY()) {
                    ((Edificio) e.getValue()).setEstarVacio(false);
                }
            }
            System.out.println("El " + this.getNombre() + " se ha movido a la posición " + "(" + vecina.getX() + "," + vecina.getY() + ")"); //NON ESTA BEN

        }
    }

    /**
     *
     * @param mapa
     * @param nedificio
     * @param tipo
     * @param direccion
     */
    public void construirEdificio(Mapa mapa, String nedificio, int tipo, String direccion) {
        if (this.tipoPersonaje != Mapa.TPAISANO) {
            System.out.println("Solo los paisanos pueden construir edificios.");
        } else {
            Celda vecina = obtenerCeldaVecina(mapa, direccion);
            if (vecina == null) {
                System.out.println("No se puede construír el edificio hacia el "
                        + direccion + ": se sale del mapa.");
            } else {
                Edificio edificio = new Edificio(vecina, nedificio, this.getCivilizacion(), tipo);
                if (this.capRecoleccion[1] >= edificio.getCRM() && this.capRecoleccion[3] >= edificio.getCRP()) {
                    mapa.addEdificio(edificio);
                    if (vecina.getVisible() != true) {
                        vecina.setVisible(true);
                        this.getCivilizacion().getCeldasCivilizacion().add(vecina);
                    }
                    vecina.setTransitable(false);
                    vecina.addNombreElemento(edificio.getNombre());
                    vecina.setEntrable(true);
                    vecina.setTipoCelda(edificio.getTipo());
                    this.capRecoleccion[Recurso.TRMADERA] = this.capRecoleccion[Recurso.TRMADERA] - edificio.getCRM();
                    this.capRecoleccion[Recurso.TRPIEDRA] = this.capRecoleccion[Recurso.TRPIEDRA] - edificio.getCRP();
                    this.capRecoleccion[0] = 100 - (this.capRecoleccion[Recurso.TRMADERA] + this.capRecoleccion[Recurso.TRPIEDRA]);
                    System.out.println("Se ha construído " + edificio.getNombre() + " en la posicion " + "(" + vecina.getX() + "," + vecina.getY() + ")");
                } else {
                    System.out.println("No se puede construír el edificio: "
                            + "el paisano no tiene los suficientes recursos");
                }
            }
        }
    }

    /**
     *
     * @param mapa
     * @param direccion
     */
    public void reparar(Mapa mapa, String direccion) {
        if (this.tipoPersonaje != Mapa.TPAISANO) {
            System.out.println("Solo los paisanos pueden reparar.");
        } else {
            Celda vecina = obtenerCeldaVecina(mapa, direccion);
            if (vecina == null) {
                System.out.println("No se puede reparar den dirección "
                        + direccion + ": se sale del mapa.");
            } else {
                for (String s : vecina.getNombreElementos()) {
                    if (mapa.getCivActiva().getEdCivilizacion().containsKey(s)) {  // La celda contiene un edificio
                        Edificio e = mapa.getCivActiva().getEdCivilizacion().get(s);
                        System.out.println("RECURSO PERSONAJE MADERA: " + this.capRecoleccion[Recurso.TRMADERA] + " RECURSO EDIFICIO EXIGE MADERA " + e.getCRM());
                        System.out.println("RECURSO PERSONAJE PIEDRA: " + this.capRecoleccion[Recurso.TRPIEDRA] + " RECURSO EDIFICIO EXIGE PIEDRA " + e.getCRP());
                        if (this.capRecoleccion[Recurso.TRMADERA] >= e.getCRM() && this.capRecoleccion[Recurso.TRPIEDRA] >= e.getCRP()) {
                            if (e.getSalud() != e.getSaludInicial()) {
                                e.reiniciarSalud(); //edificio recobra la salud
                                this.capRecoleccion[Recurso.TRMADERA] -= e.getCRM();
                                this.capRecoleccion[Recurso.TRPIEDRA] -= e.getCRP();
                                this.capRecoleccion[0] -= (e.getCRM() + e.getCRP());
                                System.out.println("Reparado el edificio " + e.getNombre());
                                System.out.println("Coste de la reparacion: " + (this.capRecoleccion[Recurso.TRMADERA] - e.getCRM()) + " de madera y " + (this.capRecoleccion[0] - e.getCRP()) + " de piedra");

                            } else {
                                System.out.println("El edificio no necesita ser reparadoa, b"
                                        + "");
                            }
                        } else {
                            System.out.println("El paisano no tiene los suficientes recursos");
                        }
                    } else {
                        System.out.println("No hay ningún edificio que reparar en esta posición");
                    }
                }
            }
        }
    }

    /**
     *
     * @param mapa
     * @param direccion
     */
    public void recolectar(Mapa mapa, String direccion) {
        if (this.tipoPersonaje != Mapa.TPAISANO) {
            System.out.println("Solo los paisanos pueden recolectar.");
        } else {
            Celda vecina = obtenerCeldaVecina(mapa, direccion);
            if (vecina == null) {
                System.out.println("No se puede recolectar hacia el "
                        + direccion + ": se sale del mapa.");
            } else {
                recolecta(mapa, vecina);
            }
        }
    }

    /**
     *
     * @param mapa
     * @param direccion
     */
    public void almacenar(Mapa mapa, String direccion) {
        if (this.tipoPersonaje != Mapa.TPAISANO) {
            System.out.println("Solo los paisanos pueden almacenar.");
        } else {
            Celda vecina = obtenerCeldaVecina(mapa, direccion);
            if (vecina == null) {
                System.out.println("No se puede almacenar hacia el "
                        + direccion + ": se sale del mapa.");
            } else {
                for (String s : vecina.getNombreElementos()) {
                    if (mapa.getCivActiva().getEdCivilizacion().containsKey(s)) {  // La celda contiene un edificio
                        Edificio e = mapa.getCivActiva().getEdCivilizacion().get(s);
                        if (e.getTipo() == Mapa.TCASA) {
                            System.out.println("El edificio seleccionado no puede almacenar.");
                        } else {
                            e.setCapAlmacenamientoTotal(e.getCapAlmacenamiento()[0] + this.capRecoleccion[0]);
                            e.setMadera(e.getCapAlmacenamiento()[Recurso.TRMADERA] + this.capRecoleccion[Recurso.TRMADERA]);
                            e.setComida(e.getCapAlmacenamiento()[Recurso.TRCOMIDA] + this.capRecoleccion[Recurso.TRCOMIDA]);
                            e.setPiedra(e.getCapAlmacenamiento()[Recurso.TRPIEDRA] + this.capRecoleccion[Recurso.TRPIEDRA]);
                            //todo lo que tiene el personaje se le pasa a la ciudadela
                            System.out.println("Almacenado " + this.capRecoleccion[Recurso.TRMADERA]
                                    + " madera, " + this.capRecoleccion[Recurso.TRCOMIDA]
                                    + " comida, y " + this.capRecoleccion[Recurso.TRPIEDRA]
                                    + " piedra en el edificio " + direccion.toUpperCase());
                            // Restauramos las capacidades del paisano
                            this.capRecoleccion[0] = this.capRecoleccionInicial; //capacidad recoleccion vuelve a ser la inicial
                            this.capRecoleccion[Recurso.TRMADERA] = 0;
                            this.capRecoleccion[Recurso.TRCOMIDA] = 0;
                            this.capRecoleccion[Recurso.TRPIEDRA] = 0;
                        }
                    } else {
                        String mensaje;
                        switch (vecina.getTipoCelda()) {
                            case Mapa.TCASA:
                                mensaje = "una casa";
                                break;
                            case Mapa.TCUARTEL:
                                mensaje = "un cuartel";
                                break;
                            case Mapa.TPAISANO:
                                mensaje = "un paisano";
                                break;
                            case Mapa.TSOLDADO:
                                mensaje = "un soldado";
                                break;
                            case Mapa.TPRADERA:
                                mensaje = "una pradera";
                                break;
                            case Mapa.TARBUSTO:
                                mensaje = "un arbusto";
                                break;
                            case Mapa.TBOSQUE:
                                mensaje = "un bosque";
                                break;
                            case Mapa.TCANTERA:
                                mensaje = "una cantera";
                                break;
                            default:
                                mensaje = "esa celda";
                        }
                        System.out.println("El " + this.nombre
                                + " no puede almacenar en " + mensaje + ".");
                    }
                }
            }
        }
    }

// Mueve el elemento en la celda antigua a la celda nueva
// y actualiza la celda actual a la nueva
    private void mover(Mapa mapa, Celda nueva, String dir) {
        Celda antigua = this.getCelda();
        if (nueva.getTransitable()) {
            nueva.setTipoCelda(this.getTipo());
            nueva.addNombreElemento(this.getNombre());
            nueva.setTransitable(false);
            if (nueva.getVisible() != true) {
                nueva.setVisible(true);
                this.getCivilizacion().getCeldasCivilizacion().add(nueva);
            }
            nueva.setEntrable(true);
            // Actualizo la celda actual
            this.celda = nueva;
            // Elimino el personaje de la celda antigua
            antigua.eliminaPersonaje(this, mapa);
            actualizaVisibilidad(mapa);
            // Si la celda es entrable    
        } else if (nueva.getEntrable()) {
            nueva.addNombreElemento(this.getNombre());
            nueva.setTipoCelda(Mapa.TVARIOS);
            this.celda = nueva;
            // Elimino el personaje de la celda antigua
            antigua.eliminaPersonaje(this, mapa);
            actualizaVisibilidad(mapa);
        } else {
            String tipoNueva;
            switch (nueva.getTipoCelda()) {
                case Mapa.TARBUSTO:
                    tipoNueva = "el arbusto";
                    break;
                case Mapa.TBOSQUE:
                    tipoNueva = "el bosque";
                    break;
                case Mapa.TCANTERA:
                    tipoNueva = "la cantera";
                    break;
                default:
                    tipoNueva = "esa celda";
            }
            System.out.println("El " + this.nombre
                    + " no puede entrar en " + tipoNueva + ".");
        }
    }

    private void recolecta(Mapa mapa, Celda c) {
        //Solo va a haber un elemento en la celd que va a ser un contenedor de recurso
        for (String s : c.getNombreElementos()) {
            if (mapa.getContRecursos().containsKey(s)) {
                ContRecurso cr = mapa.getContRecursos().get(s);
                //se restan de la capacidad del contenedor de recursos la capacidad 
                // que tiene el personaje para recolectar
                int disponible = cr.getRecurso().getCapacidad(); //Capacidad disponible en ese momento
                int recolectado;
                if (this.getCapRecoleccion() == 0) {
                    System.out.println(this.getNombre() + " ya no puede recolectar más");
                } else if (disponible > this.getCapRecoleccion()) {
                    recolectado = this.getCapRecoleccion();
                    cr.getRecurso().setCapacidad(disponible - recolectado); //cambia capacidad para recolectar en funcion de la recolectado
                    this.capRecoleccion[0] = 0;
                    this.capRecoleccion[cr.getRecurso().getTipo()] += recolectado;
                    System.out.println("El paisano ha conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
                } else if (this.getCapRecoleccion() >= disponible) {
                    recolectado = disponible;
                    this.capRecoleccion[0] -= recolectado;
                    this.capRecoleccion[cr.getRecurso().getTipo()] += recolectado;
                    // Eliminamos el contenedor de recursos agotado
                    mapa.getContRecursos().remove(s);
                    // Convertimos la celda en pradera
                    c.restartElementos();
                    c.setCivilizacion(null);
                    c.setTipoCelda(Mapa.TPRADERA);
                    c.setTransitable(true);
                    System.out.println("Has conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
                } else { //Si disponible, que 
                    System.out.println("Ocurrió un error al intentar recolectar");
                }
            } else {
                String mensaje;
                switch (c.getTipoCelda()) {
                    case Mapa.TCASA:
                        mensaje = "una casa";
                        break;
                    case Mapa.TCIUDADELA:
                        mensaje = "una ciudadela";
                        break;
                    case Mapa.TCUARTEL:
                        mensaje = "un cuartel";
                        break;
                    case Mapa.TPAISANO:
                        mensaje = "un paisano";
                        break;
                    case Mapa.TSOLDADO:
                        mensaje = "un soldado";
                        break;
                    case Mapa.TPRADERA:
                        mensaje = "una pradera";
                        break;
                    default:
                        mensaje = "esa celda";
                }
                System.out.println("El " + this.nombre
                        + " no puede recolectar de " + mensaje + ".");
            }
        }
    }

// Devuelve la celda que se encuentra a la direccion indicada de la celda 
// del personaje
    private Celda obtenerCeldaVecina(Mapa mapa, String direccion) {
        Celda actual = this.getCelda();
        Celda vecina = null;
        switch (direccion.toLowerCase()) {
            case "norte":
                if (this.getCelda().getX() > 0) {
                    vecina = mapa.getCeldas().get(actual.getX() - 1).get(actual.getY());
                }
                break;
            case "sur":
                if (this.getCelda().getX() < (mapa.getCeldas().size() - 1)) {
                    vecina = mapa.getCeldas().get(actual.getX() + 1).get(actual.getY());
                }
                break;
            case "este":
                if (this.getCelda().getY() < (mapa.getCeldas().get(0).size() - 1)) {
                    vecina = mapa.getCeldas().get(actual.getX()).get(actual.getY() + 1);
                }
                break;
            case "oeste":
                if (this.getCelda().getY() > 0) {
                    vecina = mapa.getCeldas().get(actual.getX()).get(actual.getY() - 1);
                }
                break;
        }
        return vecina;
    }

    // Hace visibles las celdas que rodean a la actual
    /**
     *
     * @param mapa
     */
    public void actualizaVisibilidad(Mapa mapa) {
        int x = this.getCelda().getX();
        int y = this.getCelda().getY();
        if (x > 0) {
            mapa.getCeldas().get(x - 1).get(y).setVisible(true);
            this.getCivilizacion().getCeldasCivilizacion().add(mapa.getCeldas().get(x - 1).get(y));
        }
        if (x < (mapa.getCeldas().size() - 1)) {
            mapa.getCeldas().get(x + 1).get(y).setVisible(true);
            this.getCivilizacion().getCeldasCivilizacion().add(mapa.getCeldas().get(x + 1).get(y));
        }
        if (y > 0) {
            mapa.getCeldas().get(x).get(y - 1).setVisible(true);
            this.getCivilizacion().getCeldasCivilizacion().add(mapa.getCeldas().get(x + 1).get(y));
        }
        if (y < (mapa.getCeldas().get(x).size() - 1)) {
            mapa.getCeldas().get(x).get(y + 1).setVisible(true);
            this.getCivilizacion().getCeldasCivilizacion().add(mapa.getCeldas().get(x).get(y + 1));

        }
    }

    public void defender(Mapa mapa, String direccion) {
        Celda vecina = obtenerCeldaVecina(mapa, direccion); //igual que en mirar comprueba que la direccion sea válida

        if (vecina == null) {
            System.out.println("No se puede mover el personaje hacia el "
                    + direccion + ": se sale del mapa.");
        } else {
            if (vecina.getNumElementos() != 0) {

                //Una celda con un edificio solo tendría ese edificio, no más elementos
                //Si los personajes entran en el edificio no cuentan como elementos dela celda
                if (mapa.getCivActiva().getEdCivilizacion().containsKey(vecina.getNombreElementos().get(0))) {  // La celda contiene un edificio
                    Edificio e = mapa.getCivActiva().getEdCivilizacion().get(vecina.getNombreElementos().get(0));
                    if ((e.getTipo() != Mapa.TCIUDADELA) && (e.getTipo() != Mapa.TCASA) && (e.getTipo() != Mapa.TCUARTEL)) { //en c no funciona con mas de un and aqui no se
                        System.out.println("El edificio seleccionado ni una casa, ni una ciudadela ni un cuartel.");
                    } else {
                        if (e.getCapPersonajes() > 0) {
                            mover(mapa, vecina, direccion);
                            e.setDefensa(this.getArmadura());
                            e.setCapPersonajes(e.getCapPersonajes() - 1);
                            this.setSalud(this.saludInicial);
                            System.out.println("El " + this.getNombre() + " ha entrado en el " + e.getNombre() + " (capacidad restante edificio es " + e.getCapPersonajes() + ")");
                        } else {
                            System.out.println("El " + e.getNombre() + " ya está al maximo de su capacidad, el " + this.getNombre() + " no ha podido entrar en el edificio.");
                        }

                    }
                } else { //la celda no contiene un edificio
                    System.out.println("LLEGA AQUI");
                    String mensaje;
                    switch (vecina.getTipoCelda()) {
                        case Mapa.TARBUSTO:
                            mensaje = "un arbusto";
                            break;
                        case Mapa.TBOSQUE:
                            mensaje = "un bosque";
                            break;
                        case Mapa.TCANTERA:
                            mensaje = "una cantera";
                            break;
                        case Mapa.TPRADERA:
                            mensaje = "una pradera";
                            break;
                        default:
                            mensaje = "esa celda";
                    }
                    System.out.println("El " + this.nombre
                            + " no puede defender " + mensaje + ".");
                }
            } else {
                System.out.println("La celda es una pradera, no se puede defender");
            }
        }
    }
    
    public void atacar(Mapa mapa, String direccion) {
        Celda vecina = obtenerCeldaVecina(mapa, direccion); 

        if (vecina == null) {
            System.out.println("No se puede atacar hacia el "
                    + direccion + ": se sale del mapa.");
        } else
        {
            if (vecina.getNumElementos() != 0) {

                Iterator it2 = obtenerCivEnemiga(mapa).getEdCivilizacion().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry e = (Map.Entry) it2.next();
                    //Hay un edificio
                    if (((Edificio) e.getValue()).getCelda().getX() == vecina.getX()
                            && ((Edificio) e.getValue()).getCelda().getY() == vecina.getY()) {
                        //De la otra civilizacion
                        if (!((Edificio) e.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
                            //Si está vacío
                            if (((Edificio) e.getValue()).getEstarVacio() == true) {
                                ((Edificio) e.getValue()).setSalud(((Edificio) e.getValue()).getSalud() - this.getAtaque());
                                System.out.println("El edificio " + ((Edificio) e.getValue()).getNombre() + " ha sido atacado.");
                                //si salud es 0
                                if (((Edificio) e.getValue()).getSalud() <= 0) {
                                    ((Edificio) e.getValue()).setEstado(false);
                                    vecina.setNumeroElementos(vecina.getNumElementos() - 1);
                                    System.out.println("El edificio " + ((Edificio) e.getValue()).getNombre() + " ha sido destruido.");
                                }
                                //No está vacio
                            } else {
                                Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry e2 = (Map.Entry) it.next();
                                    //Si tiene personajes, ataca estos primero
                                    if (((Personaje) e2.getValue()).getCelda().getX() == vecina.getX()
                                            && ((Personaje) e2.getValue()).getCelda().getY() == vecina.getY()) {
                                        //Comprueba son civilizacion enemiga
                                        if (!((Personaje) e2.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
                                            //Comprueban no en un grupo
                                            if (((Personaje) e2.getValue()).getEstarGrupo() == false) {
                                                ((Personaje) e2.getValue()).setSalud(((Personaje) e2.getValue()).getSalud()
                                                        - this.getAtaque() / ((Personaje) e2.getValue()).getArmadura());
                                                System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha sido atacado.");
                                                //Comprueba si murieron y hacen cambios
                                                if (((Personaje) e2.getValue()).getSalud() <= 0) {
                                                    ((Personaje) e2.getValue()).setEstado(false);
                                                    ((Edificio) e.getValue()).setCapPersonajes(((Edificio) e.getValue()).getCapPersonajes() + 1);
                                                    System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha muerto.");
                                                    vecina.setNumeroElementos(vecina.getNumElementos() - 1);

                                                    ((Edificio) e.getValue()).setAtaque(((Edificio) e.getValue()).getAtaque() - ((Personaje) e2.getValue()).getAtaque());
                                                    ((Edificio) e.getValue()).setDefensa(((Edificio) e.getValue()).getDefensa() - ((Personaje) e2.getValue()).getArmadura());
                                                }
                                            }
                                        } else {
                                            System.out.println("No puedes atacar a alguien de tu misma civilizacion");
                                        }
                                    }
                                }

                                Iterator it3 = obtenerCivEnemiga(mapa).getGrupoCivilizacion().entrySet().iterator();
                                while (it3.hasNext()) {
                                    Map.Entry e3 = (Map.Entry) it3.next();
                                    //Si tiene un grupo, va atacando a todos los personajes
                                    if (((Grupo) e3.getValue()).getCelda().getX() == vecina.getX()
                                            && ((Grupo) e3.getValue()).getCelda().getY() == vecina.getY()) {
                                        if (!((Grupo) e3.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
                                            Iterator it4 = ((Grupo) e3.getValue()).getCivilizacion().getPerCivilizacion().entrySet().iterator();
                                            while (it4.hasNext()) {
                                                //Comienza a atacar a todos los personajes
                                                Map.Entry e4 = (Map.Entry) it4.next();
                                                Personaje atacado = ((Personaje) e4.getValue());
                                                for (int i = 0; i < ((Grupo) e3.getValue()).getComponentes(); i++) {
                                                    atacado.setSalud(atacado.getSalud() - this.getAtaque() / atacado.getArmadura());
                                                    //Si muere uno
                                                    if (atacado.getSalud() <= 0) {
                                                        atacado.setEstado(false);
                                                        System.out.println("El " + atacado.getNombre() + " ha muerto.");
                                                        ((Grupo) e3.getValue()).desligar(atacado, mapa);
                                                    }
                                                }
                                            }
                                        } else {
                                            System.out.println("No puedes atacar a alguien de tu misma civilizacion");
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("No puedes atacar a un edificio de tu misma civilizacion");
                        }
                    }
                    else {
                        //Si hay un personaje
                        Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry e2 = (Map.Entry) it.next();
                            if (((Personaje) e2.getValue()).getCelda().getX() == vecina.getX()
                                    && ((Personaje) e2.getValue()).getCelda().getY() == vecina.getY()) {
                                if (!((Personaje) e2.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
                                    if (((Personaje) e2.getValue()).getEstarGrupo() == false) {
                                        ((Personaje) e2.getValue()).setSalud(((Personaje) e2.getValue()).getSalud()
                                                - this.getAtaque() / ((Personaje) e2.getValue()).getArmadura());
                                        if (((Personaje) e2.getValue()).getSalud() <= 0) {
                                            ((Personaje) e2.getValue()).setEstado(false);
                                            vecina.setNumeroElementos(vecina.getNumElementos() - 1);
                                            System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha muerto.");
                                        }
                                    }
                                } else {
                                    System.out.println("No puedes atacar a alguien de tu misma civilizacion");
                                }

                            }
                        }
                        Iterator it3 = obtenerCivEnemiga(mapa).getGrupoCivilizacion().entrySet().iterator();
                        while (it3.hasNext()) {
                            Map.Entry e3 = (Map.Entry) it3.next();
                            //Si es un grupo
                            if (((Grupo) e3.getValue()).getCelda().getX() == vecina.getX()
                                    && ((Grupo) e3.getValue()).getCelda().getY() == vecina.getY()) {
                                if (!((Grupo) e3.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
                                    Iterator it4 = ((Grupo) e3.getValue()).getCivilizacion().getPerCivilizacion().entrySet().iterator();
                                    while (it4.hasNext()) {
                                        Map.Entry e4 = (Map.Entry) it4.next();
                                        Personaje atacado = ((Personaje) e4.getValue());
                                        for (int i = 0; i < ((Grupo) e3.getValue()).getComponentes(); i++) {
                                            atacado.setSalud(atacado.getSalud() - this.getAtaque() / atacado.getArmadura());
                                            if (atacado.getSalud() <= 0) {
                                                atacado.setEstado(false);
                                                System.out.println("El " + atacado.getNombre() + " ha muerto.");
                                                ((Grupo) e3.getValue()).desligar(atacado, mapa);
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("No puedes atacar a alguien de tu misma civilizacion");
                                }
                            }
                        }
                    }
                }

            } else {
                System.out.println("La celda es una pradera, no se puede atacar.");
            }
            comprobarDestruccion(mapa);
        }
    }
    
    private Civilizacion obtenerCivEnemiga(Mapa mapa) {
        Civilizacion civ = this.civilizacion;
        Iterator it = mapa.getCivilizacion().entrySet().iterator();
        while (it.hasNext()) {
            //si el edificio tiene grupo dentro
            Map.Entry e = (Map.Entry) it.next();
            if (!((Civilizacion) e.getValue()).equals(mapa.getCivActiva())) {
                civ = ((Civilizacion) e.getValue());
            }
        }
        return civ;
    }

    //Borra los edificios muertos
    private void comprobarDestruccion(Mapa mapa) {
        Iterator it2 = obtenerCivEnemiga(mapa).getEdCivilizacion().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry e = (Map.Entry) it2.next();
            if (((Edificio) e.getValue()).getSalud() == 0) {
                it2.remove();
            }
        }

        Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            if (((Personaje) e.getValue()).getSalud() == 0) {
                it.remove();
            }
        }
    }
}
