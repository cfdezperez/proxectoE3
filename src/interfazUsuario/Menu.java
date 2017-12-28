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
import excepciones.celda.NoTransitablebleException;
import excepciones.edificio.EdificioException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.PersonajeException;
import excepciones.personaje.SolRepararException;
import excepciones.recursos.RecursosException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class Menu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Consola consola = new ConsolaNormal();
        boolean flag;
        String sn;
        Juego juego = null;

        flag = true;
        while (flag) {
            sn = consola.leer("¿Cargar juego de ficheros? (s/n)").toLowerCase();
            switch (sn.toLowerCase()) {
                case "s":
                    String dir = consola.leer("Directorio donde se encuentran los ficheros ");

                    try {
                        juego = cargarFicheros(dir);
                    } catch (CeldaException ex) {
                        consola.imprimir("Los datos de los ficheros son erróneos: " + ex.getMessage() + ". Salimos.");
                        System.exit(-1);
                    } catch (ParametroIncorrectoException | FileNotFoundException ex) {
                        consola.imprimir("No se ha encontrado alguno de los ficheros personajes.csv, edificios.csv o mapa.csv. " + ex.getMessage() + ". Salimos.");
                        System.exit(-1);
                    }
                    juego.getMapa().imprimir();
                    flag = false;
                    break;
                case "n":
                    String[] nombreCivilizaciones = consola.leer("Nombre de las dos civilizaciones que quieres crear (p.e. Romana, Griega): ").split(",");
                    if (nombreCivilizaciones.length != 2) {
                        consola.imprimir("Los nombres introducidos no son correctos");
                    } else {
                        consola.imprimir("Creamos mapa por defecto (tamaño 10x10)");
                        juego = new Juego(10, 10, nombreCivilizaciones);
                        try {
                            juego.juegoPorDefecto();
                        } catch (CeldaException | ParametroIncorrectoException ex) {
                            consola.imprimir("Error creando el mapa por defecto: " + ex.getMessage() + ". Salimos.");
                            System.exit(-1);
                        }
                        juego.getMapa().imprimir();
                        flag = false;
                    }
                    break;
                default:
                    consola.imprimir("Responde s o n, por favor");
            }
        }

        if (juego == null) { // Algo ha ido mal, así que me voy
            consola.imprimir("Error indeterminado. Salimos.");
            System.exit(-1);
        }
        String[] orden = new String[4];

        while (!"salir".equals(orden[0])) {
            try {
                orden = consola.leer(Juego.getCivilizacionActiva().getNomCivilizacion() + "> ").split(" ");

                switch (orden[0].toLowerCase()) {
                    case "cargar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar el directorio con los ficheros personajes.csv, edificios.csv y mapa.csv.");
                            continue;
                        }
                        try {
                            juego = cargarFicheros(orden[1]);
                        } catch (CeldaException ex) {
                            consola.imprimir("Los datos de los ficheros son erróneos: " + ex.getMessage() + ". Salimos.");
                            System.exit(-1);
                        } catch (ParametroIncorrectoException | FileNotFoundException ex) {
                            consola.imprimir("No se ha encontrado alguno de los ficheros personajes.csv, edificios.csv o mapa.csv. " + ex.getMessage() + ". Salimos.");
                            System.exit(-1);
                        }
                        break;

                    case "listar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar lo que quieres listar.");
                            continue;
                        }
                        consola.imprimir(juego.listar(orden[1]));

                        break;

                    case "describir":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar lo que quieres describir.");
                        } else if (orden.length < 3) {
                            consola.imprimir(juego.describir(orden[1]));
                        } else {
                            consola.imprimir(juego.describir(orden[1], orden[2]));
                        }
                        break;

                    case "mover":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar a quien quieres mover y hacia donde moverlo.");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección hacia donde mover "
                                    + orden[1]);
                            continue;
                        }
                        try {
                            juego.mover(orden[1], orden[2]);
                        } catch (CeldaException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no se puede mover al" + orden[2] + ": " + ex.getMessage());
                        }
                        break;

                    case "mirar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar la celda que quieres mirar, por ejemplo: mirar (1,2).");
                            continue;
                        }
                        try {
                            consola.imprimir(juego.mirar(orden[1]));
                        } catch (NumberFormatException ex) {
                            consola.imprimir("Coordenanas mal indicadas");
                        } catch (FueraDeMapaException ex) {
                            consola.imprimir("Celda incorrecta: " + ex.getMessage());
                        }
                        break;

                    case "construir":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quién quieres que construya, "
                                    + "lo que quieres construir y hacia que lado.");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar lo que quieres que "
                                    + orden[1] + " construya y hacia que lado.");
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
                                    throw new ParametroIncorrectoException("Tipo de edificio desconocido");
                            }
                            consola.imprimir("Debes indicar hacia donde quieres que "
                                    + orden[1] + " construya " + lael + orden[2]);
                            continue;
                        }
                        try {
                            juego.construir(orden[1], orden[2], orden[3].toLowerCase());
                        } catch (PersonajeException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no puede construir: " + ex.getMessage());
                        } catch (CeldaOcupadaException | CeldaEnemigaException | FueraDeMapaException ex) {
                            consola.imprimir("No se puede construir en la dirección indicada: " + ex.getMessage());
                        }
                        break;

                    case "reparar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quién quieres que repare y en qué dirección.");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección para que "
                                    + orden[1] + " repare.");
                            continue;
                        }
                        try {
                            juego.reparar(orden[1], orden[2].toLowerCase());
                        } catch (SolRepararException | FueraDeMapaException | InsuficientesRecException | EdificioException ex) {
                            consola.imprimir("El personaje " + orden[1] + " no puede reparar en la dirección " + orden[2] + ": " + ex.getMessage());
                        }

                        break;

                    case "crear":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar qué edificio es el que va crear y lo que tiene que crear.");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar lo que "
                                    + orden[1] + " tiene que crear.");
                            continue;
                        }
                        juego.crear(orden[1], orden[2]);

                    case "recolectar":

                        try {
                            juego.recolectar(orden[1], orden[2].toLowerCase());
                        } catch (PersonajeException | RecursosException | FueraDeMapaException | CeldaOcupadaException ex) {
                            consola.imprimir("No es posible recolectar: " + ex.getMessage());
                        }

                        break;

                    case "almacenar":

                        try {
                            juego.almacenar(orden[1], orden[2].toLowerCase());
                        } catch (NoTransitablebleException | FueraDeMapaException | CeldaEnemigaException | CeldaOcupadaException ex) {
                            consola.imprimir("No es posible almacenar: " + ex.getMessage());
                        }

                        break;

                    case "cambiar":
                        juego.cambiarCivilizacion(orden[1]);
                        break;

                    case "civilizacion":
                        juego.imprimirCivilizacion();
                        break;

                    case "imprimir":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar la civilización que quieres imprimir.");
                            continue;
                        }
                        juego.imprimirCivilizacion(orden[1]);
                        break;

                    case "agrupar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar la celda donde están los personajes que quieres agrupar, por ejemplo: agrupar (1,2).");
                            continue;
                        }
                        try {
                            juego.agrupar(orden[1]);
                        } catch (NumberFormatException ex) {
                            consola.imprimir("Coordenanas mal indicadas");
                        } catch (FueraDeMapaException ex) {
                            consola.imprimir("Celda incorrecta: " + ex.getMessage());
                        }
                        break;

                    case "desligar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar a quien quieres desligar");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar de que grupo quieres desligar al personaje");
                            continue;
                        }
                        juego.desligar(orden[1], orden[2]);

                        break;

                    case "desagrupar":
                        if (orden.length < 1) {
                            consola.imprimir("Debes indicar un grupo.");
                        }
                        juego.desagrupar(orden[1]);

                        break;

                    case "defender":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quien quieres que entre en el edificio para defenderlo");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección hacia donde mover " + orden[1]);
                            continue;
                        }
                        juego.defender(orden[1], orden[2].toLowerCase());

                        break;

                    case "atacar":
                        if (orden.length < 2) {
                            consola.imprimir("Debes indicar quien quieres que ataque");
                            continue;
                        }
                        if (orden.length < 3) {
                            consola.imprimir("Debes indicar la dirección hacia donde quiere atacar " + orden[1]);
                            continue;
                        }
                        juego.atacar(orden[1], orden[2].toLowerCase());

                        break;
                    case "ayuda":
                        consola.imprimir("Simbolos:");
                        consola.imprimir("\tRecursos: X - Pradera,  B - Bosque,  A - Arbusto, C - Cantera");
                        consola.imprimir("\tPersonajes: P - Paisano,  S - Soldado");
                        consola.imprimir("\tEdificios:  M - Ciudadela, L - Casa, N - Cuartel");
                        consola.imprimir("\tOtros:  G - Grupo de personajes, V - Varios elementos");
                        break;
                    case "salir":
                        consola.imprimir("Adios. Esperamos que haya disfrutado del juego.");
                        break;
                    default:
                        consola.imprimir("Lo siento, no te he entendido");
                        break;
                }

            } catch (ParametroIncorrectoException ex) {
                consola.imprimir("Lo siento, no te he entendido");
            }
        }
    }

    public static Juego cargarFicheros(String dir) throws ParametroIncorrectoException, FueraDeMapaException, CeldaOcupadaException, CeldaEnemigaException, FileNotFoundException {
        // Cargamos personajes
        List<List<String>> personajes = (new Lectura(dir + File.separator + "personajes.csv")).getElementos();
        // Cargamos edificios
        List<List<String>> edificios = (new Lectura(dir + File.separator + "edificios.csv")).getElementos();
        // Cargamos recursos
        List<List<String>> recursos = (new Lectura(dir + File.separator + "mapa.csv")).getElementos();
        if (personajes == null || edificios == null || recursos == null) {

        }
        return new Juego(personajes, edificios, recursos);
    }
}
