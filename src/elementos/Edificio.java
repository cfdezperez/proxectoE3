/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import elementos.edificio.Casa;
import elementos.edificio.Ciudadela;
import elementos.edificio.Cuartel;
import excepciones.CivilizacionDestruidaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.edificio.EdificioException;
import excepciones.personaje.AtaqueExcepcion;
import excepciones.personaje.EstarEnGrupoException;
import vista.*;

/**
 *
 * @author celia y maria
 */
public abstract class Edificio {

    private Celda celda;
    private String nombre;
    private int tipoEdificio; //1 es ciudadela, 2 es Casa y 3 Cuartel
    private Civilizacion civilizacion;
    private int salud;
    private final int saludInicial;
    private boolean estado;
    private int costeReparacionMadera;
    private int costeReparacionPiedra;
    private int ataque;
    private int defensa;
    private boolean crearPaisanos = false;
    private int costeCrearComida;
    private final double[] capAlmacenamiento = new double[4];
    private boolean capAlmacenar = false;
    private boolean capAlojar = false;
    private static int capAlojamientoTotal = 0;
    private int capAlojamiento;
    private boolean crearSoldados = false;
    private int capPersonajes;
    private boolean estarVacio;

    //CONSTRUCTORES
    /*public Edificio(Celda c) {
        //Inicializa sus atributos con unos datos predeterminados
        //Crea una ciudadela
        this(c, "Ciudadela", Mapa.TCIUDADELA);
    }*/
    public Edificio(int tipo) throws CeldaOcupadaException, ParametroIncorrectoException {
        //Inicializa sus atributos con unos datos predeterminados
        this(10, 50, 40, 50, 50, tipo);

    }

    /**
     * Creamos un edificio
     *
     * @param salud1 Salud del edificio
     * @param CRM
     * @param CRP
     * @param CCC
     * @param capAlm
     * @param tipo
     * @throws CeldaOcupadaException
     * @throws ParametroIncorrectoException
     */
    public Edificio(int salud1, int CRM, int CRP, int CCC, double capAlm, int tipo) throws CeldaOcupadaException, ParametroIncorrectoException {
        if (salud1 <= 0 || CRM < 0 || CRP < 0) { //Si no tiene salud no existe
            //this.estado = false;
            //this.saludInicial = 0;
            throw new ParametroIncorrectoException("La salud no puede ser negativa o nula");
        } else {
            this.tipoEdificio = tipo;
            this.estado = true;
            this.salud = salud1;
            this.saludInicial = salud1;
            this.costeReparacionMadera = CRM;
            this.costeReparacionPiedra = CRP;
            this.ataque = 0;
            this.defensa = 0;
            this.costeCrearComida = CCC;
            this.capAlmacenamiento[0] = capAlm;
            this.estarVacio = true;
        }
    }

    //GETTERS Y SETTERS
    public Celda getCelda() {
        return this.celda;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getTipo() {
        return this.tipoEdificio;
    }

    public Civilizacion getCivilizacion() {
        return this.civilizacion;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public int getSalud() {
        return this.salud;
    }

    public int getSaludInicial() {
        return this.saludInicial;
    }

    public int getCRM() {
        return this.costeReparacionMadera;
    }

    public int getCRP() {
        return this.costeReparacionPiedra;
    }

    public int getAtaque() {
        return this.ataque;
    }

    public int getDefensa() {
        return this.defensa;
    }

    public int getCosteCrearComida() {
        return this.costeCrearComida;
    }

    public boolean getCrearPaisanos() {
        return this.crearPaisanos;
    }

    public boolean getCrearSoldados() {
        return this.crearSoldados;
    }

    public double[] getCapAlmacenamiento() { //Devuelve el vector con las capacidades de almacenamiento
        return this.capAlmacenamiento;
    }

    public double getCapAlmacenamientoTotal() {
        return this.capAlmacenamiento[0];
    }

    public double getMadera() {
        return this.capAlmacenamiento[Recurso.TRMADERA];
    }

    public double getComida() {
        return this.capAlmacenamiento[Recurso.TRCOMIDA];
    }

    public double getPiedra() {
        return this.capAlmacenamiento[Recurso.TRPIEDRA];
    }

    public boolean getCapAlmacenar() { //es true si es ciudadela, solo se almacena recursos ahí
        return this.capAlmacenar;
    }

    public int getCapAlojamiento() { //devuelve la capacidad de alojamiento de la casa
        return this.capAlojamiento;
    }

    public int getCapPersonajes() {
        return this.capPersonajes;
    }

    public boolean getEstarVacio() {
        return this.estarVacio;
    }

    public void setCelda(Celda c) {
        this.celda = c;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }

    //No se define un setTipoCelda() ni un setTipo() pues no se puede cambiar el tipo de edificio despues de creado
    public void setSalud(int s) {
        if (s <= 0) {
            this.salud = 0;
            this.estado = false;
        } else {
            this.salud = s;
        }
    }

    public void setCivilizacion(Civilizacion civ) {
        this.civilizacion = civ;
    }

    public final void setCrearPaisanos(boolean b) {
        this.crearPaisanos = b;
    }

    public final void setCrearSoldados(boolean b) {
        this.crearSoldados = b;
    }

    public final void setCapAlmacenar(boolean b) {
        this.capAlmacenar = b;
    }

    public void setCRM(int CRM) {
        if (CRM < 0) {
            this.costeReparacionMadera = 0;
            this.estado = false;
        }
        this.costeReparacionMadera = CRM;
    }

    public void setCRP(int CRP) {
        if (CRP < 0) {
            this.costeReparacionPiedra = 0;
            this.estado = false;
        }
        this.costeReparacionPiedra = CRP;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setCapAlmacenamientoTotal(double capAl) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capAl <= 0) {
                throw new ParametroIncorrectoException("Se supera la capacidad de almacenamiento de "+this.getNombre());
            } else {
                this.capAlmacenamiento[0] = capAl;
            }
        }
    }

    /**
     * Almacena lo recolectado por un personaje
     *
     * @param recolectado
     * @throws ParametroIncorrectoException
     */
    public void almacenar(double[] recolectado) throws ParametroIncorrectoException {
        setCapAlmacenamientoTotal(getCapAlmacenamiento()[0]-(recolectado[Recurso.TRMADERA]+recolectado[Recurso.TRCOMIDA]+recolectado[Recurso.TRPIEDRA]));
        setMadera(getCapAlmacenamiento()[Recurso.TRMADERA] + recolectado[Recurso.TRMADERA]);
        setComida(getCapAlmacenamiento()[Recurso.TRCOMIDA] + recolectado[Recurso.TRCOMIDA]);
        setPiedra(getCapAlmacenamiento()[Recurso.TRPIEDRA] + recolectado[Recurso.TRPIEDRA]);
    }

    public void setMadera(double capM) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capM < 0) {
                throw new ParametroIncorrectoException("No es posible esa capacidad");

            } else {
                this.capAlmacenamiento[Recurso.TRMADERA] = capM;
            }
        }
    }

    public void setComida(double capA) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capA < 0) {
                throw new ParametroIncorrectoException("No es posible esa capacidad");

            } else {
                this.capAlmacenamiento[Recurso.TRCOMIDA] = capA;
            }
        }
    }

    public void setPiedra(double capP) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capP < 0) {
                throw new ParametroIncorrectoException("No es posible esa capacidad");

            } else {
                this.capAlmacenamiento[Recurso.TRPIEDRA] = capP;
            }
        }
    }

    public void setAtaque(int ataq) {
        if (ataq <= 0) {
            this.ataque = 0;
            this.estado = false;
        } else {
            this.ataque = ataq;
        }
    }

    public void setDefensa(int def) {
        if (def <= 0) {
            this.defensa = 0;
            this.estado = false;
        } else {
            this.defensa = def;
        }
    }

    public void setCosteCrearComida(int CCC) {
        if (CCC < 0) {
            this.costeCrearComida = 0;
            this.estado = false;
        } else {
            this.costeCrearComida = CCC;
        }
    }

    public final void setCapAlojar(boolean b) {
        this.capAlojar = b;
    }

    public final void setCapAlojamiento(int capAlojar) throws ParametroIncorrectoException {
        if (this.capAlojar == true) {
            if (capAlojar < 0) {
                throw new ParametroIncorrectoException("La capacidad de alojamiento no puede ser negativa");
            } else {
                this.capAlojamiento = capAlojar;
            }
        } else {
            this.capAlojamiento = 0;
        }
    }

    public static final void addCapAlojamientoTotal(int cap) {
        Edificio.capAlojamientoTotal += cap;
    }

    public final void setCapPersonajes(int numPer) {
        if (numPer < 0) {
            this.capPersonajes = 0; //PUEDE NO DEJAR ENTRAR A NINGUN PERSONAJE 
            this.estado = false;
        } else {
            this.capPersonajes = numPer;
        }
    }

    public void setEstarVacio(boolean a) {
        this.estarVacio = a;
    }

    public void reiniciarSalud() {
        this.salud = this.saludInicial;
    }

    @Override
    public String toString() {
        String s = "Tipo edicifio: ";
        if (this instanceof Casa) {
            s += "Casa";
        }
        if (this instanceof Ciudadela) {
            s += "Ciudadela";
        }
        if (this instanceof Cuartel) {
            s += "Cuartel";
        }
        s += ", Nombre: " + this.getNombre();
        s += "\n\tCivilización a la que pertenece: " + this.getCivilizacion().getNomCivilizacion();
        s += "\n\tSalud: " + this.getSalud();
        s += "\n\tCapacidad de almacenamiento: " + this.getCapAlmacenamiento()[0];
        s += "\n\tComida almacenada " + this.getComida();
        s += "\n\tMadera almacenada " + this.getMadera();
        s += "\n\tPiedra almacenada " + this.getPiedra();
        s += "\n\tCoste de reparación en piedra: " + this.getCRP();
        s += "\n\tCoste de reparación en madera: " + this.getCRM();
        s += "\n\tCapacidad de ataque: " + getAtaque();
        s += "\n\tCapacidad de defensa: " + getDefensa();
        return (s);
    }

    // Clases abstractas
    /**
     * Se encarga de fijar el nombre a partir del tipo de personaje y la
     * civilización a la que pertenece
     *
     * @param civil Civilización a la que pertenece
     */
    public abstract void inicializaNombre(Civilizacion civil);

    /**
     * Determina si podemos crear un personaje y la celda en la cuál podemos
     * crear el personaje
     *
     * @param tipoPersonaje
     * @return La celda en la cuál podemos crear el personaje
     *
     * @throws EdificioException
     * @throws FueraDeMapaException
     * @throws ParametroIncorrectoException
     * @throws excepciones.celda.CeldaEnemigaException
     * @throws excepciones.celda.NoTransitablebleException
     */
    public Personaje crear(String tipoPersonaje) throws EdificioException, FueraDeMapaException, ParametroIncorrectoException, 
            CeldaEnemigaException, NoTransitablebleException {

        if (Edificio.capAlojamientoTotal <= this.civilizacion.numeroPersonajes()) {
            throw new EdificioException("No se pueden crear más personajes, necesitan más casas donde alojarse.");
        }

        if (this.getComida() < this.costeCrearComida) {
            throw new EdificioException("No hay suficiente comida como para crear un personaje");
        }

        // Determina la celda en la que crear
        Celda actual = this.getCelda();
        Celda vecina;
        Celda vecinaNorte = actual.getMapa().obtenerCeldaVecina(actual, "norte");
        Celda vecinaSur = actual.getMapa().obtenerCeldaVecina(actual, "sur");
        Celda vecinaEste = actual.getMapa().obtenerCeldaVecina(actual, "este");
        Celda vecinaOeste = actual.getMapa().obtenerCeldaVecina(actual, "oeste");

        if (vecinaNorte.getTransitable()) {
            vecina = vecinaNorte;
        } else if (vecinaOeste.getTransitable()) {
            vecina = vecinaOeste;
        } else if (vecinaSur.getTransitable()) {
            vecina = vecinaSur;
        } else if (vecinaEste.getTransitable()) {
            vecina = vecinaEste;
        } else {
            throw new EdificioException("Imposible crear en ninguna celda de las que rodean al edificio.");
        }
        Personaje p = creaPersonaje(vecina, tipoPersonaje);
        this.setComida(this.getComida() - this.costeCrearComida);
        this.setCapAlmacenamientoTotal(this.getCapAlmacenamientoTotal() + this.costeCrearComida);
        return p;
    }

    public abstract Personaje creaPersonaje(Celda vecina, String tipoPersonaje) throws EdificioException, ParametroIncorrectoException, 
            CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException;
       /**
     * Ataca a una celda vecina
     * 
     * @param direccion Dirección hacia la que atacar
     * @return Mensaje de estado
     * @throws FueraDeMapaException
     * @throws ParametroIncorrectoException
     * @throws NoTransitablebleException
     * @throws CeldaEnemigaException
     * @throws AtaqueExcepcion
     * @throws EstarEnGrupoException 
     * @throws excepciones.CivilizacionDestruidaException 
     */
    public String atacar(String direccion) throws FueraDeMapaException, ParametroIncorrectoException, 
            NoTransitablebleException, CeldaEnemigaException, AtaqueExcepcion, EstarEnGrupoException, CivilizacionDestruidaException {
        String s;

        Celda actual = this.getCelda();
        Celda vecina = actual.getMapa().obtenerCeldaVecina(actual, direccion);
        Civilizacion vecinaCivil = vecina.getCivilizacion();
        
        if(vecinaCivil == null) {
            throw new ParametroIncorrectoException("Civilización desconocida");
        }
        if (vecina.getContRecurso() != null) {
            throw new NoTransitablebleException("No puedes atacar a un " + vecina.getContRecurso().getNombre());
        }
        if (vecinaCivil == this.getCivilizacion()) {
            throw new CeldaEnemigaException("No puedes atacar a una celda amiga.");
        }

        // Comprobamos si hay personajes y atacamos al más débil
        if (!vecina.getPersonajes().isEmpty()) {
            int saludEnemigo = Integer.MAX_VALUE;
            Personaje pEnemigo = null;
            for (Personaje p : vecina.getPersonajes()) {
                if (p.getSalud() < saludEnemigo) {
                    saludEnemigo = p.getSalud();
                    pEnemigo = p;
                }
            }
            if (pEnemigo != null) {
                // Intentamos atacar al personaje
                int danhoCausado = this.getAtaque() - pEnemigo.getArmadura();

                if (danhoCausado < 1) {
                    s = this.getNombre() + " no puede atacar: la defensa enemiga es demasiado fuerte.";
                } else {
                    int nuevaSalud = pEnemigo.getSalud() - danhoCausado;
                    if (nuevaSalud > 0) {
                        pEnemigo.setSalud(nuevaSalud);
                        s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + pEnemigo.getNombre()
                                + " (" + pEnemigo.getCivilizacion().getNomCivilizacion() + "): la salud de "
                                + pEnemigo.getNombre() + " es ahora " + pEnemigo.getSalud();
                    } else {
                        s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + pEnemigo.getNombre()
                                + " (" + pEnemigo.getCivilizacion().getNomCivilizacion() + "): "
                                + pEnemigo.getNombre() + " ha muerto";
                        vecina.eliminarPersonaje(pEnemigo);
                        pEnemigo.getCivilizacion().eliminaPersonaje(pEnemigo);
                        vecina.setVisitadaPor(this.getCivilizacion());
                    }
                }
                return s;
            } else {
                throw new AtaqueExcepcion("Error desconocido al atacar");
            }

            // Si no hay personajes, atacamos al edificio    
        } else if (vecina.getEdificio() != null) {
            Edificio edificioEnemigo = vecina.getEdificio();
            // Intentamos atacar al edificio
            int danhoCausado = this.getAtaque() - edificioEnemigo.getDefensa();

            if (danhoCausado < 1) {
                s = this.getNombre() + " no puede atacar a " + edificioEnemigo.getNombre()
                        + " (" + edificioEnemigo.getCivilizacion().getNomCivilizacion() + "): su defensa es demasiado fuerte.";
            } else {
                int nuevaSalud = edificioEnemigo.getSalud() - danhoCausado;
                if (nuevaSalud > 0) {
                    edificioEnemigo.setSalud(nuevaSalud);
                    s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + edificioEnemigo.getNombre()
                            + " (" + edificioEnemigo.getCivilizacion().getNomCivilizacion() + "): la salud de "
                            + edificioEnemigo.getNombre() + " es ahora " + edificioEnemigo.getSalud();
                } else {
                    s = this.getNombre() + " ha inflingido " + danhoCausado + " de daño a " + edificioEnemigo.getNombre()
                            + " (" + edificioEnemigo.getCivilizacion().getNomCivilizacion() + "): "
                            + edificioEnemigo.getNombre() + " ha quedado destruido";
                    vecina.eliminaEdificio();
                    edificioEnemigo.getCivilizacion().eliminaEdificio(edificioEnemigo);
                    vecina.setVisitadaPor(this.getCivilizacion());
                }
            }
                        // Comprueba si hamos destruido la civilizacion
            if(vecinaCivil.determinaDestruccion()) {
                throw new CivilizacionDestruidaException("La civilización "+vecinaCivil.getNomCivilizacion()+" ha sido destruida!");
            }
            return s;
        } else {
            throw new AtaqueExcepcion("No hay nadie a quién atacar");
        }
    }

//
//    private void eliminarPersonaje(Mapa m, Celda c) {
//        switch (c.getTipoCelda()) {
//            case Mapa.TARBUSTO:
//            case Mapa.TBOSQUE:
//            case Mapa.TCANTERA:
//                Iterator it = m.getContRecursos().entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry e = (Map.Entry) it.next();
//                    ContRecurso cr = (ContRecurso) e.getValue();
//                    if (cr.getCelda().getX() == c.getX()
//                            && cr.getCelda().getY() == c.getY()) {
//                        it.remove();
//                    }
//                }
//                break;
//
//            case Mapa.TCASA:
//            case Mapa.TCIUDADELA:
//            case Mapa.TCUARTEL:
//                Iterator it2 = m.getCivActiva().getMapaEdificios().entrySet().iterator();
//                while (it2.hasNext()) {
//                    Map.Entry e = (Map.Entry) it2.next();
//                    Edificio ed = (Edificio) e.getValue();
//                    if (ed.getCelda().getX() == c.getX()
//                            && ed.getCelda().getY() == c.getY()) {
//                        it2.remove();
//                    }
//                }
//                break;
//
//            case Mapa.TPAISANO:
//            case Mapa.TSOLDADO:
//                Iterator it3 = m.getCivActiva().getPerCivilizacion().entrySet().iterator();
//                while (it3.hasNext()) {
//                    Map.Entry e = (Map.Entry) it3.next();
//                    Personaje p = (Personaje) e.getValue();
//                    if (p.getCelda().getX() == c.getX()
//                            && p.getCelda().getY() == c.getY()) {
//                        it3.remove();
//                    }
//                }
//                break;
//
//        }
//    }
//        
}
