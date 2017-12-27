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
import excepciones.CeldaEnemigaException;
import excepciones.CeldaOcupadaException;
import excepciones.FueraDeMapaException;
import excepciones.ParametroIncorrectoException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import vista.Mapa;

/**
 *
 *
 */
public class Menu {

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws ParametroIncorrectoException, FueraDeMapaException {
//        // TODO code application logic here
//        Consola consola = new ConsolaNormal();
//        boolean flag;
//        String sn = "n";
//        Juego juego = null;
//
//        flag = true;
//        while (flag) {
//            sn = consola.leer("¿Cargar juego de ficheros? (s/n)").toLowerCase();
//            switch (sn.toLowerCase()) {
//                case "s":
//                    String dir = consola.leer("Directorio donde se encuentran los ficheros ");
//                    juego = cargarFicheros(dir);
//                    juego.getMapa().imprimir();
//                    flag = false;
//                    break;
//                case "n":
//                    String[] nombreCivilizaciones = consola.leer("Nombre de las dos civilizaciones que quieres crear (p.e. Romana, Griega): ").split(",");
//                    if (nombreCivilizaciones.length != 2) {
//                        consola.imprimir("Los nombres introducidos no son correctos");
//                    } else {
//                        consola.imprimir("Creamos mapa por defecto (tamaño 10x10)");
//                        juego = new Juego(10, 10, nombreCivilizaciones);
//                        juego.getMapa().imprimir();
//                        flag = false;
//                    }
//                    break;
//                default:
//                    consola.imprimir("Responde s o n, por favor");
//            }
//        }
//
//        String[] orden = new String[4];
//
//        while (!"salir".equals(orden[0])) {
//            orden = consola.leer(Juego.getCivilizacionActiva().getNomCivilizacion() + "> ").split(" ");
//
//
//            switch (orden[0].toLowerCase()) {
//                case "cargar":
//                    cargarFicheros(orden[1]);
//
//                    juego.getMapa().creaGruposdeFichero(personajes);
//                    juego.getMapa().imprimir();
//                    break;
//
//                case "listar":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar lo que quieres listar.");
//                        continue;
//                    }
//                    if (orden[1].equals("personajes")) {
//                        mapa.listarPersonajes();
//                    } else if (orden[1].equals("edificios")) {
//                        mapa.listarEdificios();
//                    } else if (orden[1].equals("civilizaciones")) {
//                        mapa.listarCivilizaciones();
//                    } else {
//                        System.out.println("No es posible listar ese elemento.");
//                    }
//                    break;
//
//                case "describir":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar lo que quieres describir.");
//                        continue;
//                    } else if (orden.length < 3) {
//                        mapa.describir(orden[1]);
//                    } else {
//                        mapa.describir(orden[1], orden[2]);
//                    }
//                    break;
//
//                case "mover":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar a quien quieres mover y hacia donde moverlo.");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar la dirección hacia donde mover "
//                                + orden[1]);
//                        continue;
//                    }
//                    String[] nombre;
//                    nombre = orden[1].split("-");
//                    if ((nombre[0].toLowerCase()).equals("paisano")) {
//                        Personaje p = obtenerPersonaje(mapa, orden[1]);
//                        if (p != null) {
//                            //p.mover(mapa, orden[1].toLowerCase(), orden[2].toLowerCase());
//                            p.mover(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//                    } else if ((nombre[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo = obtenerGrupo(mapa, orden[1]);
//                        if (grupo != null) {
//                            grupo.mover(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    } else {
//                        System.out.println("No existe el personaje/grupo " + orden[1] + ".");
//                    }
//                    break;
//
//                case "mirar":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar la celda que quieres mirar, por ejemplo: mirar (1,2).");
//                        continue;
//                    }
//                    String[] orden1 = orden[1].split("");
//
//                    int x = Integer.parseInt(orden1[1]);
//                    int y = Integer.parseInt(orden1[3]);
//                    (mapa.obtenerCeldaPos(x, y)).mirar(mapa);
//                    break;
//
//                case "construir":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar quién quieres que construya, "
//                                + "lo que quieres construir y hacia que lado.");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar lo que quieres que "
//                                + orden[1] + " construya y hacia que lado.");
//                        continue;
//                    }
//                    if (orden.length < 4) {
//                        String lael;
//                        switch (orden[2].toLowerCase()) {
//                            case "casa":
//                            case "ciudadela":
//                                lael = "la";
//                                break;
//                            case "cuartel":
//                                lael = "el";
//                                break;
//                            default:
//                                System.out.println("Tipo de edificio desconocido");
//                                continue;
//                        }
//                        System.out.println("Debes indicar hacia donde quieres que "
//                                + orden[1] + " construya " + lael + orden[2]);
//                        continue;
//                    }
//
//                    String[] nombre4;
//                    nombre4 = orden[1].split("-");
//                    if ((nombre4[0].toLowerCase()).equals("paisano")) {
//                        Personaje personaje5 = obtenerPersonaje(mapa, orden[1]);
//                        if (personaje5 != null) {
//                            int tipo;
//                            switch (orden[2].toLowerCase()) {
//                                case "casa":
//                                    tipo = Mapa.TCASA;
//                                    break;
//                                case "cuartel":
//                                    tipo = Mapa.TCUARTEL;
//                                    break;
//                                case "ciudadela":
//                                    tipo = Mapa.TCIUDADELA;
//                                    break;
//                                default:
//                                    tipo = -1;
//                            }
//                            if (tipo >= 0) {
//                                personaje5.construirEdificio(mapa, orden[2], tipo, orden[3].toLowerCase());
//                                mapa.imprimir();
//                            } else {
//                                System.out.println("Tipo de edificio desconocido");
//                            }
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//
//                    } else if ((nombre4[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo3 = obtenerGrupo(mapa, orden[1]);
//                        if (grupo3 != null) {
//                            int tipo;
//                            switch (orden[2].toLowerCase()) {
//                                case "casa":
//                                    tipo = Mapa.TCASA;
//                                    break;
//                                case "cuartel":
//                                    tipo = Mapa.TCUARTEL;
//                                    break;
//                                case "ciudadela":
//                                    tipo = Mapa.TCIUDADELA;
//                                    break;
//                                default:
//                                    tipo = -1;
//                            }
//                            if (tipo >= 0) {
//                                grupo3.construirEdificio(mapa, orden[2], tipo, orden[3].toLowerCase());
//                                mapa.imprimir();
//                            } else {
//                                System.out.println("Tipo de edificio desconocido");
//                            }
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    }
//
//                    break;
//
//                case "reparar":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar quién quieres que repare y en qué dirección.");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar la dirección para que "
//                                + orden[1] + " repare.");
//                        continue;
//                    }
//
//                    String[] nombre2;
//                    nombre2 = orden[1].split("-");
//                    if ((nombre2[0].toLowerCase()).equals("paisano")) {
//                        Personaje personaje8 = obtenerPersonaje(mapa, orden[1]);
//                        if (personaje8 != null) {
//                            personaje8.reparar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//                    } else if ((nombre2[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo8 = obtenerGrupo(mapa, orden[1]);
//                        if (grupo8 != null) {
//                            grupo8.reparar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    }
//                    break;
//
//                case "crear":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar qué edificio es el que va crear y lo que tiene que crear.");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar lo que "
//                                + orden[1] + " tiene que crear.");
//                        continue;
//                    }
//                    Edificio edificio7 = obtenerEdificio(mapa, orden[1]);
//                    if (edificio7 != null) {
//                        edificio7.crear(mapa);
//                        mapa.imprimir();
//                    } else {
//                        System.out.println("No existe el edificio " + orden[1] + ".");
//                    }
//                    break;
//
//                case "recolectar":
//                    String[] nombre8;
//                    nombre8 = orden[1].split("-");
//                    if ((nombre8[0].toLowerCase()).equals("paisano")) {
//                        Personaje personaje8 = obtenerPersonaje(mapa, orden[1]);
//                        if (personaje8 != null) {
//                            personaje8.recolectar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//                    } else if ((nombre8[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo8 = obtenerGrupo(mapa, orden[1]);
//                        if (grupo8 != null) {
//                            grupo8.recolectar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    }
//
//                    break;
//
//                case "almacenar":
//                    String[] nombre9;
//                    nombre9 = orden[1].split("-");
//                    if ((nombre9[0].toLowerCase()).equals("paisano")) {
//                        Personaje personaje8 = obtenerPersonaje(mapa, orden[1]);
//                        if (personaje8 != null) {
//                            personaje8.almacenar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//                    } else if ((nombre9[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo8 = obtenerGrupo(mapa, orden[1]);
//                        if (grupo8 != null) {
//                            grupo8.almacenar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    }
//                    break;
//
//                case "cambiar":
//                    Civilizacion civilizacion10 = obtenerCivilizacion(mapa, orden[1]);
//                    if (civilizacion10 != null) {
//                        mapa.cambiarCiv(orden[1]);
//                        mapa.imprimir();
//                    } else {
//                        System.out.println("No existe la civilización " + orden[1] + ".");
//                    }
//                    break;
//                case "civilizacion":
//                    mapa.imprimirCivActiva();
//                    mapa.imprimir();
//                    break;
//                case "imprimir":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar la civilización que quieres imprimir.");
//                        continue;
//                    }
//                    Civilizacion civilizacion12 = obtenerCivilizacion(mapa, orden[1]);
//                    mapa.imprimirActiva(civilizacion12);
//                    break;
//
//                case "agrupar":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar la celda donde están los personajes que quieres agrupar, por ejemplo: mirar (1,2).");
//                        continue;
//                    }
//
//                    String[] orden2 = orden[1].split("");
//
//                    int x1 = Integer.parseInt(orden2[1]);
//                    int y1 = Integer.parseInt(orden2[3]);
//                    (mapa.obtenerCeldaPos(x1, y1)).agrupar(mapa);
//                    mapa.imprimir();
//                    break;
//
//                case "desligar":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar a quien quieres desligar");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar de que grupo quieres desligar al personaje");
//                        continue;
//                    }
//                    Personaje personaje10 = obtenerPersonaje(mapa, orden[1]);
//                    if (personaje10 != null) {
//                        Grupo grupo1 = obtenerGrupo(mapa, orden[2]);
//                        if (grupo1 != null) {
//                            grupo1.desligar(personaje10, mapa);
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[2] + ".");
//                        }
//                    } else {
//                        System.out.println("No existe el personaje " + orden[1] + ".");
//                    }
//
//                    break;
//
//                case "desagrupar":
//                    if (orden.length < 1) {
//                        System.out.println("Debes indicar un grupo.");
//                    }
//                    Grupo grupo2 = obtenerGrupo(mapa, orden[1]);
//                    if (grupo2 != null) {
//                        grupo2.desagrupar(mapa);
//                        mapa.imprimir();
//                    } else {
//                        System.out.println("No existe el grupo " + orden[1] + ".");
//                    }
//                    break;
//
//                case "defender":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar quien quieres que entre en el edificio para defenderlo");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar la dirección hacia donde mover " + orden[1]);
//                        continue;
//                    }
//
//                    String[] nombre10;
//                    nombre10 = orden[1].split("-");
//                    if ((nombre10[0].toLowerCase()).equals("paisano")) {
//                        Personaje personaje11 = obtenerPersonaje(mapa, orden[1]);
//                        if (personaje11 != null) {
//                            personaje11.defender(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//                    } else if ((nombre10[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo5 = obtenerGrupo(mapa, orden[1]);
//                        if (grupo5 != null) {
//                            grupo5.defender(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    }
//
//                    break;
//
//                case "atacar":
//                    if (orden.length < 2) {
//                        System.out.println("Debes indicar quien quieres que ataque");
//                        continue;
//                    }
//                    if (orden.length < 3) {
//                        System.out.println("Debes indicar la dirección hacia donde quiere atacar " + orden[1]);
//                        continue;
//                    }
//
//                    String[] nombre11;
//                    nombre11 = orden[1].split("-");
//                    if ((nombre11[0].toLowerCase()).equals("paisano")) {
//                        Personaje personaje12 = obtenerPersonaje(mapa, orden[1]);
//                        if (personaje12 != null) {
//                            personaje12.atacar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el personaje " + orden[1] + ".");
//                        }
//                    } else if ((nombre11[0].toLowerCase()).equals("grupo")) {
//                        Grupo grupo6 = obtenerGrupo(mapa, orden[1]);
//                        if (grupo6 != null) {
//                            grupo6.atacar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el grupo " + orden[1] + ".");
//                        }
//                    } else if ((nombre11[0].toLowerCase()).equals("ciudadela")) {
//                        Edificio e2 = obtenerEdificio(mapa, orden[1]);
//                        if (e2 != null) {
//                            e2.atacar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el edificio " + orden[1] + ".");
//                        }
//                    } else if ((nombre11[0].toLowerCase()).equals("cuartel")) {
//                        Edificio e2 = obtenerEdificio(mapa, orden[1]);
//                        if (e2 != null) {
//                            e2.atacar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el edificio " + orden[1] + ".");
//                        }
//                    } else if ((nombre11[0].toLowerCase()).equals("casa")) {
//                        Edificio e2 = obtenerEdificio(mapa, orden[1]);
//                        if (e2 != null) {
//                            e2.atacar(mapa, orden[2].toLowerCase());
//                            mapa.imprimir();
//                        } else {
//                            System.out.println("No existe el edificio " + orden[1] + ".");
//                        }
//                    }
//                    break;
//                case "ayuda":
//                    System.out.println("Simbolos:");
//                    System.out.println("\tRecursos: X - Pradera,  B - Bosque,  A - Arbusto, C - Cantera");
//                    System.out.println("\tPersonajes: P - Paisano,  S - Soldado");
//                    System.out.println("\tEdificios:  M - Ciudadela, L - Casa, N - Cuartel");
//                    System.out.println("\tOtros:  G - Grupo de personajes, V - Varios elementos");
//                    break;
//                case "salir":
//                    System.out.println("Adios. Esperamos que haya disfrutado del juego.");
//                    break;
//                default:
//                    System.out.println("Lo siento, no te he entendido");
//                    break;
//            }
//
//        }
//    }
//

    /*    private static Personaje obtenerPersonaje(Mapa mapa, String nombre) {
        if (nombre.length() > 2) { //Me aseguro que la primera letra del nombre8 sea mayúscula 
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }
        if (mapa.getCivActiva().getPerCivilizacion().containsKey(nombre)) {
            Personaje personaje = mapa.getCivActiva().getPerCivilizacion().get(nombre);
            return personaje;
        } else {
            return null;
        }
    }

    private static Edificio obtenerEdificio(Mapa mapa, String nombre) {
        if (nombre.length() > 2) { //Me aseguro que la primera letra del nombre8 sea mayúscula 
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }
        if (mapa.getCivActiva().getEdCivilizacion().containsKey(nombre)) {
            Edificio edificio = mapa.getCivActiva().getEdCivilizacion().get(nombre);
            return edificio;
        } else {
            return null;
        }
    }

    private static Civilizacion obtenerCivilizacion(Mapa mapa, String nombre) {
        if (nombre.length() > 2) { //Me aseguro que la primera letra del nombre8 sea mayúscula 
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }
        if (mapa.getCivilizacion().containsKey(nombre)) {
            Civilizacion civilizacion = mapa.getCivilizacion().get(nombre);
            return civilizacion;
        } else {
            return null;
        }
    }

    private static Grupo obtenerGrupo(Mapa mapa, String nombre) {
        if (nombre.length() > 2) { //Me aseguro que la primera letra del nombre8 sea mayúscula 
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        }
        if (mapa.getCivActiva().getGrupoCivilizacion().containsKey(nombre)) {
            Grupo grupo = mapa.getCivActiva().getGrupoCivilizacion().get(nombre);
            return grupo;
        } else {
            return null;
        }
    }
     */
    
    public static Juego cargarFicheros(String dir) throws ParametroIncorrectoException, FueraDeMapaException, CeldaOcupadaException, CeldaEnemigaException {
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
