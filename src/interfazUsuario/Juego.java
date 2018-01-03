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
import elementos.personaje.Grupo;
import elementos.recursos.Piedra;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaException;
import excepciones.celda.NoAlmacenableException;
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.AtaqueExcepcion;
import excepciones.personaje.CapMovimientoException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.NoAgrupableException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolAlmacenarException;
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
 * @author celia y maria
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

    private final Map<String, Civilizacion> civilizaciones;
    private Map<String, ContRecurso> contRecursos;
    private static Civilizacion civilizacionActiva;
    private final Mapa mapa;
    private int[] contador; // Para los contenedores de recursos
    private int[][] contadorElementos; //Primer campo es la civilizacion, segundo campo es el tipo de dato
    private final int tamX;
    private final int tamY;

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
        this.tamX = tamX;
        this.tamY = tamY;
        
        //mapa = new Mapa(tamX, tamY);
        mapa = new Mapa(nombreCivilizaciones[0], nombreCivilizaciones[1], tamX, tamY);
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
     * @throws excepciones.celda.NoTransitablebleException
     */
    public Juego(List<List<String>> personajes, List<List<String>> edificios, List<List<String>> recursos) throws FueraDeMapaException, ParametroIncorrectoException, CeldaOcupadaException, CeldaEnemigaException, NoTransitablebleException, NoAgrupableException {
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

        this.tamX = maxX+1;
        this.tamY = maxY+1;

        // Creo las civilizaciones
        creaCivilizaciones(personajes, edificios);
        
        // Si el número de civilizaciones no es 2, uso la consola
        if(civilizaciones.size() != 2) {
        // Creo el mapa con todo praderas
            mapa = new Mapa(this.tamX, this.tamY);
        } else {
            List<String> nCivs = new ArrayList<String>(civilizaciones.keySet());
            mapa = new Mapa(nCivs.get(0), nCivs.get(1), this.tamX, this.tamY);
        }
        
        
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
    public String mover(String nombre, String direccion) throws EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException, CapMovimientoException {
        Personaje p = Juego.civilizacionActiva.getPersonaje(nombre);
        // Si el personaje puede moverse más una casilla, preguntamos
        if(p.capacidadMovimiento() > 1) {
            throw new CapMovimientoException(String.valueOf(p.capacidadMovimiento()));
        }
        // Si la capacidad de movimiento es 1
        return mover(nombre, direccion, 1);
    }
    @Override
    public String mover(String nombre, String direccion, int distancia) throws EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException {
        Personaje p = Juego.civilizacionActiva.getPersonaje(nombre);
        String s;

        if(p instanceof Caballero) {
            s = p.mover(mapa, direccion, distancia);
        } else if(distancia != 1) {
            throw new ParametroIncorrectoException("Solo los caballeros pueden avanzar más de una celda.");
        } else {
            s = p.mover(mapa, direccion);
        }
        getMapa().imprimir();
        return s;
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
        return describir(nombre, getCivilizacionActiva().getNomCivilizacion());
    }

    @Override
    public String describir(String nombre, String civilizacion) throws ParametroIncorrectoException {
        Civilizacion civ = this.getCivilizacion(civilizacion);
        if (civ.getMapaPersonajes().containsKey(nombre)) {
            return (civ.getMapaPersonajes().get(nombre).toString());
        } else if (civ.getMapaEdificios().containsKey(nombre)) {
            return (civ.getMapaEdificios().get(nombre).toString());
        } else if (civ.getMapaGrupos().containsKey(nombre)) {
            return (civ.getMapaGrupos().get(nombre).toString());
        } else {
            throw new ParametroIncorrectoException(nombre + " no corresponde a un personaje ni a un edificio.");
        }
    }

    @Override
    public String mirar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException {
        String[] fc = coordenadasCelda.split("");
        int f = Integer.parseInt(fc[1]);
        int c = Integer.parseInt(fc[3]);
        
        return ((mapa.obtenerCelda(c, f)).mirar());
    }

    @Override
    public String construir(String Personaje, String nEdificio, String direccion) throws InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, SolConstruirException, EstarEnGrupoException {
        Personaje p = civilizacionActiva.getPersonaje(Personaje);
        String s = p.construirEdificio(nEdificio, direccion);
                getMapa().imprimir();

        return s;
    }

    @Override
    public String  recolectar(String nPersonaje, String direccion) throws
            PersonajeLlenoException, SoldadoRecException, RecursosException,
            FueraDeMapaException, ParametroIncorrectoException, CeldaOcupadaException, EstarEnGrupoException {
        String s = getCivilizacionActiva().getPersonaje(nPersonaje).recolectar(direccion);
        getMapa().imprimir();
        return s;
    }
  
    @Override
    public void cambiarCivilizacion(String nCivilizacion) throws ParametroIncorrectoException {
        Juego.civilizacionActiva = this.getCivilizacion(nCivilizacion);
        getMapa().imprimir();
    }

    @Override
    public void imprimirCivilizacion() throws ParametroIncorrectoException {
        this.getMapa().imprimirVisitadasCivilizacion(getCivilizacionActiva());
    }

    @Override
    public void imprimirCivilizacion(String nCivilizacion) throws ParametroIncorrectoException {
        this.getMapa().imprimirVisitadasCivilizacion(getCivilizacion(nCivilizacion));
    }
    
    @Override
    public String agrupar(String coordenadasCelda) throws NumberFormatException, FueraDeMapaException, 
            ParametroIncorrectoException, NoAgrupableException, CeldaEnemigaException, NoTransitablebleException {
        String[] fc = coordenadasCelda.split("");
        int f = Integer.parseInt(fc[1]);
        int c = Integer.parseInt(fc[3]);
        String s = this.getMapa().obtenerCelda(c, f).agrupar();
        getMapa().imprimir();
        return s;
    }
    
    @Override
    public String desagrupar(String nGrupo) throws ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
        String s;
        Personaje p = getCivilizacionActiva().getPersonaje(nGrupo);
        if(p instanceof Grupo) {
            s = ((Grupo) p).desagrupar();
        } else {
            throw new ParametroIncorrectoException(nGrupo+" no es el nombre de un grupo");
        }
        this.getMapa().imprimir();
        return s;        
    }

    @Override
    public String desligar(String nPersonaje) throws EstarEnGrupoException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
        String s;
        Personaje p = getCivilizacionActiva().getPersonaje(nPersonaje);
        s = p.getGrupo().desligar(p);
        this.getMapa().imprimir();
        return s;
    }
    
    @Override
    public String crear(String nEdificio, String tipoPersonaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //getMapa().imprimir();
    }

    @Override
    public String almacenar(String nPersonaje, String direccion) throws InsuficientesRecException, NoAlmacenableException, SolAlmacenarException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException, EstarEnGrupoException {
        return getCivilizacionActiva().getPersonaje(nPersonaje).almacenar(direccion);
    }
    
    @Override
    public String reparar(String nPersonaje, String direccion) throws SolRepararException, FueraDeMapaException, 
            ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException, EstarEnGrupoException {
        String s = getCivilizacionActiva().getPersonaje(nPersonaje).reparar(direccion);
        getMapa().imprimir();
        return s;
    }

    @Override
    public String defender(String nPersonaje, String direccion) throws FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, CeldaOcupadaException, EstarEnGrupoException, EdificioException, CeldaException {
        String s = getCivilizacionActiva().getPersonaje(nPersonaje).defender(direccion);
        getMapa().imprimir();
        return s;
    }

    @Override
    public String atacar(String nPersonaje, String direccion) throws FueraDeMapaException, ParametroIncorrectoException, 
            NoTransitablebleException, CeldaEnemigaException, AtaqueExcepcion, EstarEnGrupoException {
        String s = getCivilizacionActiva().getPersonaje(nPersonaje).atacar(direccion);
        getMapa().imprimir();
        return s;
    }
    
    @Override
    public void salir() {
        getMapa().salir();
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
            if (x < 0 || y < 0 || x >= tamX || y >= tamY) {
                throw new FueraDeMapaException("La posición (" + x + ", " + y + ") del edificio " + edificio.get(2) + " se sale del mapa");
            } else {
                String nombreCivilizacion = edificio.get(4);
                // Creamos la civilizacion
                Civilizacion civ;
                if (!civilizaciones.containsKey(nombreCivilizacion)) {  // Si la civilizacion no existe, la creamos
                    civ = new Civilizacion(nombreCivilizacion, idCiv);
                    civilizaciones.put(nombreCivilizacion, civ);
                    if (idCiv == 0) {
                        Juego.civilizacionActiva = civ;
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
            if (x < 0 || y < 0 || x >= tamX || y >= tamY) {
                throw new FueraDeMapaException("La posición (" + x + ", " + y + ") del personaje " + personaje.get(2) + " se sale del mapa");
            } else {
                String nombreCivilizacion = personaje.get(9);
                // Creamos la civilizacion si no existe
                Civilizacion civ;
                if (!civilizaciones.containsKey(nombreCivilizacion)) {  // Si la civilizacion no existe, la creamos
                    civ = new Civilizacion(nombreCivilizacion, idCiv);
                    civilizaciones.put(nombreCivilizacion, civ);
                    if (idCiv == 0) {
                        Juego.civilizacionActiva = civ;
                    }
                    idCiv++;
                }
            }
        }
    }

    private void introduceCR(List<List<String>> recursos) throws ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException {
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

    private void introducePersonajes(List<List<String>> personajes) throws ParametroIncorrectoException, CeldaEnemigaException, FueraDeMapaException, NoTransitablebleException {
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
    

    private void creaGruposDeFichero(List<List<String>> personajes) throws FueraDeMapaException, ParametroIncorrectoException, NoAgrupableException, CeldaEnemigaException, NoTransitablebleException {
        // Recorremos de nuevo en busca de grupos
        int fAnterior = -1;
        int cAnterior = -1;
        for (List<String> personaje : personajes) {
            int f = new Integer(personaje.get(0).split(",")[0]);
            int c = new Integer(personaje.get(0).split(",")[1]);
            if (c < 0 || f < 0 || c >= mapa.getTamX() || f >= mapa.getTamY()) {
                throw new FueraDeMapaException("El personaje " + personaje.get(2) + " se sale del mapa");
            } else {
                String nombreGrupo = personaje.get(8);
                Civilizacion civilizacion = getCivilizacion(personaje.get(9));
                if (c != cAnterior || f != fAnterior) { // Empezamos una nueva celda
                    // Agrupamos la celda
                    Celda celda = mapa.obtenerCelda(c, f);
                    if(celda.getPersonajes().size() > 1) {
                        celda.agrupar(nombreGrupo, civilizacion);
                    }
                    cAnterior = c;
                    fAnterior = f;
                }
            }
        }
    }


    
    public void juegoPorDefecto() throws CeldaOcupadaException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
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
        anhadePersonaje(new Caballero(), m.obtenerCelda(3, 8), civ1);

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
    private void anhadePersonaje(Personaje p, Celda c, Civilizacion civ) throws CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException {
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



}
