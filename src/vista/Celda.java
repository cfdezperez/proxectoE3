/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import elementos.Personaje;
import java.util.ArrayList;
import java.util.List;

import elementos.Civilizacion;
import elementos.ContRecurso;
import elementos.Edificio;
import elementos.cr.Pradera;
import elementos.personaje.Grupo;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.personaje.NoAgrupableException;
import interfazUsuario.Juego;

/**
 * Clase que define una celda en el mapa
 *
 * @author celia
 */
public class Celda {

    private int x, y;   // Posición del elemento en la pantalla
    private Mapa mapa;
    private int tipoCelda;
    private List<Personaje> listaPersonajes;
    private Edificio edificio = null;
    private ContRecurso contRecurso = null;
    private Civilizacion civilizacion;
    private boolean transitable = true;
    private boolean visible = false;
    private Civilizacion visitadaPor;

    /**
     * Crea una nueva celda vacía en un mapa
     *
     * @param m El mapa donde colocamos la celda
     * @param x Posición x de la celda
     * @param y Posición y de la celda
     */
    public Celda(Mapa m, int x, int y) {
        this.visitadaPor = null;
        this.x = x;
        this.y = y;
        this.mapa = m;
        this.civilizacion = null;
    }

    //GETTERS Y SETTERS
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Mapa getMapa() {
        return this.mapa;
    }

    public int getTipoCelda() {
        return this.tipoCelda;
    }

    public Civilizacion getCivilizacion() {
        return this.civilizacion;
    }

    public int getNumElementos() {
        int count = 0;
        if (contRecurso != null) {
            return 1;
        } else if (edificio != null) {
            count = 1;
        }
        return count + this.listaPersonajes.size();
    }

    public boolean getTransitable() {
        return this.transitable;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public Edificio getEdificio() {
        return this.edificio;
    }

    public ContRecurso getContRecurso() {
        return this.contRecurso;
    }

    public List<Personaje> getPersonajes() {
        return this.listaPersonajes;
    }

    public Civilizacion getVisitadaPor() {
        return this.visitadaPor;
    }

    public void setX(int x_ej) {
        if (x_ej > 0) {
            this.x = x_ej;
        } else {
            this.x = 0;
        }
    }

    public void setY(int y_ej) {
        if (y_ej > 0) {
            this.y = y_ej;
        } else {
            this.y = 0;
        }
    }

    public void setTipoCelda(int tipo) {
        this.tipoCelda = tipo;
    }

    public void setTransitable(boolean transitable) {
        this.transitable = transitable;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setCivilizacion(Civilizacion civ) {
        this.civilizacion = civ;
    }

    public void setContRecurso(ContRecurso x) {
        this.contRecurso = x;
    }

    public void setVisitadaPor(Civilizacion civ) {
        this.visitadaPor = civ;
    }
    //FUNCIONES

    /**
     * Añade un personaje a la celda
     *
     * @param p Personaje a añadir
     * @throws excepciones.celda.CeldaEnemigaException
     * @throws excepciones.celda.NoTransitablebleException
     */
    public void anhadePersonaje(Personaje p) throws CeldaEnemigaException, NoTransitablebleException {
        if (getTransitable()) {
            // Si es una pradera, la elimino
            if ((this.contRecurso != null) && (this.contRecurso instanceof Pradera)) {
                this.contRecurso = null;
            }
            // Si la celda pertenece a otra civilización, tiro una excepción
            if ((this.getCivilizacion() != null) && (this.getCivilizacion() != p.getCivilizacion())) {
                throw new CeldaEnemigaException("La celda está ocupada por el enemigo");
            }
            // Añado el personaje a la lista de personajes
            this.listaPersonajes.add(p);
            p.setCelda(this);
            // Haz visible esta celda y las que la rodean
            this.setVisible(true);
            p.actualizaVisibilidad();

            // Haz la celda transitable, ponle la civilizacion del personaje y corrige el tipo
            this.setTransitable(true);
            this.setCivilizacion(p.getCivilizacion());
            this.setTipo();
        } else {
            throw new NoTransitablebleException("La celda no es transitable");
        }
    }

    /**
     * Añade un edificio a la celda
     *
     * @param e Edificio a añadir
     * @throws excepciones.celda.CeldaOcupadaException
     */
    public void anhadeEdificio(Edificio e) throws CeldaOcupadaException, CeldaEnemigaException {
        if (getTransitable()) {
            // Si es una pradera, la elimino
            if ((this.contRecurso != null) && (this.contRecurso instanceof Pradera)) {
                this.contRecurso = null;
            }
            // Si la celda pertenece a otra civilización, tiro una excepción
            if ((this.getCivilizacion() != null) && (this.getCivilizacion() != e.getCivilizacion())) {
                throw new CeldaEnemigaException("La celda está ocupada por el enemigo");
            }
            if (this.edificio == null) {
                this.edificio = e;
                e.setCelda(this);
                // Haz la celda transitable, ponle la civilizacion del personaje y corrige el tipo
                this.setTransitable(true);
                this.setCivilizacion(e.getCivilizacion());
                this.setTipo();
            } else {
                throw new CeldaOcupadaException("La celda está ocupada.");
            }
        } else {
            throw new CeldaOcupadaException("La celda no es transitable.");
        }
    }

    /**
     * Añade un contenedor de recursos a la celda
     *
     * @param cr Contenedor a añadir
     * @throws excepciones.celda.CeldaOcupadaException
     */
    public void anhadeCR(ContRecurso cr) throws CeldaOcupadaException {
        if ((this.listaPersonajes.isEmpty()) && (this.edificio == null)) {
            this.contRecurso = cr;
            cr.setCelda(this);
            this.civilizacion = null;
            // Solo las praderas son transitables
            if (cr instanceof Pradera) {
                this.transitable = true;
            } else {
                this.transitable = false;
            }
            setTipo();
        } else {
            throw new CeldaOcupadaException("No se puede añadir el contenedor, la celda está ocupada.");
        }
    }

    public String agrupar() throws ParametroIncorrectoException, NoAgrupableException, CeldaEnemigaException, NoTransitablebleException {
        if (this.getPersonajes().size() <= 1) {
            throw new NoAgrupableException("No hay personajes suficientes para agrupar");
        }
        // Crea e inicializa el grupo
        Grupo grupo = new Grupo();
        grupo.inicializaNombre(Juego.getCivilizacionActiva());
        Juego.getCivilizacionActiva().anhadeGrupo(grupo);

        // Recorre la lista de personajes
        for (Personaje p : this.getPersonajes()) {
            grupo.anhadirPersonaje(p);
            p.setGrupo(grupo);
            this.eliminarPersonaje(p);
        }

        this.anhadePersonaje(grupo);
        this.setTipo();
        
        
        String s = "Se ha creado el " + grupo.getNombre() + " de la civilización " + Juego.getCivilizacionActiva() + grupo;
        return s;
    }

    /**
     * Devuelve una cadena con las coordenadas de la celdas
     *
     * @return Las coordenadas de la celda
     */
    @Override
    public String toString() {
        String s = "(" + this.getY() + "," + this.getX() + ")";
        return s;
    }

    public void eliminarPersonaje(Personaje p) {
        this.listaPersonajes.remove(p);
        // Fijo el tipo después de eliminar el personaje
        this.setTipo();
    }

    /**
     * Devuelve un String con información sobre el contenido de la celda
     *
     */
    public String mirar() {
        String s = "\nCelda en fila " + getY() + " columna " + getX();
        s += "\nContenido:";
        // Si hay un CR no puede haber nada más
        if (this.contRecurso != null) {
            s += this.contRecurso.toString();
        } else {
            if (this.edificio != null) {
                s += "\t" + this.edificio.toString() + "\n";
            }
            for (Personaje p : listaPersonajes) {
                s += "\t" + p.toString() + "\n";
            }
        }
        return s;
    }

    /**
     * Reinicializa la lista de personajes de la celda
     */
    public void restartPersonajes() {
        this.listaPersonajes = new ArrayList<Personaje>();
    }

    /**
     * Reinicializa la celda
     */
    public void restartCelda() {
        this.restartPersonajes();
        this.edificio = null;
        Pradera pr = new Pradera();
        this.contRecurso = pr;
        pr.setCelda(this);
        this.tipoCelda = Juego.TPRADERA;
        this.civilizacion = null;
        this.transitable = true;
    }

    private void setTipo() {
        // Si no tiene elementos, la convierto en pradera
        if (getNumElementos() <= 0) {
            restartCelda();
        } else if (getNumElementos() > 1) { // Queda más de un elemento
            this.setTipoCelda(Juego.TVARIOS);
        } else { // Si queda solo un tipo de elemento, miramos si es un CR, edificio o un personaje
            if (this.contRecurso != null) {
                this.setTipoCelda(this.contRecurso.getTipo());
            } else if (this.edificio != null) {
                this.setTipoCelda(this.edificio.getTipo());
            } else if (!this.getPersonajes().isEmpty()) {
                this.setTipoCelda(this.listaPersonajes.get(0).getTipo());
            }
        }
    }
}
