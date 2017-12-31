/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import elementos.personaje.Arquero;
import elementos.personaje.Caballero;
import elementos.personaje.Grupo;
import elementos.personaje.Legionario;
import elementos.personaje.Paisano;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.recursos.NoRecolectableException;
import excepciones.celda.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaException;
import excepciones.celda.NoAlmacenableException;
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.AtaqueExcepcion;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolAlmacenarException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SolRepararException;
import excepciones.recursos.RecursosException;
import excepciones.personaje.SoldadoRecException;
import vista.Celda;
import interfazUsuario.Juego;
import java.util.Iterator;
import vista.Mapa;

/**
 *
 * @author celia y maria
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
    private Grupo grupo;
    private int capMovimiento;

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
        if (salud < 0) {
            throw new ParametroIncorrectoException("La salud no puede ser negativa");
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
            grupo = null;
            // Todos los personajes tienen capacidad de movimiento 1 menos los caballeros, que tienen 2
            this.capMovimiento = 1;
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

    public Grupo getGrupo() throws EstarEnGrupoException {
        if (estarGrupo) {
            return this.grupo;
        } else {
            throw new EstarEnGrupoException("El personaje " + getNombre() + " no está en ningún grupo");
        }
    }
    
    public int capacidadMovimiento() {
        return capMovimiento;
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
        if (s < 0) {
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

    public void setGrupo(Grupo g) {
        this.estarGrupo = true;
        this.grupo = g;
    }

    public void setTipo(int tipo) {
        this.tipoPersonaje = tipo;
    }
    
    /**
     * Solo las subclases pueden cambiar la capacidad de movimiento del personaje
     * 
     * @param cap 
     */
    protected void setCapMovimiento(int cap) {
        this.capMovimiento = cap;
    }

    //FUNCIONES
    /**
     * Mueve el personaje una celda hacia una dirección
     *
     * @param mapa El mapa en el que nos movemos
     * @param direccion La dirección hacia la que nos movemos (norte, sur, esye
     * u oeste)
     * @return Mensaje de accion completada
     *
     * @throws excepciones.celda.NoTransitablebleException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.CeldaEnemigaException
     * @throws excepciones.celda.CeldaOcupadaException
     * @throws excepciones.personaje.EstarEnGrupoException
     */
    public String mover(Mapa mapa, String direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException, EstarEnGrupoException, NoTransitablebleException {
        return mover(mapa, direccion, 1);
    }

    /**
     * Mueve el personaje varias celdas hacia una dirección
     *
     * @param mapa El mapa en el que nos movemos
     * @param direccion La dirección hacia la que nos movemos (norte, sur, esye
     * u oeste)
     * @param distancia El número de celdas a las que avanzamos
     * @return Mensaje de accion completada
     *
     * @throws excepciones.celda.NoTransitablebleException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.CeldaEnemigaException
     * @throws excepciones.celda.CeldaOcupadaException
     * @throws excepciones.personaje.EstarEnGrupoException
     */
    public String mover(Mapa mapa, String direccion, int distancia) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException, EstarEnGrupoException, NoTransitablebleException {

        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no se puede mover, ya que está en el grupo " + getGrupo().getNombre());
        }

        Celda actual = this.getCelda();
        Celda vecina = mapa.obtenerCeldaVecina(actual, direccion, distancia);

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

            // Si la celda que abandonamos tenía edificio, le aumentamos su capacidad de acogida y le reducimos la defensa
            Edificio e = actual.getEdificio();
            if (e != null) {
                e.setDefensa(e.getDefensa() - this.getArmadura());
                e.setCapPersonajes(e.getCapPersonajes() + 1);
            }

            return ("El " + this.getNombre() + " se ha movido a la celda " + vecina);

        }
    }

    /**
     *
     * @param direccion
     * @return
     * @throws excepciones.personaje.EstarEnGrupoException
     * @throws RecursosException
     * @throws PersonajeLlenoException
     * @throws FueraDeMapaException
     * @throws ParametroIncorrectoException
     * @throws NoRecolectableException
     * @throws CeldaOcupadaException
     * @throws SoldadoRecException
     */
    public abstract String recolectar(String direccion) throws EstarEnGrupoException, RecursosException, PersonajeLlenoException, FueraDeMapaException, ParametroIncorrectoException, NoRecolectableException, CeldaOcupadaException, SoldadoRecException;

    //public abstract void construirEdificio(Mapa mapa, String nedificio, int tipo, String direccion);
    /**
     * Se encarga de fijar el nombre a partir del tipo de personaje y la
     * civilización a la que pertenece
     *
     * @param civil Civilización a la que pertenece
     */
    public abstract void inicializaNombre(Civilizacion civil);

    public abstract String construirEdificio(String nedificio, String direccion) throws SolConstruirException, InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, EstarEnGrupoException;

    public abstract String reparar(String direccion) throws SolRepararException, FueraDeMapaException, ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException, EstarEnGrupoException;

    public abstract String almacenar(String direccion) throws InsuficientesRecException, SolAlmacenarException, FueraDeMapaException, ParametroIncorrectoException, NoAlmacenableException, EstarEnGrupoException;

    public String defender(String direccion) throws FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, CeldaOcupadaException, EstarEnGrupoException, EdificioException, CeldaException {
        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no puede defender, ya que está en el grupo " + getGrupo());
        }
        String s;
        Celda vecina = this.getCelda().getMapa().obtenerCeldaVecina(this.getCelda(), direccion); //igual que en mirar comprueba que la direccion sea válida

        if (vecina.getEdificio() != null) {// La celda contiene un edificio
            //Una celda con un edificio solo tendría ese edificio, no más elementos
            Edificio e = vecina.getEdificio();
            if (vecina.getCivilizacion() != this.getCivilizacion()) {
                throw new CeldaEnemigaException("El edificio pertenece a la civilización enemiga\n");
            } else {
                if (e.getCapPersonajes() > 0) {
                    // Hago la celda vecina transitable para poder entrar
                    vecina.setTransitable(true);
                    mover(this.getCelda().getMapa(), direccion);
                    vecina.setTransitable(false);
                    
                    e.setDefensa(e.getDefensa() + this.getArmadura());
                    e.setCapPersonajes(e.getCapPersonajes() - 1);
                    this.setSalud(this.saludInicial);
                    s = "El " + this.getNombre() + " ha entrado en el " + e.getNombre() + " (capacidad restante edificio es " + e.getCapPersonajes() + ")\n";

                } else {
                    throw new EdificioException("El edificio está al máximo de capacidad de personajes, no puede entrar\n");
                }
            }
        } else { //la celda no contiene un edificio
            throw new CeldaException("La celda no contiene un edificio, no se puede defender\n");
        }
        return s;
    }

    public String atacar(String direccion) throws FueraDeMapaException, ParametroIncorrectoException, NoTransitablebleException, CeldaEnemigaException, AtaqueExcepcion, EstarEnGrupoException {
        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no puede atacar, ya que está en el grupo " + getGrupo());
        }
        String s;

        Celda actual = this.getCelda();
        Celda vecina = actual.getMapa().obtenerCeldaVecina(actual, direccion);

        if (vecina.getContRecurso() != null) {
            throw new NoTransitablebleException("No puedes atacar a un " + vecina.getContRecurso().getNombre());
        } else if (vecina.getCivilizacion() != null && vecina.getCivilizacion() == this.getCivilizacion()) {
            throw new CeldaEnemigaException("No puedes atacar a una celda amiga.");
        }

        // Comprobamos si hay personajes y atacamos al más débil
        if (!vecina.getPersonajes().isEmpty()) {
            int saludEnemigo = Integer.MAX_VALUE;
            Personaje pEnemigo = null;
            for (Personaje p : vecina.getPersonajes()) {
                if (p.getSalud() < saludEnemigo) {
                    saludEnemigo = p.getSalud();
                    pEnemigo = p;
                }
            }
            if (pEnemigo != null) {
                // Intentamos atacar al personaje
                int danhoCausado = this.getAtaque() - pEnemigo.getArmadura();
                // Los caballeros hacen el doble de daño a los legionarios y arqueros
                if ((this instanceof Caballero) && ((pEnemigo instanceof Legionario) || (pEnemigo instanceof Arquero))) {
                    danhoCausado *= 2;
                }

                if (danhoCausado < 1) {
                    s = this.getNombre() + " no puede atacar: la defensa enemiga es demasiado fuerte.";
                } else {
                    int nuevaSalud = pEnemigo.getSalud() - danhoCausado;
                    if (nuevaSalud > 0) {
                        pEnemigo.setSalud(nuevaSalud);
                        s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + pEnemigo.getNombre()
                                + " (" + pEnemigo.getCivilizacion().getNomCivilizacion() + "): la salud de "
                                + pEnemigo.getNombre() + " es ahora " + pEnemigo.getSalud();
                    } else {
                        s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + pEnemigo.getNombre()
                                + " (" + pEnemigo.getCivilizacion().getNomCivilizacion() + "): "
                                + pEnemigo.getNombre() + " ha muerto";
                        vecina.eliminarPersonaje(pEnemigo);
                        pEnemigo.getCivilizacion().eliminaPersonaje(pEnemigo);
                        vecina.setVisitadaPor(this.getCivilizacion());
                    }
                }
                return s;
            } else {
                throw new AtaqueExcepcion("Error desconocido al atacar");
            }
            
        // Si no hay personajes, atacamos al edificio    
        } else if (vecina.getEdificio() != null) { 
            Edificio edificioEnemigo = vecina.getEdificio();
            // Intentamos atacar al edificio
            int danhoCausado = this.getAtaque() - edificioEnemigo.getDefensa();
            // Si el que ataca a un edificio es un arquero, el daño se reduce a la mitad
            if (this instanceof Arquero) {
                danhoCausado /= 2;
            }

            if (danhoCausado < 1) {
                s = this.getNombre() + " no puede atacar a " + edificioEnemigo.getNombre()
                        + " (" + edificioEnemigo.getCivilizacion().getNomCivilizacion() + "): su defensa es demasiado fuerte.";
            } else {
                int nuevaSalud = edificioEnemigo.getSalud() - danhoCausado;
                if (nuevaSalud > 0) {
                    edificioEnemigo.setSalud(nuevaSalud);
                    s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + edificioEnemigo.getNombre()
                            + " (" + edificioEnemigo.getCivilizacion().getNomCivilizacion() + "): la salud de "
                            + edificioEnemigo.getNombre() + " es ahora " + edificioEnemigo.getSalud();
                } else {
                    s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + edificioEnemigo.getNombre()
                            + " (" + edificioEnemigo.getCivilizacion().getNomCivilizacion() + "): "
                            + edificioEnemigo.getNombre() + " ha quedado destruido";
                    vecina.eliminaEdificio();
                    edificioEnemigo.getCivilizacion().eliminaEdificio(edificioEnemigo);
                    vecina.setVisitadaPor(this.getCivilizacion());
                }
            }
            return s;
        } else {
            throw new AtaqueExcepcion("No hay nadie a quién atacar");
        }
    }
    
    /**
     * Haz visibles las celdas que rodean al personaje
     *
     */
    public void actualizaVisibilidad() throws FueraDeMapaException {
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

    @Override
    public String toString() {
        String s = "\tTipo personaje: ";
        if (this instanceof Paisano) {
            s += "Paisano";
        }
        if (this instanceof Arquero) {
            s += "Arquero";
        }
        if (this instanceof Caballero) {
            s += "Caballero";
        }
        if (this instanceof Legionario) {
            s += "Legionario";
        }
        if (this instanceof Grupo) {
            s += "Grupo";
        }
        s += ", Nombre: " + this.getNombre();
        s += "\n\tCivilización a la que pertenece: " + this.getCivilizacion().getNomCivilizacion();
        s += "\n\tSalud: " + this.getSalud();
        s += "\n\tArmadura: " + this.getArmadura();
        s += "\n\tAtaque: " + this.getAtaque();
        return (s);
    }

}
