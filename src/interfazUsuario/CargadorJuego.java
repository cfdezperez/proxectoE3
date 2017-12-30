/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.personaje.NoAgrupableException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 *
 * @author celia y maria
 */
public class CargadorJuego {

    private Consola consola;

    public CargadorJuego(Consola consola) {
        this.consola = consola;
    }

    public Juego juegoDeFichero() throws ParametroIncorrectoException, FueraDeMapaException, CeldaOcupadaException, CeldaEnemigaException, FileNotFoundException, NoTransitablebleException, NoAgrupableException {
        String dir = consola.leer("Directorio donde se encuentran los ficheros ");
        Juego juego = juegoDeFichero(dir);
        juego.getMapa().imprimir();
        return juego;
    }

    public Juego juegoDeFichero(String dir) throws ParametroIncorrectoException, FueraDeMapaException, CeldaOcupadaException, CeldaEnemigaException, FileNotFoundException, NoTransitablebleException, NoAgrupableException {
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

    public Juego juegoPorDefecto() throws CeldaException, ParametroIncorrectoException {
        Juego juego = null;
        String[] nombreCivilizaciones;

        do {
            nombreCivilizaciones = consola.leer("Nombre de las dos civilizaciones que quieres crear (p.e. Romana, Griega): ").split(",");
            if (nombreCivilizaciones.length != 2) {
                consola.imprimir("Los nombres introducidos no son correctos.\n");
            }
        } while (nombreCivilizaciones.length != 2);

        consola.imprimir("Creamos mapa por defecto (tamaño 10x10).\n");
        juego = new Juego(10, 10, nombreCivilizaciones);
        juego.juegoPorDefecto();
        juego.getMapa().imprimir();

        return juego;
    }

}
