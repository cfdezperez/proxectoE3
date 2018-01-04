/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazUsuario;

import excepciones.CivilizacionDestruidaException;
import excepciones.celda.CeldaEnemigaException;
import excepciones.celda.CeldaOcupadaException;
import excepciones.celda.FueraDeMapaException;
import excepciones.celda.NoTransitablebleException;
import excepciones.ParametroIncorrectoException;
import excepciones.celda.CeldaException;
import excepciones.celda.NoAlmacenableException;
import excepciones.edificio.EdificioException;
import excepciones.edificio.NoNecRepararException;
import excepciones.personaje.AtaqueExcepcion;
import excepciones.personaje.CapMovimientoException;
import excepciones.personaje.EstarEnGrupoException;
import excepciones.personaje.InsuficientesRecException;
import excepciones.personaje.NoAgrupableException;
import excepciones.personaje.PersonajeLlenoException;
import excepciones.personaje.SolAlmacenarException;
import excepciones.personaje.SolConstruirException;
import excepciones.personaje.SolRepararException;
import excepciones.personaje.SoldadoRecException;
import excepciones.recursos.RecursosException;

/**
 *
 * @author celia y maria
 */
public interface Comando {
    public String mover(String nPersonaje, String direccion) throws CapMovimientoException, EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public String mover(String nPersonaje, String direccion, int distancia) throws EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public String listar(String tipo) throws ParametroIncorrectoException;
    public String describir(String nombre) throws ParametroIncorrectoException;
    public String describir(String nombre, String Civilizacion) throws ParametroIncorrectoException;
    public String mirar(String coordenadasCelda) throws ParametroIncorrectoException, NumberFormatException, FueraDeMapaException;
    public String construir(String nPersonaje, String nEdificio, String direccion) throws EstarEnGrupoException, InsuficientesRecException, ParametroIncorrectoException, CeldaOcupadaException, FueraDeMapaException, CeldaEnemigaException, SolConstruirException;
    public String crear(String nEdificio, String tipoPersonaje) throws ParametroIncorrectoException, EdificioException, FueraDeMapaException, CeldaEnemigaException, NoTransitablebleException ;
    public String reparar(String nPersonaje, String direccion) throws EstarEnGrupoException, SolRepararException, FueraDeMapaException, 
            ParametroIncorrectoException, NoNecRepararException, InsuficientesRecException, EdificioException;
    public String recolectar(String nPersonaje, String direccion) throws 
            EstarEnGrupoException, PersonajeLlenoException, SoldadoRecException, RecursosException, FueraDeMapaException, 
            ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public String almacenar(String nPersonaje, String direccion) throws InsuficientesRecException, NoAlmacenableException, SolAlmacenarException, EstarEnGrupoException, NoTransitablebleException, FueraDeMapaException, ParametroIncorrectoException, CeldaEnemigaException, CeldaOcupadaException;
    public void cambiarCivilizacion(String nCivilizacion) throws ParametroIncorrectoException;
    public void imprimirCivilizacion() throws ParametroIncorrectoException;
    public void imprimirCivilizacion(String nCivilizacion) throws ParametroIncorrectoException;
    public String agrupar(String coordenadasCelda) throws ParametroIncorrectoException, NumberFormatException, FueraDeMapaException, 
            ParametroIncorrectoException, NoAgrupableException, CeldaEnemigaException, NoTransitablebleException;
    public String desligar(String nPersonaje) throws NoAgrupableException, EstarEnGrupoException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException;
    public String desagrupar(String nGrupo) throws NoAgrupableException, ParametroIncorrectoException, CeldaEnemigaException, NoTransitablebleException, FueraDeMapaException;    
    public String defender(String nPersonaje, String direccion) throws FueraDeMapaException, ParametroIncorrectoException, 
            CeldaEnemigaException, NoTransitablebleException, CeldaOcupadaException, EstarEnGrupoException, EdificioException, CeldaException;
    public String atacar(String nPersonaje, String direccion) throws FueraDeMapaException, ParametroIncorrectoException, 
            NoTransitablebleException, CeldaEnemigaException, AtaqueExcepcion, EstarEnGrupoException, CivilizacionDestruidaException;
    public void salir();
}
