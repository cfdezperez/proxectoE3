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
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.PersonajeLlenoException;
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
    private int ataque;
    private int defensa;
    private static int[] numeroGrupos = new int[Civilizacion.getNumDeCivilizaciones()];

    public Grupo(int salud, int armadura, int ataque, int capacidad) throws ParametroIncorrectoException {
        super(salud, armadura, ataque, true, Juego.TGRUPO);
        personajes = new ArrayList<Personaje>();
        //numeroGrupos[civil.getIdCivilizacion()]++;
        //setNombre("Grupo-" + numeroGrupos[civil.getIdCivilizacion()]);
        this.capRecoleccion[0] = capacidad < 0 ? 0 : capacidad;
        this.capRecoleccionInicial = this.capRecoleccion[0];
        this.ataque = 0;
        this.defensa = 0;
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
     * @param direccion
     * @throws excepciones.personaje.SoldadoRecException
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.recursos.NoRecolectableException
     * @throws excepciones.personaje.PersonajeLlenoException
     */
    @Override
    public void recolectar(String direccion) throws SoldadoRecException, RecursosException, PersonajeLlenoException, FueraDeMapaException, ParametroIncorrectoException, NoRecolectableException {
        Celda actual = this.getCelda();
        Celda vecina = actual.getMapa().obtenerCeldaVecina(actual, direccion);

        if ((vecina.getContRecurso() == null) || (vecina.getContRecurso() instanceof Pradera)) {
            throw new NoRecolectableException("La celda no contiene un contenedor de recursos");
        }

        for (Personaje p : this.getPersonajes()) {
            if (p instanceof Soldado) {
                throw new SoldadoRecException("El grupo contiene uno o más soldados, no puede recolectar");
            }
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
            System.out.println("El paisano ha conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
        } else if (this.getCapRecoleccion() >= disponible) {
            recolectado = disponible;
            this.capRecoleccion[0] -= recolectado;
            this.capRecoleccion[cr.getRecurso().getTipo()] += recolectado;

            // Convertimos la celda en pradera
            vecina.restartCelda();

            System.out.println("Has conseguido " + recolectado + " unidades de " + cr.getRecurso().getNombre());
        } else { //Si disponible, que 
            throw new RecursosException("Error al recolectar");
        }
    }

    public void anhadirPersonaje(Personaje p) {
        personajes.add(p);
        this.ataque += p.getAtaque();
        this.defensa += p.getArmadura();
        if (p instanceof Paisano) {
            Paisano pa = (Paisano) p;
            this.capRecoleccion[0] += pa.getCapRecoleccion();
            this.capRecoleccion[Recurso.TRMADERA] += pa.getMadera();
            this.capRecoleccion[Recurso.TRCOMIDA] += pa.getComida();
            this.capRecoleccion[Recurso.TRPIEDRA] += pa.getPiedra();
        }
        //compruebaSoldadoEnGrupo();
    }
    
    @Override
    public String toString() {
        String s = super.toString();       
        s += "\n\tEstá formado por " + this.personajes.size() + " personajes.";
        s += "\n\tPersonajes que forman el grupo: ";
        for(Personaje p: this.personajes) {
            s += "\n\t\t"+p.getNombre();
        }
        return(s);
    }

//    private void compruebaSoldadoEnGrupo() {
//        Iterator it = this.getPersonajes().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry a = (Map.Entry) it.next();
//            if (((Personaje) a.getValue()).getTipo() == Juego.TSOLDADO) {
//                this.capRecoleccion[0] = 0;
//                this.capRecoleccion[Recurso.TRMADERA] = 0;
//                this.capRecoleccion[Recurso.TRCOMIDA] = 0;
//                this.capRecoleccion[Recurso.TRPIEDRA] = 0;
//                break;
//            }
//        }
//    }
//        /**
//     *
//     * @param nedificio: nombre edificio(casa, ciudadela o cuartel)
//     * @param direccion
//     * @throws excepciones.personaje.InsuficientesRecException
//     * @throws excepciones.celda.FueraDeMapaException
//     * @throws excepciones.ParametroIncorrectoException
//     * @throws excepciones.celda.CeldaOcupadaException
//     */
//    public void construirEdificio(String nedificio, String direccion) throws InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException {
//
//        Celda vecina = this.getCelda().getMapa().obtenerCeldaVecina(this.getCelda(), direccion);
//        Edificio ed = null;
//        
//        switch (nedificio) {
//            case "ciudadela":
//                ed = new Ciudadela();
//                break;
//            case "cuartel":
//                ed = new Cuartel();
//                break;
//            case "casa":
//                ed = new Casa();
//                break;
//            default:
//                throw new ParametroIncorrectoException("Tipo de edificio desconocido");
//        }
//        if (this.capRecoleccion[1] >= ed.getCRM() && this.capRecoleccion[3] >= ed.getCRP()) {
//                    ed.inicializaNombre(Juego.getCivilizacionActiva());
//                    Juego.getCivilizacionActiva().anhadeEdificio(ed);
//                    vecina.anhadeEdificio(ed);
//                    vecina.setVisible(true);
//                    vecina.setTransitable(true); //Ponerlo a true
//                    this.capRecoleccion[Recurso.TRMADERA] = this.capRecoleccion[Recurso.TRMADERA] - ed.getCRM();
//                    this.capRecoleccion[Recurso.TRPIEDRA] = this.capRecoleccion[Recurso.TRPIEDRA] - ed.getCRP();
//                    this.capRecoleccion[0] = 100 - (this.capRecoleccion[Recurso.TRMADERA] + this.capRecoleccion[Recurso.TRPIEDRA]);
//                    System.out.println("Se ha construído " + ed.getNombre() + " en la posicion " + "(" + vecina.getX() + "," + vecina.getY() + ")");
//                } else {
//                    throw new InsuficientesRecException("El paisano no tiene suficientes recursos, no puede construír");
//                }
//
//    }
}
