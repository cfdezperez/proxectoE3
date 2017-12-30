/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import elementos.edificio.Casa;
import elementos.edificio.Ciudadela;
import elementos.edificio.Cuartel;
import excepciones.celda.CeldaOcupadaException;
import excepciones.ParametroIncorrectoException;
import vista.*;

/**
 *
 * @author celia
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
    private int[] capAlmacenamiento = new int[4];
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
        this(100, 50, 40, 50, 50, 25, 20, tipo);

    }
    /**
     * Creamos un edificio
     * 
     * @param salud1 Salud del edificio
     * @param CRM
     * @param CRP
     * @param ataq
     * @param def
     * @param CCC
     * @param capAlm
     * @throws CeldaOcupadaException
     * @throws ParametroIncorrectoException 
     */
    public Edificio(int salud1, int CRM, int CRP, int ataq, int def, int CCC, int capAlm, int tipo) throws CeldaOcupadaException, ParametroIncorrectoException {
        if (salud1 <= 0 || CRM < 0 || CRP < 0) { //Si no tiene salud no existe
            //this.estado = false;
            //this.saludInicial = 0;
            throw new ParametroIncorrectoException("La salud no puede ser negativa o nula");
        } else {
            //this.celda = c1;
            //this.nombre = nombre;
            //this.civilizacion = civil;
            this.tipoEdificio = tipo;
            this.estado = true;
            this.salud = salud1;
            this.saludInicial = salud1;
            this.costeReparacionMadera = CRM;
            this.costeReparacionPiedra = CRP;
            this.ataque = ataq;
            this.defensa = def;
            this.costeCrearComida = CCC;
            this.capAlmacenamiento[0] = capAlm;
            this.estarVacio = true;

            //this.celda.anhadeEdificio(this);
            //this.celda.setCivilizacion(Juego.getCivilizacionActiva());
            //this.celda.setTransitable(true);
            // Si es una ciudadela, puede crear paisanos
//            if (this.tipoEdificio == Juego.TCIUDADELA) {
//                this.crearPaisanos = true;
//                this.capAlmacenar = true;
//            } // Si es un cuartel puede crear soldados
//            else if (this.tipoEdificio == Juego.TCUARTEL) {
//                this.crearSoldados = true;
//            } else {
//                this.crearPaisanos = false;
//                this.crearSoldados = false;
//                this.capAlojar = true;
//                this.capAlojamiento = capAlojar;
//                Edificio.capAlojamientoTotal += this.capAlojamiento;
//            }
            // Añade eficifio a la civilizacion
            //this.civilizacion.anhadeEdificio(this);
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

    public int[] getCapAlmacenamiento() { //Devuelve el vector con las capacidades de almacenamiento
        return this.capAlmacenamiento;
    }

    public int getCapAlmacenamientoTotal() {
        return this.capAlmacenamiento[0];
    }

    public int getMadera() {
        return this.capAlmacenamiento[Recurso.TRMADERA];
    }

    public int getComida() {
        return this.capAlmacenamiento[Recurso.TRCOMIDA];
    }

    public int getPiedra() {
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

    public void setCapAlmacenamientoTotal(int capAl) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capAl <= 0) {
                throw new ParametroIncorrectoException("No es posible esa capacidad");
            } else {
                this.capAlmacenamiento[0] = capAl;
            }
        }
    }

    public void setMadera(int capM) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capM <= 0) {
                throw new ParametroIncorrectoException("No es posible esa capacidad");

            } else {
                this.capAlmacenamiento[Recurso.TRMADERA] = capM;
            }
        }
    }

    public void setComida(int capA) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capA <= 0) {
                throw new ParametroIncorrectoException("No es posible esa capacidad");

            } else {
                this.capAlmacenamiento[Recurso.TRCOMIDA] = capA;
            }
        }
    }

    public void setPiedra(int capP) throws ParametroIncorrectoException {
        if (this.getCapAlmacenar() == true) {
            if (capP <= 0) {
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
        String s = "\n\tTipo edicifio: ";
        if(this instanceof Casa) {
            s += "Casa";
        }
        if(this instanceof Ciudadela) {
            s += "Ciudadela";
        }
        if(this instanceof Cuartel) {
            s += "Cuartel";
        }
        s += ", Nombre: "+this.getNombre();
        s += "\n\tCivilización a la que pertenece: " + this.getCivilizacion().getNomCivilizacion();
        s += "\n\tSalud: " + this.getSalud();
        s += "\n\tCoste de reparación en piedra: " + this.getCRP();
        s += "\n\tCoste de reparación en madera: " + this.getCRM();
        return(s);
    }    

    // Clases abstractas
    /**
     * Se encarga de fijar el nombre a partir del tipo de personaje y la
     * civilización a la que pertenece
     *
     * @param civil Civilización a la que pertenece
     */
    public abstract void inicializaNombre(Civilizacion civil);

//    public void crear(Mapa mapa) {
//        int x = this.getCelda().getX();
//        int y = this.getCelda().getY();
//        System.out.println("CCC "+this.getComida()+ " "+ this.costeCrearComida);
//        if (Edificio.capAlojamientoTotal <= this.civilizacion.numeroPersonajes()) {
//            System.out.println("No se pueden crear más personajes, necesitan más casas donde alojarse.");
//        } else {
//            if (this.getComida() < this.costeCrearComida) {
//                System.out.println("No hay suficiente comida como para crear un personaje");
//            } else {
//                switch (this.getTipo()) {
//                    //si edificio es ciudadela se tiene que crear paisano
//                    case Mapa.TCIUDADELA:
//                        //Compruebas celda norte                
//                        if (x > 0 && mapa.getCeldas().get(x - 1).get(y).getTransitable()) {
//                            crearPaisano(mapa, mapa.getCeldas().get(x - 1).get(y));
//                        } //SUR
//                        else if (x < (mapa.getCeldas().size() - 1)
//                                && mapa.getCeldas().get(x + 1).get(y).getTransitable()) {
//                            crearPaisano(mapa, mapa.getCeldas().get(x + 1).get(y));
//                        } //ESTE
//                        else if (y > 0 && mapa.getCeldas().get(x).get(y - 1).getTransitable()) {
//                            crearPaisano(mapa, mapa.getCeldas().get(x).get(y - 1));
//                        } //OESTE
//                        else if (y < (mapa.getCeldas().get(0).size() - 1)
//                                && mapa.getCeldas().get(x).get(y + 1).getTransitable()) {
//                            crearPaisano(mapa, mapa.getCeldas().get(x).get(y + 1));
//                        } else {
//                            System.out.println("Imposible crear Paisano.");
//                        }
//                        break;
//
//                    //si edificio es cuartel se crea soldado
//                    case Mapa.TCUARTEL:
//                        //Compruebas celda norte                
//                        if (x > 0 && mapa.getCeldas().get(x - 1).get(y).getTransitable()) {
//                            crearSoldado(mapa, mapa.getCeldas().get(x - 1).get(y));
//                        } //SUR
//                        else if (x < (mapa.getCeldas().size() - 1)
//                                && mapa.getCeldas().get(x + 1).get(y).getTransitable()) {
//                            crearSoldado(mapa, mapa.getCeldas().get(x + 1).get(y));
//                        } //ESTE
//                        else if (y > 0 && mapa.getCeldas().get(x).get(y - 1).getTransitable()) {
//                            crearSoldado(mapa, mapa.getCeldas().get(x).get(y - 1));
//                        } //OESTE
//                        else if (y < (mapa.getCeldas().get(0).size() - 1)
//                                && mapa.getCeldas().get(x).get(y + 1).getTransitable()) {
//                            crearSoldado(mapa, mapa.getCeldas().get(x).get(y + 1));
//                        } else {
//                            System.out.println("Imposible crear Soldado.");
//                        }
//                        break;
//
//                    default:
//                        System.out.println("No se pueden crear personajes desde este edificio.");
//                        break;
//                }
//            }
//        }
//    }
//
//    private void crearPaisano(Mapa mapa, Celda c) {
//        Personaje Paisano = new Personaje(c, "Paisano", this.getCivilizacion(), Mapa.TPAISANO);
//        mapa.addPersonaje(Paisano);
//        c.setTransitable(false);
//        c.setEntrable(true);
//        if(c.getVisible() != true){
//            c.setVisible(true);
//            this.getCivilizacion().getCeldasCivilizacion().add(c);
//        }
//        c.setTipoCelda(Mapa.TPAISANO);
//        this.setComida(this.getComida()-this.costeCrearComida);
//    }
//
//    private void crearSoldado(Mapa mapa, Celda c) {
//        Personaje Soldado = new Personaje(c, "Soldado", this.getCivilizacion(), Mapa.TSOLDADO);
//        mapa.addPersonaje(Soldado);
//        c.setTransitable(false);
//        c.setEntrable(true);
//        if(c.getVisible() != true){
//            c.setVisible(true);
//            this.getCivilizacion().getCeldasCivilizacion().add(c);
//        }
//        c.setTipoCelda(Mapa.TSOLDADO);
//        this.setComida(this.getComida()-this.costeCrearComida);
//    }
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
//    public void atacar(Mapa mapa, String direccion) {
//        Celda vecina = obtenerCeldaVecina(mapa, direccion); 
//
//        if (vecina == null) {
//            System.out.println("No se puede atacar hacia el "
//                    + direccion + ": se sale del mapa.");
//        } else
//        {
//            if (vecina.getNumElementos() != 0) {
//
//                Iterator it2 = obtenerCivEnemiga(mapa).getMapaEdificios().entrySet().iterator();
//                while (it2.hasNext()) {
//                    Map.Entry e = (Map.Entry) it2.next();
//                    if (((Edificio) e.getValue()).getCelda().getX() == vecina.getX()
//                            && ((Edificio) e.getValue()).getCelda().getY() == vecina.getY()) {
//                        if (!((Edificio) e.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                            if (((Edificio) e.getValue()).getEstarVacio() == true) {
//                                ((Edificio) e.getValue()).setSalud(((Edificio) e.getValue()).getSalud() - this.getAtaque());
//                                System.out.println("El edificio " + ((Edificio) e.getValue()).getNombre() + " ha sido atacado.");
//                                if (((Edificio) e.getValue()).getSalud() <= 0) {
//                                    ((Edificio) e.getValue()).setEstado(false);
//                                    vecina.setNumeroElementos(vecina.getNumElementos() - 1);
//                                    System.out.println("El edificio " + ((Edificio) e.getValue()).getNombre() + " ha sido destruido.");
//                                }
//                            } else {
//                                Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
//                                while (it.hasNext()) {
//                                    Map.Entry e2 = (Map.Entry) it.next();
//                                    if (((Personaje) e2.getValue()).getCelda().getX() == vecina.getX()
//                                            && ((Personaje) e2.getValue()).getCelda().getY() == vecina.getY()) {
//                                        if (!((Personaje) e2.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                            if (((Personaje) e2.getValue()).getEstarGrupo() == false) {
//                                                ((Personaje) e2.getValue()).setSalud(((Personaje) e2.getValue()).getSalud()
//                                                        - this.getAtaque() / ((Personaje) e2.getValue()).getArmadura());
//                                                System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha sido atacado.");
//                                                if (((Personaje) e2.getValue()).getSalud() <= 0) {
//                                                    ((Personaje) e2.getValue()).setEstado(false);
//                                                    ((Edificio) e.getValue()).setCapPersonajes(((Edificio) e.getValue()).getCapPersonajes() + 1);
//                                                    System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha muerto.");
//                                                    vecina.setNumeroElementos(vecina.getNumElementos() - 1);
//
//                                                    ((Edificio) e.getValue()).setAtaque(((Edificio) e.getValue()).getAtaque() - ((Personaje) e2.getValue()).getAtaque());
//                                                    ((Edificio) e.getValue()).setDefensa(((Edificio) e.getValue()).getDefensa() - ((Personaje) e2.getValue()).getArmadura());
//                                                }
//                                            }
//                                        } else {
//                                            System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                        }
//                                    }
//                                }
//
//                                Iterator it3 = obtenerCivEnemiga(mapa).getMapaGrupos().entrySet().iterator();
//                                while (it3.hasNext()) {
//                                    Map.Entry e3 = (Map.Entry) it3.next();
//                                    if (((Grupo) e3.getValue()).getCelda().getX() == vecina.getX()
//                                            && ((Grupo) e3.getValue()).getCelda().getY() == vecina.getY()) {
//                                        if (!((Grupo) e3.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                            Iterator it4 = ((Grupo) e3.getValue()).getCivilizacion().getPerCivilizacion().entrySet().iterator();
//                                            while (it4.hasNext()) {
//                                                Map.Entry e4 = (Map.Entry) it4.next();
//                                                Personaje atacado = ((Personaje) e4.getValue());
//                                                for (int i = 0; i < ((Grupo) e3.getValue()).getComponentes(); i++) {
//                                                    atacado.setSalud(atacado.getSalud() - this.getAtaque() / atacado.getArmadura());
//                                                    if (atacado.getSalud() <= 0) {
//                                                        atacado.setEstado(false);
//                                                        System.out.println("El " + atacado.getNombre() + " ha muerto.");
//                                                        ((Grupo) e3.getValue()).desligar(atacado, mapa);
//                                                    }
//                                                }
//                                            }
//                                        } else {
//                                            System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            System.out.println("No puedes atacar a un edificio de tu misma civilizacion");
//                        }
//                    }
//                    else {
//                        Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
//                        while (it.hasNext()) {
//                            Map.Entry e2 = (Map.Entry) it.next();
//                            if (((Personaje) e2.getValue()).getCelda().getX() == vecina.getX()
//                                    && ((Personaje) e2.getValue()).getCelda().getY() == vecina.getY()) {
//                                if (!((Personaje) e2.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                    if (((Personaje) e2.getValue()).getEstarGrupo() == false) {
//                                        ((Personaje) e2.getValue()).setSalud(((Personaje) e2.getValue()).getSalud()
//                                                - this.getAtaque() / ((Personaje) e2.getValue()).getArmadura());
//                                        if (((Personaje) e2.getValue()).getSalud() <= 0) {
//                                            ((Personaje) e2.getValue()).setEstado(false);
//                                            vecina.setNumeroElementos(vecina.getNumElementos() - 1);
//                                            System.out.println("El " + ((Personaje) e2.getValue()).getNombre() + " ha muerto.");
//                                        }
//                                    }
//                                } else {
//                                    System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                }
//
//                            }
//                        }
//                        Iterator it3 = obtenerCivEnemiga(mapa).getMapaGrupos().entrySet().iterator();
//                        while (it3.hasNext()) {
//                            Map.Entry e3 = (Map.Entry) it3.next();
//                            if (((Grupo) e3.getValue()).getCelda().getX() == vecina.getX()
//                                    && ((Grupo) e3.getValue()).getCelda().getY() == vecina.getY()) {
//                                if (!((Grupo) e3.getValue()).getCivilizacion().equals(this.getCivilizacion())) {
//                                    Iterator it4 = ((Grupo) e3.getValue()).getCivilizacion().getPerCivilizacion().entrySet().iterator();
//                                    while (it4.hasNext()) {
//                                        Map.Entry e4 = (Map.Entry) it4.next();
//                                        Personaje atacado = ((Personaje) e4.getValue());
//                                        for (int i = 0; i < ((Grupo) e3.getValue()).getComponentes(); i++) {
//                                            atacado.setSalud(atacado.getSalud() - this.getAtaque() / atacado.getArmadura());
//                                            if (atacado.getSalud() <= 0) {
//                                                atacado.setEstado(false);
//                                                System.out.println("El " + atacado.getNombre() + " ha muerto.");
//                                                ((Grupo) e3.getValue()).desligar(atacado, mapa);
//                                            }
//                                        }
//                                    }
//                                } else {
//                                    System.out.println("No puedes atacar a alguien de tu misma civilizacion");
//                                }
//                            }
//                        }
//                    }
//                }
//
//            } else {
//                System.out.println("La celda es una pradera, no se puede atacar.");
//            }
//            comprobarDestruccion(mapa);
//        }
//    }
//    
//    private Civilizacion obtenerCivEnemiga(Mapa mapa) {
//        Civilizacion civ = this.civilizacion;
//        Iterator it = mapa.getCivilizacion().entrySet().iterator();
//        while (it.hasNext()) {
//            //si el edificio tiene grupo dentro
//            Map.Entry e = (Map.Entry) it.next();
//            if (!((Civilizacion) e.getValue()).equals(mapa.getCivActiva())) {
//                civ = ((Civilizacion) e.getValue());
//            }
//        }
//        return civ;
//    }
//
//    private void comprobarDestruccion(Mapa mapa) {
//        Iterator it2 = obtenerCivEnemiga(mapa).getMapaEdificios().entrySet().iterator();
//        while (it2.hasNext()) {
//            Map.Entry e = (Map.Entry) it2.next();
//            if (((Edificio) e.getValue()).getSalud() == 0) {
//                it2.remove();
//            }
//        }
//
//        Iterator it = obtenerCivEnemiga(mapa).getPerCivilizacion().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry e = (Map.Entry) it.next();
//            if (((Personaje) e.getValue()).getSalud() == 0) {
//                it.remove();
//            }
//        }
//    }
//    
//    // Devuelve la celda que se encuentra a la direccion indicada de la celda 
//// del personaje
//    private Celda obtenerCeldaVecina(Mapa mapa, String direccion) {
//        Celda actual = this.getCelda();
//        Celda vecina = null;
//        switch (direccion.toLowerCase()) {
//            case "norte":
//                if (this.getCelda().getX() > 0) {
//                    vecina = mapa.getCeldas().get(actual.getX() - 1).get(actual.getY());
//                }
//                break;
//            case "sur":
//                if (this.getCelda().getX() < (mapa.getCeldas().size() - 1)) {
//                    vecina = mapa.getCeldas().get(actual.getX() + 1).get(actual.getY());
//                }
//                break;
//            case "este":
//                if (this.getCelda().getY() < (mapa.getCeldas().get(0).size() - 1)) {
//                    vecina = mapa.getCeldas().get(actual.getX()).get(actual.getY() + 1);
//                }
//                break;
//            case "oeste":
//                if (this.getCelda().getY() > 0) {
//                    vecina = mapa.getCeldas().get(actual.getX()).get(actual.getY() - 1);
//                }
//                break;
//        }
//        return vecina;
//    }
}
