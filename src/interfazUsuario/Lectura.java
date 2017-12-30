/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author celia y maria
 */
public class Lectura {

    /**
     * Clase para leer ficheros CSV
     *
     * @param file Ficheiro de entrada. Exemplo: c:\\temp\\personajes.csv
     */
    private List<List<String>> elementos;

    public Lectura(String file) throws FileNotFoundException {
        Scanner leer = new Scanner(new File(file.trim()));
        String linea;
        elementos = new ArrayList<List<String>>();
        // Leemos las lineas
        while (leer.hasNext()) {
            linea = leer.nextLine();
            if (!linea.startsWith("#")) { // Me salto los comentarios
                String[] campos = linea.split(";");
                List<String> elemento = new ArrayList<String>();
                for (String s : campos) {
                    elemento.add(s);
                }
                elementos.add(elemento);
            }
        }
    }

    public List<List<String>> getElementos() {
        return elementos;
    }
}
