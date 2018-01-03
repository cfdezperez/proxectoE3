/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos.edificio;

import elementos.Civilizacion;
import elementos.Edificio;
import elementos.Personaje;
import elementos.personaje.Paisano;
import excepciones.celda.CeldaOcupadaException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.edificio.EdificioException;
import interfazUsuario.Juego;
import vista.Celda;

/**
 *
 * @author celia y maria
 */
public class Ciudadela extends Edificio {

    private static int[] numeroCiudadelas = new int[Civilizacion.getNumDeCivilizaciones()];

    public Ciudadela() throws CeldaOcupadaException, ParametroIncorrectoException {
        this(10);
    }

    public Ciudadela(int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        this(1000, 50, 40, 25, 5000, capPer);
    }

    public Ciudadela(int salud, int CRM, int CRP, int CCC, int capAl, int capPer) throws CeldaOcupadaException, ParametroIncorrectoException {
        super(salud, CRM, CRP, CCC, capAl, Juego.TCIUDADELA);
        // Una ciudadela puede crear paisanos y almacednar
        setCrearPaisanos(true);
        setCapPersonajes(capPer);
        setCrearSoldados(false);
        setCapAlmacenar(true);
    }

    @Override
    public void inicializaNombre(Civilizacion civil) {
        numeroCiudadelas[civil.getIdCivilizacion()]++;
        setNombre("Ciudadela-" + numeroCiudadelas[civil.getIdCivilizacion()]);
    }

    /**
     * Crea un nuevo personaje (paisano) a la celda
     * @param c La celda
     * @param tipoPersonaje
     * @return
     * @throws ParametroIncorrectoException
     * @throws CeldaEnemigaException
     * @throws NoTransitablebleException
     * @throws FueraDeMapaException
     * @throws excepciones.edificio.EdificioException
     */
    @Override
    public Personaje creaPersonaje(Celda c, String tipoPersonaje) throws ParametroIncorrectoException, CeldaEnemigaException,
            NoTransitablebleException, FueraDeMapaException, EdificioException {
        if(!tipoPersonaje.equalsIgnoreCase("paisano")) {
            throw new EdificioException("Las ciudadelas solo pueden crear paisanos");
        }
        Personaje paisano = new Paisano();
        paisano.inicializaNombre(Juego.getCivilizacionActiva());
        Juego.getCivilizacionActiva().anhadePersonaje(paisano);
        c.anhadePersonaje(paisano);
        return paisano;
    }

}

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
