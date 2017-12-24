/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import elementos.ContRecursos;
import elementos.Civilizacion;
import java.util.List;
import vista.Celda;

/**
 *
 * @author tomas
 */
public class Juego {
    public static final int TPAISANO = 4;
    public static final int TSOLDADO = 5;
    public static final int TCIUDADELA = 6;
    public static final int TCASA = 7;
    public static final int TCUARTEL = 8;
    public static final int TBOSQUE = 1;
    public static final int TARBUSTO = 2;
    public static final int TCANTERA = 3;
    public static final int TPRADERA = 0;
    public static final int TGRUPO = 9;
    public static final int TVARIOS = 10;

    public static final String[][] SIMBOLOS = {{" X ", " B ", " A ", " C ", " P ", " S ", " M ", " L ", " N ", " G ", " V "},
    {" X ", " B ", " A ", " C ", " p ", " s ", " m ", " l ", " n ", " g ", " v "}};

    private Map<String, Civilizacion> Civilizaciones;
    private Map<String, ContRecursos> ContRecursos;
    private List<List<Celda>> Celdas;
    private Civilizacion civilizacionActiva;
    private int tamX;
    private int tamY;
    private int[] contador; // Para los contenedores de recursos
    private int[][] contadorElementos; //Primer campo es la civilizacion, segundo campo es el tipo de dato
    
    /**
     * Construye un juego
     * @param nombreCivilizaciones Nombre de las civilizaciones que participan en el juego 
     */

    public  Juego(String[] nombreCivilizaciones) {
        this.ContRecursos = new HashMap<String, ContRecursos>();
        this.Celdas = new ArrayList<List<Celda>>();
        this.tamX = 10;
        this.tamY = 10;
        this.contador = new int[9];
        String[] nc = new String[nombreCivilizaciones.length];

        // Creamos las civilizaciones
        Civilizaciones = new HashMap<String, Civilizacion>(2);
        int idCiv = 0;
        for (String nombreCiv : nombreCivilizaciones) {
            nc[idCiv] = nombreCiv.trim();
            Civilizaciones.put(nc[idCiv].trim(), new Civilizacion(nc[idCiv], idCiv));
            // La civilización inicialmente activa es la que tiene id = 0
            if (idCiv == 0) {
                this.civilizacionActiva = this.Civilizaciones.get(nc[idCiv]);
            }
            idCiv++;
        }        
    }
}
