/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import elementos.Arbusto;
import elementos.Bosque;
import elementos.Cantera;
import java.util.HashMap;
import java.util.Map;
import elementos.ContRecurso;
import elementos.Civilizacion;
import elementos.Comida;
import elementos.Madera;
import elementos.Piedra;
import elementos.Recurso;
import excepciones.CeldaEnemigaException;
import excepciones.CeldaOcupadaException;
import excepciones.FueraDeMapaException;
import excepciones.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import java.util.ArrayList;
import java.util.List;
import vista.Celda;
import vista.Mapa;

/**
 * Clase principal para inciar el juego
 *
 * @author celia
 */
public class Juego implements Comando {

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
    public static final int TLEGIONARIO = 13;

    public static final String[][] SIMBOLOS = {{" X ", " B ", " A ", " C ", " P ", " S ", " M ", " L ", " N ", " G ", " V ", " Q ", " O ", " I "},
    {" X ", " B ", " A ", " C ", " p ", " s ", " m ", " l ", " n ", " g ", " v ", " q ", " o ", " i "}};

    private Map<String, Civilizacion> civilizaciones;
    private Map<String, ContRecurso> contRecursos;
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
     * @param nombreCivilizaciones Nombres de las civilizaciones que participan
     * en el juego
     */
    public Juego(int tamX, int tamY, String[] nombreCivilizaciones) throws CeldaOcupadaException {
        mapa = new Mapa(tamX, tamY);
        this.contRecursos = new HashMap<String, ContRecurso>();
        this.contador = new int[9];
        String[] nc = new String[nombreCivilizaciones.length];

        // Creamos las civilizaciones
        civilizaciones = new HashMap<String, Civilizacion>(2);
        int idCiv = 0;
        for (String nombreCiv : nombreCivilizaciones) {
            nc[idCiv] = nombreCiv.trim();
            civilizaciones.put(nc[idCiv].trim(), new Civilizacion(nc[idCiv], idCiv));
            // La civilización inicialmente activa es la que tiene id = 0
            if (idCiv == 0) {
                this.civilizacionActiva = this.civilizaciones.get(nc[idCiv]);
            }
            idCiv++;
        }
    }

    /**
     * Construye un juego a partie de una lista de personajes, edificios y
     * recursos
     *
     * @param personajes Campos:
     * Coordenada;Tipo;Codigo;Descripcion;Ataque;Defensa;Salud;Capacidad;Grupo;Civilizacion
     * @param edificios
     * @param recursos # Coordenada;Tipo;Codigo;Descripcion;Cantidad
     */
    public Juego(List<List<String>> personajes, List<List<String>> edificios, List<List<String>> recursos) throws ParametroIncorrectoException, FueraDeMapaException {
        this.civilizaciones = new HashMap<String, Civilizacion>(2);

        int maxX = 0, maxY = 0;
        // Obtenemos el tamaño del mapa
        for (List<String> recurso : recursos) {
            int x = new Integer(recurso.get(0).split(",")[0]);
            int y = new Integer(recurso.get(0).split(",")[1]);
            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
        }

        // Creo el mapa con todo praderas
        mapa = new Mapa(maxX, maxY);

        // Creo las civilizaciones
        creaCivilizaciones(personajes, edificios);
        
        // Meto los contenedores de recursos
        introduceCR(recursos);
        
        // Le meto los personajes
        introducePersonajes(personajes);
        
        // Creo los grupos
        creaGruposDeFichero(personajes);
        
        // Le meto los edificios
        introduceEdificios(edificios);        
    }

    public static Civilizacion getCivilizacionActiva() {
        return civilizacionActiva;
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public Map<String, ContRecurso> getContRecursos() {
        return this.contRecursos;
    }

    @Override
    public void mover(String nombre, int direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException {
        this.civilizacionActiva.getPersonaje(nombre).mover(mapa, nombre);
    }

    // Métodos privados
    
    private void creaCivilizaciones(List<List<String>> personajes, List<List<String>> edificios) throws FueraDeMapaException {
        // Creamos las civilizaciones
        Civilizacion.resetNumDeCivilizaciones();
        int idCiv = 0;
        // civilizaciones definidas en el fichero de edificios
        for (List<String> edificio : edificios) {
            int x = new Integer(edificio.get(0).split(",")[0]);
            int y = new Integer(edificio.get(0).split(",")[1]);
            if (x >= mapa.getTamX() || y >= mapa.getTamY()) {
                throw new FueraDeMapaException("El edificio " + edificio.get(2) + " se sale del mapa");
            } else {
                String nombreCivilizacion = edificio.get(4);
                // Creamos la civilizacion
                Civilizacion civ;
                if (!civilizaciones.containsKey(nombreCivilizacion)) {  // Si la civilizacion no existe, la creamos
                    civ = new Civilizacion(nombreCivilizacion, idCiv);
                    civilizaciones.put(nombreCivilizacion, civ);
                    if (idCiv == 0) {
                        this.civilizacionActiva = civ;
                    }
                    idCiv++;
                }
            }
        }
        idCiv = civilizaciones.size();
        // civilizaciones definidas en el fichero de personajes
        for (List<String> personaje : personajes) {
            int x = new Integer(personaje.get(0).split(",")[0]);
            int y = new Integer(personaje.get(0).split(",")[1]);
            if (x >= mapa.getTamX() || y >= mapa.getTamY()) {
                throw new FueraDeMapaException("El personaje " + personaje.get(2) + " se sale del mapa");
            } else {
                String nombreCivilizacion = personaje.get(9);
                // Creamos la civilizacion si no existe
                Civilizacion civ;
                if (!civilizaciones.containsKey(nombreCivilizacion)) {  // Si la civilizacion no existe, la creamos
                    civ = new Civilizacion(nombreCivilizacion, idCiv);
                    civilizaciones.put(nombreCivilizacion, civ);
                    if (idCiv == 0) {
                        this.civilizacionActiva = civ;
                    }
                    idCiv++;
                }
            }
        }
    }
    
    private void introduceCR(List<List<String>> recursos) throws ParametroIncorrectoException, CeldaOcupadaException {
               for (List<String> recurso : recursos) {
            int x = new Integer(recurso.get(0).split(",")[0]);
            int y = new Integer(recurso.get(0).split(",")[1]);

            String tipo = recurso.get(1);

            if (!tipo.equals("Pradera")) {
                String codigo = recurso.get(2);
                String descripcion = recurso.get(3);
                int cantidad = new Integer(recurso.get(4));
                Celda c = mapa.obtenerCelda(x, y);
                ContRecurso cr = null;
                switch (tipo) {
                    case "Arbusto":
                        cr = new Arbusto(c, new Comida(cantidad));
                        break;
                    case "Bosque":
                        cr = new Bosque(c, new Madera(cantidad));
                        break;
                    case "Cantera":
                        cr = new Cantera(c, new Piedra(cantidad));
                        break;
                    default:
                        throw new ParametroIncorrectoException("Tipo de contenedor de recursos desconocido");
                }
            }
        }
    }

    private void introducePersonajes(List<List<String>> personajes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void introduceEdificios(List<List<String>> edificios) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void creaGruposDeFichero(List<List<String>> personajes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
