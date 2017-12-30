/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import elementos.personaje.Grupo;
import excepciones.ParametroIncorrectoException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase para las civilizaciones que participan en el juego
 *
 * @author celia
 */
public class Civilizacion {

    private Map<String, Personaje> Personajes; //personajes que pertenecen a la civilización
    private Map<String, Edificio> Edificios; //edificios que pertenecen a la civilización
    private Map<String, Grupo> Grupos; //grupos que pertenecen a la civilizacion
    private String nombreCivilizacion; //Nombre da la civilización
    private int idCivilizacion; //Identifica con un número que civilización es 0 primera, 1 segunda, 2...
    private static int numeroDeCivilizaciones = 0;

    /**
     * Crea una civilización con un nombre y un identificador determinados
     *
     * @param nCiv nombre de la civilización
     * @param idCiv identifica con un número que civilización es
     */
    public Civilizacion(String nCiv, int idCiv) {
        this.Personajes = new HashMap<String, Personaje>();
        this.Edificios = new HashMap<String, Edificio>();
        this.Grupos = new HashMap<String, Grupo>();
        this.nombreCivilizacion = nCiv;
        this.idCivilizacion = idCiv;
        Civilizacion.numeroDeCivilizaciones++;
    }

    /**
     * Obtiene los personajes pertenecientes a la civilizacion
     *
     * @return el HashMap de los personajes pertenecientes a la civilizacion
     */
    public Map<String, Personaje> getMapaPersonajes() {
        return this.Personajes;
    }

    /**
     * Obtiene los edificios pertenecientes a la civilización
     *
     * @return el HashMap de los edificios pertenecientes a la civilizacion
     */
    public Map<String, Edificio> getMapaEdificios() {
        return this.Edificios;
    }

    /**
     * Obtiene los grupos pertenecientes a la civilización
     *
     * @return el HashMap de los grupos pertenecientes a la civilizacion
     */
    public Map<String, Grupo> getMapaGrupos() {
        return this.Grupos;
    }

    /**
     * Obtiene el nombre de la civilizacion
     *
     * @return el nombre de la civilizacion
     */
    public String getNomCivilizacion() {
        return this.nombreCivilizacion;
    }

    /**
     * Obtiene el identificador de la civilización
     *
     * @return el identificador de la civilización
     */
    public int getIdCivilizacion() {
        return this.idCivilizacion;
    }

    /**
     * Obtiene el número total de civilizaciones definidas en el juego
     *
     * @return número de civilizaciones
     */
    public static int getNumDeCivilizaciones() {
        return numeroDeCivilizaciones;
    }

    /**
     * Pone el número de civilizaciones a 0
     */
    public static void resetNumDeCivilizaciones() {
        Civilizacion.numeroDeCivilizaciones = 0;
    }

    /**
     * Devuelve el numero de personajes
     */
    public int numeroPersonajes() {
        return this.Personajes.size();

    }

    /**
     * Devuelve un personaje (individual o grupo) de la civilización dado su
     * nombre
     *
     * @param nombre Nombre del personaje
     * @return El personaje
     */
    public Personaje getPersonaje(String nombre) throws ParametroIncorrectoException {
        if (Personajes.containsKey(nombre)) {
            return Personajes.get(nombre);
        } else if (Grupos.containsKey(nombre)) {
            return Grupos.get(nombre);
        } else {
            throw new ParametroIncorrectoException("El personaje " + nombre
                    + " no existe en la civilización " + this.getNomCivilizacion());
        }
    }

    /**
     * Añade un personaje a la civilizacion
     *
     * @param p Personaje a añadir
     */
    public void anhadePersonaje(Personaje p) {
        p.setCivilizacion(this);
        this.Personajes.put(p.getNombre(), p);
    }

    /**
     * Añade un edificio a la civilizacion
     *
     * @param e Edificio a añadir
     */
    public void anhadeEdificio(Edificio e) {
        e.setCivilizacion(this);
        this.Edificios.put(e.getNombre(), e);
    }

    /**
     * Añade un grupo a la civilizacion
     *
     * @param g Grupo a añadir
     */
    public void anhadeGrupo(Grupo g) {
        g.setCivilizacion(this);
        this.Grupos.put(g.getNombre(), g);
    }

    /**
     * Elimina un personaje de la civilizacion
     *
     * @param p El personaje a eliminar
     * @throws excepciones.ParametroIncorrectoException
     */
    public void eliminaPersonaje(Personaje p) throws ParametroIncorrectoException {
        String nombre = p.getNombre();
        if (Personajes.containsKey(nombre)) {
            Personajes.remove(nombre);
        } else if (Grupos.containsKey(nombre)) {
            Grupos.remove(nombre);
        } else {
            throw new ParametroIncorrectoException("El personaje " + nombre
                    + " no existe en la civilización " + this.getNomCivilizacion());
        }
    }

    /**
     * Elimina un edificio de la civilizacion
     *
     * @param e El edificio a eliminar
     *
     * @throws excepciones.ParametroIncorrectoException
     */
    public void eliminaEdificio(Edificio e) throws ParametroIncorrectoException {
        String nombre = e.getNombre();
        if (Edificios.containsKey(nombre)) {
            Edificios.remove(nombre);
        } else {
            throw new ParametroIncorrectoException("El edificio " + nombre
                    + " no existe en la civilización " + this.getNomCivilizacion());
        }
    }

    @Override
    public String toString() {
        return (this.getNomCivilizacion());
    }

    public String listarPersonajes() {
        String s = "Personajes de la civilizacion " + this.getNomCivilizacion() + ":\n";
        Iterator it = this.getMapaPersonajes().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            s += "\t" + e.getKey() + " " + ((Personaje) e.getValue()).getCelda();
        }
        return s;
    }

    public String listarEdificios() {
        String s = "Edificios de la civilizacion " + this.getNomCivilizacion() + ":\n";
        Iterator it = this.getMapaEdificios().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            s += "\t" + e.getKey() + " " + ((Edificio) e.getValue()).getCelda();
        }

        return s;
    }

    public String listarGrupos() {
        String s = "Grupos de la civilizacion " + this.getNomCivilizacion() + ":\n";
        Iterator it = this.getMapaGrupos().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            s += "\t" + e.getKey() + " " + ((Grupo) e.getValue()).getCelda();
        }
        return s;
    }
}
