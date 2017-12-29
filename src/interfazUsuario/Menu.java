/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaException;
import excepciones.celda.NoAlmacenableException;
import excepciones.celda.NoTransitablebleException;
import excepciones.edificio.EdificioException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.NoAgrupableException;
import excepciones.personaje.PersonajeException;
import excepciones.personaje.SolAlmacenarException;
import excepciones.personaje.SolRepararException;
import excepciones.recursos.RecursosException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 *
 */
public class Menu {

    /**
     * Inicia el menu
     */
    public static void start() {
        // TODO code application logic here
        Consola consola = new ConsolaNormal();
        boolean flag;
        String sn;
        Juego juego = null;

        flag = true;
        while (flag) {
            sn = consola.leer("¿Cargar juego de ficheros? (s/n) ").toLowerCase();
            switch (sn.toLowerCase()) {
                case "s":
                    String dir = consola.leer("Directorio donde se encuentran los ficheros ");

                    try {
                        juego = cargarFicheros(dir);
                    } catch (CeldaException | NoAgrupableException ex) {
                        consola.imprimir("Los datos de los ficheros son erróneos: " + ex.getMessage() + ". Salimos.\n");
                        System.exit(-1);
                    } catch (ParametroIncorrectoException | FileNotFoundException ex) {
                        consola.imprimir("No se ha encontrado alguno de los ficheros personajes.csv, edificios.csv o mapa.csv. " 
                                + ex.getMessage() + ". Salimos.\n");
                        System.exit(-1);
                    }
                    juego.getMapa().imprimir();
                    flag = false;
                    break;
                case "n":
                    String[] nombreCivilizaciones = consola.leer("Nombre de las dos civilizaciones que quieres crear (p.e. Romana, Griega): ").split(",");
                    if (nombreCivilizaciones.length != 2) {
                        consola.imprimir("Los nombres introducidos no son correctos.\n");
                    } else {
                        consola.imprimir("Creamos mapa por defecto (tamaño 10x10).\n");
                        juego = new Juego(10, 10, nombreCivilizaciones);
                        try {
                            juego.juegoPorDefecto();
                        } catch (CeldaException | ParametroIncorrectoException ex) {
                            consola.imprimir("Error creando el mapa por defecto: " + ex.getMessage() + ". Salimos.\n");
                            System.exit(-1);
                        }
                        juego.getMapa().imprimir();
                        flag = false;
                    }
                    break;
                default:
                    consola.imprimir("Responde s o n, por favor.\n");
            }
        }

        if (juego == null) { // Algo ha ido mal, así que me voy
            consola.imprimir("Error indeterminado. Salimos.\n");
            System.exit(-1);
        }
        String[] orden = new String[4];

        while (!"salir".equals(orden[0])) {
            try {
                orden = consola.leer(Juego.getCivilizacionActiva().getNomCivilizacion() + "> ").split(" ");

                switch (orden[0].toLowerCase()) {
                    case "cargar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar el directorio con los ficheros personajes.csv, edificios.csv y mapa.csv.\n");
                            continue;
                        }
                        try {
                            juego = cargarFicheros(orden[1]);
                        } catch (CeldaException | NoAgrupableException ex) {
                            consola.imprimir("Los datos de los ficheros son erróneos: " + ex.getMessage() + ". Salimos.\n");
                            System.exit(-1);
                        } catch (ParametroIncorrectoException | FileNotFoundException ex) {
                            consola.imprimir("No se ha encontrado alguno de los ficheros personajes.csv, edificios.csv o mapa.csv. " 
                                    + ex.getMessage() + ". Salimos.\n");
                            System.exit(-1);
                        }
                        break;

                    case "listar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar lo que quieres listar.\n");
                            continue;
                        }
                        consola.imprimir(juego.listar(orden[1])+"\n");

                        break;

                    case "describir":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar lo que quieres describir.\n");
                        } else if (orden.length < 3) {
                            consola.imprimir(juego.describir(orden[1])+"\n");
                        } else {
                            consola.imprimir(juego.describir(orden[1], orden[2])+"\n");
                        }
                        break;

                    case "mover":
                        int distancia = 1;
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar a quien quieres mover y hacia donde moverlo.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección hacia donde mover "
                                    + orden[1]+"\n");
                            continue;
                        }
                        if (orden.length == 4) { // Se indica una distancia (opcional)
                            try {
                                distancia = Integer.parseInt(orden[3]);
                            } catch(NumberFormatException ex) {
                                throw new ParametroIncorrectoException(ex.getMessage());
                            }
                        }
                        if((distancia != 1) && (distancia != 2)) {
                            throw new ParametroIncorrectoException("Solo puedes indicar una distancia de 1 o 2.\n");
                        }
                        try {
                            consola.imprimir(juego.mover(orden[1], orden[2], distancia)+"\n");
                        } catch (CeldaException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no se puede mover al " + orden[2] + ": " + ex.getMessage()+"\n");
                        } catch (EstarEnGrupoException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no se puede mover: " + ex.getMessage()+".\n");
                        }
                        break;

                    case "mirar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar la celda que quieres mirar, por ejemplo: mirar (1,2).\n");
                            continue;
                        }
                        try {
                            consola.imprimir(juego.mirar(orden[1])+"\n");
                        } catch (NumberFormatException ex) {
                            consola.imprimir("Coordenanas mal indicadas.\n");
                        } catch (FueraDeMapaException ex) {
                            consola.imprimir("Celda incorrecta: " + ex.getMessage()+"\n");
                        }
                        break;

                    case "construir":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quién quieres que construya, "
                                    + "lo que quieres construir y hacia que lado.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar lo que quieres que "
                                    + orden[1] + " construya y hacia que lado.\n");
                            continue;
                        }
                        if (orden.length < 4) {
                            String lael;
                            switch (orden[2].toLowerCase()) {
                                case "casa":
                                case "ciudadela":
                                    lael = "la";
                                    break;
                                case "cuartel":
                                    lael = "el";
                                    break;
                                default:
                                    throw new ParametroIncorrectoException("Tipo de edificio desconocido.");
                            }
                            consola.imprimir("Debes indicar hacia donde quieres que "
                                    + orden[1] + " construya " + lael + orden[2]+"\n");
                            continue;
                        }
                        try {
                            consola.imprimir(juego.construir(orden[1], orden[2], orden[3].toLowerCase())+"\n");
                        } catch (PersonajeException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no puede construir: " + ex.getMessage()+"\n");
                        } catch (CeldaOcupadaException | CeldaEnemigaException | FueraDeMapaException ex) {
                            consola.imprimir("No se puede construir en la dirección indicada: " + ex.getMessage()+"\n");
                        }
                        break;

                    case "reparar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quién quieres que repare y en qué dirección.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección para que "+ orden[1] + " repare.\n");
                            continue;
                        }
                        try {
                            consola.imprimir(juego.reparar(orden[1], orden[2].toLowerCase())+"\n");
                        } catch (SolRepararException | FueraDeMapaException | InsuficientesRecException | EdificioException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no puede reparar en la dirección " + orden[2] + ": " + ex.getMessage()+"\n");
                        } catch (EstarEnGrupoException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no puede reparar: " + ex.getMessage()+"\n");
                        }

                        break;

                    case "crear":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar qué edificio es el que va crear y lo que tiene que crear.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar lo que " + orden[1] + " tiene que crear.\n");
                            continue;
                        }
                        consola.imprimir(juego.crear(orden[1], orden[2])+"\n");

                    case "recolectar":
                        try {
                            consola.imprimir(juego.recolectar(orden[1], orden[2].toLowerCase())+"\n");
                        } catch (PersonajeException | RecursosException | FueraDeMapaException | CeldaOcupadaException ex) {
                            consola.imprimir("No es posible recolectar: " + ex.getMessage()+"\n");
                        }

                        break;

                    case "almacenar":
                        try {
                            consola.imprimir(juego.almacenar(orden[1], orden[2].toLowerCase())+"\n");
                        } catch (EstarEnGrupoException | SolAlmacenarException | NoAlmacenableException | InsuficientesRecException | NoTransitablebleException | FueraDeMapaException | CeldaEnemigaException | CeldaOcupadaException ex) {
                            consola.imprimir("No es posible almacenar: " + ex.getMessage()+"\n");
                        }

                        break;

                    case "cambiar":
                        if(orden.length < 2) {
                            consola.imprimir("Debes indicar el nombre de la civilización a la que quieres cambiar.\n");
                            continue;
                        }
                        juego.cambiarCivilizacion(orden[1]);
                        break;

                    case "civilizacion":
                        juego.imprimirCivilizacion();
                        break;

                    case "imprimir":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar la civilización que quieres imprimir.\n");
                            continue;
                        }
                        juego.imprimirCivilizacion(orden[1]);
                        break;

                    case "agrupar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar la celda donde están los personajes que quieres agrupar, por ejemplo: agrupar (1,2).\n");
                            continue;
                        }
                        try {
                            consola.imprimir(juego.agrupar(orden[1])+"\n");
                        } catch (NumberFormatException ex) {
                            consola.imprimir("Coordenanas mal indicadas.\n");
                        } catch (FueraDeMapaException | CeldaEnemigaException | NoTransitablebleException ex) {
                            consola.imprimir("Celda incorrecta: " + ex.getMessage()+"\n");
                        } catch (NoAgrupableException ex) {
                            consola.imprimir("Imposible agrupar: "+ex.getMessage()+"\n");
                        }
                        break;

                    case "desligar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar a quien quieres desligar.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar de que grupo quieres desligar al personaje.\n");
                            continue;
                        }
                        juego.desligar(orden[1], orden[2]);

                        break;

                    case "desagrupar":
                        if (orden.length < 1) {
                            consola.imprimir("Debes indicar un grupo.\n");
                        }
                        juego.desagrupar(orden[1]);

                        break;

                    case "defender":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quien quieres que entre en el edificio para defenderlo.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección hacia donde defender " + orden[1]+"\n");
                            continue;
                        }
                        juego.defender(orden[1], orden[2].toLowerCase());

                        break;

                    case "atacar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quien quieres que ataque.\n");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección hacia donde quiere atacar " + orden[1]+"\n");
                            continue;
                        }
                        juego.atacar(orden[1], orden[2].toLowerCase());

                        break;
                    case "ayuda":
                        consola.imprimir("Simbolos:\n");
                        consola.imprimir("\tRecursos: X - Pradera,  B - Bosque,  A - Arbusto, C - Cantera.\n");
                        consola.imprimir("\tPersonajes: P - Paisano,  S - Soldado.\n");
                        consola.imprimir("\tEdificios:  M - Ciudadela, L - Casa, N - Cuartel.\n");
                        consola.imprimir("\tOtros:  G - Grupo de personajes, V - Varios elementos.\n");
                        break;
                    case "salir":
                        consola.imprimir("Adios. Esperamos que haya disfrutado del juego.\n");
                        juego.salir();
                        break;
                    default:
                        consola.imprimir("Lo siento, no te he entendido.\n");
                        break;
                }

            } catch (ParametroIncorrectoException ex) {
                consola.imprimir("Error en parámetro: "+ex.getMessage()+"\n");
            }
        }
    }

    public static Juego cargarFicheros(String dir) throws ParametroIncorrectoException, FueraDeMapaException, CeldaOcupadaException, CeldaEnemigaException, FileNotFoundException, NoTransitablebleException, NoAgrupableException {
        // Cargamos personajes
        List<List<String>> personajes = (new Lectura(dir + File.separator + "personajes.csv")).getElementos();
        // Cargamos edificios
        List<List<String>> edificios = (new Lectura(dir + File.separator + "edificios.csv")).getElementos();
        // Cargamos recursos
        List<List<String>> recursos = (new Lectura(dir + File.separator + "mapa.csv")).getElementos();
        if (personajes == null || edificios == null || recursos == null) {
            throw new FileNotFoundException("Alguno de los fichros está vacío");
        }
        return new Juego(personajes, edificios, recursos);
    }
}
