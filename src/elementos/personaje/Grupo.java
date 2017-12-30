/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.personaje;

import elementos.Civilizacion;
import elementos.ContRecurso;
import elementos.Edificio;
import elementos.Personaje;
import elementos.Recurso;
import elementos.cr.Pradera;
import elementos.edificio.Casa;
import elementos.edificio.Ciudadela;
import elementos.edificio.Cuartel;
import excepciones.celda.FueraDeMapaException;
import excepciones.recursos.NoRecolectableException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.NoAlmacenableException;
import excepciones.celda.NoTransitablebleException;
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolAlmacenarException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SolRepararException;
import excepciones.recursos.RecursosException;
import excepciones.personaje.SoldadoRecException;
import interfazUsuario.Juego;
import java.util.ArrayList;
import java.util.List;
import vista.Celda;

/**
 *
 * @author celia
 */
public class Grupo extends Personaje {

    private List<Personaje> personajes;
    private int[] capRecoleccion = new int[4]; //0 capacidad total, 1 capacidad madera, 
    //2 capacidad comida, 3 capacidad piedra
    private final int capRecoleccionInicial;
    private static int[] numeroGrupos = new int[Civilizacion.getNumDeCivilizaciones()];
    public boolean tieneSoldado = false;

    public Grupo() throws ParametroIncorrectoException {
        super(0, 0, 0, true, Juego.TGRUPO);
        personajes = new ArrayList<Personaje>();
        //numeroGrupos[civil.getIdCivilizacion()]++;
        //setNombre("Grupo-" + numeroGrupos[civil.getIdCivilizacion()]);
        this.capRecoleccion[0] = 0;
        this.capRecoleccionInicial = this.capRecoleccion[0];        
    }

    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroGrupos[civil.getIdCivilizacion()]++;
        setNombre("Grupo-" + numeroGrupos[civil.getIdCivilizacion()]);
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

    public List<Personaje> getPersonajes() {
        return this.personajes;
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

    public void setMadera(int a) {
        this.capRecoleccion[Recurso.TRMADERA] = a;
    }

    public void setComida(int a) {
        this.capRecoleccion[Recurso.TRCOMIDA] = a;
    }

    public void setPiedra(int a) {
        this.capRecoleccion[Recurso.TRPIEDRA] = a;
    }

    /**
     *
     * @param nedificio: nombre edificio(casa, ciudadela o cuartel)
     * @param direccion
     * @throws excepciones.personaje.InsuficientesRecException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.CeldaOcupadaException
     * @throws excepciones.celda.CeldaEnemigaException
     */
    @Override
    public String construirEdificio(String nedificio, String direccion) throws InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, EstarEnGrupoException, SolConstruirException {
        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje puede construir, ya que está en un grupo " + getGrupo());
        }
        if (tieneSoldado) {
            throw new SolConstruirException("El grupo tiene soldados");
        }
        String s;
        Celda vecina = this.getCelda().getMapa().obtenerCeldaVecina(this.getCelda(), direccion);
        Edificio ed = null;

        switch (nedificio) {
            case "ciudadela":
                ed = new Ciudadela();
                break;
            case "cuartel":
                ed = new Cuartel();
                break;
            case "casa":
                ed = new Casa();
                break;
            default:
                throw new ParametroIncorrectoException("Tipo de edificio desconocido");
        }
        if (this.capRecoleccion[1] >= ed.getCRM() && this.capRecoleccion[3] >= ed.getCRP()) {
            ed.inicializaNombre(Juego.getCivilizacionActiva());
            Juego.getCivilizacionActiva().anhadeEdificio(ed);
            vecina.anhadeEdificio(ed);
            vecina.setVisible(true);
            vecina.setTransitable(true); //Ponerlo a true
            this.capRecoleccion[Recurso.TRMADERA] = this.capRecoleccion[Recurso.TRMADERA] - ed.getCRM();
            this.capRecoleccion[Recurso.TRPIEDRA] = this.capRecoleccion[Recurso.TRPIEDRA] - ed.getCRP();
            this.capRecoleccion[0] = 100 - (this.capRecoleccion[Recurso.TRMADERA] + this.capRecoleccion[Recurso.TRPIEDRA]);
            s = ("Se ha construído " + ed.getNombre() + " en la posicion " + "(" + vecina.getX() + "," + vecina.getY() + ")");
        } else {
            throw new InsuficientesRecException("El grupo no tiene suficientes recursos, no puede construír");
        }
        return s;
    }

    @Override
    public String reparar(String direccion) throws FueraDeMapaException, ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException, EstarEnGrupoException, SolRepararException {
        String s;
        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no se puede mover, ya que está en el grupo " + getGrupo());
        }
        if (tieneSoldado) {
            throw new SolRepararException("El grupo tiene soldados");
        }

        Celda vecina = this.getCelda().getMapa().obtenerCeldaVecina(this.getCelda(), direccion);

        if (vecina.getEdificio() != null) {  // La celda contiene un edificio
            Edificio e = vecina.getEdificio();
            if (this.capRecoleccion[Recurso.TRMADERA] >= e.getCRM() && this.capRecoleccion[Recurso.TRPIEDRA] >= e.getCRP()) {
                if (e.getSalud() != e.getSaludInicial()) {
                    e.reiniciarSalud(); //edificio recobra la salud
                    this.capRecoleccion[Recurso.TRMADERA] -= e.getCRM();
                    this.capRecoleccion[Recurso.TRPIEDRA] -= e.getCRP();
                    this.capRecoleccion[0] -= (e.getCRM() + e.getCRP());
                    s = "Reparado el edificio " + e.getNombre() + "\n";
                    s += "Coste de la reparacion: " + (this.capRecoleccion[Recurso.TRMADERA] - e.getCRM()) + " de madera y " + (this.capRecoleccion[0] - e.getCRP()) + " de piedra";

                } else {
                    throw new NoNecRepararException("El edificio no necesita ser reparado");
                }
            } else {
                throw new InsuficientesRecException("El grupo no tiene los suficientes recursos para reparar");
            }
        } else {
            throw new EdificioException("No hay ningún edificio que reparar en esta posición");
        }
        return s;

    }

    /**
     *
     * @param direccion
     * @throws excepciones.personaje.EstarEnGrupoException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.recursos.NoRecolectableException
     * @throws excepciones.personaje.PersonajeLlenoException
     */
    @Override
    public String recolectar(String direccion) throws EstarEnGrupoException, FueraDeMapaException, ParametroIncorrectoException, NoRecolectableException, PersonajeLlenoException, RecursosException, SoldadoRecException {
        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no se puede mover, ya que está en el grupo " + getGrupo());
        }
        if (tieneSoldado) {
            throw new SoldadoRecException("El grupo tiene soldados");
        }
        String s;
        Celda actual = this.getCelda();
        Celda vecina = actual.getMapa().obtenerCeldaVecina(actual, direccion);

        if ((vecina.getContRecurso() instanceof Pradera) || (vecina.getContRecurso() == null)) {
            throw new NoRecolectableException("La celda no contiene un contenedor de recursos");
        }

        ContRecurso cr = vecina.getContRecurso();
        //Solo va a haber un elemento en la celd que va a ser un contenedor de recurso
        //se restan de la capacidad del contenedor de recursos la capacidad 
        // que tiene el personaje para recolectar
        int disponible = cr.getRecurso().getCapacidad(); //Capacidad disponible en ese momento
        int recolectado;
        if (this.getCapRecoleccion() == 0) {
            throw new PersonajeLlenoException("El grupo "+this.getNombre()+" agotó su capacidad de recolección");
        } else if (disponible > this.getCapRecoleccion()) {
            recolectado = this.getCapRecoleccion();
            cr.getRecurso().setCapacidad(disponible - recolectado); //cambia capacidad para recolectar en funcion de la recolectado
            this.capRecoleccion[0] = 0;
            this.capRecoleccion[cr.getRecurso().getTipo()] += recolectado;
            s = ("El grupo "+this.getNombre()+" ha conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
        } else if (this.getCapRecoleccion() >= disponible) {
            recolectado = disponible;
            this.capRecoleccion[0] -= recolectado;
            this.capRecoleccion[cr.getRecurso().getTipo()] += recolectado;

            // Convertimos la celda en pradera
            vecina.restartCelda();

            s = ("Has conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
        } else { //Si disponible, que 
            throw new RecursosException("Error al recolectar");
        }
        return s;
    }

    /**
     *
     * @param direccion
     * @return
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.NoAlmacenableException
     * @throws excepciones.personaje.EstarEnGrupoException
     * @throws excepciones.personaje.SolAlmacenarException
     */
    @Override
    public String almacenar(String direccion) throws FueraDeMapaException, ParametroIncorrectoException, NoAlmacenableException, EstarEnGrupoException, SolAlmacenarException {
        if (getEstarGrupo()) {
            throw new EstarEnGrupoException("El grupo no se puede almacenar, ya que está en el grupo " + getGrupo());
        }
        if (tieneSoldado) {
            throw new SolAlmacenarException("El grupo tiene soldados");
        }
        String s;

        Celda vecina = this.getCelda().getMapa().obtenerCeldaVecina(this.getCelda(), direccion);

        if (vecina.getEdificio() != null) {  // La celda contiene un edificio
            Edificio e = this.getCelda().getEdificio();
            if (e instanceof Casa) {
                throw new NoAlmacenableException("Una casa no puede almacenar");
            } else {
                e.setCapAlmacenamientoTotal(e.getCapAlmacenamiento()[0] + this.capRecoleccion[0]);
                e.setMadera(e.getCapAlmacenamiento()[Recurso.TRMADERA] + this.capRecoleccion[Recurso.TRMADERA]);
                e.setComida(e.getCapAlmacenamiento()[Recurso.TRCOMIDA] + this.capRecoleccion[Recurso.TRCOMIDA]);
                e.setPiedra(e.getCapAlmacenamiento()[Recurso.TRPIEDRA] + this.capRecoleccion[Recurso.TRPIEDRA]);
                //todo lo que tiene el personaje se le pasa a la ciudadela
                s = ("Almacenado " + this.capRecoleccion[Recurso.TRMADERA]
                        + " madera, " + this.capRecoleccion[Recurso.TRCOMIDA]
                        + " comida, y " + this.capRecoleccion[Recurso.TRPIEDRA]
                        + " piedra en el edificio " + direccion.toUpperCase());
                // Restauramos las capacidades del grupo
                this.capRecoleccion[0] = this.capRecoleccionInicial; //capacidad recoleccion vuelve a ser la inicial
                this.capRecoleccion[Recurso.TRMADERA] = 0;
                this.capRecoleccion[Recurso.TRCOMIDA] = 0;
                this.capRecoleccion[Recurso.TRPIEDRA] = 0;
            }
        } else {
            throw new NoAlmacenableException("En esa celda no se puede almacenar");
        }
        return s;
    }

    public void anhadirPersonaje(Personaje p) {
        personajes.add(p);
        setAtaque(this.getAtaque() + p.getAtaque());
        setArmadura(this.getArmadura() + p.getArmadura());
        setSalud(this.getSalud() + p.getSalud());
        if (p instanceof Paisano) {
            Paisano pa = (Paisano) p;
            this.capRecoleccion[0] += pa.getCapRecoleccion();
            this.capRecoleccion[Recurso.TRMADERA] += pa.getMadera();
            this.capRecoleccion[Recurso.TRCOMIDA] += pa.getComida();
            this.capRecoleccion[Recurso.TRPIEDRA] += pa.getPiedra();
        } else if(p instanceof Soldado) {
            this.tieneSoldado = true;
        }
    }
    
    /**
     * Desagrupamos el grupo grupo
     * 
     * @return Mensaje de acción
     * @throws ParametroIncorrectoException
     * @throws CeldaEnemigaException
     * @throws NoTransitablebleException
     * @throws FueraDeMapaException 
     */
    public String desagrupar() throws ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
        String s = "";
        Celda actual = this.getCelda();
        // Elimino el grupo de la celda actual
        actual.eliminarPersonaje(this);
        // Elimino el grupo de la civilizacion
        this.getCivilizacion().eliminaPersonaje(this);
        // Recorro la lista de personajes del grupo
        for(Personaje p : this.personajes) {
            s += "\tRecuperado personaje "+p.getNombre()+"\n";
            // Añado el personaje a la celda actual
            actual.anhadePersonaje(p);
            p.setEstarGrupo(false);
        }
        this.setCelda(null);
        this.setCivilizacion(null);
        this.personajes = new ArrayList<Personaje>();
        return s;
    }
    
    /**
     * Separamos un personaje del grupo
     * 
     * @param p El personaje a separar
     * @return Mesanje de acción
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.CeldaEnemigaException
     * @throws excepciones.celda.NoTransitablebleException
     * @throws excepciones.celda.FueraDeMapaException
     */
    public String desligar(Personaje p) throws ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
        // Saca al personaje de la lista de personajes del grupo
        if(this.personajes.contains(p)) {
            this.personajes.remove(p);
            p.setEstarGrupo(false);
        } else {
            throw new ParametroIncorrectoException("El personaje "+p.getNombre()+" no está en el grupo "+this.getNombre());
        }
        // añade el personaje a la celda
        this.getCelda().anhadePersonaje(p);
        // Si el grupo se ha quedado con un solo personaje, lo eliminamos
        if(this.personajes.size() == 1) {
            this.desagrupar();
        }
        return "Personaje "+p.getNombre()+" separado del grupo "+this.getNombre();
    }
    
    @Override
    public String toString() {
        String s = super.toString();
        if(!tieneSoldado) {
            s += "\n\tCapacidad de recolección: " + this.getCapRecoleccion();
            s += "\n\tDispone de " + this.getMadera() + " madera, " + this.getPiedra() + " piedra y " + this.getComida() + " comida.";
        }
        s += "\n\tEstá formado por " + this.personajes.size() + " personajes.";
        s += "\n\tPersonajes que forman el grupo: ";
        for (Personaje p : this.personajes) {
            s += "\n\t\t" + p.getNombre();
        }
        return (s);
    }

}
