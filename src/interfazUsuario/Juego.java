/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import elementos.cr.Arbusto;
import elementos.personaje.Arquero;
import elementos.cr.Bosque;
import elementos.personaje.Caballero;
import elementos.cr.Cantera;
import elementos.edificio.Casa;
import elementos.edificio.Ciudadela;
import java.util.HashMap;
import java.util.Map;
import elementos.ContRecurso;
import elementos.Civilizacion;
import elementos.recursos.Comida;
import elementos.edificio.Cuartel;
import elementos.Edificio;
import elementos.personaje.Legionario;
import elementos.recursos.Madera;
import elementos.personaje.Paisano;
import elementos.Personaje;
import elementos.recursos.Piedra;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.NoAgrupableException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SolRepararException;
import excepciones.personaje.SoldadoRecException;
import excepciones.recursos.RecursosException;
import java.util.ArrayList;
import java.util.Iterator;
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
    public Juego(int tamX, int tamY, String[] nombreCivilizaciones) {
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
     * @throws excepciones.celda.FueraDeMapaException
     * @throws excepciones.ParametroIncorrectoException
     * @throws excepciones.celda.CeldaOcupadaException
     * @throws excepciones.celda.CeldaEnemigaException
     */
    public Juego(List<List<String>> personajes, List<List<String>> edificios, List<List<String>> recursos) throws FueraDeMapaException, ParametroIncorrectoException, CeldaOcupadaException, CeldaEnemigaException {
        this.civilizaciones = new HashMap<String, Civilizacion>(2);

        int maxX = 0, maxY = 0;
        // Obtenemos el tamaño del mapa
        for (List<String> recurso : recursos) {
            int y = new Integer(recurso.get(0).split(",")[0]);
            int x = new Integer(recurso.get(0).split(",")[1]);
            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
        }

        // Creo el mapa con todo praderas
        mapa = new Mapa(maxX + 1, maxY + 1);

        // Creo las civilizaciones
        creaCivilizaciones(personajes, edificios);

        // Meto los contenedores de recursos
        introduceCR(recursos);

        // Le meto los personajes
        introducePersonajes(personajes);

        // Creo los grupos
//        creaGruposDeFichero(personajes);
        // Le meto los edificios
        introduceEdificios(edificios);
    }

    public static Civilizacion getCivilizacionActiva() {
        return civilizacionActiva;
    }

    /**
     * Devuelve una civilización a partir de un nombre
     *
     * @param nombre El nombre de la civilización
     * @return La civilicación asociada a ese nombre
     * @throws excepciones.ParametroIncorrectoException
     */
    public Civilizacion getCivilizacion(String nombre) throws ParametroIncorrectoException {
        if (civilizaciones.containsKey(nombre)) {
            return civilizaciones.get(nombre);
        } else {
            throw new ParametroIncorrectoException("Nombre de civilización incorrecto");
        }
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public Map<String, ContRecurso> getContRecursos() {
        return this.contRecursos;
    }

    @Override
    public void mover(String nombre, String direccion) throws EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException {
        Juego.civilizacionActiva.getPersonaje(nombre).mover(mapa, direccion);
    }

    @Override
    public String listar(String tipo) throws ParametroIncorrectoException {
        switch (tipo.toLowerCase()) {
            case "personajes":
                return getCivilizacionActiva().listarPersonajes();
            case "edificios":
                return getCivilizacionActiva().listarEdificios();
            case "civilizaciones":
                return listarCivilizaciones();
            default:
                throw new ParametroIncorrectoException("Elementos a listar desconocidos.");
        }
    }

    @Override
    public String describir(String nombre) throws ParametroIncorrectoException {
        if (getCivilizacionActiva().getMapaPersonajes().containsKey(nombre)) {
            return (getCivilizacionActiva().getMapaPersonajes().get(nombre).toString());
        } else if (getCivilizacionActiva().getEdCivilizacion().containsKey(nombre)) {
            return (getCivilizacionActiva().getEdCivilizacion().get(nombre).toString());
        } else {
            throw new ParametroIncorrectoException(nombre + " no corresponde a un personaje ni a un edificio.");
        }
    }

    @Override
    public String describir(String nombre, String civilizacion) throws ParametroIncorrectoException {
        Civilizacion civ = this.getCivilizacion(civilizacion);
        if (civ.getMapaPersonajes().containsKey(nombre)) {
            return (civ.getMapaPersonajes().get(nombre).toString());
        } else if (civ.getEdCivilizacion().containsKey(nombre)) {
            return (civ.getEdCivilizacion().get(nombre).toString());
        } else {
            throw new ParametroIncorrectoException(nombre + " no corresponde a un personaje ni a un edificio.");
        }
    }

    @Override
    public String mirar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException {
        String[] fc = coordenadasCelda.split("");
        int f = Integer.parseInt(fc[1]);
        int c = Integer.parseInt(fc[3]);
        if ((f < 0) | (c < 0) | (f >= (getMapa().getTamY() - 1)) | (c >= (getMapa().getTamX() - 1))) {
            throw new FueraDeMapaException("La celda no está en el mapa");
        }
        return ((mapa.obtenerCelda(c, f)).mirar());
    }

    @Override
    public void construir(String Personaje, String nEdificio, String direccion) throws InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, SolConstruirException, EstarEnGrupoException {
        Personaje p = civilizacionActiva.getPersonaje(Personaje);
        p.construirEdificio(nEdificio, direccion);
    }

    @Override
    public void recolectar(String nPersonaje, String direccion) throws
            PersonajeLlenoException, SoldadoRecException, RecursosException,
            FueraDeMapaException, ParametroIncorrectoException, CeldaOcupadaException, EstarEnGrupoException {
        getCivilizacionActiva().getPersonaje(nPersonaje).recolectar(direccion);
    }

    @Override
    public void cambiarCivilizacion(String nCivilizacion) throws ParametroIncorrectoException {
        Juego.civilizacionActiva = this.getCivilizacion(nCivilizacion);
    }

    @Override
    public void imprimirCivilizacion() throws ParametroIncorrectoException {
        this.getMapa().imprimirCivilizacion(getCivilizacionActiva());
        imprimirCivilizacion(getCivilizacionActiva().getNomCivilizacion());
    }

    @Override
    public void imprimirCivilizacion(String nCivilizacion) throws ParametroIncorrectoException {
        this.getMapa().imprimirCivilizacion(getCivilizacion(nCivilizacion));
    }
    
    @Override
    public String agrupar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException, 
            ParametroIncorrectoException, NoAgrupableException, CeldaEnemigaException {
        String[] fc = coordenadasCelda.split("");
        int f = Integer.parseInt(fc[1]);
        int c = Integer.parseInt(fc[3]);
        if ((f < 0) | (c < 0) | (f >= (getMapa().getTamY() - 1)) | (c >= (getMapa().getTamX() - 1))) {
            throw new FueraDeMapaException("La celda no está en el mapa");
        }
        return this.getMapa().obtenerCelda(c, f).agrupar();
    }
    
    // Métodos privados
    private void creaCivilizaciones(List<List<String>> personajes, List<List<String>> edificios) throws FueraDeMapaException {
        // Creamos las civilizaciones
        Civilizacion.resetNumDeCivilizaciones();
        int idCiv = 0;
        // civilizaciones definidas en el fichero de edificios
        for (List<String> edificio : edificios) {
            int y = new Integer(edificio.get(0).split(",")[0]);
            int x = new Integer(edificio.get(0).split(",")[1]);
            if (x < 0 || y < 0 || x >= mapa.getTamX() || y >= mapa.getTamY()) {
                throw new FueraDeMapaException("La posición (" + x + ", " + y + ") del edificio " + edificio.get(2) + " se sale del mapa");
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
            int y = new Integer(personaje.get(0).split(",")[0]);
            int x = new Integer(personaje.get(0).split(",")[1]);
            if (x < 0 || y < 0 || x >= mapa.getTamX() || y >= mapa.getTamY()) {
                throw new FueraDeMapaException("La posición (" + x + ", " + y + ") del personaje " + personaje.get(2) + " se sale del mapa");
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
            int y = new Integer(recurso.get(0).split(",")[0]);
            int x = new Integer(recurso.get(0).split(",")[1]);

            String tipo = recurso.get(1);

            if (!tipo.equals("Pradera")) {
                String codigo = recurso.get(2);
                String descripcion = recurso.get(3);
                int cantidad = new Integer(recurso.get(4));
                Celda c = mapa.obtenerCelda(x, y);
                ContRecurso cr = null;
                switch (tipo) {
                    case "Arbusto":
                        cr = new Arbusto(new Comida(cantidad));
                        break;
                    case "Bosque":
                        cr = new Bosque(new Madera(cantidad));
                        break;
                    case "Cantera":
                        cr = new Cantera(new Piedra(cantidad));
                        break;
                    default:
                        throw new ParametroIncorrectoException("Tipo de contenedor de recursos desconocido");
                }
                c.anhadeCR(cr);
            }
        }
    }

    private void introduceEdificios(List<List<String>> edificios) throws FueraDeMapaException, CeldaOcupadaException, ParametroIncorrectoException, CeldaEnemigaException {
        // Metemos los edificios
        for (List<String> edificio : edificios) {
            int y = new Integer(edificio.get(0).split(",")[0]);
            int x = new Integer(edificio.get(0).split(",")[1]);
            if (x < 0 || y < 0 || x >= mapa.getTamX() || y >= mapa.getTamY()) {
                throw new FueraDeMapaException("La posición (" + x + ", " + y + ") del edificio " + edificio.get(2) + " se sale del mapa");
            } else {
                String tipo = edificio.get(1);
                String codigo = edificio.get(2);
                String descripcion = edificio.get(3);
                Civilizacion civilizacion = getCivilizacion(edificio.get(4));
                Celda c = mapa.obtenerCelda(x, y);
                Edificio e = null;
                switch (tipo) {
                    case "Casa":
                        e = new Casa();
                        break;
                    case "Ciudadela":
                        e = new Ciudadela();
                        break;
                    case "Cuartel":
                        e = new Cuartel();
                        break;
                    default:
                        throw new ParametroIncorrectoException("Tipo de edificio desconocido");
                }
                if (e != null) {
                    // Inicializo el nombre para que se actualice el contador
                    e.inicializaNombre(civilizacion);
                    // Le pongo el nombre del fichero
                    e.setNombre(codigo);
                    civilizacion.anhadeEdificio(e);
                    c.anhadeEdificio(e);
                }
            }
        }
    }

    private void introducePersonajes(List<List<String>> personajes) throws ParametroIncorrectoException, CeldaEnemigaException, FueraDeMapaException {
        // Recorremos la infprmación de los personajes
        for (List<String> personaje : personajes) {
            int y = new Integer(personaje.get(0).split(",")[0]);
            int x = new Integer(personaje.get(0).split(",")[1]);
            if (x < 0 || y < 0 || x >= mapa.getTamX() || y >= mapa.getTamY()) {
                throw new FueraDeMapaException("La posición (" + x + ", " + y + ") del personaje " + personaje.get(2) + " se sale del mapa");
            } else {
                String tipo = personaje.get(1);
                String codigo = personaje.get(2);
                String descripcion = personaje.get(3);
                int ataque = new Integer(personaje.get(4));
                int defensa = new Integer(personaje.get(5));
                int salud = new Integer(personaje.get(6));
                int capacidad = new Integer(personaje.get(7));
                Civilizacion civilizacion = getCivilizacion(personaje.get(9));
                Celda c = mapa.obtenerCelda(x, y);
                // Anhadimos el personaje
                Personaje per = null;
                switch (tipo) {
                    case "Paisano":
                        per = new Paisano(salud, defensa, ataque, capacidad);
                        break;
                    case "Arquero":
                        per = new Arquero(salud, defensa, ataque);
                        break;
                    case "Caballero":
                        per = new Caballero(salud, defensa, ataque);
                        break;
                    case "Legionario":
                        per = new Legionario(salud, defensa, ataque);
                        break;
                    default:
                        throw new ParametroIncorrectoException("Tipo de personaje desconocido");
                }
                if (per != null) {
                    per.inicializaNombre(civilizacion);
                    per.setNombre(codigo);
                    civilizacion.anhadePersonaje(per);
                    c.anhadePersonaje(per);
                }
            }
        }
    }
//
//    private void creaGruposDeFichero(List<List<String>> personajes) throws FueraDeMapaException, ParametroIncorrectoException {
//        // Recorremos de nuevo en busca de grupos
//        int xAnterior = -1;
//        int yAnterior = -1;
//        for (List<String> personaje : personajes) {
//            int x = new Integer(personaje.get(0).split(",")[0]);
//            int y = new Integer(personaje.get(0).split(",")[1]);
//            if (x < 0 || y < 0 || x >= mapa.getTamX() || y >= mapa.getTamY()) {
//                throw new FueraDeMapaException("El personaje " + personaje.get(2) + " se sale del mapa");
//            } else {
//                String nombreGrupo = personaje.get(8);
//                Civilizacion civilizacion = getCivilizacion(personaje.get(9));
//                if (x != xAnterior || y != yAnterior) { // Empezamos una nueva celda
//                    // Agrupamos la celda
//                    Celda c = mapa.obtenerCelda(x, y);
//                    c.agrupar(this, civilizacion, nombreGrupo);
//                    xAnterior = x;
//                    yAnterior = y;
//                }
//            }
//        }
//    }

    @Override
    public void crear(String nEdificio, String tipoPersonaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void almacenar(String nPersonaje, String direccion) throws NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void reparar(String nPersonaje, String direccion) throws SolRepararException, FueraDeMapaException, 
            ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException, EstarEnGrupoException {
        getCivilizacionActiva().getPersonaje(nPersonaje).reparar(direccion);
    }

    public void juegoPorDefecto() throws CeldaOcupadaException, ParametroIncorrectoException, CeldaEnemigaException {
        // Obtiene una lista con las dos civilizaciones
        List<Civilizacion> civs = new ArrayList<Civilizacion>(civilizaciones.values());
        Civilizacion civ0 = civs.get(0);
        Civilizacion civ1 = civs.get(1);
        Mapa m = getMapa();

        // Añadimos elementos por columnas
        // columna 0 (primera coordenada = columna, segunda = fila)
        // Bosque en (0, 0) con 10 Madera
        anhadeCR(new Bosque(new Madera(10)), m.obtenerCelda(0, 0));
        // Arbusto en (0, 4) con 5 Comida
        anhadeCR(new Arbusto(new Comida(5)), m.obtenerCelda(0, 4));
        // Arbusto en (0, 9) con 5 Comida
        anhadeCR(new Arbusto(new Comida(5)), m.obtenerCelda(0, 9));

        // Columna 1
        anhadeEdificio(new Ciudadela(), m.obtenerCelda(1, 1), civ0);
        anhadePersonaje(new Paisano(), m.obtenerCelda(1, 2), civ0);
        anhadeCR(new Cantera(new Piedra(40)), m.obtenerCelda(1, 6));
        anhadeCR(new Bosque(new Madera(100)), m.obtenerCelda(1, 7));
        anhadeCR(new Arbusto(new Comida(20)), m.obtenerCelda(1, 9));

        // Columna 2
        anhadeEdificio(new Casa(), m.obtenerCelda(2, 2), civ0);
        anhadeCR(new Bosque(new Madera(10)), m.obtenerCelda(2, 4));
        anhadeCR(new Arbusto(new Comida(10)), m.obtenerCelda(2, 9));

        // Columna 3
        anhadeCR(new Cantera(new Piedra(80)), m.obtenerCelda(3, 0));
        anhadeCR(new Cantera(new Piedra(20)), m.obtenerCelda(3, 4));

        // Columna 4
        anhadeCR(new Cantera(new Piedra(40)), m.obtenerCelda(4, 6));

        // Columna 5
        anhadeCR(new Bosque(new Madera(80)), m.obtenerCelda(5, 1));
        anhadeCR(new Bosque(new Madera(10)), m.obtenerCelda(5, 2));
        anhadeCR(new Cantera(new Piedra(40)), m.obtenerCelda(5, 5));
        anhadeCR(new Cantera(new Piedra(40)), m.obtenerCelda(5, 6));

        // Columna 6
        anhadeCR(new Bosque(new Madera(20)), m.obtenerCelda(6, 1));
        anhadeCR(new Bosque(new Madera(60)), m.obtenerCelda(6, 2));
        anhadeCR(new Arbusto(new Comida(20)), m.obtenerCelda(6, 8));
        anhadeCR(new Arbusto(new Comida(40)), m.obtenerCelda(6, 9));

        // Columna 7
        anhadeEdificio(new Cuartel(), m.obtenerCelda(7, 4), civ1);
        anhadePersonaje(new Paisano(), m.obtenerCelda(7, 5), civ1);
        anhadeEdificio(new Ciudadela(), m.obtenerCelda(7, 6), civ1);
        anhadeCR(new Bosque(new Madera(80)), m.obtenerCelda(7, 9));

        // Columna 8
        anhadeCR(new Arbusto(new Comida(10)), m.obtenerCelda(8, 0));
        anhadeEdificio(new Casa(), m.obtenerCelda(8, 5), civ1);

        // Columna 9
        anhadeCR(new Arbusto(new Comida(40)), m.obtenerCelda(9, 8));
        anhadeCR(new Cantera(new Piedra(100)), m.obtenerCelda(9, 2));
        anhadeCR(new Bosque(new Madera(90)), m.obtenerCelda(9, 3));
        anhadeCR(new Bosque(new Madera(30)), m.obtenerCelda(9, 4));
        anhadeCR(new Cantera(new Piedra(40)), m.obtenerCelda(9, 7));
        anhadeCR(new Cantera(new Piedra(80)), m.obtenerCelda(9, 8));
        anhadeCR(new Cantera(new Piedra(20)), m.obtenerCelda(9, 9));
    }

    // Método auxiliar para añadir un CR a una celda
    private void anhadeCR(ContRecurso cr, Celda c) throws CeldaOcupadaException {
        c.anhadeCR(cr);
    }

    // Método auxiliar para añadir un personaje a una civilización y a una celda
    private void anhadePersonaje(Personaje p, Celda c, Civilizacion civ) throws CeldaEnemigaException {
        p.inicializaNombre(civ);
        civ.anhadePersonaje(p);
        c.anhadePersonaje(p);
    }

    // Método auxiliar para añadir un edificio a una civilización y a una celda    
    private void anhadeEdificio(Edificio e, Celda c, Civilizacion civ) throws CeldaOcupadaException, CeldaEnemigaException {
        e.inicializaNombre(civ);
        civ.anhadeEdificio(e);
        c.anhadeEdificio(e);
    }
    
    private String listarCivilizaciones() {
        String s = "Civilizaciones:\n";
        Iterator it = this.civilizaciones.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            s += "\t"+e.getKey();
        }
        return s;
    }

    @Override
    public void desligar(String nPersonaje, String nGrupo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void desagrupar(String nGrupo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void defender(String nPersonaje, String direccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atacar(String nPersonaje, String direccion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
