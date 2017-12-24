/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vista.Celda;
 

/**
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
     * @param nCiv nombre de la civilización
     * @param idCiv identifica con un número que civilización es
     */
    public Civilizacion(String nCiv, int idCiv){
        this.Personajes = new HashMap<String, Personaje>(); 
        this.Edificios = new HashMap<String, Edificio>();
        this.Grupos = new HashMap<String, Grupo>();
        this.nombreCivilizacion = nCiv;
        this.idCivilizacion = idCiv;
        Civilizacion.numeroDeCivilizaciones++;
    }
}
