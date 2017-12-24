/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import java.util.HashMap;
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
    public Map<String, Personaje> getPerCivilizacion() {
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
}
