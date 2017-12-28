/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import elementos.personaje.Grupo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase para las civilizaciones que participan en el juego
 *
 * @author celia
 */
public class Civilizacion {

    private Map<String, Personaje> Personajes; //personajes que pertenecen a la civilización
    private Map<String, Edificio> Edificios; //edificios que pertenecen a la civilización
    private Map<String, Grupo> Grupos; //grupos que pertenecen a la civilizacion
    private String nombreCivilizacion; //Nombre da la civilización
    private int idCivilizacion; //Identifica con un número que civilización es 0 primera, 1 segunda, 2...
    private static int numeroDeCivilizaciones = 0;

    /**
     * Crea una civilización con un nombre y un identificador determinados
     *
     * @param nCiv nombre de la civilización
     * @param idCiv identifica con un número que civilización es
     */
    public Civilizacion(String nCiv, int idCiv) {
        this.Personajes = new HashMap<String, Personaje>();
        this.Edificios = new HashMap<String, Edificio>();
        this.Grupos = new HashMap<String, Grupo>();
        this.nombreCivilizacion = nCiv;
        this.idCivilizacion = idCiv;
        Civilizacion.numeroDeCivilizaciones++;
    }

    /**
     * Obtiene los personajes pertenecientes a la civilizacion
     *
     * @return el HashMap de los personajes pertenecientes a la civilizacion
     */
    public Map<String, Personaje> getMapaPersonajes() {
        return this.Personajes;
    }

    /**
     * Obtiene los edificios pertenecientes a la civilización
     *
     * @return el HashMap de los edificios pertenecientes a la civilizacion
     */
    public Map<String, Edificio> getEdCivilizacion() {
        return this.Edificios;
    }

    /**
     * Obtiene los grupos pertenecientes a la civilización
     *
     * @return el HashMap de los grupos pertenecientes a la civilizacion
     */
    public Map<String, Grupo> getGrupoCivilizacion() {
        return this.Grupos;
    }

    /**
     * Obtiene el nombre de la civilizacion
     *
     * @return el nombre de la civilizacion
     */
    public String getNomCivilizacion() {
        return this.nombreCivilizacion;
    }

    /**
     * Obtiene el identificador de la civilización
     *
     * @return el identificador de la civilización
     */
    public int getIdCivilizacion() {
        return this.idCivilizacion;
    }

    /**
     * Obtiene el número total de civilizaciones definidas en el juego
     *
     * @return número de civilizaciones
     */
    public static int getNumDeCivilizaciones() {
        return numeroDeCivilizaciones;
    }

    /**
     * Pone el número de civilizaciones a 0
     */
    public static void resetNumDeCivilizaciones() {
        Civilizacion.numeroDeCivilizaciones = 0;
    }

    /**
     * Devuelve el numero de personajes
     */
    public int numeroPersonajes() {
        return this.Personajes.size();

    }

    /**
     * Devuelve un personaje de la civilización dado su nombre
     *
     * @param nombre Nombre del personaje
     * @return El personaje
     */
    public Personaje getPersonaje(String nombre) {
        Personaje p = Personajes.get(nombre);
        return p;
    }

    /**
     * Añade un personaje a la civilizacion
     *
     * @param p Personaje a añadir
     */
    public void anhadePersonaje(Personaje p) {
        p.setCivilizacion(this);
        this.Personajes.put(p.getNombre(), p);
    }

    /**
     * Añade un edificio a la civilizacion
     *
     * @param e Edificio a añadir
     */
    public void anhadeEdificio(Edificio e) {
        e.setCivilizacion(this);
        this.Edificios.put(e.getNombre(), e);
    }

    /**
     * Añade un grupo a la civilizacion
     *
     * @param g Grupo a añadir
     */
    public void anhadeGrupo(Grupo g) {
        g.setCivilizacion(this);
        this.Grupos.put(g.getNombre(), g);
    }

    @Override
    public String toString() {
        return (this.getNomCivilizacion());
    }

    public void listarPersonajes() {
        Iterator it = this.getMapaPersonajes().entrySet().iterator();

        System.out.println("Personajes de la civilizacion " + this.getNomCivilizacion() + ":");

        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("\t" + e.getKey() + " " + ((Personaje) e.getValue()).getCelda());
        }
    }

    public void listarEdificios() {
        Iterator it = this.getEdCivilizacion().entrySet().iterator();
        System.out.println("Edificios de la civilizacion " + this.getNomCivilizacion() + ":");
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println("\t" + e.getKey() + " " + ((Edificio) e.getValue()).getCelda());
        }
    }

    public void listarCivilizaciones() {
        Iterator it = this.Civilizaciones.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println(e.getKey());
        }
    }

    public void listarGrupos() {
        Iterator it = this.getGrupoCivilizacion().entrySet().iterator();
        System.out.println("Grupos de la civilizacion " + this.getNomCivilizacion() + ":");
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println(e.getKey());
        }
    }
}
