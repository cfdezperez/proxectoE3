/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import excepciones.FueraDeMapaException;
import excepciones.ParametroIncorrectoException;
import interfazUsuario.Juego;
import vista.Celda;
import vista.Mapa;
import excepciones.NoRecolectableException;
import excepciones.PersonajeLlenoException;
import excepciones.RecursosException;

/**
 *
 * @author celia
 */
public class Paisano extends Personaje {
    
    private int[] capRecoleccion = new int[4]; //0 capacidad total, 1 capacidad madera, 
    //2 capacidad comida, 3 capacidad piedra
    private final int capRecoleccionInicial;
    private static int[] numeroPaisanos = new int[Civilizacion.getNumDeCivilizaciones()];

    public Paisano(Celda c, Civilizacion civ) throws ParametroIncorrectoException {
        this(c, civ, 100, 25, 25, 150);
    }

    public Paisano(Celda c, Civilizacion civil, int salud, int armadura, int ataque, int capacidad) throws ParametroIncorrectoException {
        super(c, civil, salud, armadura, ataque, true, Juego.TPAISANO);
        numeroPaisanos[civil.getIdCivilizacion()]++;
        setNombre("Paisano-" + numeroPaisanos[civil.getIdCivilizacion()]);
        this.capRecoleccion[0] = capacidad < 0 ? 0 : capacidad;
        this.capRecoleccionInicial = this.capRecoleccion[0];
    }

    /**
     *
     * @param mapa
     * @param direccion
     * @throws excepciones.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.NoRecolectableException
     * @throws excepciones.PersonajeLlenoException
     */
    public void recolectar(String direccion) throws RecursosException, PersonajeLlenoException, FueraDeMapaException, ParametroIncorrectoException, NoRecolectableException {
        Celda actual = this.getCelda();
        Celda vecina = actual.getMapa().obtenerCeldaVecina(actual, direccion);

        if (vecina.getContRecurso() == null) {
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
}
