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
import elementos.cr.Pradera;
import elementos.Recurso;
import elementos.edificio.Casa;
import elementos.edificio.Ciudadela;
import elementos.edificio.Cuartel;
import excepciones.celda.FueraDeMapaException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.NoAlmacenableException;
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import interfazUsuario.Juego;
import vista.Celda;
import excepciones.recursos.NoRecolectableException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.recursos.RecursosException;

/**
 *
 * @author celia y maria
 */
public class Paisano extends Personaje {

    private final int[] capRecoleccion = new int[4]; //0 capacidad total, 1 capacidad madera, 
    //2 capacidad comida, 3 capacidad piedra
    private final int capRecoleccionInicial;
    private static int[] numeroPaisanos = new int[Civilizacion.getNumDeCivilizaciones()];

    public Paisano() throws ParametroIncorrectoException {
        this(100, 25, 25, 150);
    }

    public Paisano(int salud, int armadura, int ataque, int capacidad) throws ParametroIncorrectoException {
        super(salud, armadura, ataque, true, Juego.TPAISANO);
        this.capRecoleccion[0] = capacidad < 0 ? 0 : capacidad;
        this.capRecoleccionInicial = this.capRecoleccion[0];
    }

    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroPaisanos[civil.getIdCivilizacion()]++;
        setNombre("Paisano-" + numeroPaisanos[civil.getIdCivilizacion()]);
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

    @Override
    public String toString() {
        String s = super.toString();
        s += "\n\tCapacidad de recolección: " + this.getCapRecoleccion();
        s += "\n\tDispone de " + this.getMadera() + " madera, " + this.getPiedra() + " piedra y " + this.getComida() + " comida.";
        return (s);
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
    public String recolectar(String direccion) throws EstarEnGrupoException, FueraDeMapaException, ParametroIncorrectoException, NoRecolectableException, PersonajeLlenoException, RecursosException {
        if(getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no se puede mover, ya que está en el grupo "+getGrupo());
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
            throw new PersonajeLlenoException("El personaje agotó su capacidad de recolección");
        } else if (disponible > this.getCapRecoleccion()) {
            recolectado = this.getCapRecoleccion();
            cr.getRecurso().setCapacidad(disponible - recolectado); //cambia capacidad para recolectar en funcion de la recolectado
            this.capRecoleccion[0] = 0;
            this.capRecoleccion[cr.getRecurso().getTipo()] += recolectado;
            s = ("El paisano ha conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
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
     * @param nedificio: nombre edificio(casa, ciudadela o cuartel)
     * @param direccion
     * @throws excepciones.personaje.InsuficientesRecException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.CeldaOcupadaException
     * @throws excepciones.celda.CeldaEnemigaException
     * @throws excepciones.personaje.EstarEnGrupoException
     */
    @Override
    public String construirEdificio(String nedificio, String direccion) throws InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, EstarEnGrupoException {
        if(getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no se puede mover, ya que está en el grupo "+getGrupo());
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
            throw new InsuficientesRecException("El paisano no tiene suficientes recursos, no puede construír");
        }
        return s;
    }

    @Override
    public String reparar(String direccion) throws FueraDeMapaException, ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException, EstarEnGrupoException {
        String s;
        if(getEstarGrupo()) {
            throw new EstarEnGrupoException("El personaje no se puede mover, ya que está en el grupo "+getGrupo());
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
                    s = "Reparado el edificio " + e.getNombre()+"\n";
                    s += "Coste de la reparacion: " + (this.capRecoleccion[Recurso.TRMADERA] - e.getCRM()) + " de madera y " + (this.capRecoleccion[0] - e.getCRP()) + " de piedra";

                } else {
                    throw new NoNecRepararException("El edificio no necesita ser reparado");
                }
            } else {
                throw new InsuficientesRecException("El paisano no tiene los suficientes recursos para reparar");
            }
        } else {
            throw new EdificioException("No hay ningún edificio que reparar en esta posición");
        }
        return s;

    }

    /**
     *
     * @param direccion
     * @return 
     * @throws excepciones.personaje.InsuficientesRecException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.NoAlmacenableException
     * @throws excepciones.personaje.EstarEnGrupoException
     */
    @Override
    public String almacenar(String direccion) throws InsuficientesRecException, FueraDeMapaException, ParametroIncorrectoException, NoAlmacenableException, EstarEnGrupoException {
        if(getEstarGrupo()) {
            throw new EstarEnGrupoException("El paisano puede almacenar, ya que está en el grupo "+getGrupo());
        } 
        String s;
        
        Celda vecina = this.getCelda().getMapa().obtenerCeldaVecina(this.getCelda(), direccion);

        if (vecina.getEdificio() != null) {  // La celda contiene un edificio
            Edificio e = vecina.getEdificio();
            if (e instanceof Casa) {
                throw new NoAlmacenableException("Una casa no puede almacenar");
            } else {
                if(this.getCapRecoleccion() == (this.getCRInicial())){
                    throw new InsuficientesRecException("El paisano no tiene recursos que almacenar");
                }else{
                
                e.almacenar(this.capRecoleccion);

                //todo lo que tiene el personaje se le pasa a la ciudadela
                s = ("Almacenado " + this.capRecoleccion[Recurso.TRMADERA]
                        + " madera, " + this.capRecoleccion[Recurso.TRCOMIDA]
                        + " comida, y " + this.capRecoleccion[Recurso.TRPIEDRA]
                        + " piedra en el edificio " + direccion.toUpperCase());
                // Restauramos las capacidades del paisano
                this.capRecoleccion[0] = this.capRecoleccionInicial; //capacidad recoleccion vuelve a ser la inicial
                this.capRecoleccion[Recurso.TRMADERA] = 0;
                this.capRecoleccion[Recurso.TRCOMIDA] = 0;
                this.capRecoleccion[Recurso.TRPIEDRA] = 0;
                }
            }
        } else {
            throw new NoAlmacenableException("En esa celda no se puede almacenar");
        }
        return s;
    }
}
