/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import java.util.HashMap;
import java.util.Map;
import elementos.ContRecurso;
import elementos.Civilizacion;
import excepciones.CeldaEnemigaException;
import excepciones.FueraDeMapaException;
import excepciones.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import vista.Mapa;

/**
 * Clase principal para inciar el juego
 * 
 * @author celia
 */
public class Juego implements Comando{
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
    public static final int TARQUERO = 11;
    public static final int TCABALLERO = 12;
    public static final int TLEGIONARIO= 13;

    public static final String[][] SIMBOLOS = {{" X ", " B ", " A ", " C ", " P ", " S ", " M ", " L ", " N ", " G ", " V ", " Q ", " O ", " I "},
    {" X ", " B ", " A ", " C ", " p ", " s ", " m ", " l ", " n ", " g ", " v ", " q ", " o ", " i "}};

    private Map<String, Civilizacion> Civilizaciones;
    private Map<String, ContRecurso> ContRecursos;
    private static Civilizacion civilizacionActiva;
    private Mapa mapa;
    private int[] contador; // Para los contenedores de recursos
    private int[][] contadorElementos; //Primer campo es la civilizacion, segundo campo es el tipo de dato
    
    
    /**
     * Construye un juego por defecto
     * 
     * @param tamX Número de columnas del mapa
     * @param tamY Número de filas del mapa
     * 
     * @param nombreCivilizaciones Nombres de las civilizaciones que participan en el juego 
     */
    public Juego(int tamX, int tamY, String[] nombreCivilizaciones) {
        mapa = new Mapa(tamX, tamY);
        this.ContRecursos = new HashMap<String, ContRecurso>();
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
    
    public static Civilizacion getCivilizacionActiva() {
        return civilizacionActiva;
    }

    public Mapa getMapa() {
        return this.mapa;
    }
    
    public Map<String, ContRecurso> getContRecursos(){
        return this.ContRecursos;
    }
    
    @Override
    public void mover(String nombre, int direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException{
        this.civilizacionActiva.getPersonaje(nombre).mover(mapa, nombre);
    }
    
}
